/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shared;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Arrays;

/**
 *
 * @author Thijn
 */
public interface IEffectenbeurs extends Remote {
    IFonds[] getKoersen() throws RemoteException;
}
