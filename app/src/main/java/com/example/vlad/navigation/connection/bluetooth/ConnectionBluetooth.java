package com.example.vlad.navigation.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.renderscript.ScriptGroup;

import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.exeption.NotFindBluetoothModuleException;
import com.example.vlad.navigation.utils.InputOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Tmp on 13.02.2016.
 */
public class ConnectionBluetooth implements Connection {
    //private static final String REQUEST_ENABLE_BT = "new string" ;
    private BluetoothAdapter bluetooth;
    public ConnectionBluetooth(){
        bluetooth = BluetoothAdapter.getDefaultAdapter();
    }
    @Override
    public InputOutputStream runReadDate() throws Exception {
        InputStream input = null;
        OutputStream out = null;

        BluetoothSocket socket = null;
        if(bluetooth != null){
            if (bluetooth.isEnabled()) {

                //BluetoothDevice device = new BluetoothDeviceImpl(bluetooth);
                //socket = device.getSocket();
                byte[] pin = {1,2,3,4};
                Set<android.bluetooth.BluetoothDevice> pairedDevices = bluetooth.getBondedDevices();
                if(!pairedDevices.isEmpty()){
                    for(android.bluetooth.BluetoothDevice device : pairedDevices) {
                        String tmp = device.getName();
                        Method mPin = device.getClass().getMethod("setPin", byte[].class);
                        mPin.invoke(device, pin);
                        Method m = device.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        socket = (BluetoothSocket) m.invoke(device, 1);
                        //socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
                    }
                }
                socket.connect();

                    input = socket.getInputStream();
                    out = socket.getOutputStream();
                InputOutputStream stream = new InputOutputStream(socket.getInputStream(),socket.getOutputStream());
                    //now you can use out to send output via out.write
                    return stream;
            }
            else
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //TODO: start dialog window with question turn on bluetooth
            }
        }
        else
        {
            Exception bluetoothExeption = new NotFindBluetoothModuleException();
            throw bluetoothExeption;
        }
        return null;
    }

    @Override
    public Device getDevice() {
        BluetoothDevice device = new BluetoothDeviceImpl(bluetooth);
        return device;
    }

    @Override
    public void setParameters(Object manager) {

    }


}
