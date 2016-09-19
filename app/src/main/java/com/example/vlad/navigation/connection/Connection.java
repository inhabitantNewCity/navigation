package com.example.vlad.navigation.connection;

import android.content.res.ObbInfo;
import android.hardware.SensorManager;

import com.example.vlad.navigation.utils.InputOutputStream;

import java.io.OutputStream;

/**
 * Created by Tmp on 13.02.2016.
 */
public interface Connection {
    InputOutputStream runReadDate() throws Exception;
    Device getDevice();

    void setParameters (Object manager);
}
