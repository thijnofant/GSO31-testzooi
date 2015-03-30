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
import javafx.application.Platform;

/**
 *
 * @author jsf3
 */
public class KochManager{
    private JSF31KochFractalFX application;    
    private ArrayList<Edge> edgeslist;
    private List<Edge> edges;
    public static int count = 0;
    TimeStamp ts;
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.edgeslist = new ArrayList<>();
        this.edges = Collections.synchronizedList(edgeslist);  
    }
    
    public void changeLevel(int nxt) {
        edges.clear();
        CyclicBarrier cb = new CyclicBarrier(3);       
        ts = new TimeStamp();
        ts.setBegin();
        Thread t1 = new Thread(new leftRunnable(nxt, edges, this, cb));
        t1.start();
        Thread t2 = new Thread(new bottomRunnable(nxt, edges, this, cb));
        t2.start();
        Thread t3 = new Thread(new rightRunnable(nxt, edges, this, cb));
        t3.start();             
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
    }
    
    public void requestDrawEdges() {         
        application.requestDrawEdges();
    }
}
