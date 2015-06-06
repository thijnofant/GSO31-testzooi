/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import jsf31kochfractalfx.*;
import timeutil.*;

/**
 *
 * @author Robert
 */
public class KochManager {
    private final JSF31KochFractalFX application;
    private final ArrayList<Edge> edges;
    public final ArrayList<Edge> bottomEdges;
    public final ArrayList<Edge> leftEdges;
    public final ArrayList<Edge> rightEdges;
    public CyclicBarrier cyclicBarrier;
    private CalculateTask calculateBottom;
    private CalculateTask calculateLeft;
    private CalculateTask calculateRight;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(3);
    private TimeStamp calcTimeStamp;
    private TimeStamp drawTimeStamp;
    
    public KochManager(JSF31KochFractalFX application) {
        this.application = application;
        this.edges = new ArrayList<>();
        this.bottomEdges = new ArrayList<>();
        this.leftEdges = new ArrayList<>();
        this.rightEdges = new ArrayList<>();
        this.cyclicBarrier = new CyclicBarrier(3);
    }
    
    public void changeLevel(int nxt) {
        if (calculateBottom != null) {
            calculateBottom.stopTask();
            calculateLeft.stopTask();
            calculateRight.stopTask();
            cyclicBarrier.reset();
            edges.clear();
            bottomEdges.clear();
            leftEdges.clear();
            rightEdges.clear();
        }
        calculateBottom = new CalculateTask(nxt, this, "bottom", application);
        calculateLeft = new CalculateTask(nxt, this, "left", application);
        calculateRight = new CalculateTask(nxt, this, "right", application);
        application.getBottomProgress().progressProperty().bind(calculateBottom.progressProperty());
        application.getLeftProgress().progressProperty().bind(calculateLeft.progressProperty());
        application.getRightProgress().progressProperty().bind(calculateRight.progressProperty());
        this.calcTimeStamp = new TimeStamp();
        calcTimeStamp.setBegin("Begin calculation");
        threadPool.submit(calculateBottom);
        threadPool.submit(calculateLeft);
        threadPool.submit(calculateRight);
    }
    
    public void calculationComplete() {
        calcTimeStamp.setEnd("end calculation");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                application.setTextCalc(calcTimeStamp.toString());
            }
        });
        edges.addAll(bottomEdges);
        edges.addAll(leftEdges);
        edges.addAll(rightEdges);
        application.requestDrawEdges();
    }
    
    public void drawEdges() {
        application.clearKochPanel();
        this.drawTimeStamp = new TimeStamp();
        drawTimeStamp.setBegin("Begin drawing");
        for (Edge e : edges) {
            application.drawEdge(e);
        }
        drawTimeStamp.setEnd("end drawing");
        application.setTextDraw(drawTimeStamp.toString());
        application.setTextNrEdges(Integer.toString(edges.size()));
    }
    
    public void stopThreadPool() {
        threadPool.shutdown();
    }
}
