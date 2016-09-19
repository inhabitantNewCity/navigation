/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.utils.filters;

/**
 *
 * @author Roman
 */
public class LowPasFilter implements Filter {
    
    private static float k = 0.3f;
    private double[] lastMass = new double[3];

    private static final LowPasFilter instance = new LowPasFilter();
    
    private LowPasFilter() {
    }
    
    public static Filter getInstance(){
        return instance;
    }
    @Override
    public double[] filtering (double[] ar){
        double[] mass = new double[3];
  
        for(int i = 0; i < mass.length; i++){
            mass[i] = (1-k) * lastMass[i] + k * ar[i];
        } 
        lastMass = mass;
        return mass;
    }
    
    public void clear(){
        lastMass = new double[3];
    }
}
