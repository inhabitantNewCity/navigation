package com.example.vlad.navigation.utils;

import com.example.vlad.navigation.exeption.ArraySizeException;

import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;

/**
 * Created by Tmp on 19.02.2016.
 */
public class Vector {
    private String title;

    private float x;
    private float y;
    private float z;

    public Vector(String title, float[] arr) throws IllegalFormatException {
        if(arr.length < 3) {
            throw new ArraySizeException();
        }
        x = arr[0];
        y = arr[1];
        z = arr[2];
        this.title = title;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }


}
