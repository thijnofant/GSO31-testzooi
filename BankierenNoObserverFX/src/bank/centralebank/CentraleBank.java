/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralebank;

import bank.bankieren.Money;

/**
 *
 * @author Thijn
 */
public class CentraleBank implements ICentraleBank{
    public static void main(String[] args) {
        
    }

    @Override
    public boolean transactieTussenBanken(int source, int destination, Money amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNieuwRekNR() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
