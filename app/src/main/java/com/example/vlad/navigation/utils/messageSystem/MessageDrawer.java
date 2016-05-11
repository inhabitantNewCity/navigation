package com.example.vlad.navigation.utils.messageSystem;

import com.example.vlad.navigation.utils.Vector;

/**
 * Created by Tmp on 14.02.2016.
 */
public class MessageDrawer implements MessageSystem {
    private Vector vectors;

    private int numberCounter;

    public MessageDrawer(Vector vectors){
        this.vectors = vectors;
    }

    @Override
    public void setMessage(Object data) {

    }

    @Override
    public Object getMessage() {
        return vectors;
    }

    public  void setNumberCounter(int i){
        numberCounter = i;
    }

    public int getNumberCounter(){
        return numberCounter;
    }
}
