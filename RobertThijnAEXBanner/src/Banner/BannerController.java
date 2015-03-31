/**
 * ***** BannerController.java **********************************
 */
package Banner;

import Shared.*;
import java.rmi.Naming;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BannerController extends Application {

    private AEXBanner banner;
    private IFonds[] fondsen;
//  private IEffectenBeurs effectenbeurs;
    private IEffectenbeurs MockEffectenbeurs;
    public static final String bindingName ="MockEffectenbeurs";
    public static final String ip = "127.0.0.1";
    public static final int port = 1099;

    public void start(Stage primaryStage) {
        banner = new AEXBanner();
        //primaryStage acts as the common stage of the AEXBanner and the 
        //BannerController:
        banner.start(primaryStage);
        bindBeurs(ip, port);

        //create a timer which polls every 2 seconds
        Timer pollingTimer = new Timer();
        // todo
        pollingTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                fondsen = MockEffectenbeurs.getKoersen();
                String temp = "";
                if (fondsen.length >= 1) {
                    for (int i = 0; i < fondsen.length; i++) {
                        temp += fondsen[i].getNaam();
                        temp += ": ";
                        //temp += fondsen[i].getKoers();
                        //
                        String temp2 =  Double.toString(fondsen[i].getKoers());
                        int index = temp2.indexOf('.');
                        String temp3 = temp2.substring(0, index + 3);                                                
                        temp += temp3;
                        //
                        temp += "   ";
                    }
                } else {
                    temp = "Er zijn op dit moment geen koersen beschikbaar";
                }
                
                Platform.runLater(new KoersSetterRun(temp, banner));
                
                //banner.setKoersen(temp);
            }
        }, 0, 2000);

        //remove pollingTimer as soon as primaryStage is closing:
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            pollingTimer.cancel();
        });
    }
    
    public void bindBeurs(String ipAddress, int portNumber) {
        try {
            MockEffectenbeurs = (IEffectenbeurs) Naming.lookup("rmi://" + ipAddress + ":" + portNumber + "/" + bindingName);
        }
        catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    /**
     * unfortunately, is needed pro forma to act as runnable class
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
