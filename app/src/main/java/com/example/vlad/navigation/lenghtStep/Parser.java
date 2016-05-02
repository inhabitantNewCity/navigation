package com.example.vlad.navigation.lenghtStep;

import android.hardware.SensorManager;

import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.ConnectionFactory;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.utils.messageSystem.MessageCounter;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class Parser implements Runnable {
    private Connection connection;
    private ConcurrentLinkedQueue<MessageSystem> queue;
    private static ConcurrentLinkedQueue<MessageSystem> myQuery = new ConcurrentLinkedQueue<>();
    private Device device;
    private SensorManager manager;

    public Parser(){
        connection = ConnectionFactory.getDefaultConnection();
        this.queue = CounterAlphaBetta.getQueue();
        device = connection.getDevice();
    }

    public Parser(SensorManager systemService) {
        connection = ConnectionFactory.getDefaultConnection();
        this.queue = CounterAlphaBetta.getQueue();
        device = connection.getDevice();
        manager = systemService;
    }

    @Override
    public void run()  {
        Connection connect = ConnectionFactory.getLocalConnection();
        connect.setParameters(manager);
        try {
            connect.runReadDate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        CounterAlphaBetta counterAlphaBetta = new CounterAlphaBetta();
        counterAlphaBetta.run();

        while (true){
            if(myQuery.isEmpty()){
                try {
                    wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                ArrayList<Number> array = new ArrayList<>();
                MessageSystem message = null;
                try {
                    message = new MessageCounter(connection.getDevice().parse(myQuery.poll()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                queue.add(message);
            }
        }
    }

    public static ConcurrentLinkedQueue<MessageSystem> getQuery(){
        return myQuery;
    }
}
