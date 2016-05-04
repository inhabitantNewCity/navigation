package com.example.vlad.navigation.connection.localSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by RoMka on 02.05.2016.
 */
public class ListenerLocalAccelerometer implements SensorEventListener {

    BuilderMessageToCounter builder = BuilderMessageToCounter.getInstance();

    private final float alpha = 0.8f;
    private float[] gravity = {0,0,0};

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] linerAccelerometerDate = new float[3];

        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

        linerAccelerometerDate[0] = event.values[0] - gravity[0];
        linerAccelerometerDate[1] = event.values[1] - gravity[1];
        linerAccelerometerDate[2] = event.values[2] - gravity[2];

        builder.setAccelerometerData(linerAccelerometerDate);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
