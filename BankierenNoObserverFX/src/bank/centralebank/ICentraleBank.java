/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralebank;

import bank.bankieren.Money;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Thijn
 */
public interface ICentraleBank extends Remote{
    /**
     * Deze methode handeld de transacties tussen 2 banken af.
     * @param source rekeningNr van de rekening waar het geld van wordt afgeschreven.
     * @param destination rekeningNr van de rekening waar het geld naar toe word gestuurd
     * @param amount het bedrag dat overgeschreven wordt
     * @return een Boolean die true is als de transactie voltooid is
     */
    public boolean transactieTussenBanken(int source, int destination, Money amount) throws RemoteException;

    /**
     * Deze methode haalt het nieuwste rekeningsnummer op.
     * @return returned een rekeningnummer.
     */
    public int getNieuwRekNR() throws RemoteException;

}
