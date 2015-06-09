/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.centralebank;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thijn
 */
public class CentraleRMI {
    
    public static void main(String[] args) {
        CentraleRMI centrale = new CentraleRMI();
    }

    public static final int poortNr = 1099;
    public static final String bindingName = "Centrale";
    
    CentraleBank overboekCentrale;
    public CentraleRMI() {
        try {
            overboekCentrale = new CentraleBank();
            Registry registry = LocateRegistry.createRegistry(poortNr);
            registry.rebind(bindingName, overboekCentrale);
        } catch (RemoteException ex) {
            Logger.getLogger(CentraleRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
