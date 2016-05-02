package com.example.vlad.navigation.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.vlad.navigation.exeption.ArraySizeException;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Tmp on 13.02.2016.
 */
public class BluetoothDeviceImpl implements com.example.vlad.navigation.connection.bluetooth.BluetoothDevice {
    private static final String MAC_ADDRESS = "20:13:05:24:07:18" ;
    private static final UUID uuid = UUID.randomUUID();
    private static final int sizeArray = 12;
    private static final byte[] pin = {1,2,3,4};
    private static final String[] nameFieldsDevice = {"A","G","M","pitch","roll","yaw"};
    private BluetoothDevice device;

    public BluetoothDeviceImpl(BluetoothAdapter adapter) {
        this.device = adapter.getRemoteDevice(MAC_ADDRESS);
        Log.w("Log", device.getName());
    }

    public static String getMacAddress() {
        return MAC_ADDRESS;
    }

    public static UUID getUUID() {
        return uuid;
    }

    @Override
    public HashMap<String, float[]> parse(MessageSystem data) throws Exception {
        HashMap<String,float[]> map = new HashMap<>();
        Integer array[][] = new Integer[6][3] ;
        //if(data.size() != sizeArray){
          //  throw new ArraySizeException();
        //}
        for(int i = 0 ; i < sizeArray - 3; i++){
            if(i < 3){
           //     array[0][i] = (Integer)data.get(i);
            }
            else {
                if (i < 6) {
            //        array[1][i] = (Integer)data.get(i);
                }
                else{
                    if(i < 9){
              //          array[2][i] = (Integer)data.get(i);
                    }

                }
            }
        }
        for(int j = 3, i = 9; i < sizeArray; i++, j++){
         //   array[j][i] = (Integer)data.get(i);
        }
        for(int i = 0 ; i < nameFieldsDevice.length ; i++) {
          //  map.put(nameFieldsDevice[i], (ArrayList) Arrays.asList(array[i]));
        }
        return map;
    }

    @Override
    public BluetoothSocket getSocket() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method mPin = device.getClass().getMethod("setPin", byte[].class);
        mPin.invoke(device,pin);
        Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
        return (BluetoothSocket) m.invoke(device, 1);
    }
}
