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

/**
 *
 * @author Robert
 */
public class GenerateEdgeCallable implements Callable, Observer {
    KochFractal koch = new KochFractal();
    int level;
    String direction;
    KochManager kochmanager;
    ArrayList<Edge> edges;
    
    public GenerateEdgeCallable(int level, KochManager manager, String direction) {
        this.level = level;
        this.kochmanager = manager;
        this.direction = direction;
        this.edges = new ArrayList<>();
        this.koch.addObserver(this);
    }
    
    @Override
    public List<Edge> call() throws Exception {
        koch.setLevel(level);
        
        if(direction == "left") {
            koch.generateLeftEdge();
        }
        if(direction == "bottom") {
            koch.generateBottomEdge();
        }
        if(direction == "right") {
            koch.generateRightEdge();
        }
        
        synchronized(kochmanager) {
            kochmanager.count += 1;
        }
        
        return edges;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        //synchronized(kochmanager) {
            edges.add((Edge)arg);
        //}
    }
}