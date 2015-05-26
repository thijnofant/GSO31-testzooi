/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

import java.util.concurrent.BrokenBarrierException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert
 */
public class WaitingThread implements Runnable {
    KochManager koch;
    
    public WaitingThread(KochManager koch) {
        this.koch = koch;
    }
    
    public void run() {
        try {
            koch.barrier.await();
        } catch (InterruptedException ex) {
            Logger.getLogger(WaitingThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(WaitingThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        koch.calcComplete();
    }
}
