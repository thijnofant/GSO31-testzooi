/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class BallRunnable implements Runnable {

    private Ball ball;
    private RW monitor;

    public BallRunnable(Ball ball, RW monitor) {
        this.ball = ball;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                ball.move();
                if (ball.isEnteringCs()) {
                    if (ball.getColor() == Color.RED) {
                        monitor.enterReader();
                    } else if (ball.getColor() == Color.BLUE) {
                        monitor.enterWriter();
                    }
                }

                if (ball.isLeavingCs()) {
                    if (ball.getColor() == Color.RED) {
                        monitor.exitReader();
                    } else if (ball.getColor() == Color.BLUE) {
                        monitor.exitWriter();
                    }
                }
                Thread.sleep(ball.getSpeed());

                if (Thread.currentThread().isInterrupted()) {
                    if (ball.getColor() == Color.RED) {
                        monitor.exitReader();
                    } else if (ball.getColor() == Color.BLUE) {
                        monitor.exitWriter();
                    }
                }

            } catch (InterruptedException ex) {
                if (ball.getColor() == Color.RED) {
                    try {
                        monitor.exitReader();
                    } catch (InterruptedException ex1) {

                    }
                } else if (ball.getColor() == Color.BLUE) {
                    try {
                        monitor.exitWriter();
                    } catch (InterruptedException ex1) {

                    }
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}
