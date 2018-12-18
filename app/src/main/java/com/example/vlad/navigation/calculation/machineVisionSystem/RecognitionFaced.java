package com.example.vlad.navigation.calculation.machineVisionSystem;

import com.example.vlad.navigation.database.model.Point;

import java.util.List;

public interface RecognitionFaced {
    int recognize(byte[] image, int width, int height);
}
