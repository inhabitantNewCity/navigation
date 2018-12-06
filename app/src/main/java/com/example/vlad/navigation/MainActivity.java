package com.example.vlad.navigation;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.vlad.navigation.ui.camera.Videoflow;
import com.example.vlad.navigation.ui.defineLocationSystems.dialogSystem.DialogSystemActivity;
import com.example.vlad.navigation.ui.defineLocationSystems.pointOnMapSystem.PointOnMapActivity;

public class MainActivity extends AppCompatActivity {
    BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onClickMap(View view){
        Intent intent = new Intent(this, PointOnMapActivity.class);
        //BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        //btAdapter.enable();
        startActivity(intent);
    }
    public void onClickDialog(View view){
        Intent intent = new Intent(this, DialogSystemActivity.class);
        startActivity(intent);
    }

    public void onClickVideoFlow(View view){
        Intent intent = new Intent(this, Videoflow.class);
        startActivity(intent);
    }
}
