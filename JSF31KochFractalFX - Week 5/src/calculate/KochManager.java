/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import jsf31kochfractalfx.*;
import timeutil.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javafx.application.Platform;
import javafx.concurrent.Task;

/**
 *
 * @author jsf3
 */
public class KochManager{
    private JSF31KochFractalFX application;    
    public ArrayList<Edge> edges;
    public CyclicBarrier barrier;
    private Task drawLeft;
    private Task drawBottom;
    private Task drawRight;
    TimeStamp ts;
    ExecutorService pool = Executors.newFixedThreadPool(4);
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.edges = new ArrayList<>();
        barrier = new CyclicBarrier(4);
    }
    
    public void changeLevel(int nxt) {
        if (drawLeft != null) {
            drawLeft.cancel();
            drawBottom.cancel();
            drawRight.cancel();
            application.clearKochPanel();
        }
        edges.clear();   
        ts = new TimeStamp();
        drawLeft = new KochDrawTask(nxt, this, "left", application);
        drawBottom = new KochDrawTask(nxt, this, "bottom", application);
        drawRight = new KochDrawTask(nxt, this, "right", application);
        application.getLeftProgress().progressProperty().bind(drawLeft.progressProperty());
        Platform.runLater(new Runnable() {
            public void run() {
                application.getLeftProgress().progressProperty().bind(drawLeft.progressProperty());
                application.getBottomProgress().progressProperty().bind(drawBottom.progressProperty());
                application.getRightProgress().progressProperty().bind(drawRight.progressProperty());
            }
        });
        ts.setBegin();
        pool.submit(drawLeft);
        pool.submit(drawBottom);
        pool.submit(drawRight);
        pool.execute(new WaitingThread(this));
    }
    
    public void drawEdges() {
        TimeStamp ts2 = new TimeStamp();
        ts2.setBegin();
        application.clearKochPanel();
        for (Edge e : edges) {
            application.drawEdge(e);
        }
        ts2.setEnd(); 
        application.setTextDraw(ts2.toString());
        application.setTextNrEdges(Integer.toString(edges.size()));
    }
    
    public void calcComplete() {
        ts.setEnd();
        
        Platform.runLater(new Runnable() 
         {
            @Override public void run() 
            {                                                                    
                application.setTextCalc(ts.toString());
            }
         });
        
        try {
            edges.addAll((List<Edge>)drawLeft.get());
            edges.addAll((List<Edge>)drawBottom.get());
            edges.addAll((List<Edge>)drawRight.get());
        }
        catch(Exception e) {
        }
        
        application.requestDrawEdges();
    }

    public void stopThreadPool() {
        pool.shutdown();
    }
}
