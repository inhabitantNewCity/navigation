/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.calculation.inertialSystem.fourPartAlgorithm;

import com.example.vlad.navigation.utils.filters.Filter;
import com.example.vlad.navigation.utils.filters.LowPasFilter;
import com.example.vlad.navigation.utils.norming.DefoultNorm;
import com.example.vlad.navigation.utils.norming.Norm;

/**
 *
 * @author Roman
 */
public class FirstPart {
    private static final double ACCSTEP = 2.0;
    private static final double ACCRUN = 4.0;
    
    private boolean flagStepFinded = false;
    
    
    private final Filter filter = (Filter) LowPasFilter.getInstance();
    private final Norm norm = new DefoultNorm();
    private final SecondPart secondPart = SecondPart.getInstance();
    
    private static final FirstPart firstPart = new FirstPart();
    
    private FirstPart(){};
    
    public static FirstPart getInstatance(){
        return firstPart;
    }

    public double getAcceleration(double[] ar){
        double[] mass;
        mass = filter.filtering(ar);
        float[] result  = new float[3];
        for(int i = 0; i < result.length; i++){
            result[i] = (float)mass[i];
        }
        //mass = norm.norming(mass);
        double mod = norm.modul(result);
        return mod;
    } 
    public boolean recognitionStepOnModuleAcc(double mod){
                if(recognitionStep(mod)){
            if(!flagStepFinded){
                flagStepFinded = true;
                return true;
            }
        }
        else{
            flagStepFinded = false;
        }
        return false;
    }
    private boolean recognitionStep(double a){
       return (a > ACCSTEP) & (a < ACCRUN);
    }
}
