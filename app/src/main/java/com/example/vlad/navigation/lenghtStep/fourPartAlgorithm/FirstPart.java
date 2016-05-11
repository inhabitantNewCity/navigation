/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.lenghtStep.fourPartAlgorithm;

import com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.filters.Filter;
import com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.filters.LowPasFilter;
import com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.norming.DefoultNorm;
import com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.norming.Norm;

/**
 *
 * @author Roman
 */
public class FirstPart {
    private static final double ACC_STEP = 1.0;
    private static final double ACC_RUN = 120.0;
    
    private boolean flagStepFind = false;
    
    
    private final Filter filter = (Filter) LowPasFilter.getInstance();
    private final Norm norm = new DefoultNorm();
    private final SecondPart secondPart = SecondPart.getInstance();
    
    private static final FirstPart firstPart = new FirstPart();
    
    private FirstPart(){};
    
    public static FirstPart getInstance(){
        return firstPart;
    }

    public float getAcceleration(float[] ar){
        float[] mass;
        mass = filter.filtering(ar);
        //mass = norm.norming(mass);
        float mod = norm.modul(mass);
        return mod;
    } 

    public boolean recognitionStepOnModuleAcc(double mod){
                if(recognitionStep(mod)){
            if(!flagStepFind){
                flagStepFind = true;
                return true;
            }
        }
        else{
            flagStepFind = false;
        }
        return false;
    }
    private boolean recognitionStep(double a){
       return (a > ACC_STEP) && (a < ACC_RUN);
    }
}
