package com.example.vlad.navigation.utils.messageSystem;

import com.example.vlad.navigation.utils.Vector;

import math.geom2d.Point2D;

/**
 * Created by Tmp on 14.02.2016.
 */
public class MessageDrawer implements MessageSystem {
    private Vector vector;
    private ResultMapCheck resultMapCheck;

    public MessageDrawer(Vector vector, ResultMapCheck resultMapCheck){
        this.vector = vector;
        this.resultMapCheck = resultMapCheck;
    }

    @Override
    public void setMessage(Object data) {

    }

    @Override
    public Object getMessage() {
        return vector;
    }


    public Point2D getPoint() {
        return resultMapCheck.getPoint2D();
    }

    public boolean isUpdated() {
        return resultMapCheck.isUpdated();
    }

    public boolean isFinished() {
        return resultMapCheck.isFinished();
    }

    public Vector getVector() {
        return vector;
    }
}
