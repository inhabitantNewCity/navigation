package com.example.vlad.navigation.utils.messageSystem;

import com.example.vlad.navigation.utils.Vector;

/**
 * Created by RoMka on 03.05.2016.
 */
public class MessageCorrectionData implements MessageSystem {
    Vector vectors;

    public MessageCorrectionData(Vector vectors){
        this.vectors = vectors;
    }

    @Override
    public void setMessage(Object data) {

    }

    @Override
    public Object getMessage() {
        return vectors;
    }
}
