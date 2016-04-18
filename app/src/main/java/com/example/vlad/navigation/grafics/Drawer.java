package com.example.vlad.navigation.grafics;

import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class Drawer implements Runnable {
    private static ConcurrentLinkedQueue<MessageSystem> myQueue = new ConcurrentLinkedQueue();

    @Override
    public void run() {

    }

    public static ConcurrentLinkedQueue<MessageSystem> getQueue() {
        return myQueue;
    }
}
