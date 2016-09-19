/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.utils.norming;

/**
 *
 * @author Roman
 */
public class DefoultNorm implements Norm {

    @Override
    public double[] norming(double[] ar) {
        double[] mass = ar;
        for(int i = 0; i < ar.length; i++){
            mass[i] /= 100.0;
        }
        return mass;
    }

    @Override
    public double modul(float[] ar) {
        double tmp = 0.0;
        for(int i = 0 ; i < ar.length; i++){
            tmp += ar[i]*ar[i];
        }
        return Math.sqrt(tmp);
    }
    
    
}
