package com.eugene.livewallpaper.painter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.BatteryManager;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.eugene.livewallpaper.R;
import com.eugene.livewallpaper.entity.BlueFish;
import com.eugene.livewallpaper.entity.Fish;
import com.eugene.livewallpaper.entity.PinkFish;
import com.eugene.livewallpaper.utils.Assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import kotlin.Triple;

public class Painter {
    Paint paint = new Paint();
    private static int batteryLevel;

    public static float top;
    private Context context;

    private volatile List<Fish> fishes = new ArrayList<>();
    private Canvas canvas;
    int screenHeight;

    public Painter(Context context)
    {
        this.context = context;

        updateBatteryLevel();

        paint.setColor(Color.BLACK);
        paint.setTextSize(200);


        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        this.screenHeight = displayMetrics.heightPixels;

    }


    public void updateBatteryLevel() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                    if (level != -1 && scale != -1) {
                        Painter.batteryLevel = (int) ((level / (float) scale) * 100);
                    }
                }
            }
        };
        context.registerReceiver(batteryReceiver, filter);
    }

    public void drawAquarium()
    {
        canvas.drawColor(Color.BLACK);
        if (Painter.batteryLevel > 0 && top < canvas.getHeight()) {
            paint.setColor(Color.BLUE);
            //Painter.top = screenHeight - (Painter.batteryLevel / 100f) * screenHeight;
            Painter.top++;
            canvas.drawRect(0, top, canvas.getWidth(), canvas.getHeight(), paint);
        }
        paint.setColor(Color.WHITE);
        if (top != 0) {
            canvas.drawText("Size: " + top, (float) canvas.getWidth() / 2 - 1000, 600, paint);
        }
        canvas.drawText(Painter.batteryLevel + "%", (float) canvas.getWidth() / 2, 800, paint);

    }

    public List<Fish> getFishes() {
        return fishes;
    }

    public void initFishes()
    {

        Random random = new Random();
        for (int i = 0; i <= 5; i++) {
            int z = random.nextInt(this.screenHeight - (int) top + 1);
            addFish(new PinkFish(i * 350, (int) (z + top), Assets.loadThreeTextures(context.getApplicationContext(), new Triple<>(R.drawable.pink_fish, R.drawable.pink_fish2, R.drawable.die_pink_fish))));
            addFish(new BlueFish(i * 200, (int) (z + top), Assets.loadThreeTextures(context.getApplicationContext(), new Triple<>(R.drawable.blue_fish, R.drawable.blue_fish2, R.drawable.die_blue_fish))));
        }
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void draw() {
        drawAquarium();
        drawFishes();
    }



    public void addFish(Fish fish) {
        fishes.add(fish);
    }

    public void drawFishes()
    {
        for (Fish fish : fishes)
        {
            fish.moveX(canvas);
            fish.moveY();
            fish.draw(canvas);
        }
    }
}