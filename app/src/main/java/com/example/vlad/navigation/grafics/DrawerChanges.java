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

    private int offset = 10;

    public DrawerChanges(SurfaceHolder builder){
        builderSpaceForDraw = builder;
    }
    @Override
    public void run() {
        setInitialisationParameters();
        while(true){
            if(!myQueue.isEmpty()){
                MessageDrawer ms = (MessageDrawer) myQueue.poll();
                Vector vector = (Vector) ms.getMessage();
                parse(vector, ms.getNumberCounter());
                drawChanges(ms.getNumberCounter());
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
        start.x = 100;
        start.y = 200;
        canvas = builderSpaceForDraw.lockCanvas();
        try{
            synchronized (builderSpaceForDraw){
                //canvas.drawColor(Color.WHITE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(canvas != null)
                builderSpaceForDraw.unlockCanvasAndPost(canvas);
        }
    }

    private void drawChanges(int flag) {
        this.canvas = builderSpaceForDraw.lockCanvas();
        try {
            synchronized (builderSpaceForDraw) {
                    //canvas.drawColor(Color.WHITE);
                    Paint pen = new Paint();
                    //pen.set
                    if(flag == 0) {
                        pen.setColor(Color.RED);
                    }else{
                        pen.setColor(Color.WHITE);
                    }
                    this.canvas.drawLine(start.x,start.y, finish.x,finish.y, pen);
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

    private void parse(Vector vector, int numberAlgorithm) {
        // get 4 point for draw line from vectors
        //Vector lengthStep = vector;
        //Vector  = vector[1];

        float lengthCurrentStep = vector.getLength();   //lengthCurrentStep(lengthStep.getX(), lengthStep.getY(), lengthStep.getZ());
        setOrientationLine(vector,lengthCurrentStep, numberAlgorithm);
        // define orientation current vector


    }
    private void setOrientationLine(Vector orientationVector, float lengthStep, int numberAlgorithm ){
        //use matrix angle

        finish.x = (float) -Math.sin(orientationVector.getX()) * (lengthStep) + start.x;
        finish.y = (float) Math.cos(orientationVector.getX()) * (lengthStep) + start.y + numberAlgorithm * offset;

    }
    public static ConcurrentLinkedQueue<MessageSystem> getQueue() {
        return myQueue;
    }
}
