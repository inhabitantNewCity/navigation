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

import java.io.OutputStream;
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
        device = connection.getDevice();
        manager = systemService;
        this.context = context;
    }
    @Override
    public void run()  {

        ExecutorMapAndData executorMapAndData = new ExecutorMapAndData();
        Thread treadMapAndDate = new Thread(executorMapAndData);
        treadMapAndDate.start();
        try {
            Counter counter = AlgorithmFactory.getAlgorithmOnIndex(0);
            OutputStream stream = connection.runReadDate();
            while (true){

                    HashMap<String,float[]> map = device.parse(stream);
                    MessageCorrectionData message = new MessageCorrectionData(counter.run(map));
                    queue.add(message);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentLinkedQueue<MessageSystem> getQuery(){
        return myQuery;
    }
}
