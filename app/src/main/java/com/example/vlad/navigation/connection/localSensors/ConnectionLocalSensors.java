package com.example.vlad.navigation.connection.localSensors;

import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.calculation.inertialSystem.ExecutorAlgorithm;
import com.example.vlad.navigation.utils.InputOutputStream;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by RoMka on 02.05.2016.
 */
public class ConnectionLocalSensors implements Connection {
    public Device device = new DeviceLocalSensors();
    private ConcurrentLinkedQueue<MessageSystem> queue = ExecutorAlgorithm.getQuery();
    private SensorManager manager;
    @Override
    public InputOutputStream runReadDate() throws Exception {
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensor0 = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        Sensor sensor1 = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor sensor2 = manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor sensor3 = manager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        //Sensor sensor1 = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        List<Sensor> sensorList = manager.getSensorList(Sensor.TYPE_ALL);
        String tmp = "";
        for(int i =0 ; i < sensorList.size() ; i++){
            tmp += sensorList.get(i).getName();
        }
        ListenerLocalAccelerometer listenerAccelerometer = new ListenerLocalAccelerometer();
        ListenerLocalOrientation listenerLocalOrientation = new ListenerLocalOrientation();

        manager.registerListener(listenerLocalOrientation,sensor0,SensorManager.AXIS_MINUS_X);
        manager.registerListener(listenerAccelerometer,sensor,SensorManager.AXIS_MINUS_X);
        return null;
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
