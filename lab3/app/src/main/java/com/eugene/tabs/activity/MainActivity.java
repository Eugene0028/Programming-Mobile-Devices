package com.eugene.tabs.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eugene.tabs.R;

public class MainActivity extends Activity implements View.OnClickListener
{

    Button startButton;
    LinearLayout highScoreLayout;
    @SuppressLint("StaticFieldLeak")
    public static TextView highScoreText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        startButton = findViewById(R.id.start_button);

        highScoreLayout = findViewById(R.id.high_score_layout);
        highScoreText = findViewById(R.id.high_score_text);

        startButton.setOnClickListener(this);
        highScoreLayout.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.start_button == id)
        {
            Intent intent = new Intent(this, MainGame.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
