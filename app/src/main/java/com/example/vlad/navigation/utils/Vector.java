package com.example.vlad.navigation.utils;

import com.example.vlad.navigation.exeption.ArraySizeException;

import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;

/**
 * Created by Tmp on 19.02.2016.
 */
public class Vector {
    public String title;

    public  float x;
    public  float y;
    public  float z;
    public  double length;

    public Vector(String title, float[] arr, double length) throws IllegalFormatException {
        if(arr.length < 3) {
            throw new ArraySizeException();
        }
        x = arr[0];
        y = arr[1];
        z = arr[2];
        this.length = length;
        this.title = title;
    }


}
