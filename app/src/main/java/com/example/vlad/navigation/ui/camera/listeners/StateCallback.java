package com.example.vlad.navigation.ui.camera.listeners;

import android.app.Activity;
import android.hardware.camera2.CameraDevice;
import android.support.annotation.NonNull;

import com.example.vlad.navigation.ui.camera.VideoflowFragment;

public class StateCallback extends CameraDevice.StateCallback {

    private VideoflowFragment fragment;

    public StateCallback(VideoflowFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onOpened(@NonNull CameraDevice cameraDevice) {
        // This method is called when the camera is opened.  We start camera preview here.
        fragment.mCameraOpenCloseLock.release();
        fragment.mCameraDevice = cameraDevice;
        fragment.createCameraPreviewSession();
    }

    @Override
    public void onDisconnected(@NonNull CameraDevice cameraDevice) {
        fragment.mCameraOpenCloseLock.release();
        cameraDevice.close();
        fragment.mCameraDevice = null;
    }

    @Override
    public void onError(@NonNull CameraDevice cameraDevice, int error) {
        fragment.mCameraOpenCloseLock.release();
        cameraDevice.close();
        fragment.mCameraDevice = null;
        Activity activity = fragment.getActivity();
        if (null != activity) {
            activity.finish();
        }
    }
}
