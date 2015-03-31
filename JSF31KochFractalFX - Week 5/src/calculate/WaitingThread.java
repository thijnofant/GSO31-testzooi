/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculate;

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
        while (!(koch.count >= 3)) {
            System.out.println("waiting" + koch.count);
        }
        
        koch.count = 0;
        koch.calcComplete();
    }
}
