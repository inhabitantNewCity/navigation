package com.example.vlad.navigation.connection;

import android.content.res.ObbInfo;
import android.hardware.SensorManager;

/**
 * Created by Tmp on 13.02.2016.
 */
public interface Connection {
    void runReadDate() throws Exception;
    Device getDevice();

    void setParameters (Object manager);
}
