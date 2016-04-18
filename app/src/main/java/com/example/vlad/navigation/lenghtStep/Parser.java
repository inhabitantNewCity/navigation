package com.example.vlad.navigation.lenghtStep;

import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.ConnectionFactory;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.utils.messageSystem.MessageCounter;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class Parser implements Runnable {
    private Connection connection;
    private ConcurrentLinkedQueue<MessageSystem> queue;
    private Device device;
    public Parser(){
        connection = ConnectionFactory.getDefaultConnection();
        this.queue = CounterAlphaBetta.getQueue();
        device = connection.getDevice();
    }

    @Override
    public void run()  {
        try {
            InputStream out = (InputStream)connection.getConnection();
            ArrayList<Number> array = new ArrayList<>();
            //while(out.)
            parse(out);
            MessageSystem message = new MessageCounter(device.parse(array));

            queue.add(message);
        } catch (Exception e) {
            e.printStackTrace();
            //throw e;
        }
    }

    private ArrayList<Number> parse(InputStream out) {
        ArrayList<Number> array = new ArrayList<>();
        //TODO: parse out and add in ArrayList
        return array;
    }

}
