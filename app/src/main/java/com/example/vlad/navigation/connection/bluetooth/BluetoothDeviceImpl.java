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
    private static final String MAC_ADDRESS = "20:13:05:24:07:18" ;
    private static final UUID uuid = UUID.randomUUID();
    private static final int sizeArray = 12;
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

        map.put(nameFieldsDevice[0], parseData(readBytes, 4, 9)); // parsing accelerations
        map.put(nameFieldsDevice[1], parseData(readBytes, 11, 16)); // parsing data from gyroscope
        map.put(nameFieldsDevice[2], parseData(readBytes, 17, 22)); // parsing data from compose
        map.put(nameFieldsDevice[3], parseData(readBytes, 23, 27)); // parsing angles

        /*for(int i = 0, j = 0 ; i < readBytes.length; i+=2, j++) {
            buffer.put(readBytes[i]);
            buffer.put(readBytes[i+1]);
            readShorts[j] = buffer.getShort(0);
            buffer = ByteBuffer.allocate(2);
            forDebug += "      " + readShorts[j];
        }*/

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
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.nativeOrder());

        float[] result = new float[3];
        for(int i = start, j = 0; i < finish; i += 2, j++){
            buffer.put(readBytes[i]);
            buffer.put(readBytes[i+1]);
            buffer = ByteBuffer.allocate(2);
            result[j] = buffer.getShort(0);
        }
        return result;
    }
}
