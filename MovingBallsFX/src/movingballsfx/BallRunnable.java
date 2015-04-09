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
                        monitor.enterReader(ball);
                    } else if (ball.getColor() == Color.BLUE) {
                        monitor.enterWriter(ball);
                    }
                }

                if (ball.isLeavingCs()) {
                    if (ball.getColor() == Color.RED) {
                        monitor.exitReader(ball);
                    } else if (ball.getColor() == Color.BLUE) {
                        monitor.exitWriter(ball);
                    }
                }
                Thread.sleep(ball.getSpeed());

                if (Thread.currentThread().isInterrupted()) {
                    if (ball.getColor() == Color.RED) {
                        monitor.exitReader(ball);
                    } else if (ball.getColor() == Color.BLUE) {
                        monitor.exitWriter(ball);
                    }
                }

            } catch (InterruptedException ex) {
                if (ball.getColor() == Color.RED) {
                    try {
                        monitor.exitReader(ball);
                    } catch (InterruptedException ex1) {

                    }
                } else if (ball.getColor() == Color.BLUE) {
                    try {
                        monitor.exitWriter(ball);
                    } catch (InterruptedException ex1) {

                    }
                }
                Thread.currentThread().interrupt();
            }
        }
    }
}
