/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.internettoegang;

import bank.bankieren.Bank;
import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thijn
 */
public class BalieTest {
    
    public BalieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openRekening method, of class Balie.
     */
    @Test
    public void testOpenRekening() throws Exception {
        System.out.println("openRekeningTest");
        Bank testBank = new Bank("testBank");
        Balie instance = new Balie(testBank);
        String naam = "tester";
        String plaats = "eindhoven";
        String wachtwoord = "test";
        int reknr = testBank.openRekening(naam, plaats)+1;
        String accountnaam = instance.openRekening(naam, plaats, wachtwoord);
        
        String expResult = null;
        String result = instance.openRekening(naam, plaats, wachtwoord);
        assertNotNull("AccountNaam is null",result);
        assertEquals("AccountNaam is niet lang genoeg",8, result.length());
    }

    /**
     * Test of logIn method, of class Balie.
     */
    @Test
    public void testLogIn() throws Exception {
        System.out.println("logInTest");
        Bank testBank = new Bank("testBank");
        Balie instance = new Balie(testBank);
        String naam = "tester";
        String plaats = "eindhoven";
        String wachtwoord = "test";
        int reknr = testBank.openRekening(naam, plaats);
        String accountnaam = instance.openRekening(naam, plaats, wachtwoord);
        
        IBankiersessie expResult = new Bankiersessie(reknr+1,testBank);
        IBankiersessie result = instance.logIn(accountnaam, wachtwoord);
        assertEquals("Niet dezelfde rekening",expResult.getRekening(), result.getRekening());
        assertEquals("Niet beide geldig",expResult.isGeldig(), result.isGeldig());
    }
    
}
