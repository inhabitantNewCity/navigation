package com.example.vlad.navigation.ui.camera;

import android.util.SparseIntArray;
import android.view.Surface;

public class CameraConfigurationConstants {

    public static final int STATE_PREVIEW = 0;
    public static final int STATE_WAITING_LOCK = 1;
    public static final int STATE_WAITING_PRECAPTURE = 2;
    public static final int STATE_WAITING_NON_PRECAPTURE = 3;
    public static final int STATE_PICTURE_TAKEN = 4;
    public static final int MAX_PREVIEW_WIDTH = 1920;
    public static final int MAX_PREVIEW_HEIGHT = 1080;
    public static final String FRAGMENT_DIALOG = "dialog";
    public static final int REQUEST_CAMERA_PERMISSION = 1;

    public static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

}
