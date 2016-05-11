package com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm;

import com.example.vlad.navigation.grafics.DrawerChanges;
import com.example.vlad.navigation.lenghtStep.Counter;
import com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.filters.Filter;
import com.example.vlad.navigation.lenghtStep.alphaBettaAlgorithm.filters.LowPasFilter;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.messageSystem.MessageCounter;
import com.example.vlad.navigation.utils.messageSystem.MessageDrawer;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;


import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class CounterAlphaBetta implements Counter {

  //  private static ConcurrentLinkedQueue<MessageSystem> myQueue = new ConcurrentLinkedQueue();
    //private static ConcurrentLinkedQueue<MessageSystem> queueGraf;
    private Filter lowPasFilter = LowPasFilter.getInstance();

    private float[] acc = new float[3];
    private float[] accAngle = new float[3];

    private float[] S = new float[3];
    private float[] Vo = new float[3];
    private float[] angle = new float[3];

    private float dt = 0.2f;

    private float betta = 0.2f;


    public CounterAlphaBetta() {

    }

    //public static ConcurrentLinkedQueue<MessageSystem> getQueue() {
        // myQueue;
    //}

    public Vector run(HashMap<String,float[]> map) {
        parse(map);
        lowPasFilter.filtering(acc);
        lengthStep();
        return sendMessage();
    }

    private Vector sendMessage() {
        //vectors[0] = new Vector("S",S);
        Vector vector = new Vector("Angle",angle,norming());
        return vector;
    }

    private void parse(HashMap<String, float[]> map) {
        acc =  map.get("Acc");
        angle =  map.get("Angl");
    }
    private float norming(){
        float sum = 0;
        for (int i = 0; i < S.length; i++ ){
            sum += S[i]*S[i];
        }
        return (float)Math.sqrt(sum);
    }
    private void lengthStep() {

        for(int i = 0; i < acc.length; i++){
            S[i] = (Vo[i]*dt + (acc[i]*(dt*dt))/2);
            Vo[i] = Vo[i] + acc[i] * dt;
        }
    }
}
