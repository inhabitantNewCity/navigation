package com.example.vlad.navigation.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.example.vlad.navigation.utils.InputOutputStream;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.UUID;

import static  com.example.vlad.navigation.utils.Constants.*;
/**
 * Created by Tmp on 13.02.2016.
 */
public class BluetoothDeviceImpl implements com.example.vlad.navigation.connection.bluetooth.BluetoothDevice {
    private static final String MAC_ADDRESS = "98:D3:31:70:2B:5B" ;
    private static final UUID uuid = UUID.randomUUID();
    private static final byte[] pin = {1,2,3,4};

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
    public HashMap<String, float[]> parse(InputOutputStream stream) throws Exception {
        HashMap<String,float[]> map = new HashMap<>();

        byte[] readBytes = new byte[28];
        byte inputtedByte = '0';

        stream.write(inputtedByte);
        stream.read(readBytes);

        short lastAngle = (short)((readBytes[27] << 8) + readBytes[26]);

        map.put(nameFieldsDevice[0], parseAccDate(readBytes)); // parsing accelerations
        map.put(nameFieldsDevice[1], parseData(readBytes, 11, 16)); // parsing data from gyroscope
        map.put(nameFieldsDevice[2], parseData(readBytes, 17, 22)); // parsing data from compose
        map.put(nameFieldsDevice[3], parseAngles(readBytes)); // parsing angles


        return map;
    }

    @Override
    public BluetoothSocket getSocket() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method mPin = device.getClass().getMethod("setPin", byte[].class);
        mPin.invoke(device, pin);
        Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
        return (BluetoothSocket) m.invoke(device, 1);
    }

    private float[] parseData(byte[] readBytes, int start, int finish){
        float[] result = new float[3];
        for(int i = start, j = 0; i < finish; i += 2, j++){
            result[j] = (short)((readBytes[i+1] << 8) + readBytes[i]);
        }
        return result;
    }

    private float[] parseAccDate(byte[] readBytes)
    {
        float[] result = new float[3];
        for(int i = 10, j = 0 ; i < 15; i++){
            result[j] = ((short) ((readBytes[i+1] << 8) + readBytes[i])) / 256 * 9.8f;
        }
        return result;
    }

    private float[] parseAngles(byte[] readBytes){

        float[] result = new float[3];
        for(int i = 22, j = 0 ; i < 27; i++){
            result[j] = ((short) ((readBytes[i+1] << 8) + readBytes[i])) / 100;
        }
        return result;
    }
}
