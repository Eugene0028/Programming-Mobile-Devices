package com.eugene.tabs.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.eugene.tabs.view.CustomView;
import com.eugene.tabs.R;

import java.util.ArrayList;
import java.util.List;




public class MainGame extends Activity implements View.OnClickListener
{

    private CustomView customView;

    private int Score;

    public int incrementScore(){
        return ++Score;
    }

    public int getScore() {
        return Score;
    }

    private final List<ImageView> healthViews = new ArrayList<>();

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadHealth();
        customView = new CustomView(this);

        customView.setHealthImages(healthViews);
        ViewGroup layout = findViewById(R.id.layout);
        Button restartButton = findViewById(R.id.addMoreBugsButton);
        restartButton.setOnClickListener(this);

        layout.addView(customView);


    }
    private void loadHealth()
    {
        healthViews.add(findViewById(R.id.fifth));
        healthViews.add(findViewById(R.id.fourth));
        healthViews.add(findViewById(R.id.third));
        healthViews.add(findViewById(R.id.second));
        healthViews.add(findViewById(R.id.first));
        for (ImageView image : healthViews) {
            image.setImageResource(R.drawable.life);
        }
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == R.id.addMoreBugsButton){
            customView.init();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }
}
