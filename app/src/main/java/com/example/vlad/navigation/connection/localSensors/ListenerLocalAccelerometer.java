package com.example.vlad.navigation.connection.localSensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.example.vlad.navigation.utils.messageSystem.MessageSystem;
import com.example.vlad.navigation.utils.messageSystem.MessageToParser;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by RoMka on 02.05.2016.
 */
public class ListenerLocalAccelerometer implements SensorEventListener {
    private ConcurrentLinkedQueue<MessageSystem> queue;

    public ListenerLocalAccelerometer(ConcurrentLinkedQueue<MessageSystem> query) {
        this.queue = query;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        MessageToParser message = new MessageToParser();
        message.setMessage(event.values);
        queue.add(message);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
