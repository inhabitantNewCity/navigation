/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.norming;

/**
 *
 * @author Roman
 */
public class DefoultNorm implements Norm {

    @Override
    public float[] norming(float[] ar) {
        float[] mass = ar;
        for(int i = 0; i < ar.length; i++){
            mass[i] /= 100.0;
        }
        return mass;
    }

    @Override
    public float modul(float[] ar) {
        double tmp = 0.0;
        for(int i = 0 ; i < ar.length; i++){
            tmp += ar[i]*ar[i];
        }
        return (float)Math.sqrt(tmp);
    }
    
    
}
