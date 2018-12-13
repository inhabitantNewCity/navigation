package com.example.vlad.navigation.calculation.machineVisionSystem;

import android.media.Image;

import com.example.vlad.navigation.database.model.Point;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class ImageProcessor implements Runnable {

    private final Image mImage;
    private final File mFile;
    private final RecognitionFaced recognitionFaced = RecognitionFacedFactory.getFaced();

    public ImageProcessor(Image image, File file) {
        mImage = image;
        mFile = file;
    }

    @Override
    public void run() {
        ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        Point point = recognitionFaced.recognize(bytes);
    }

}