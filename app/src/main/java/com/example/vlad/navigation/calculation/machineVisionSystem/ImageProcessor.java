package com.example.vlad.navigation.calculation.machineVisionSystem;

import android.media.Image;

import com.example.vlad.navigation.calculation.machineVisionSystem.filtration.BlurDetectionMap;
import com.example.vlad.navigation.database.DataAccessFactory;
import com.example.vlad.navigation.database.DataAccessService;
import com.example.vlad.navigation.database.model.Point;
import com.example.vlad.navigation.database.model.Template;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.List;

public class ImageProcessor implements Runnable {

    private final Image mImage;
    private final File mFile;
    private final RecognitionFaced recognitionFaced = RecognitionFacedFactory.getFaced();
    private DataAccessService dataAccess = DataAccessFactory.getDataAccess();
    private BlurDetectionMap blurDetection = BlurDetectionMap.getInstance();

    public ImageProcessor(Image image, File file) {
        mImage = image;
        mFile = file;
    }

    @Override
    public void run() {
        Image detected = blurDetection.getImage(mImage);
        if(mImage == detected) {
            ByteBuffer buffer = mImage.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            Template template = dataAccess.getTemplateById(recognitionFaced.recognize(bytes, mImage.getWidth(), mImage.getHeight()));
            List<Point> point = template.getPoints();
            System.out.print(point);
        }
    }

}