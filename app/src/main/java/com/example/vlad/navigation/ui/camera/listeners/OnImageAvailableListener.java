package com.example.vlad.navigation.ui.camera.listeners;

import android.media.ImageReader;

import com.example.vlad.navigation.calculation.machineVisionSystem.ImageProcessor;
import com.example.vlad.navigation.ui.camera.VideoflowFragment;

public class OnImageAvailableListener implements ImageReader.OnImageAvailableListener {

    private VideoflowFragment fragment;

    public OnImageAvailableListener(VideoflowFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onImageAvailable(ImageReader reader) {
        fragment.mBackgroundHandler.post(new ImageProcessor(reader.acquireNextImage(), fragment.mFile));
    }
}
