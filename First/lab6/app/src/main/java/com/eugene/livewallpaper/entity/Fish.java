package com.eugene.livewallpaper.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;

import com.eugene.livewallpaper.painter.Painter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import kotlin.Triple;

public abstract class Fish
{
    protected float x, y;
    protected float vx, vy;
    protected int radius;
    protected Paint paint = new Paint();

    protected Triple<Bitmap, Bitmap, Bitmap> bitmapTriple;

    Set<Integer> usedXValues = new HashSet<>();
    public void draw(Canvas canvas)
    {
        if (Painter.top >= canvas.getHeight() - 500)canvas.drawBitmap(bitmapTriple.component3(), x, y, paint);
        else if ((System.currentTimeMillis() / 100 % 10) % 2 == 0)canvas.drawBitmap(bitmapTriple.component1(), x, y, paint);
        else canvas.drawBitmap(bitmapTriple.component2(), x, y, paint);
    }

    public void moveY()
    {
        if (y + radius <= Painter.top + 20){
            y++;
        }
        //else if (y + radius >= canvas.getHeight()) y--;
    }


    public abstract void moveX(Canvas canvas);

}

