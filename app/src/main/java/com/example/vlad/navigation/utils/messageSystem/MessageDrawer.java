package com.example.vlad.navigation.utils.messageSystem;

import com.example.vlad.navigation.utils.Vector;

/**
 * Created by Tmp on 14.02.2016.
 */
public class MessageDrawer implements MessageSystem {
    Vector vector;
    public MessageDrawer(Vector vector){
        this.vector = vector;
    }

    @Override
    public void setMessage(Object data) {

    }

    @Override
    public Object getMessage() {
        return vector;
    }
}
