package com.example.vlad.navigation.utils;

import com.example.vlad.navigation.exeption.ArraySizeException;

import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;

/**
 * Created by Tmp on 19.02.2016.
 */
public class Vector {
    private String title;

    private double x;
    private double y;
    private double z;

    public Vector(String title, double[] arr) throws IllegalFormatException {
        if(arr.length < 3) {
            throw new ArraySizeException();
        }
        x = arr[0];
        y = arr[1];
        z = arr[2];
        this.title = title;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }


}
