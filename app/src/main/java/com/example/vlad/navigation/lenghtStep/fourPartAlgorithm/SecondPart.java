/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.lenghtStep.fourPartAlgorithm;

/**
 *
 * @author Roman
 */
public class SecondPart {
   private float summ = 0.0f;
   private int countStep;
   
    
   private static SecondPart secondPart = new SecondPart();
   
   public SecondPart(){};
   public static SecondPart getInstance(){
       return secondPart;
   }
   
   public float getlenghtStep(float a){
       return calculationLenghtStep(a);
   }
   
   private float calculationLenghtStep(float a){
       summ += a / countStep;
       return (float)Math.pow(summ, 1/3);
   }
}
