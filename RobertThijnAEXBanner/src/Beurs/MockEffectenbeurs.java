/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beurs;

import java.util.ArrayList;

/**
 *
 * @author Thijn
 */
public class MockEffectenbeurs implements IEffectenbeurs{
    private ArrayList<IFonds> koersen;
    
    public MockEffectenbeurs() {
//        koersen = new ArrayList<>() {
//            new Fonds("Philips", 100);
//        };
    }
    
    @Override
    public ArrayList<IFonds> getKoersen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
