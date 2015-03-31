/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beurs;

import Banner.KoersSetterRun;
import com.sun.javaws.Main;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author Thijn
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs {

    private static IFonds[] koersen;
    DecimalFormat df = new DecimalFormat("#.00");

    public MockEffectenbeurs() throws RemoteException {
        koersen = new IFonds[]{
            new Fonds("Philips", 26.34),
            new Fonds("KPN", 3.15),
            new Fonds("Heineken", 71.47),
            new Fonds("Ahold", 18.32),
            new Fonds("Shell", 28.48),
            new Fonds("Unilever", 39.46),};
        
        Timer pollingTimer = new Timer();
        // todo
        pollingTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                fluctueerKoersen();
            }
        }, 0, 1000);
        
    }

    @Override
    public IFonds[] getKoersen() {
        
        return koersen;
    }
    
    public static void fluctueerKoersen() {
        for (int i = 0; i < koersen.length; i++) {
            Fonds f = (Fonds) koersen[i];
            Random r = new Random();
            int updown = r.nextInt(7) -3;
            double change = 1+((double)updown / 100);
            System.out.println(updown);
            //System.out.println(change);
            f.setKoers(f.getKoers() * change);
        }
    }
}
