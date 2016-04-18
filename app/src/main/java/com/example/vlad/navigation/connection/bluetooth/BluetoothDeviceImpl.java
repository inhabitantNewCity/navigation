package com.example.vlad.navigation.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.example.vlad.navigation.exeption.ArraySizeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tmp on 13.02.2016.
 */
public class BluetoothDeviceImpl implements com.example.vlad.navigation.connection.bluetooth.BluetoothDevice {
    private static final String MAC_ADDRESS = "00:15:83:3D:0A:57" ;
    private static final UUID uuid = UUID.randomUUID();
    private static final int sizeArray = 12;
    private static final String[] nameFieldsDevice = {"A","G","M","pitch","roll","yaw"};
    private BluetoothDevice device;

    public BluetoothDeviceImpl(BluetoothAdapter adapter) {
        this.device = adapter.getRemoteDevice(MAC_ADDRESS);
    }

    public static String getMacAddress() {
        return MAC_ADDRESS;
    }

    public static UUID getUUID() {
        return uuid;
    }

    @Override
    public HashMap<String, ArrayList<Integer>> parse(ArrayList<Number> data) throws Exception {
        HashMap<String,ArrayList<Integer>> map = new HashMap<>();
        Integer array[][] = new Integer[6][3] ;
        if(data.size() != sizeArray){
            throw new ArraySizeException();
        }
        for(int i = 0 ; i < sizeArray - 3; i++){
            if(i < 3){
                array[0][i] = (Integer)data.get(i);
            }
            else {
                if (i < 6) {
                    array[1][i] = (Integer)data.get(i);
                }
                else{
                    if(i < 9){
                        array[2][i] = (Integer)data.get(i);
                    }

                }
            }
        }
        for(int j = 3, i = 9; i < sizeArray; i++, j++){
            array[j][i] = (Integer)data.get(i);
        }
        for(int i = 0 ; i < nameFieldsDevice.length ; i++) {
            map.put(nameFieldsDevice[i], (ArrayList) Arrays.asList(array[i]));
        }
        return map;
    }

    @Override
    public BluetoothSocket getSocket() throws IOException {
        return device.createRfcommSocketToServiceRecord(uuid);
        //BluetoothSocket socket = device.
    }
}
