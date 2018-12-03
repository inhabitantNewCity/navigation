package com.example.vlad.navigation.ui.map;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.vlad.navigation.ui.DrawerChanges;

/**
 * Created by RoMka on 03.05.2016.
 */
public class BuilderSpaceForDraw extends SurfaceView implements SurfaceHolder.Callback {

    public BuilderSpaceForDraw(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        DrawerChanges drawerChanges = new DrawerChanges(getHolder());
        new Thread(drawerChanges).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
