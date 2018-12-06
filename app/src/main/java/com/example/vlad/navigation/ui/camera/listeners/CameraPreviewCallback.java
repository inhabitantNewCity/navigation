package com.example.vlad.navigation.ui.camera.listeners;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.support.annotation.NonNull;

import com.example.vlad.navigation.ui.camera.VideoflowFragment;

public class CameraPreviewCallback extends CameraCaptureSession.StateCallback {

    private VideoflowFragment fragment;

    public CameraPreviewCallback(VideoflowFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
        // The camera is already closed
        if (null == fragment.mCameraDevice) {
            return;
        }

        // When the session is ready, we start displaying the preview.
        fragment.mCaptureSession = cameraCaptureSession;
        try {
            // Auto focus should be continuous for camera preview.
            fragment.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE,
                    CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // Flash is automatically enabled when necessary.
            fragment.setAutoFlash(fragment.mPreviewRequestBuilder);

            // Finally, we start displaying the camera preview.
            fragment.mPreviewRequest = fragment.mPreviewRequestBuilder.build();
            fragment.mCaptureSession.setRepeatingRequest(fragment.mPreviewRequest,
                    fragment.mCaptureCallback, fragment.mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

    }
}
