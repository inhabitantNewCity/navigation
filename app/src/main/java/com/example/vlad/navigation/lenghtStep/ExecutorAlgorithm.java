package com.example.vlad.navigation.lenghtStep;

import android.content.Context;
import android.hardware.SensorManager;

import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.ConnectionFactory;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.grafics.DrawerChanges;
import com.example.vlad.navigation.grafics.map.BindingMapAndData.ExecutorMapAndData;
import com.example.vlad.navigation.grafics.map.BuilderSpaceForDraw;
import com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.CounterAlphaBetta;
import com.example.vlad.navigation.utils.messageSystem.MessageCorrectionData;
import com.example.vlad.navigation.utils.messageSystem.MessageCounter;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class ExecutorAlgorithm  implements Runnable {
    private Connection connection;

    private ConcurrentLinkedQueue<MessageSystem> queue = ExecutorMapAndData.getQueue();
    private static ConcurrentLinkedQueue<MessageSystem> myQuery = new ConcurrentLinkedQueue<>();

    private Device device;
    private SensorManager manager;

    private Context context;
    private Object lock = new Object();

    public ExecutorAlgorithm(SensorManager systemService, Context context) {
        connection = ConnectionFactory.getDefaultConnection();
        //this.queue = CounterAlphaBetta.getQueue();
        device = connection.getDevice();
        manager = systemService;
        this.context = context;
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

        ExecutorMapAndData executorMapAndData = new ExecutorMapAndData();
        Thread treadMapAndDate = new Thread(executorMapAndData);
        treadMapAndDate.start();
        //executorMapAndData.run();

        //BuilderSpaceForDraw builderSpaceForDraw = new BuilderSpaceForDraw(context);
        //Thread threadDraw = new Thread(drawerChanges);
        //threadDraw.start();

        Counter counter = AlgorithmFactory.getAlgorithmOnIndex(0);
        while (true){
            if(myQuery.isEmpty()){
                try {
                    synchronized (lock) {
                        lock.wait(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                HashMap<String,float[]> map =(HashMap<String, float[]>)( (myQuery.poll()).getMessage());
                MessageCorrectionData message = new MessageCorrectionData(counter.run(map));
                queue.add(message);
            }
        }
    }

    public static ConcurrentLinkedQueue<MessageSystem> getQuery(){
        return myQuery;
    }
}
