package com.example.vlad.navigation.connection;

import com.example.vlad.navigation.connection.bluetooth.ConnectionBluetooth;
import com.example.vlad.navigation.connection.localSensors.ConnectionLocalSensors;

/**
 * Created by Tmp on 13.02.2016.
 */
public class ConnectionFactory {
    static Connection [] connections =  {
        new ConnectionBluetooth(), new ConnectionLocalSensors()
    };
    public static Connection getDefaultConnection(){
        return connections[0];
    }
    public static Connection getLocalConnection(){return connections[1];}

}
