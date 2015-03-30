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
    KochFractal koch;
    int level;
    String direction;
    KochManager kochmanager;
    ArrayList<Edge> edges;
    
    public GenerateEdgeCallable(int level, KochManager manager, String direction) {
        this.level = level;
        this.kochmanager = manager;
        this.direction = direction;
        this.edges = new ArrayList<>();
    }
    
    @Override
    public Object call() throws Exception {
        switch (direction) {
            case "left":
                koch.generateLeftEdge();
            case "bottom":
                koch.generateBottomEdge();
            case "right":
                koch.generateRightEdge();
        }
        
        synchronized(kochmanager) {
            kochmanager.count++;
        }
        
        return edges;
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
    }
}
