package com.example.vlad.navigation.database;

import com.example.vlad.navigation.database.model.NavigationMap;
import com.example.vlad.navigation.database.model.NavigationWay;

import java.util.ArrayList;
import java.util.List;

import math.geom2d.line.Line2D;

public class DataAccessServiceStub implements DataAccessService {


    @Override
    public NavigationMap getMap() {
        List<Line2D> linesMap = new ArrayList<Line2D>(){{
            add(new Line2D(0,0,100,0));
            add(new Line2D(100,0,100,100));
            add(new Line2D(100,100,100,20));
            add(new Line2D(100,100,50,100));
            add(new Line2D(100,100,150,100));
            add(new Line2D(100,100,100,300));
        }};
        return new NavigationMap(linesMap);
    }

    @Override
    public NavigationWay getWay() {
        List<Line2D> linesMap = new ArrayList<Line2D>(){{
            add(new Line2D(0,0,100,0));
            add(new Line2D(100,0,100,100));
            add(new Line2D(100,100,100,200));
            add(new Line2D(100,100,100,300));
        }};
        return new NavigationWay(linesMap);
    }
}
