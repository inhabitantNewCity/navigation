package com.example.vlad.navigation.calculation.machineVisionSystem;

import com.example.vlad.navigation.database.model.Point;

import java.util.List;

public class RecognitionFacedJNI implements RecognitionFaced {

    @Override
    public int recognize(byte[] image, int width, int height) {
        return recognizeFromJNI(image, width, height);
    }

    public native int recognizeFromJNI(byte[] image, int width, int height);

    public native int unimplementedStringFromJNI(byte[] image, int width, int height);

    static {
        System.loadLibrary("recognition");
    }
}
