package com.example.vlad.navigation.calculation.machineVisionSystem;

import com.example.vlad.navigation.database.model.Point;

import java.util.List;

public class RecognitionFacedJNI implements RecognitionFaced {

    @Override
    public String recognize(byte[] image) {
        return recognizeFromJNI(image);
    }

    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native String recognizeFromJNI(byte[] image);

    /* This is another native method declaration that is *not*
     * implemented by 'hello-jni'. This is simply to show that
     * you can declare as many native methods in your Java code
     * as you want, their implementation is searched in the
     * currently loaded native libraries only the first time
     * you call them.
     *
     * Trying to call this function will result in a
     * java.lang.UnsatisfiedLinkError exception !
     */
    public native String  unimplementedStringFromJNI(byte[] image);

    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("recognition");
    }
}
