/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Thijn
 */
public class Monitor {
    private int readersActive;
    private int writersActive;
    ReentrantLock monLock;
    
    public Monitor(){
        this.readersActive = 0;
        this.writersActive = 0;
        this.monLock = new ReentrantLock();
    }
    
    public void enterReader(){
        
    }
    
    public void enterWriter() throws InterruptedException{
        monLock.lock();
        try{
        while(writersActive > 0 || readersActive > 0)
            okToWrite.await();
        writersActive++;
        }
        finally{  
        monLock.unlock();
        }
    }
    
    public void exitReader(){
        
    }
    
    public void exitWriter(){
        
    }    
}
