/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.vlad.navigation.grafics.map.BindingMapAndData;

import com.example.vlad.navigation.grafics.DrawerChanges;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.messageSystem.MessageCorrectionData;
import com.example.vlad.navigation.utils.messageSystem.MessageDrawer;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Roman
 */
public class ExecutorMapAndData implements Runnable {
    
    private static Resampling resampling = Resampling.getInstance();
    private static Forecast forecast = Forecast.getInstance();
    private static Correction correction = Correction.getInstance();

    private ConcurrentLinkedQueue<MessageSystem> queue = DrawerChanges.getQueue();
    private static ConcurrentLinkedQueue<MessageSystem> myQueue = new ConcurrentLinkedQueue<>();

    private Object lock = new Object();

    public static ConcurrentLinkedQueue<MessageSystem> getQueue(){
        return myQueue;
    }
    @Override
    public void run() {
        while (true){
            if(myQueue.isEmpty()){
                try {
                    synchronized (lock) {
                        lock.wait(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                MessageCorrectionData message = (MessageCorrectionData) myQueue.poll();
                Vector vectors = optimizationData(message);
                MessageDrawer messageDrawer = new MessageDrawer(vectors);
                messageDrawer.setNumberCounter(message.getNumberCounter());
                queue.add(messageDrawer);
            }
        }
    }

    private Vector optimizationData(MessageSystem message) {
        Vector vectors = (Vector) message.getMessage();
        return vectors;
    }
}
