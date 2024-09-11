package com.eugene.compas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{


    private ImageView imageView;

    private float[] mGravity = new float[3];

    private float[] mGeomagnetic = new float[3];

    private float azimuth = 0f;

    private float currectAzimuth = 0f;

    private SensorManager sensorManager;

    private TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.compass);
        textView = findViewById(R.id.degree);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    @Override
    public synchronized void onSensorChanged(SensorEvent sensorEvent) {
        float alpha = 0.97f;
        //synchronized (this){
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                for (int i = 0; i < 3; i++)  mGravity[i] = alpha * mGravity[i] + (1 - alpha) * sensorEvent.values[i];
            }
            if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                for (int i = 0; i < 3; i++) mGeomagnetic[i] = alpha * mGeomagnetic[i] + (1 - alpha) * sensorEvent.values[i];
            }

            float[] R = new float[9];
            float[] I = new float[9];
            if (SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic))
            {
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = (float)Math.toDegrees(orientation[0]);
                azimuth = (azimuth + 360) % 360;
                textView.setText(String.valueOf(azimuth).substring(0, 6));
                Animation animation = new RotateAnimation(-currectAzimuth,
                        -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                currectAzimuth = azimuth;
                animation.setDuration(500);
                animation.setRepeatCount(0);
                animation.setFillAfter(true);
                imageView.startAnimation(animation);
            }
        //}
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume(){
        super.onResume();
        //sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}