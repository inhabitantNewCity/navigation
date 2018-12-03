package com.example.vlad.navigation.ui.map;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vlad.navigation.calculation.inertialSystem.ExecutorAlgorithm;

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
