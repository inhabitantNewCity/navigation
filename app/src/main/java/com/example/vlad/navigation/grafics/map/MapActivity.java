package com.example.vlad.navigation.grafics.map;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vlad.navigation.R;
import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.ConnectionFactory;
import com.example.vlad.navigation.lenghtStep.ExecutorAlgorithm;

import java.io.InputStream;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BuilderSpaceForDraw(this));
        try {
            ExecutorAlgorithm parser = new ExecutorAlgorithm(((SensorManager) getSystemService(SENSOR_SERVICE)), this);
            (new Thread(parser)).start();
            //text = new TextView(getBas
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
