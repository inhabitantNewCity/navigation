package com.example.vlad.navigation.ui.camera.listeners;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.support.annotation.NonNull;

import com.example.vlad.navigation.ui.camera.VideoflowFragment;

import static com.example.vlad.navigation.ui.camera.CameraConfigurationConstants.STATE_PICTURE_TAKEN;
import static com.example.vlad.navigation.ui.camera.CameraConfigurationConstants.STATE_PREVIEW;
import static com.example.vlad.navigation.ui.camera.CameraConfigurationConstants.STATE_WAITING_LOCK;
import static com.example.vlad.navigation.ui.camera.CameraConfigurationConstants.STATE_WAITING_NON_PRECAPTURE;
import static com.example.vlad.navigation.ui.camera.CameraConfigurationConstants.STATE_WAITING_PRECAPTURE;

public class CaptureCallback extends CameraCaptureSession.CaptureCallback{

    private VideoflowFragment fragment;

    public CaptureCallback(VideoflowFragment fragment) {
        this.fragment = fragment;
    }

    private void process(CaptureResult result) {
        switch (fragment.mState) {
            case STATE_PREVIEW: break;
            case STATE_WAITING_LOCK: {
                Integer afState = result.get(CaptureResult.CONTROL_AF_STATE);
                if (afState == null) {
                    fragment.captureStillPicture();
                } else if (CaptureResult.CONTROL_AF_STATE_FOCUSED_LOCKED == afState ||
                        CaptureResult.CONTROL_AF_STATE_NOT_FOCUSED_LOCKED == afState) {
                    Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                    if (aeState == null || aeState == CaptureResult.CONTROL_AE_STATE_CONVERGED) {
                        fragment.mState = STATE_PICTURE_TAKEN;
                        fragment.captureStillPicture();
                    } else {
                        fragment.runPrecaptureSequence();
                    }
                }
                break;
            }
            case STATE_WAITING_PRECAPTURE: {
                Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                if (aeState == null ||
                        aeState == CaptureResult.CONTROL_AE_STATE_PRECAPTURE ||
                        aeState == CaptureRequest.CONTROL_AE_STATE_FLASH_REQUIRED) {
                    fragment.mState = STATE_WAITING_NON_PRECAPTURE;
                }
                break;
            }
            case STATE_WAITING_NON_PRECAPTURE: {
                Integer aeState = result.get(CaptureResult.CONTROL_AE_STATE);
                if (aeState == null || aeState != CaptureResult.CONTROL_AE_STATE_PRECAPTURE) {
                    fragment.mState = STATE_PICTURE_TAKEN;
                    fragment.captureStillPicture();
                }
                break;
            }
        }
    }

    @Override
    public void onCaptureProgressed(
            @NonNull CameraCaptureSession session,
            @NonNull CaptureRequest request,
            @NonNull CaptureResult partialResult) {
        process(partialResult);
    }

    @Override
    public void onCaptureCompleted(
            @NonNull CameraCaptureSession session,
            @NonNull CaptureRequest request,
            @NonNull TotalCaptureResult result) {
        process(result);
    }
}