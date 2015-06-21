/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package calculate;

import java.io.Serializable;
import javafx.scene.paint.Color;

/**
 *
 * @author Peter Boots
 */
public class SerializableEdge implements Serializable {
    public double X1, Y1, X2, Y2, r, g, b;
    
    public SerializableEdge(double X1, double Y1, double X2, double Y2, Color color) {
        this.X1 = X1;
        this.Y1 = Y1;
        this.X2 = X2;
        this.Y2 = Y2;
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
    }
    
    @Override
    public String toString() {
        return String.format("%f %f %f %f %f %f %f ", X1, Y1, X2, Y2, r, g, b);
    }
}
