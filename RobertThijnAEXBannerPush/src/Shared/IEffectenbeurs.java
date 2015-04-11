/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;
import fontys.observer.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Thijn
 */
public interface IEffectenbeurs extends Remote, RemotePublisher {
    IFonds[] getKoersen() throws RemoteException;
    @Override
    void addListener(RemotePropertyListener listener, String property)
            throws RemoteException;
    @Override
    void removeListener(RemotePropertyListener listener, String property)
            throws RemoteException;
    
}
