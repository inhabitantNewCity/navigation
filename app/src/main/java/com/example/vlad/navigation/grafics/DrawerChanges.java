package com.example.vlad.navigation.grafics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.vlad.navigation.utils.Point;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.messageSystem.MessageDrawer;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Tmp on 14.02.2016.
 */
public class DrawerChanges implements Runnable {

    private static ConcurrentLinkedQueue<MessageSystem> myQueue = new ConcurrentLinkedQueue();

    private static Object lock = new Object();
    private boolean flagParity = false;


    private Canvas canvas;
    private SurfaceHolder builderSpaceForDraw;

    private Point start  = new Point();
    private Point finish = new Point();

    public DrawerChanges(SurfaceHolder builder){
        builderSpaceForDraw = builder;
    }
    @Override
    public void run() {
        setInitialisationParameters();
        while(true){
            if(!myQueue.isEmpty()){
                MessageDrawer ms = (MessageDrawer) myQueue.poll();
                Vector[] vectors = (Vector[]) ms.getMessage();
                parse(vectors);
                drawChanges(canvas);
            }
            try {
                synchronized (lock) {
                    lock.wait(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setInitialisationParameters() {
        start.x = 0;
        start.y = 0;
    }

    private void drawChanges(Canvas canvas) {
        canvas = builderSpaceForDraw.lockCanvas();
        try {
            synchronized (builderSpaceForDraw) {
                    Paint pen = new Paint();
                    pen.setColor(Color.RED);
                    canvas.drawLine(start.x,start.y, finish.x,finish.y, pen);
                    start.x = finish.x;
                    start.y = finish.y;
                // canvas.drawBitmap(picture, matrix, null);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(canvas != null){
                builderSpaceForDraw.unlockCanvasAndPost(canvas);
            }
        }
    }

    private void parse(Vector[] vectors) {
        // get 4 point for draw line from vectors
        Vector lengthStep = vectors[0];
        Vector orientationStep = vectors[1];

        float lengthCurrentStep = lengthCurrentStep(lengthStep.getX(), lengthStep.getY(), lengthStep.getZ());
        setOrientationLine(orientationStep,lengthCurrentStep);
        // define orientation current vector


    }
    private void setOrientationLine(Vector orientationVector, float lengthStep ){
        //use matrix angle

        finish.x = (float) -Math.sin(orientationVector.getX()) * (lengthStep) + start.x;
        finish.y = (float) Math.cos(orientationVector.getX()) * (lengthStep) + start.y;

    }
    private float lengthCurrentStep(float x, float y, float z){
        return (float) Math.sqrt( x*x + y*y + z*z );
    }
    public static ConcurrentLinkedQueue<MessageSystem> getQueue() {
        return myQueue;
    }
}
