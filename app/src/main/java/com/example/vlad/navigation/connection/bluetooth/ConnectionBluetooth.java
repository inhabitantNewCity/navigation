package com.example.vlad.navigation.connection.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.Device;
import com.example.vlad.navigation.exeption.NotFindBluetoothModuleException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
    public InputStream getConnection() throws Exception {
        InputStream input = null;
        BluetoothSocket socket = null;
        if(bluetooth != null){
            if (bluetooth.isEnabled()) {

                BluetoothDevice device = new BluetoothDeviceImpl(bluetooth);

                    socket.connect();
                    input = socket.getInputStream();
                    //now you can use out to send output via out.write
            }
            else
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //TODO: start diologigal window with question turn on bluetooth
            }
        }
        else
        {
            Exception bluetoothExeption = new NotFindBluetoothModuleException();
            throw bluetoothExeption;
        }
        return input;
    }

    @Override
    public Device getDevice() {
        BluetoothDevice device = new BluetoothDeviceImpl(bluetooth);
        return device;
    }


}
