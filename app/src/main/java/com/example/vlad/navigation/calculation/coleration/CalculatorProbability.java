package com.example.vlad.navigation.calculation.coleration;

import com.example.vlad.navigation.utils.NormalizedDistribution;

public class CalculatorProbability {
    private float a;
    private float b;
    NormalizedDistribution distance_probility;
    NormalizedDistribution angle_probility;

    public CalculatorProbability(float a, float b, NormalizedDistribution distance_probility, NormalizedDistribution angle_probility){
        this.distance_probility = distance_probility;
        this.angle_probility = angle_probility;
        this.a = a;
        this.b = b;
    }

    public double getCurrentValue(double angle, double distance) {
        return a * distance_probility.getDensity(distance) + b * angle_probility.getDensity(angle);
    }
}
