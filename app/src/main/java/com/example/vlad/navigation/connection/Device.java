package com.example.vlad.navigation.connection;

import android.bluetooth.BluetoothSocket;

import com.example.vlad.navigation.utils.InputOutputStream;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tmp on 14.02.2016.
 */
public interface Device {
    HashMap<String,float[]> parse(InputOutputStream stream) throws Exception;
    BluetoothSocket getSocket() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
