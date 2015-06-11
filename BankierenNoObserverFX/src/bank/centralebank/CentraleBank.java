/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralebank;

import bank.bankieren.Bank;
import bank.bankieren.IBankForCentrale;
import bank.bankieren.Money;
import bank.internettoegang.IBalie;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.rmi.server.UnicastRemoteObject;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thijn
 */
public class CentraleBank extends UnicastRemoteObject implements ICentraleBank{
    private int nieuwReknr;
    private List<IBankForCentrale> remoteBanken;
    private Registry registry;
    
    public CentraleBank() throws RemoteException{
        nieuwReknr = 100000000;
        remoteBanken = new ArrayList<>();
    }

    @Override
    public boolean transactieTussenBanken(int source, int destination, Money amount) throws RemoteException {
        
        if (source == destination) {
            throw new RuntimeException(
                    "cannot transfer money to your own account");
        }
        
        if (!amount.isPositive()) {
            throw new RuntimeException("money must be positive");
        }
        
        Money negative = Money.difference(new Money(0, amount.getCurrency()),amount);
        boolean success1 = false;
        boolean success2 = false;
        
        IBankForCentrale sourcebank = getBankByRekNr(source);
        IBankForCentrale destbank = getBankByRekNr(destination);
        
        success1 = sourcebank.Afschrijven(source, amount);

        if (success1){
            success2 = destbank.Bijschrijven(destination, amount);
        }

        if (!success2 && success1) // rollback
        {
            sourcebank.Bijschrijven(source, amount);
            return false;
        }
        return success1;
    }

    @Override
    public synchronized int getNieuwRekNR() throws RemoteException {
        int returnval = nieuwReknr;
        nieuwReknr++;
        return returnval;
    }
    
    public IBankForCentrale getBankByRekNr(int rekNr) throws RemoteException{
        for (IBankForCentrale get : remoteBanken) {
           if(get.rekeningVanBank(rekNr)){
               return get;
           }
        }
        return null;
    }
    
    @Override
    public void bindBanken(){
        remoteBanken.clear();            
        try {
            registry = LocateRegistry.getRegistry("localhost",1101);
            remoteBanken.add((IBankForCentrale) registry.lookup("RaboBankb"));
        }
        catch (Exception e) {
        }
        try {
            registry = LocateRegistry.getRegistry("localhost",1102);
            remoteBanken.add((IBankForCentrale) registry.lookup("INGb"));
        }
        catch (Exception e) {
        }
        try {
            registry = LocateRegistry.getRegistry("localhost",1103);
            remoteBanken.add((IBankForCentrale) registry.lookup("SNSb"));
        }
        catch (Exception e) {
        }
        try {
            registry = LocateRegistry.getRegistry("localhost",1104);
            remoteBanken.add((IBankForCentrale) registry.lookup("ABN AMBROb"));
        }
        catch (Exception e) {
        }
        try {
            registry = LocateRegistry.getRegistry("localhost",1105);
            remoteBanken.add((IBankForCentrale) registry.lookup("ASNb"));
        }
        catch (Exception e) {
        }
    }
}
