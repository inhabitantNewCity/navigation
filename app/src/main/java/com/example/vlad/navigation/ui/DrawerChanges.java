package com.example.vlad.navigation.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.vlad.navigation.database.DataAccessFactory;
import com.example.vlad.navigation.database.DataAccessService;
import com.example.vlad.navigation.database.DataAccessServiceRest;
import com.example.vlad.navigation.database.DataAccessServiceStub;
import com.example.vlad.navigation.utils.Point;
import com.example.vlad.navigation.utils.Vector;
import com.example.vlad.navigation.utils.messageSystem.MessageDrawer;
import com.example.vlad.navigation.utils.messageSystem.MessageSystem;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import math.geom2d.line.Line2D;

/**
 * Created by Tmp on 14.02.2016.
 */
public class DrawerChanges implements Runnable {

    private static ConcurrentLinkedQueue<MessageSystem> myQueue = new ConcurrentLinkedQueue();

    private static Object lock = new Object();
    private boolean flagParity = false;
    private DataAccessService dataService = DataAccessFactory.getDataAccess();
    private Paint penMap = new Paint();
    private Paint penWay = new Paint();

    private Canvas canvas;
    private SurfaceHolder builderSpaceForDraw;

    private Point start  = new Point();
    private Point finish = new Point();

    public DrawerChanges(SurfaceHolder builder){
        penMap.setColor(Color.RED);
        penWay.setColor(Color.YELLOW);
        builderSpaceForDraw = builder;
    }
    @Override
    public void run() {
        setInitialisationParameters();
        drawLines();
        while(true){
            if(!myQueue.isEmpty()){
                MessageDrawer ms = (MessageDrawer) myQueue.poll();
                Vector vectors = (Vector) ms.getMessage();
                setOrientationLine(vectors);
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

    private void drawLines() {
        canvas = builderSpaceForDraw.lockCanvas();
        List<Line2D> linesMap = dataService.getMap().getList();
        List<Line2D> linesWay = dataService.getWay().getList();
        try {
            synchronized (builderSpaceForDraw) {
                for(Line2D line: linesMap) {
                    canvas.drawLine((float) line.getX1(), (float) line.getY1(), (float) line.getX2(), (float) line.getY2(), penMap);
                }
                for(Line2D line: linesWay) {
                    canvas.drawLine((float) line.getX1(), (float) line.getY1(), (float) line.getX2(), (float) line.getY2(), penWay);
                }
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

    private void setOrientationLine(Vector orientationVector){
        //use matrix angle

        finish.x = (float) (-Math.sin(orientationVector.x) * (orientationVector.length) + start.x);
        finish.y = (float) (Math.cos(orientationVector.y) * (orientationVector.length) + start.y);

    }
    public static ConcurrentLinkedQueue<MessageSystem> getQueue() {
        return myQueue;
    }
}
