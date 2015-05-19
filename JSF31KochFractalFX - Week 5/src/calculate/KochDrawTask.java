/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;

/**
 *
 * @author Robert
 */
public class KochDrawTask extends Task implements Observer{
    private JSF31KochFractalFX application;
    private String direction;
    private int level;
    private int expectedEdges;
    private KochFractal koch;
    private KochManager manager;
    private List<Edge> edges;
    
    public KochDrawTask(int level, KochManager manager, String direction, JSF31KochFractalFX application){
        this.application = application;
        this.manager = manager;
        this.direction = direction;
        this.level = level;
        this.expectedEdges = (int) ((3*(Math.pow(4,level-1)))/3);
        this.edges = new ArrayList<>();
        this.koch = new KochFractal();
        this.koch.addObserver(this);
    }
    
    @Override
    protected List<Edge> call() throws Exception {
        koch.setLevel(level);
        
        if(direction.equals("left")) {
            koch.generateLeftEdge();
        }
        if(direction.equals("bottom")) {
            koch.generateBottomEdge();
        }
        if(direction.equals("right")) {
            koch.generateRightEdge();
        }
        
        manager.barrier.await();
        
        return edges;
    }

    @Override
    public void update(Observable o, Object arg) {
        final Edge e = (Edge)arg;
        edges.add(e);
        e.color = Color.WHITE;
        updateProgress(edges.size(), expectedEdges);
        Platform.runLater(new Runnable() {
            public void run() {
                if(direction.equals("left")) {
                    application.setNrEdgesLeft(Integer.toString(edges.size()));
                    application.drawEdge(e);
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(KochDrawTask.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if(direction.equals("bottom")) {
                    application.setNrEdgesBottom(Integer.toString(edges.size()));
                    application.drawEdge(e);
//                    try {
//                        Thread.sleep(3);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(KochDrawTask.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
                if(direction.equals("right")) {
                    application.setNrEdgesRight(Integer.toString(edges.size()));
                    application.drawEdge(e);
//                    try {
//                        Thread.sleep(5);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(KochDrawTask.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
            }
        });
    }
}
