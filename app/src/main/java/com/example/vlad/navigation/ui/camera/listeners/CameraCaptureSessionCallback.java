package com.example.vlad.navigation.ui.camera.listeners;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.support.annotation.NonNull;

import com.example.vlad.navigation.ui.camera.VideoflowFragment;

public class CameraCaptureSessionCallback extends CameraCaptureSession.CaptureCallback {

    private VideoflowFragment fragment;

    public CameraCaptureSessionCallback(VideoflowFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onCaptureCompleted(
            @NonNull CameraCaptureSession session,
            @NonNull CaptureRequest request,
            @NonNull TotalCaptureResult result) {

        fragment.unlockFocus();
    }
}
