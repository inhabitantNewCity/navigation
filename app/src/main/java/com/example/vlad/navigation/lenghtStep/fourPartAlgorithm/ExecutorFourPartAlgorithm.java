/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.lenghtStep.fourPartAlgorithm;

import com.example.vlad.navigation.lenghtStep.Counter;
import com.example.vlad.navigation.utils.Vector;

import java.util.HashMap;

/**
 *
 * @author Roman
 */
public class ExecutorFourPartAlgorithm implements Counter {
    private static final FirstPart firstPart = FirstPart.getInstance();
    private static final SecondPart secondPart = SecondPart.getInstance();

    
    private static final int COUNT_RETURN_VALUE = 4;
    private float[] returnedMass = new float[COUNT_RETURN_VALUE];

    private float lengthStep;

    private static final int COUNT_ACC_VALUE = 3;
    private float[] acc = new float[COUNT_ACC_VALUE];
    private float[] angles = new float[COUNT_ACC_VALUE];
   
    // method must have name equals name of method in interface(project android studio)
    // method return 4 value: lenght and 3 angle
    // parametors method is 12 doubles (3 sensors give 9 value plus 3 angle)
    public float execute(){
        //parseSomething(ar);
        float a = firstPart.getAcceleration(acc);
      if(firstPart.recognitionStepOnModuleAcc(a)) {
          lengthStep = secondPart.getlenghtStep(a);
      }
        else {
          lengthStep = 0;
      }
      return lengthStep;
    }
    private void parseSomething(HashMap<String,float[]> map){
        acc =  map.get("Acc");
        angles =  map.get("Angl");
    }

    @Override
    public Vector run(HashMap<String, float[]> map) {
        parseSomething(map);
        return new Vector(" ",angles,execute());
    }
}
