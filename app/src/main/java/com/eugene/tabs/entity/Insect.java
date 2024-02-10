package com.eugene.tabs.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.eugene.tabs.view.CustomView;
import com.eugene.tabs.R;
import com.eugene.tabs.utils.Assets;
import com.eugene.tabs.utils.Pair;

public class Insect extends Thread
{
    private float x;
    private float y;
    private float speedX, speedY;

    private final int id;

    private boolean isTouched;



    private final Pair<Bitmap, Bitmap> bitmapPair;

    public Insect(float x, float y, float speedX, float speedY, int id, Pair<Bitmap, Bitmap> bitmapPair)
    {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.id = id;
        this.isTouched = false;
        this.bitmapPair = bitmapPair;
    }

    public void move(int screenWidth, int screenHeight) {
        if (x <= 0 || x >= screenWidth) {
            speedX = -speedX;
        }
        if (y <= 0 || y >= screenHeight) {
            speedY = -speedY;
        }
        x += speedX;
        y += speedY;
    }

    public boolean isTouched(float touchX, float touchY)
    {
        int centerX = (int) (this.x + bitmapPair.getFirst().getWidth() / 2);
        int centerY = (int) (this.y + bitmapPair.getFirst().getHeight() / 2);
        double dis = Math.sqrt((touchX - centerX) * (touchX - centerX) + (touchY - centerY) * (touchY - centerY));
        return dis <= 100;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public boolean getTouched() {
        return isTouched;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void draw(Canvas canvas, Paint paint) {

        if (!isTouched)
        {
            if ((System.currentTimeMillis() / 100 % 10) % 2 == 0) {
                canvas.drawBitmap(bitmapPair.getFirst(), x, y, paint);
            } else {
                canvas.drawBitmap(bitmapPair.getSecond(), x, y, paint);
            }
        }
        else
        {
            if (this.id > 2)
            {
                canvas.drawBitmap(Assets.loadTexture(CustomView.context, R.drawable.spider_dead), x, y, paint);
            }
            else canvas.drawBitmap(Assets.loadTexture(CustomView.context, R.drawable.scorpion_dead), x, y, paint);

        }

    }
    @Override
    public void run(){
        while (!getTouched())
        {
            move(CustomView.screenWidth, CustomView.screenHeight);
            try
            {
                Thread.sleep(15);

            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        }
    }
}
