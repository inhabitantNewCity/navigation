package com.example.vlad.navigation.ui.defineLocationSystems.pointOnMapSystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vlad.navigation.R;
import com.example.vlad.navigation.ui.map.MapActivity;

public class PointOnMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_on_map);
        TextView text = (TextView) findViewById(R.id.text);
        //Connection connection = ConnectionFactory.getDefaultConnection();
    }

    public void onClick(View view){
        Intent intent = new Intent(this, MapActivity.class);
        intent = setNecessaryParameters(intent);
        //Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    private Intent setNecessaryParameters(Intent intent) {

        return intent;
    }

}
