package com.example.vlad.navigation.utils;

import android.util.Size;
import java.math.*;
import java.util.Comparator;

public class CompareSizesByArea implements Comparator<Size> {
    @Override
    public int compare(Size lhs, Size rhs) {
        return (int) Math.signum(lhs.getWidth() * lhs.getHeight() - rhs.getWidth()* rhs.getWidth());
    }
}
