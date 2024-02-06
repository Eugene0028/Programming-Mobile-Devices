package com.eugene.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

public class CustomView extends View
{
    private List<Point> points;
    private Paint paint = new Paint();

    private final int screenWidth = 1000, screenHeight = 1700;

    @SuppressLint("ClickableViewAccessibility")
    public CustomView(Context context, List<Point> points) {
        super(context);
        this.points = points;
        setFocusable(true);
        setOnTouchListener((v, event) ->
        {
            for (Point point : points) {
                if (point.isTouched(event.getX(), event.getY())) {
                    points.remove(point);
                    break;
                }
            }
            return true;
        });
    }

    private void startThreadForPoint(Point point){
        new Thread(() -> {
            while (true) {
                // Обрабатываем движение точки
                point.move(screenWidth, screenHeight);
                // Перерисовываем экран
                postInvalidate();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        for (Point point : points) {
            point.draw(canvas, paint);
        }
    }

    public void init(){
        // Создаем 5 точек
        for (int i = 0; i < 5; i++) {
            float x = (float) (Math.random() * screenWidth);
            float y = (float) (Math.random() * screenHeight);
            float speedX = (float) (Math.random() * 10);
            float speedY = (float) (Math.random() * 10);
            Point point = new Point(x, y, speedX, speedY);
            points.add(point);
            // Запускаем поток для точки
            startThreadForPoint(point);
        }
    }
}