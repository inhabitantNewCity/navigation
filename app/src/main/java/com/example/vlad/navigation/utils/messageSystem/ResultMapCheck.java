package com.example.vlad.navigation.utils.messageSystem;

import math.geom2d.Point2D;

public class ResultMapCheck implements MessageSystem {

    private Point2D point2D;
    private boolean isUpdated;
    private boolean isFinished;

    public ResultMapCheck(Point2D result, boolean b, boolean b1) {
        this.point2D = result;
        this.isUpdated = b;
        this.isFinished = b1;
    }

    @Override
    public void setMessage(Object data) {
        ResultMapCheck source = (ResultMapCheck) data;
        this.point2D = source.point2D;
        this.isUpdated = source.isUpdated;
        this.isFinished = source.isFinished;
    }

    @Override
    public Object getMessage() {
        return this;
    }

    public Point2D getPoint2D() {
        return point2D;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
