package com.example.vlad.navigation.connection.localSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by RoMka on 03.05.2016.
 */
public class ListenerLocalOrientation implements SensorEventListener {

    BuilderMessageToCounter builder = BuilderMessageToCounter.getInstance();

    private int deltaTime = 10;
    private long lastTime = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        long currentTime = System.currentTimeMillis();
        if(deltaTime < currentTime - lastTime) {
            builder.setOrientationData(event.values);
            lastTime = currentTime;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
