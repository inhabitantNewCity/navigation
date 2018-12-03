package com.example.vlad.navigation.calculation.inertialSystem.alphaBettaAlgorithm;

import com.example.vlad.navigation.calculation.inertialSystem.Counter;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.norming.DefoultNorm;
import com.example.vlad.navigation.utils.norming.Norm;


import java.util.HashMap;

/**
 * Created by Tmp on 14.02.2016.
 */
public class CounterAlphaBetta implements Counter {

  //  private static ConcurrentLinkedQueue<MessageSystem> myQueue = new ConcurrentLinkedQueue();
    //private static ConcurrentLinkedQueue<MessageSystem> queueGraf;

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
                lengthStep();
                return sendMessage();
    }

    private Vector sendMessage() {
        Norm norm = new DefoultNorm();
        Vector vector = new Vector("from Alpha Betta Algorithm", angle, norm.modul(S));

        //vectors = new Vector("Angle",angle);
        return vector;
    }

    private void parse(HashMap<String, float[]> map) {
        acc =  map.get("Acc");
        angle =  map.get("Angl");
    }

    private void lengthStep() {

        for(int i = 0; i < acc.length; i++){
            S[i] = (Vo[i]*dt + (acc[i]*(dt*dt))/2);
            Vo[i] = Vo[i] + acc[i] * dt;
        }
    }
}
