package com.example.acarrell.w4_as6;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.graphics.drawable.PathInterpolatorCompat.EPSILON;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.cos;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String TAG = "MainActivity_TAG";
    private static final float NS2S = 1.0f / 1000000000.0f;
    private float timestamp;

    private SensorManager sensorManager;
    private Sensor mGyroscope;

    @BindView(R.id.tvAxisX) TextView tvResultX;
    @BindView(R.id.tvAxisY) TextView tvResultY;
    @BindView(R.id.tvAxisZ) TextView tvResultZ;

    public MainActivity() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mGyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // This time step's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
            // Axis of the rotation sample, not normalized yet.
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            // Calculate the angular speed of the sample
            float omegaMagnitude = (float)sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            // Normalize the rotation vector if it's big enough to get the axis
//            if (omegaMagnitude > EPSILON) {
//                axisX /= omegaMagnitude;
//                axisY /= omegaMagnitude;
//                axisZ /= omegaMagnitude;
//            }

            tvResultX.setText(Integer.toString(Math.round(axisX)));
            tvResultY.setText(Integer.toString(Math.round(axisY)));
            tvResultZ.setText(Integer.toString(Math.round(axisZ)));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
