package com.example.vlad.navigation.calculation.machineVisionSystem.parser;

import com.example.vlad.navigation.database.model.Point;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ResponseParserImpl implements ResponseParser {

    private final ObjectMapper mapper;

    public ResponseParserImpl() {
        mapper = new ObjectMapper();
    }

    @Override
    public List<Point> parse(String json) throws IOException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, Point.class));
    }
}
