/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beurs;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Robert
 */
public class RMIServer {
    public static final int portNumber = 1099;  
    public static final String bindingName = "MockEffectenbeurs";
    
    public RMIServer() { 
       try {
           MockEffectenbeurs beurs = new MockEffectenbeurs();
           Registry registry = LocateRegistry.createRegistry(portNumber);
           registry.rebind(bindingName, beurs);
       }
       catch (Exception ex) {
           System.out.println("Exception: " + ex.getMessage());
       }
    }
    
    public static void main(String[] args) {
        RMIServer server = new RMIServer();        
    }
}
