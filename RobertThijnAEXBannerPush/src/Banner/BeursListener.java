/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banner;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import fontys.observer.*;
import Shared.*;
import java.beans.PropertyChangeEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

/**
 *
 * @author Robert
 */
public class BeursListener extends UnicastRemoteObject implements RemotePropertyListener, RemotePublisher {
    private IFonds[] fondsen;
    private IEffectenbeurs mockEffectenbeurs;
    public static final String bindingName ="MockEffectenbeurs";
    public static final String ip = "145.144.250.24";
    public static final int port = 1099;
    private Registry registry = null;
    private BasicPublisher publisher;
    
    public BeursListener() throws RemoteException {
        publisher = new BasicPublisher(new String[]{"koersen"});
        bindBeurs();
    }
    
    private void bindBeurs() {
        try {
            registry = LocateRegistry.getRegistry(ip, port);
            System.out.println("Registry located.");
            mockEffectenbeurs = (IEffectenbeurs) registry.lookup(bindingName);
            mockEffectenbeurs.addListener(this, "koersen");
            System.out.println("Effectenbeurs bound");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        this.fondsen = (IFonds[]) evt.getNewValue();
        publisher.inform(this, "koersen", null, fondsen);
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }
}
