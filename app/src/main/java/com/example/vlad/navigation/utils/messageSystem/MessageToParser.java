package com.example.vlad.navigation.utils.messageSystem;

/**
 * Created by RoMka on 02.05.2016.
 */
public class MessageToParser implements MessageSystem {
    private float[] data;
    @Override
    public void setMessage(Object data) {
        this.data = (float[]) data;
    }

    @Override
    public Object getMessage() {
        return data;
    }
}
