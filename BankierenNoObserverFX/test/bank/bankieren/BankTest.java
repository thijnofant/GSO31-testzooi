/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import java.rmi.RemoteException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thijn
 */
public class BankTest {
    
    
    public BankTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of openRekening method, of class Bank.
     */
    @Test
    public void testOpenRekening() throws RemoteException {
        System.out.println("openRekeningTest");
        String name = "testPers1";
        String city = "Eindhoven";
        Bank instance = new Bank("testbank1");
        int expResult = instance.remoteCentraleBank.getNieuwRekNR()+1;
        int result = instance.openRekening(name, city);
        assertEquals("OpenRekening fail",expResult, result);
        
        name = "";
        result = instance.openRekening(name, city);
        assertEquals("Name cannot be empty",-1, result);
        
        name = "test";
        city = "";
        result = instance.openRekening(name, city);
        assertEquals("Place cannot be empty",-1, result);
    }

    /**
     * Test of getRekening method, of class Bank.
     */
    @Test
    public void testGetRekening() throws RemoteException {
        System.out.println("getRekeningTest");
        Bank instance = new Bank("testbank1");
        int nr = instance.openRekening("testPers", "Eindhoven");
        IRekening result = instance.getRekening(nr);
        assertEquals(nr, result.getNr());
    }

    /**
     * Test of maakOver method, of class Bank.
     */
    @Test
    public void testMaakOver() throws Exception {
        System.out.println("maakOverTest");
        Bank instance = new Bank("testbank1");
        int source = instance.openRekening("testperson1", "Eindhoven");
        int destination = instance.openRekening("testperson2", "Eindhoven");
        Money money = new Money(1, Money.EURO);
        
        Money expMoneyResSource = new Money(-1, Money.EURO);
        Money expMoneyResDestination = new Money(1, Money.EURO);
        
        boolean expResult = true;
        boolean result = instance.maakOver(source, destination, money);
        Money ResultSource = instance.getRekening(source).getSaldo();
        Money ResultDestination = instance.getRekening(destination).getSaldo();
        
        assertEquals("transaction failed according to method",expResult, result);
        assertEquals("saldo not correct source", expMoneyResSource, ResultSource);
        assertEquals("saldo not correct destination", expMoneyResDestination, ResultDestination);
        
        //niet naar zelf
        try {
            result = instance.maakOver(source, source, money);
            fail("Je mag niet naar jezelf overmaken");
        } catch (Exception e) {
        }
        
        //niet negatief       
        try {
            Money neg = new Money(-10, Money.EURO);
            result = instance.maakOver(source, destination, neg);
            fail("Je mag geen negatief bedrag overmaken");
        } catch (Exception e) {
        }
        
        //niet meer dan kredietlimit
        Money tooMuch = new Money(100000, Money.EURO);
        result = instance.maakOver(source, destination, tooMuch);
        assertFalse("Je mag niet meer dan je kridiet limiet overmaken", result);
    }

    /**
     * Test of getName method, of class Bank.
     */
    @Test
    public void testGetName() throws RemoteException {
        System.out.println("getNameTest");
        Bank instance = new Bank("testbank1");
        String expResult = "testbank1";
        String result = instance.getName();
        assertEquals(expResult, result);
    }
    
}
