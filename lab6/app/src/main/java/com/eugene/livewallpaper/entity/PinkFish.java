package com.eugene.livewallpaper.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.eugene.livewallpaper.painter.Painter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import kotlin.Triple;

public class PinkFish extends Fish
{

    public PinkFish(int x, int y, Triple<Bitmap, Bitmap, Bitmap> bitmapTriple) {
        this.x = x;
        this.y = y;
        this.vx = 3;
        this.vy = 1;
        this.bitmapTriple = bitmapTriple;
    }



    @Override
    public void moveX(Canvas canvas) {
        if (x - radius < -200) {
            x = canvas.getWidth() + 10;
        }
        if (Painter.top < canvas.getHeight() - 500) x -= vx;
    }
}
