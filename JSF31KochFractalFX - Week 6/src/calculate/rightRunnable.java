/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.Observable;
import java.util.Observer;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsf3
 */
public class rightRunnable implements Runnable, Observer {
    KochFractal koch;
    int level;
    List<Edge> edges;
    KochManager kochmanager;
    CyclicBarrier cb;
    
    public rightRunnable(int level, List<Edge> edges, KochManager manager, CyclicBarrier cb) {
        koch = new KochFractal();
        koch.setLevel(level);
        koch.addObserver(this);
        this.edges = edges;
        this.kochmanager = manager;
        this.cb = cb;
    }
    
    @Override
    public void run() {
        koch.generateRightEdge();
        try {
            cb.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(rightRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(rightRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        addEdge((Edge)arg);
    }
    
    public synchronized void addEdge(Edge edge) {
        edges.add(edge);
    }
}