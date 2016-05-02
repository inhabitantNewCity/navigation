package com.example.vlad.navigation.utils.messageSystem;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Tmp on 14.02.2016.
 */
public class MessageCounter implements MessageSystem {

    private HashMap<String,float[]> data;

    public MessageCounter(HashMap<String,float[]> data) {
        this.data = data;
    }

    @Override
    public void setMessage(Object data) {

    }

    @Override
    public  HashMap<String,float[]> getMessage() {
        return data;
    }
}
