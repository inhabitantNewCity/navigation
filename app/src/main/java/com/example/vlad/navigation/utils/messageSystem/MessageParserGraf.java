package com.example.vlad.navigation.utils.messageSystem;

import com.example.vlad.navigation.utils.Vector;

import java.util.HashMap;

/**
 * Created by Tmp on 14.02.2016.
 */
public class MessageParserGraf implements MessageSystem {
    private Vector[] data;

    public MessageParserGraf(Vector[] data) {
        this.data = data;
    }

    @Override
    public void setMessage(Object data) {

    }

    @Override
    public Object getMessage() {
        return data;
    }
}
