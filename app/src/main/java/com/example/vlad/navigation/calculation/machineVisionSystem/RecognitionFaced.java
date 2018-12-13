package com.example.vlad.navigation.calculation.machineVisionSystem;

import com.example.vlad.navigation.database.model.Point;

public interface RecognitionFaced {
    Point recognize(byte[] image);
}
