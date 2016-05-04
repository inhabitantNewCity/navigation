/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.grafics.map.BindingMapAndData;

import com.example.vlad.navigation.utils.Point;

/**
 *
 * @author Roman
 */
public class Forecast {
   private static final int SIZE_FILD = 1000;
   private Point[] fildOfMap = new Point[SIZE_FILD];
   
   private static final Forecast forecast = new Forecast();
   
   private Forecast(){};
   public static Forecast getInstance(){
       return forecast;
   }
   
   public Point[] executeForecast(double lenghtStep, double acceleration){
        for(int j = 0; j < fildOfMap.length ; ++j){
                //fildOfMap[j] =      
        }
       
       return fildOfMap;
   }
}
