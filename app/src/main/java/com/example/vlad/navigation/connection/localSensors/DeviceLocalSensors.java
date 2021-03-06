package com.example.vlad.navigation.connection.localSensors;

import android.bluetooth.BluetoothSocket;

import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by RoMka on 02.05.2016.
 */
public class DeviceLocalSensors implements Device {

    @Override
    public HashMap<String, float[]> parse(MessageSystem data) throws Exception {
        float[] arrayData = (float[]) data.getMessage();
        HashMap<String,float[]> map = new HashMap<>();

        map.put("A",arrayData);
        return map;
    }

    @Override
    public BluetoothSocket getSocket() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return null;
    }
}
