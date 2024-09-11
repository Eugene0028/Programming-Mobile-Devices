package com.eugene.livewallpaper.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.eugene.livewallpaper.painter.Painter;

import java.util.Random;

import kotlin.Triple;

public class BlueFish extends Fish
{

    public BlueFish(int x, int y, Triple<Bitmap, Bitmap, Bitmap> bitmapTriple)
    {
        this.x = x;
        this.y = y;
        this.vx = 3;
        this.vy = 1;
        this.bitmapTriple = bitmapTriple;
    }



    @Override
    public void moveX(Canvas canvas)
    {
        if (x + radius > canvas.getWidth() + 20) {
            x = -200;
        }
        if (Painter.top < canvas.getHeight() - 500) x += vx;
    }


}
