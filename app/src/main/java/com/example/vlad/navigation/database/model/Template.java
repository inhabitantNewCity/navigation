package com.example.vlad.navigation.database.model;

import java.util.List;

public class Template {

    private int id;
    private List<Point> points;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
