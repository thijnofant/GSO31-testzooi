/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beurs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Thijn
 */
public class MockEffectenbeurs implements IEffectenbeurs{
    private IFonds[] koersen;
    DecimalFormat df = new DecimalFormat("#.00");
    
    public MockEffectenbeurs() {
        koersen = new IFonds[] {
            new Fonds("Philips", 26.34),
            new Fonds("KPN", 3.15),
            new Fonds("Heineken", 71.47),
            new Fonds("Ahold", 18.32),
            new Fonds("Shell", 28.48),
            new Fonds("Unilever", 39.46),
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
            double verandering = (-1 + r.nextDouble()) / 10 ;
//            f.setKoers(df.format(f.getKoers() * verandering));
        }
    }
}
