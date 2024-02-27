package com.eugene.livewallpaper.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.Pair;

import kotlin.Triple;


public class Assets {

    public static Pair<Bitmap, Bitmap> loadTwoTextures(Context context, Pair<Integer, Integer> id)
    {
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), id.first);
        Bitmap scaleBitmap1 = Bitmap.createScaledBitmap(bitmap1, 200, 200, false);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), id.second);
        Bitmap scaleBitmap2 = Bitmap.createScaledBitmap(bitmap2, 200, 200, false);

        return new Pair<>(scaleBitmap1, scaleBitmap2);
    }

    public static Triple<Bitmap, Bitmap, Bitmap> loadThreeTextures(Context context, Triple<Integer, Integer, Integer> id){
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), id.component1());
        Bitmap scaleBitmap1 = Bitmap.createScaledBitmap(bitmap1, 200, 200, false);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), id.component2());
        Bitmap scaleBitmap2 = Bitmap.createScaledBitmap(bitmap2, 200, 200, false);

        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), id.component3());
        Bitmap scaleBitmap3 = Bitmap.createScaledBitmap(bitmap3, 200, 200, false);

        return new Triple<>(scaleBitmap1, scaleBitmap2, scaleBitmap3);
    }

    public static Bitmap loadTexture(Context context, int id) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), id), 200, 200, false);
    }


}
