/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import jsf31kochfractalfx.*;
import timeutil.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javafx.application.Platform;

/**
 *
 * @author jsf3
 */
public class KochManager{
    private JSF31KochFractalFX application;    
    public ArrayList<Edge> edges;
    public CyclicBarrier barrier;
    TimeStamp ts;
    ExecutorService pool = Executors.newFixedThreadPool(4);
    Future<List<Edge>> leftedges;
    Future<List<Edge>> bottomedges;
    Future<List<Edge>> rightedges;
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.edges = new ArrayList<>();
        barrier = new CyclicBarrier(4);
    }
    
    public void changeLevel(int nxt) {
        edges.clear();   
        ts = new TimeStamp();
        ts.setBegin();
        leftedges = pool.submit(new GenerateEdgeCallable(nxt, this, "left"));
        rightedges = pool.submit(new GenerateEdgeCallable(nxt, this, "right"));
        bottomedges = pool.submit(new GenerateEdgeCallable(nxt, this, "bottom"));
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
            edges.addAll(leftedges.get());
            edges.addAll(bottomedges.get());
            edges.addAll(rightedges.get());
        }
        catch(Exception e) {
        }
        
        requestDrawEdges();
    }
    
    public void requestDrawEdges() {         
        application.requestDrawEdges();
    }
    
    public void stopThreadPool() {
        pool.shutdown();
    }
}
