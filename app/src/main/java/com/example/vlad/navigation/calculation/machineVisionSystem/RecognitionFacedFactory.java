package com.example.vlad.navigation.calculation.machineVisionSystem;

public class RecognitionFacedFactory {
    public static RecognitionFaced getFaced(){
        return new RecognitionFacedJNI();
    }
}
