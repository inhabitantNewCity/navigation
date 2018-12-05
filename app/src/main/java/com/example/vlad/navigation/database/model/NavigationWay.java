package com.example.vlad.navigation.database.model;

import java.util.List;

import math.geom2d.Point2D;
import math.geom2d.line.Line2D;

public class NavigationWay extends NavigationMap {

    private boolean isOptim;

    public NavigationWay(List<Line2D> way) {
        super(way);
    }

    public Line2D getStartLine(){
        return lines.get(0);
    }

    public void setOptimFlag(boolean flag) {
        isOptim = flag;
    }

    public boolean isOptimWay() {
        return isOptim;
    }

    public Line2D getNext(List<Line2D> neboures) {

        for (int i = 0; i < neboures.size(); i++) {
            for (int j = 0; j < lines.size(); j++) {
                if ((neboures.get(i).getY1() == lines.get(j).getX1() &&
                        neboures.get(i).getY1() == lines.get(j).getY1()) &&
                (neboures.get(i).getX2() == lines.get(j).getX2() &&
                        neboures.get(i).getY2() == lines.get(j).getY2()))
                return neboures.get(i);
            }
        }
        return null;
    }

    public Point2D getLastPoint() {
        return new Point2D(lines.get(lines.size() - 1).getX2(), lines.get(lines.size() - 1).getY2());
    }
}
