package com.example.vlad.navigation.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by RoMka on 28.04.2016.
 */
public class InputOutputStream {
    public InputStream in;
    public OutputStream out;

    public InputOutputStream(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;
    }
    public int read(byte[] buffer) throws IOException {
        return in.read(buffer);
    }
    public void write(byte buffer) throws IOException {
        out.write(buffer);
    }

}
