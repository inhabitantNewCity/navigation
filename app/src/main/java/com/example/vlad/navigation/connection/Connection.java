package com.example.vlad.navigation.connection;

/**
 * Created by Tmp on 13.02.2016.
 */
public interface Connection {
    Object getConnection() throws Exception;
    Device getDevice();
}
