/**
 * ***** BannerController.java **********************************
 */
package Banner;

import Shared.*;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BannerController extends Application implements RemotePropertyListener, 
        Serializable {

    private transient AEXBanner banner;
    private IFonds[] fondsen;
    private IEffectenbeurs MockEffectenbeurs;
    public static final transient String bindingName ="MockEffectenbeurs";
    public static final transient String ip = "192.168.0.100";
    public static final transient int port = 1099;
    private transient Registry registry = null;
    
    @Override
    public void start(Stage primaryStage) {
        banner = new AEXBanner();
        //primaryStage acts as the common stage of the AEXBanner and the 
        //BannerController:
        banner.start(primaryStage);
        
        try {
            registry = LocateRegistry.getRegistry(ip, port);
            System.out.println("Registry located.");
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
               
        bindBeurs(ip, port);

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
    
    public void bindBeurs(String ipAddress, int portNumber) {
        try {
            MockEffectenbeurs = (IEffectenbeurs) registry.lookup(bindingName);
            MockEffectenbeurs.addListener(this, null);
            System.out.println("Effectenbeurs bound");
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

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        this.fondsen = (IFonds[]) evt.getNewValue();
    }
}