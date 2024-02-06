package com.eugene.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements View.OnClickListener
{
    private final List<Point> points = new ArrayList<>();
    private CustomView customView;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customView = new CustomView(this, points);

        ViewGroup layout = findViewById(R.id.layout);
        restartButton = findViewById(R.id.restartButton);
        restartButton.setOnClickListener(this);

        customView.init();
        layout.addView(customView);
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.restartButton){
            customView.init();
        }
    }
}
