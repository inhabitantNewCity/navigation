package com.example.vlad.navigation.calculation.inertialSystem;

import com.example.vlad.navigation.utils.Vector;

import java.util.HashMap;

/**
 * Created by Tmp on 14.02.2016.
 */
public interface Counter {
    Vector run(HashMap<String,float[]> map);
}
