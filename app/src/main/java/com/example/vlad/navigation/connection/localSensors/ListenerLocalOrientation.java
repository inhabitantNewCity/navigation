package com.example.vlad.navigation.connection.localSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by RoMka on 03.05.2016.
 */
public class ListenerLocalOrientation implements SensorEventListener {

    BuilderMessageToCounter builder = BuilderMessageToCounter.getInstance();

    @Override
    public void onSensorChanged(SensorEvent event) {
        builder.setOrientationData(event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
