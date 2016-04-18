package com.example.vlad.navigation.lenghtStep;

import com.example.vlad.navigation.grafics.ParserGraf;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.messageSystem.MessageCounter;
import com.example.vlad.navigation.utils.messageSystem.MessageParserGraf;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class CounterAlphaBetta implements Counter {

    private static ConcurrentLinkedQueue<MessageSystem> myQueue = new ConcurrentLinkedQueue();
    private static ConcurrentLinkedQueue<MessageSystem> queueGraf;

    private double[] acc = new double[3];
    private double[] accAngle = new double[3];

    private double[] S = new double[3];
    private double[] Vo = new double[3];
    private double[] angle = new double[3];

    private double dt = 0.2;

    private double betta = 0.2;


    public CounterAlphaBetta() {
        queueGraf = ParserGraf.getQueue();
    }

    public static ConcurrentLinkedQueue<MessageSystem> getQueue() {
        return myQueue;
    }
    @Override
    public void run() {
        while(true){
            if(!myQueue.isEmpty()){
                MessageCounter ms = (MessageCounter)myQueue.poll();
                HashMap<String,ArrayList<Integer>> map = ms.getMessage();
                parse(map);
                lengthStep();
                sendMessage();
            }
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessage() {
        Vector[] vectors = new Vector[2];
        vectors[0] = new Vector("S",S);
        vectors[1] = new Vector("Angle",angle);
        MessageSystem ms = new MessageParserGraf(vectors);
        queueGraf.add(ms);
    }

    private void parse(HashMap<String, ArrayList<Integer>> map) {
        ArrayList<Integer> accInput = map.get("A");
        ArrayList<Integer> accAngleInput = map.get("G");
        //TODO: parse 3 angles
        for(int i = 0; i < acc.length; i++){
            acc[i] = accInput.get(i);
            accAngle[i] = accAngleInput.get(i);
        }
    }

    private void lengthStep() {

        for(int i = 0; i < acc.length; i++){
            S[i] = (1-betta)*(4/*TODO: length gyraskop*/) + (betta)*(Vo[i]*dt + (acc[i]*(dt*dt))/2);
            Vo[i] = Vo[i] + acc[i] * dt;
        }
    }
}
