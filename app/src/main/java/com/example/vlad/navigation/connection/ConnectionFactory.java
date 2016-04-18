package com.example.vlad.navigation.connection;

/**
 * Created by Tmp on 13.02.2016.
 */
public class ConnectionFactory {
    static Connection [] connections;
    public static Connection getDefaultConnection(){
        return connections[0];
    }

}
