package com.example.vlad.navigation.calculation.inertialSystem.threePhaseAlgorithm;

import com.example.vlad.navigation.calculation.inertialSystem.Counter;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.norming.DefoultNorm;
import com.example.vlad.navigation.utils.norming.Norm;

import java.util.HashMap;
import java.util.logging.Logger;

import static  com.example.vlad.navigation.utils.Constants.*;
/**
 * Created by Tmp on 16.09.2016.
 */
public class ThreePhaseAlgorithm implements Counter{

    private final double CONST_G = 9.8f * 100;
    private final double ANGLE_BETWEEN_LEGS = Math.PI /4;
    private final double ACCELERATION_MIN = 0.8 * CONST_G;
    private final double ACCELERATION_MAX = 2.3 * CONST_G;
    private final double HEIGHT_MIN = 0.6 * CONST_G;
    private final double HEIGHT_MAX = 2.0 * CONST_G;

    private double[] IDEAL_ACCELERATION = new double[12];

    private boolean[] isCurrentPhaseDetected = new boolean[3];

    private double sumAccelerations = 0;
    private int countStep = 0;

    public ThreePhaseAlgorithm() {
        isCurrentPhaseDetected[0] = true;
        IDEAL_ACCELERATION[0] = -2.0;
        IDEAL_ACCELERATION[1] = 2.0;
        IDEAL_ACCELERATION[2] = 1.0;
        IDEAL_ACCELERATION[3] = -7.0;

        IDEAL_ACCELERATION[4] = (HEIGHT_MIN - CONST_G) * Math.sin(ANGLE_BETWEEN_LEGS) + ACCELERATION_MIN * Math.cos(ANGLE_BETWEEN_LEGS);
        IDEAL_ACCELERATION[5] = (HEIGHT_MAX - CONST_G) * Math.sin(ANGLE_BETWEEN_LEGS) + ACCELERATION_MAX * Math.cos(ANGLE_BETWEEN_LEGS);
        IDEAL_ACCELERATION[6] = (HEIGHT_MIN - CONST_G) * Math.cos(ANGLE_BETWEEN_LEGS) + ACCELERATION_MIN * Math.sin(ANGLE_BETWEEN_LEGS);
        IDEAL_ACCELERATION[7] = (HEIGHT_MAX - CONST_G) * Math.cos(ANGLE_BETWEEN_LEGS) + ACCELERATION_MAX * Math.sin(ANGLE_BETWEEN_LEGS);

        IDEAL_ACCELERATION[8] = (HEIGHT_MIN - CONST_G) * Math.cos(ANGLE_BETWEEN_LEGS) + ACCELERATION_MIN * Math.sin(ANGLE_BETWEEN_LEGS);
        IDEAL_ACCELERATION[9] = (HEIGHT_MAX - CONST_G) * Math.cos(ANGLE_BETWEEN_LEGS) + ACCELERATION_MAX * Math.sin(ANGLE_BETWEEN_LEGS);
        IDEAL_ACCELERATION[10] = (HEIGHT_MIN - CONST_G) * Math.sin(ANGLE_BETWEEN_LEGS) + ACCELERATION_MIN * Math.cos(ANGLE_BETWEEN_LEGS);
        IDEAL_ACCELERATION[11] = (HEIGHT_MAX - CONST_G) * Math.sin(ANGLE_BETWEEN_LEGS) + ACCELERATION_MAX * Math.cos(ANGLE_BETWEEN_LEGS);
    }

    @Override
    public Vector run(HashMap<String, float[]> map) {
        float[] acc = map.get(nameFieldsDevice[0]);
        if(isStepDetected(acc)) {
            countStep++;
            isCurrentPhaseDetected[1] = false;
            isCurrentPhaseDetected[2] = false;
            return lengthStep(acc, map.get(nameFieldsDevice[3]));
        }
        return null;
    }

    private boolean isStepDetected(float[] as) {
        if(isCurrentPhaseDetected[0]){
            detectingCurrentPhase(as, 1);
            if(isCurrentPhaseDetected[1])
                detectingCurrentPhase(as,2);
        }
        return  isCurrentPhaseDetected[0]&& isCurrentPhaseDetected[1] && isCurrentPhaseDetected[2]; //(((acc[0] > accMinVerticalForFirstPhase) && (acc[0] < accMaxVerticalForFirstPhase)) && ((acc[1] )))
    }

    private void detectingCurrentPhase(float[] as, int numberDetectingPhase) {
        int startIndex = numberDetectingPhase * 4;
        if(((as[0] > IDEAL_ACCELERATION[startIndex]) && (as[0] < IDEAL_ACCELERATION[startIndex + 1]))
                && ((as[1] > IDEAL_ACCELERATION[startIndex + 2]) && (as[1] < IDEAL_ACCELERATION[startIndex + 3])))
            isCurrentPhaseDetected[numberDetectingPhase] = true;
    }

    private Vector parseIntoVector(double lengthStep, float[] angles) {
        return new Vector("Data from Three phase algorithm", angles, lengthStep);
    }

    private Vector lengthStep(float[] acc, float[] angles) {
        Norm norm = new DefoultNorm();
        double result = norm.modul(acc);

        sumAccelerations += result;
        return parseIntoVector(CONST_G * Math.pow(sumAccelerations/countStep, 1.0/3.0),angles);
    }
}
