/**
 * ***** BannerController.java **********************************
 */
package Banner;

import Shared.*;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BannerController extends Application implements RemotePropertyListener {
    private IFonds[] fondsen;
    private AEXBanner banner;
    private BeursListener bl;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            bl = new BeursListener();
            bl.addListener(this, "koersen");
        }
        catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
        
        banner = new AEXBanner();
        //primaryStage acts as the common stage of the AEXBanner and the 
        //BannerController:
        banner.start(primaryStage);
        
        //create a timer which polls every 2 seconds
        Timer pollingTimer = new Timer();
        // todo
        pollingTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                String temp = "";
                try {
                    for (IFonds fondsen1 : fondsen) {
                        temp += fondsen1.getNaam();
                        temp += ": ";
                        String temp2 = Double.toString(fondsen1.getKoers());
                        int index = temp2.indexOf('.');
                        String temp3 = temp2.substring(0, index + 3);                                                
                        temp += temp3;
                        temp += "   ";
                    }
                }
                catch (Exception ex) {
                    if(ex instanceof RemoteException) {
                        System.out.println(ex.getMessage());
                    }
                    else 
                    if(ex instanceof NullPointerException) {
                        temp = "Er zijn op dit moment geen koersen beschikbaar";
                    }
                }
                
                Platform.runLater(new KoersSetterRun(temp, banner));
            }
        }, 0, 2000);

        //remove pollingTimer as soon as primaryStage is closing:
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            pollingTimer.cancel();
        });
    }
   
    /**
     * unfortunately, is needed pro forma to act as runnable class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        this.fondsen = (IFonds[]) evt.getNewValue();
    }
}