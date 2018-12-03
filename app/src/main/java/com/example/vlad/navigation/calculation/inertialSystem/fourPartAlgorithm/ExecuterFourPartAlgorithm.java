/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.calculation.inertialSystem.fourPartAlgorithm;

import com.example.vlad.navigation.calculation.inertialSystem.Counter;
import com.example.vlad.navigation.utils.Vector;

import java.util.HashMap;

/**
 *
 * @author Roman
 */
public class ExecuterFourPartAlgorithm implements Counter {
    private static final FirstPart firstPart = FirstPart.getInstatance();
    private static final SecondPart secondPart = SecondPart.getInstance();

    
    private static final int COUNT_RETURN_VALUE = 4;
    private double[] returnedMass = new double[COUNT_RETURN_VALUE];
    
    private static final int COUNT_ACC_VALUE = 4;
    private double[] acc = new double[COUNT_ACC_VALUE];
    private double[] angles = new double[COUNT_ACC_VALUE];
   
    // method must have name equals name of method in interface(project android studio)
    // method return 4 value: lenght and 3 angle
    // parameters method is 12 doubles (3 sensors give 9 value plus 3 angle)
    public double[] execute(Object ar){
        parseSomething(ar);
        double a = firstPart.getAcceleration(acc);
      if(firstPart.recognitionStepOnModuleAcc(a))
          returnedMass[0] = secondPart.getlenghtStep(a);
      return returnedMass;
    }
    private void parseSomething(Object ar){
        // todo: parse object in parametrs
        // angles parse in returnedMass
    }

    @Override
    public Vector run(HashMap<String, float[]> map) {
        return new Vector("from Four Part Algorithm", new float[3], 2);
    }
}
