package com.example.vlad.navigation.connection.localSensors;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.lenghtStep.Parser;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by RoMka on 02.05.2016.
 */
public class ConnectionLocalSensors implements Connection {
    public Device device = new DeviceLocalSensors();
    private ConcurrentLinkedQueue<MessageSystem> queue = Parser.getQuery();
    private SensorManager manager;
    @Override
    public void runReadDate() throws Exception {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        ListenerLocalAccelerometer listener = new ListenerLocalAccelerometer(Parser.getQuery());

        manager.registerListener(listener,sensor,SensorManager.AXIS_MINUS_X);
    }
    @Override
    public Device getDevice() {
        return device;
    }

    @Override
    public void setParameters(Object manager) {
        this.manager = (SensorManager) manager;
    }
}
