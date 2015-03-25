/**
 * ***** BannerController.java  **********************************
 */
package Banner;

import Beurs.*;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BannerController extends Application {

    private AEXBanner banner;
    private IFonds[] fondsen;
//  private IEffectenBeurs effectenbeurs;
    private IEffectenbeurs MockEffectenbeurs;

    @Override
     ;

    public void start(Stage primaryStage) {
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
                fondsen = MockEffectenbeurs.getKoersen();
                String temp = "";
                if (fondsen.length >= 1) {
                    for (int i = 0; i < fondsen.length; i++) {
                        temp += fondsen[i].getNaam();
                        temp += " ";
                        temp += fondsen[i].getKoers();
                        temp += " ";
                    }
                } else {
                    temp = "Er zijn op dit moment geen koersen beschikbaar";
                }
                banner.setKoersen(temp);
            }
        }, 0, 2000);

        //remove pollingTimer as soon as primaryStage is closing:
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            //pollingTimer.cancel();
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

}
