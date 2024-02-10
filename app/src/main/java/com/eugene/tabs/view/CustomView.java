package com.eugene.tabs.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.eugene.tabs.R;
import com.eugene.tabs.activity.MainActivity;
import com.eugene.tabs.activity.MainGame;
import com.eugene.tabs.entity.Insect;
import com.eugene.tabs.utils.Assets;
import com.eugene.tabs.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@SuppressLint("ViewConstructor")
public class CustomView extends View
{
    private final List<Insect> insects = new ArrayList<>();
    private final Paint paint = new Paint();

    private List<ImageView> healthImages;

    public void setHealthImages(List<ImageView> healthImages) {
        this.healthImages = healthImages;
    }


    public static Context context;




    public static final int screenWidth = 900;
    public static final int screenHeight = 1300;



    Pair<Integer, Integer> spiderPair =  new Pair<>(R.drawable.spider1, R.drawable.spider2);
    Pair<Integer, Integer> scorpPair =  new Pair<>(R.drawable.scorpion1, R.drawable.scorpion2);

    Handler handler = new Handler();

    @SuppressLint("ClickableViewAccessibility")
    public CustomView(MainGame mainGame)
    {
        super(mainGame.getApplicationContext());
        context = mainGame.getApplicationContext();
        this.init();
        AtomicInteger cnt = new AtomicInteger();
        Assets.init(context);
        setFocusable(true);
        setOnTouchListener((v, event) ->
        {
            int actionMask = event.getAction();
            if (MotionEvent.ACTION_DOWN == actionMask) {
                for (Insect insect : insects) {
                    if (insect.isTouched(event.getX(), event.getY()))
                    {
                        handleInsectTouch(insect, mainGame, cnt.incrementAndGet());
                        return true;
                    }
                }
                handleMissedInsect(mainGame);
            }
            return true;
        });
    }


    private void handleInsectTouch(Insect insect, MainGame mainGame, int cnt)
    {

        Log.i(insect.getName(), "KILLED");
        insect.setTouched(true);
        Assets.playSquishSound();
        handler.postDelayed(() ->
        {
            insects.remove(insect);
            insect.interrupt();
            if (cnt > mainGame.getScore())MainActivity.highScoreText.setText(String.valueOf(cnt));
        }, 1000);
    }

    private void handleMissedInsect(MainGame mainGame) {
        if (!healthImages.isEmpty()) {
            healthImages.get(0).setImageResource(0);
            healthImages.remove(0);
            Assets.playThumpSound();
        } else {
            for (Insect insect : insects) {
                insect.setX(9000);
                insect.setY(9000);
            }
            Assets.playGameOverSound();
            mainGame.finish();
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas)
    {
        super.onDraw(canvas);
        postInvalidate();
        for (Insect insect : insects) {
            insect.draw(canvas, paint);

        }
    }

    public void init()
    {
            for (int i = 0; i < 6; i++) {
            float x = (float) (Math.random() * screenWidth);
            float y = (float) (Math.random() * screenHeight);
            float speedX = (float) (Math.random() * 10);
            float speedY = (float) (Math.random() * 10);
            Insect insect = new Insect(x, y, speedX, speedY, i, Assets.loadTextures(context, i < 3 ? scorpPair : spiderPair));
            insects.add(insect);
            insect.start();
        }
    }
}

