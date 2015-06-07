/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import jsf31kochfractalfx.JSF31KochFractalFX;

/**
 *
 * @author Robert
 */
public class CalculateTask extends Task implements Observer {
    private final int kochLevel;
    private final KochManager kochManager;
    private final String side;
    private final KochFractal kochFractal;
    private boolean stopRequested;
    private final int expectedEdges;
    private final JSF31KochFractalFX application;
    
    public CalculateTask (int kochLevel, KochManager kochManager, String side, JSF31KochFractalFX application) {
        this.kochLevel = kochLevel;
        this.kochManager = kochManager;
        this.side = side;
        this.kochFractal = new KochFractal();
        this.stopRequested = false;
        this.expectedEdges = (int) ((3*(Math.pow(4,kochLevel-1)))/3);
        this.application = application;
    }
    
    public void stopTask() {
        kochFractal.cancel();
        stopRequested = true;
    }
    
    @Override
    protected Object call() throws Exception {
        this.kochFractal.setLevel(kochLevel);
        this.kochFractal.addObserver(this);
        
        if(side.equals("bottom")) {
            kochFractal.generateBottomEdge();
        }
        if(side.equals("left")) {
            kochFractal.generateLeftEdge();
        }
        if(side.equals("right")) {
            kochFractal.generateRightEdge();
        }
        
        kochManager.cyclicBarrier.await();
        
        if (side.equals("right")) {
            if (stopRequested == false) { 
                kochManager.calculationComplete();
            }
        }
        
        return null;
    }

    @Override
    public void update(Observable o, Object o1) {
        if (stopRequested)
            return;
        Edge edge = (Edge) o1;
        if (side.equals("bottom")) {
            kochManager.bottomEdges.add(edge);
            updateProgress(kochManager.bottomEdges.size(), expectedEdges);
        }
        if (side.equals("left")) {
            kochManager.leftEdges.add(edge);
            updateProgress(kochManager.leftEdges.size(), expectedEdges);
        }
        if (side.equals("right")) {
            kochManager.rightEdges.add(edge);
            updateProgress(kochManager.rightEdges.size(), expectedEdges);
            System.out.println("yes");
        }

        final Edge edge2 = new Edge(edge.X1, edge.Y1, edge.X2, edge.Y2, Color.WHITE);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(side.equals("bottom")) {
                    application.setNrEdgesBottom(Integer.toString(kochManager.bottomEdges.size()));
                }
                if(side.equals("left")) {
                    application.setNrEdgesLeft(Integer.toString(kochManager.leftEdges.size()));
                }
                if(side.equals("right")) {
                    application.setNrEdgesRight(Integer.toString(kochManager.rightEdges.size()));
                }
                application.drawEdge(edge2);
            }
        });
        
    }
}
