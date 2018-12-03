package com.example.vlad.navigation.utils;

import static java.lang.Math.PI;
import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

public class NormalizedDistribution {

    private double math_exp;
    private double sqrt_dispersion;

    public NormalizedDistribution(double math_exp, double sqrt_dispersion) {
        this.math_exp = math_exp;
        this.sqrt_dispersion = sqrt_dispersion;
    }

    public double getDensity(double x) {
        return 1.0 / (sqrt_dispersion * sqrt(2.0 * PI))*exp(-((x - math_exp) * (x - math_exp)) / (2.0 * sqrt_dispersion * sqrt_dispersion));
    }
}
