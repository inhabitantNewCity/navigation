package com.example.vlad.navigation.calculation.machineVisionSystem.filtration;

import android.media.Image;

import java.util.SortedSet;
import java.util.TreeSet;

public class BlurDetectionMap {
    private SortedSet<BlurImage> query;
    private BluringDetector detector = new BluringDetectorImpl();
    private static BlurDetectionMap instance;

    private BlurDetectionMap() {
        this.query = new TreeSet<>();
    }

    public static BlurDetectionMap getInstance(){
        if(instance == null)
            instance = new BlurDetectionMap();
        return instance;
    }

    public Image getImage(Image image){
        BlurImage topImage = query.first();
        Double gause = detector.blurDetection(image);
        if(topImage.getGauseDestribution().compareTo(gause) != -1) {
            BlurImage newImage = new BlurImage(image);
            newImage.setGauseDestribution(gause);
            query.add(newImage);
            return image;
        }
        return topImage.getImage();
    }
}
