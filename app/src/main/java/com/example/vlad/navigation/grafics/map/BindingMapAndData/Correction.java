/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.grafics.map.BindingMapAndData;

/**
 *
 * @author Roman
 */
public class Correction {
    
   private static final Correction correction = new Correction();
   
   private Correction(){};
   public static Correction getInstance(){
       return correction;
   }
   
}
