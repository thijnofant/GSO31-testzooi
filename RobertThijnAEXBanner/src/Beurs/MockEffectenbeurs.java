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
        return koersen;
    }
}
