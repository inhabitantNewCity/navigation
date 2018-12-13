package com.example.vlad.navigation.calculation.machineVisionSystem;

import android.media.Image;

import com.example.vlad.navigation.calculation.machineVisionSystem.filtration.BlurDetectionMap;
import com.example.vlad.navigation.calculation.machineVisionSystem.filtration.BluringDetector;
import com.example.vlad.navigation.calculation.machineVisionSystem.parser.ResponseParser;
import com.example.vlad.navigation.calculation.machineVisionSystem.parser.ResponseParserImpl;
import com.example.vlad.navigation.database.model.Point;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;

public class ImageProcessor implements Runnable {

    private final Image mImage;
    private final File mFile;
    private final RecognitionFaced recognitionFaced = RecognitionFacedFactory.getFaced();
    private ResponseParser responseParser = new ResponseParserImpl();
    private BlurDetectionMap blurDetection = BlurDetectionMap.getInstance();

    public ImageProcessor(Image image, File file) {
        mImage = image;
        mFile = file;
    }

    @Override
    public void run() {
        try {
            Image detected = blurDetection.getImage(mImage);
            if(mImage == detected) {
                ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                List<Point> point = responseParser.parse(recognitionFaced.recognize(bytes));
                System.out.print(point);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}