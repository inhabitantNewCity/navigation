package com.example.vlad.navigation.connection.localSensors;

import com.example.vlad.navigation.calculation.inertialSystem.ExecutorAlgorithm;
import com.example.vlad.navigation.utils.messageSystem.MessageCounter;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by RoMka on 03.05.2016.
 */
public class BuilderMessageToCounter {

    private static BuilderMessageToCounter builder = new BuilderMessageToCounter();

    private ConcurrentLinkedQueue<MessageSystem> queue = ExecutorAlgorithm.getQuery();

    private float[] accelerometerData = {0,0,0};
    private float[] orientationData = {0,0,0};

    private int countSetParameters = 0;

    private BuilderMessageToCounter(){};

    public static BuilderMessageToCounter getInstance(){
        return builder;
    }

    public void setAccelerometerData(float[] data){
        accelerometerData = data;
        countSetParameters++;
        if(countSetParameters == 2){
            HashMap<String,float[]> map = getSandedMap();
            MessageCounter message = new MessageCounter(map);
            queue.add(message);
            countSetParameters = 0;
        }

    }

    public void setOrientationData(float[] data){
    orientationData = data;
    countSetParameters++;
    if(countSetParameters == 2){
        HashMap<String,float[]> map = getSandedMap();
        MessageCounter message = new MessageCounter(map);
        //message.setMessage(sandedArray);
        queue.add(message);
        countSetParameters = 0;
    }
}
    private HashMap<String,float[]> getSandedMap() {
        HashMap<String,float[]> tmp = new HashMap<>();
        tmp.put("Acc",accelerometerData);
        tmp.put("Angl", orientationData);
        return tmp;
    }
}
