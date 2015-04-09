/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Thijn
 */
public class RW {

    private List<Ball> readersActiveList;
    private List<Ball> writersActiveList;
    private List<Ball> readersWaitingList;
    private List<Ball> writersWaitingList;
    ReentrantLock monLock;
    Condition okToWrite; //writersActive == 0
    Condition okToRead; //writersActive == 0 && readersActive == 0

    public RW() {
        this.monLock = new ReentrantLock();
        okToRead = monLock.newCondition();
        okToWrite = monLock.newCondition();
        readersActiveList = new ArrayList<>();
        writersActiveList = new ArrayList<>();
        readersWaitingList = new ArrayList<>();
        writersWaitingList = new ArrayList<>();
    }

    public void enterReader(Ball ball) throws InterruptedException {
        monLock.lock();
        try {
            while (writersActiveList.size() > 0 || writersWaitingList.size() > 0) {
                System.err.println("EnterReader");
                readersWaitingList.add(ball);
                okToRead.await();
                readersWaitingList.remove(ball);
            }
            readersActiveList.add(ball);
        } catch (InterruptedException e) {
            readersWaitingList.remove(ball);
            Thread.currentThread().interrupt();
        } finally {
            monLock.unlock();
        }
    }

    public void enterWriter(Ball ball) throws InterruptedException {
        monLock.lock();
        try {
            while (writersActiveList.size() > 0 || readersActiveList.size() > 0) {
                System.err.println("EnterWriter");
                writersWaitingList.add(ball);
                okToWrite.await();
                writersWaitingList.remove(ball);
            }
            writersActiveList.add(ball);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            monLock.unlock();
        }
    }

    public void exitReader(Ball ball) throws InterruptedException {
        monLock.lock();
        try {
            System.err.println("ExitReader");
            if (readersActiveList.contains(ball)) {
                readersActiveList.remove(ball);
            }

            if (readersActiveList.size() <= 0) {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

    public void exitWriter(Ball ball) throws InterruptedException {
        monLock.lock();
        try {
            if (writersActiveList.contains(ball) || writersWaitingList.contains(ball)) {
                writersActiveList.remove(ball);
                writersWaitingList.remove(ball);
            }

            if (writersWaitingList.size() >= 1) {
                okToWrite.signal();
            } else if (readersWaitingList.size() >= 1) {
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
