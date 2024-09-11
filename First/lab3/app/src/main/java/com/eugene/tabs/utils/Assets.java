package com.eugene.tabs.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;

import com.eugene.tabs.R;

public class Assets {
    private static MediaPlayer thump, squish1, squish2, squish3, gameOver;


    public static void init(Context context) {
        thump = MediaPlayer.create(context, R.raw.thump);
        squish1 = MediaPlayer.create(context, R.raw.squish1);
        squish2 = MediaPlayer.create(context, R.raw.squish2);
        squish3 = MediaPlayer.create(context, R.raw.squish3);
        gameOver = MediaPlayer.create(context, R.raw.game_over);
    }



    public static Pair<Bitmap, Bitmap> loadTextures(Context context, Pair<Integer, Integer> id)
    {
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), id.getFirst());
        Bitmap scaleBitmap1 = Bitmap.createScaledBitmap(bitmap1, 200, 200, false);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), id.getSecond());
        Bitmap scaleBitmap2 = Bitmap.createScaledBitmap(bitmap2, 200, 200, false);

        return new Pair<>(scaleBitmap1, scaleBitmap2);
    }

    public static Bitmap loadTexture(Context context, int id) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), id), 200, 200, false);
    }

    public static void playSquishSound() {

            int r = (int) (Math.random() * 3);
            Log.i(String.valueOf(r), "SOUND");
            switch (r)
            {
                case 0:
                    squish1.start();
                    squish1.seekTo(0);
                    break;
                case 1:
                    squish2.start();
                    squish2.seekTo(0);
                    break;
                case 2:
                    squish3.start();
                    squish3.seekTo(0);
                    break;
            }

    }

    public static void playThumpSound() {
        thump.start();
    }

    public static void playGameOverSound() {
        gameOver.start();
    }
}