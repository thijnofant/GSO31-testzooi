/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beurs;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Thijn
 */
public class MockEffectenbeurs implements IEffectenbeurs{
    private IFonds[] koersen;
    
    public MockEffectenbeurs() {
        koersen = new IFonds[] {
            new Fonds("Philips", 26.345),
            new Fonds("KPN", 3.154),
            new Fonds("Heineken", 71.470),
            new Fonds("Ahold", 18.325),
            new Fonds("Shell", 28.485),
            new Fonds("Unilever", 39.460),
        };
    }
    
    @Override
    public IFonds[] getKoersen() {
        fluctueerKoersen();
        return koersen;
    }
    
    public void fluctueerKoersen() {
        for(int i = 0; i < koersen.length; i++) {
            Fonds f = (Fonds)koersen[i];
            Random r = new Random();
            double verandering = (100 + (-3 +  r.nextInt(6))) / 100;
            f.setKoers(f.getKoers() * verandering);
        }
    }
}
