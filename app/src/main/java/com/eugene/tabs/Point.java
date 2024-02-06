package com.eugene.tabs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Point {
    private float x, y;
    private float speedX, speedY;
    private boolean isTouched;

    public Point(float x, float y, float speedX, float speedY) {
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.isTouched = false;
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

    public boolean isTouched(float touchX, float touchY) {
        return Math.sqrt(Math.pow((x - touchX), 2) + Math.pow((y - touchY), 2)) <= 50;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }

    public boolean getTouched() {
        return isTouched;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (!isTouched) {
            paint.setColor(Color.RED);
            canvas.drawCircle(x, y, 40, paint);
        }
    }
}