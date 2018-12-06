package com.example.vlad.navigation.ui.camera.listeners;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

import com.example.vlad.navigation.R;
import com.example.vlad.navigation.ui.camera.VideoflowFragment;

public class VideoListener implements View.OnClickListener {

    private VideoflowFragment fragment;

    public VideoListener(VideoflowFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.picture: {
                fragment.takePicture();
                break;
            }
            case R.id.info: {
                Activity activity = fragment.getActivity();
                if (null != activity) {
                    new AlertDialog.Builder(activity)
                            .setMessage(R.string.intro_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
                break;
            }
        }
    }
}
