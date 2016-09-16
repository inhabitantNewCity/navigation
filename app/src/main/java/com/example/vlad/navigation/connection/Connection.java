package com.example.vlad.navigation.connection;

import android.content.res.ObbInfo;
import android.hardware.SensorManager;

import java.io.OutputStream;

/**
 * Created by Tmp on 13.02.2016.
 */
public interface Connection {
    OutputStream runReadDate() throws Exception;
    Device getDevice();

    void setParameters (Object manager);
}
