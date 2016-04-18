package com.example.vlad.navigation.connection;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tmp on 14.02.2016.
 */
public interface Device {
    HashMap<String,ArrayList<Integer>> parse(ArrayList<Number> data) throws Exception;
    BluetoothSocket getSocket() throws IOException;
}
