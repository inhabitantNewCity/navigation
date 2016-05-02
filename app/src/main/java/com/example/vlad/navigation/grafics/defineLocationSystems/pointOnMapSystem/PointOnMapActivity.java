package com.example.vlad.navigation.grafics.defineLocationSystems.pointOnMapSystem;

import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vlad.navigation.R;
import com.example.vlad.navigation.connection.Connection;
import com.example.vlad.navigation.connection.ConnectionFactory;
import com.example.vlad.navigation.lenghtStep.Parser;

import java.io.InputStream;

public class PointOnMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_on_map);
        TextView text = (TextView) findViewById(R.id.text);
        Connection connection = ConnectionFactory.getDefaultConnection();
        try {
            Parser parser = new Parser(((SensorManager) getSystemService(SENSOR_SERVICE)));
            parser.run();
            //text = new TextView(getBas
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
