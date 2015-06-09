/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralebank;

import bank.bankieren.IBankForCentrale;
import bank.bankieren.Money;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.rmi.server.UnicastRemoteObject;
import java.util.ListIterator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Thijn
 */
public class CentraleBank extends UnicastRemoteObject implements ICentraleBank{
    private int nieuwReknr;
    private List<IBankForCentrale> banken = new ArrayList<>();
    
    public CentraleBank() throws RemoteException{
        nieuwReknr = 100000000;
    }

    @Override
    public boolean transactieTussenBanken(int source, int destination, Money amount) {
        for (IBankForCentrale get : banken) {
            
        }
        throw new NotImplementedException();
    }

    @Override
    public synchronized int getNieuwRekNR() {
        int returnval = nieuwReknr;
        nieuwReknr++;
        return returnval;
    }
    
}
