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
        //mass = norm.norming(mass);
        double mod = norm.modul(mass);
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