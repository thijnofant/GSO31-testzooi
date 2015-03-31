/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Banner;

import Shared.*;
import java.rmi.Naming;

/**
 *
 * @author Robert
 */
public class RMIClient {
    public static final String bindingName = "MockEffectenbeurs";
    private IEffectenbeurs beurs = null;
    
    public RMIClient(String ipAddress, int portNumber) {
        try {
            beurs = (IEffectenbeurs) Naming.lookup("rmi://" + ipAddress + ":" + portNumber + "/" + bindingName);
        }
        catch(Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
    
    public IEffectenbeurs getBeurs() {
        return beurs;
    }
}
