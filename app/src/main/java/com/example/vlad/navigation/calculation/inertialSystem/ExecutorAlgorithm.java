package com.example.vlad.navigation.calculation.inertialSystem;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.vlad.navigation.calculation.coleration.MapChecker;
import com.example.vlad.navigation.calculation.coleration.ProbabilityMapChecker;
import com.example.vlad.navigation.utils.messageSystem.ResultMapCheck;
import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.ConnectionFactory;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.database.DataAccessService;
import com.example.vlad.navigation.database.DataAccessServiceStub;
import com.example.vlad.navigation.utils.InputOutputStream;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class ExecutorAlgorithm  implements Runnable {
    private Connection connection;

    private static ConcurrentLinkedQueue<MessageSystem> myQuery = new ConcurrentLinkedQueue<>();

    private Device device;
    private SensorManager manager;
    private MapChecker mapChecker;
    private DataAccessService dataAccessService = new DataAccessServiceStub();
    private final static String TAG = "PNS";

    private Context context;

    public ExecutorAlgorithm(SensorManager systemService, Context context) {
        connection = ConnectionFactory.getDefaultConnection();
        device = connection.getDevice();
        manager = systemService;
        this.context = context;
        mapChecker = new ProbabilityMapChecker(dataAccessService.getMap(), dataAccessService.getWay());
    }
    @Override
    public void run()  {

        try {
            Counter counter = AlgorithmFactory.getAlgorithmOnIndex(2);
            InputOutputStream stream = connection.runReadDate();
            while (true){
                HashMap<String,float[]> map = device.parse(stream);
                Log.d(TAG, map.toString());
                Vector sendVector = counter.run(map);
                ResultMapCheck result = mapChecker.checkOnMap((float) sendVector.length, sendVector.z);
                myQuery.add(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConcurrentLinkedQueue<MessageSystem> getQuery(){
        return myQuery;
    }
}
