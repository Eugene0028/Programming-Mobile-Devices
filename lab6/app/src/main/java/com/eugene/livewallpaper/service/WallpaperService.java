package com.eugene.livewallpaper.service;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import android.os.*;

import com.eugene.livewallpaper.R;
import com.eugene.livewallpaper.entity.BlueFish;
import com.eugene.livewallpaper.entity.PinkFish;
import com.eugene.livewallpaper.painter.Painter;
import com.eugene.livewallpaper.utils.Assets;

import java.util.Random;

public class WallpaperService extends android.service.wallpaper.WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine();
    }

    class WallpaperEngine extends Engine {
        SurfaceHolder surfaceHolder;
        Handler handler;
        Painter painter = new Painter(getApplicationContext());


        Runnable redrawRunnable = new Runnable() {
            @Override
            public void run() {
                draw();
                handler.postDelayed(this, 10);
                if (painter.getFishes().size() == 0) painter.initFishes();
            }
        };

        private void draw()
        {
            if (surfaceHolder != null)
            {
                try
                {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    painter.setCanvas(canvas);
                    painter.draw();
                    surfaceHolder.unlockCanvasAndPost(canvas);
                } catch (Exception e) {
                    Log.e("WALLPAPER", e.getMessage(), e);
                }
            }
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.surfaceHolder = surfaceHolder;
            handler = new Handler();
            handler.post(redrawRunnable);

        }


        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(redrawRunnable);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            handler.removeCallbacks(redrawRunnable);
            if (visible) handler.post(redrawRunnable);
        }

        @Override
        public void onSurfaceRedrawNeeded(SurfaceHolder holder) {
            super.onSurfaceRedrawNeeded(holder);
            surfaceHolder = holder;
            draw();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            surfaceHolder = holder;
            handler.removeCallbacks(redrawRunnable);
        }


    }

}