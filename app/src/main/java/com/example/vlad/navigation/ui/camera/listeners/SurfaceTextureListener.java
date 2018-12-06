package com.example.vlad.navigation.ui.camera.listeners;

import android.graphics.SurfaceTexture;
import android.view.TextureView;

import com.example.vlad.navigation.ui.camera.VideoflowFragment;

public class SurfaceTextureListener implements TextureView.SurfaceTextureListener{

    private VideoflowFragment fragment;

    public SurfaceTextureListener(VideoflowFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
        fragment.openCamera(width, height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
        fragment.configureTransform(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture texture) {
    }
}

