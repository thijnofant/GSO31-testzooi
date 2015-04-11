/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beurs;

import Shared.IFonds;
import Shared.IEffectenbeurs;
import fontys.observer.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.*;

/**
 *
 * @author Thijn
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs, RemotePublisher {

    private IFonds[] koersen;
    private DecimalFormat df = new DecimalFormat("#.00");
    private BasicPublisher publisher;

    public MockEffectenbeurs() throws RemoteException {
        koersen = new IFonds[]{
            new Fonds("Philips", 26.34),
            new Fonds("KPN", 3.15),
            new Fonds("Heineken", 71.47),
            new Fonds("Ahold", 18.32),
            new Fonds("Shell", 28.48),
            new Fonds("Unilever", 39.46),};
        
        publisher = new BasicPublisher(new String[]{});
        Timer pollingTimer = new Timer();
        // todo
        pollingTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                fluctueerKoersen();  
            }
        }, 0, 2000);
    }

    @Override
    public IFonds[] getKoersen() throws RemoteException {
        return koersen;
    }

    private void fluctueerKoersen() {
        for (IFonds k : koersen) {
            Fonds f = (Fonds) k;
            Random r = new Random();
            int updown = r.nextInt(7) -3;
            double change = 1+((double)updown / 100);
            f.setKoers(f.getKoers() * change);
            //System.out.println(f.getKoers());
        } 
        publisher.inform(this, null, null, koersen);
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
        System.out.println("Listener registered.");
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }
}
