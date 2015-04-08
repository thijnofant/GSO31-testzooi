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
public class RW {

    private int readersActive;
    private int writersActive;
    private int readersWaiting;
    private int writersWaiting;
    ReentrantLock monLock;
    Condition okToWrite; //writersActive == 0
    Condition okToRead; //writersActive == 0 && readersActive == 0

    public RW() {
        this.readersActive = 0;
        this.writersActive = 0;
        this.readersWaiting = 0;
        this.writersWaiting = 0;
        this.monLock = new ReentrantLock();
        okToRead = monLock.newCondition();
        okToWrite = monLock.newCondition();
    }

    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            //todo enterReader
            while (writersActive > 0 || writersWaiting > 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
            readersActive++;
            System.err.println("EnterReader");
        } catch (InterruptedException e) {
            readersWaiting--;
            readersActive--;
            Thread.currentThread().interrupt();
        } finally {
            monLock.unlock();
        }
    }

    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            //todo enterWriter
            while (writersActive > 0 || readersActive > 0) {
                writersWaiting++;
                okToWrite.await();
                writersWaiting--;
            }
            writersActive++;
            System.err.println("EnterWriter");
        } catch (InterruptedException e) {
            writersWaiting--;
            writersActive--;
            Thread.currentThread().interrupt();
        } finally {
            monLock.unlock();
        }
    }

    public void exitReader() throws InterruptedException {
        monLock.lock();
        try {
            //todo ExitReader
            readersActive--;
            if (readersActive <= 0) {
                okToWrite.signal();
            }
            System.err.println("ExitReader");
        } finally {
            monLock.unlock();
        }
    }

    public void exitWriter() throws InterruptedException {
        monLock.lock();
        try {
            //todo ExitWriter
            writersActive--;

            if (writersWaiting >= 1) {
                okToWrite.signal();
            } else if (readersWaiting >= 1) {
                okToRead.signalAll();
            } else {
                okToWrite.signal();
            }
            System.err.println("ExitWriter");
        } finally {
            monLock.unlock();
        }
    }
}
