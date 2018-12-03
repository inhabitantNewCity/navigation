/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.calculation.inertialSystem.fourPartAlgorithm;

/**
 *
 * @author Roman
 */
public class SecondPart {
   private double summ = 0.0;
   private int countStep;
   
    
   private static SecondPart secondPart = new SecondPart();
   
   public SecondPart(){};
   public static SecondPart getInstance(){
       return secondPart;
   }
   
   public double getlenghtStep(double a){
       return calculationLenghtStep(a);
   }
   
   private double calculationLenghtStep(double a){
       summ += a / countStep;
       return Math.pow(summ, 1/3);
   }
}
