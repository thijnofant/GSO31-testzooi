/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author Robert
 */
public class GenerateEdgeCallable implements Callable, Observer {
    private KochFractal koch;
    private int level;
    private String direction;
    private KochManager kochmanager;
    private ArrayList<Edge> edges;
    
    public GenerateEdgeCallable(int level, KochManager manager, String direction) {
        this.level = level;
        this.kochmanager = manager;
        this.direction = direction;
        this.edges = new ArrayList<>();
        this.koch = new KochFractal();
        this.koch.addObserver(this);
    }
    
    @Override
    public List<Edge> call() throws Exception {
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
        
        kochmanager.barrier.await();
        
        return edges;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        edges.add((Edge)arg);
    }
}