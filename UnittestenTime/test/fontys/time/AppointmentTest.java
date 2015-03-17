/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Iterator;
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
public class AppointmentTest {
    
    public AppointmentTest() {
    }
    
    public Time testTime1;
    public Time testTime2;
    public TimeSpan testTimeSpan;
    public Appointment testAppointment;
    
    @Before
    public void setUp() {
        testTime1 = new Time(2015,4,21,13,5);
        testTime2 = new Time(2015,4,21,13,10);
        testTimeSpan = new TimeSpan(testTime1, testTime2);
        testAppointment = new Appointment("This is a test", testTimeSpan);
    }
       
    /**
     * Test of getSubject method, of class Appointment.
     */
    @Test
    public void testGetSubject() {
        String result = "This is a test";
        assertEquals("The Subject was not the expected Subject", result, testAppointment.getSubject());
    }
    
    /**
     * Test of getTimeSpan method, of class Appointment.
     */
    @Test
    public void testGetTimeSpan() {
        //Test to see if the timespan can be empty in the constructor.
        try {
            Appointment timenotright = new Appointment("Test Subject",null);
            fail("There was no Exception thrown for the absence of a timespan");
        } 
        catch (IllegalArgumentException e) {
        }
        
        //test to get the timespan from the Appointment made in the setUp()
        assertEquals("The right Timespan was not retrieved", testTimeSpan, testAppointment.getTimeSpan());
    }

    /**
     * Test of invitees method, of class Appointment.
     */
    @Test
    public void testInvitees() {
        
        //testAddContacts;
        Contact test1 = new Contact("Pers1");
        Contact test2 = new Contact("Pers2");
        Contact test3 = new Contact("Pers3");
        
        assertTrue(testAppointment.addContact(test1));
        assertTrue(testAppointment.addContact(test2));
        assertTrue(testAppointment.addContact(test3));
        
        Iterator<Contact> Contacts = testAppointment.invitees();
        
        assertEquals("The right Contact was not retrieved", test1, Contacts.next());
        assertEquals("The right Contact was not retrieved", test2, Contacts.next());
        assertEquals("The right Contact was not retrieved", test3, Contacts.next());
        
        //testRemoveContacts;
        testAppointment.removeContact(test1);
        testAppointment.removeContact(test3);
        
        Contacts = testAppointment.invitees();
        
        assertEquals("The right Contact was not retrieved", test2, Contacts.next());
    }    
}
