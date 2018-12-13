package com.example.vlad.navigation.calculation.machineVisionSystem.parser;

import com.example.vlad.navigation.database.model.Point;

import java.io.IOException;
import java.util.List;

public interface ResponseParser {
    List<Point> parse(String json) throws IOException;
}
