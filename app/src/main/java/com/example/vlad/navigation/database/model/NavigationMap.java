package com.example.vlad.navigation.database.model;

import java.util.ArrayList;
import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.line.Line2D;

public class NavigationMap {

    protected List<Line2D> lines;

    public NavigationMap(List<Line2D> intervals) {
        this.lines = intervals;
    }

    public static List<Line2D> convert(List<Line> intervals) {
        List<Line2D> lines = new ArrayList<>();
        for(Line line: intervals){
            Point a = line.getA();
            Point b = line.getB();
            Line2D line2D = new Line2D(a.getX(), a.getY(), b.getX(), b.getY());
            lines.add(line2D);
        }
        return lines;
    }

    public List<Line2D> getRelatedEges(Line2D line) {
        List<Line2D> result = new ArrayList<Line2D>();
        for (int i = 0; i < lines.size(); i++) {

            if (line == lines.get(i))
                continue;
            else if (line.getX2() == lines.get(i).getX1() && line.getY2() == lines.get(i).getY1())
                result.add(lines.get(i));
			else if (line.getX2() == lines.get(i).getX2() && line.getY2() == lines.get(i).getY2())
                result.add(lines.get(i));
        }
        return result;
    }

    public List<Line2D> getList() {
        return lines;
    }

    public Point2D getNearestPoint(Point2D source_point) {
        /// TODO: get nerest point of current map
        return source_point;
    }
}
