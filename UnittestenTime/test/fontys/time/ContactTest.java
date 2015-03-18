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
public class ContactTest {
    
    public ContactTest() {
    }
    
    Contact test1;
    
    public Time testTime1;
    public Time testTime2;
    public TimeSpan testTimeSpan;
    public Appointment testAppointment;
    
    public Time testTime3;
    public Time testTime4;
    public TimeSpan testTimeSpan2;
    public Appointment testAppointment2;
    
    public Time testTime5;
    public Time testTime6;
    public TimeSpan testTimeSpan3;
    public Appointment testAppointment3;
    
    public Time testTime7;
    public Time testTime8;
    public TimeSpan testTimeSpan4;
    public Appointment testAppointment4;
    
    @Before
    public void setUp() {
        test1 = new Contact("Jan");
        
        testTime1 = new Time(2015,4,21,13,5);
        testTime2 = new Time(2015,4,21,13,10);
        testTimeSpan = new TimeSpan(testTime1, testTime2);
        testAppointment = new Appointment("This is a test", testTimeSpan);
        
        testTime3 = new Time(2015,4,20,13,5);
        testTime4 = new Time(2015,4,22,13,10);
        testTimeSpan2 = new TimeSpan(testTime3, testTime4);
        testAppointment2 = new Appointment("This is test 2", testTimeSpan2);
        
        testTime5 = new Time(2016,2,20,13,5);
        testTime6 = new Time(2016,2,22,13,10);
        testTimeSpan3 = new TimeSpan(testTime5, testTime6);
        testAppointment3 = new Appointment("This is test 3", testTimeSpan3);
        
        testTime7 = new Time(2015,8,6,13,5);
        testTime8 = new Time(2015,8,20,13,10);
        testTimeSpan4 = new TimeSpan(testTime7, testTime8);
        testAppointment4 = new Appointment("This is test 4", testTimeSpan4);
    }
    
    /**
     * Test of constructor method, of class Contact.
     */
    @Test
    public void testNewContact() {
        try {
            Contact c1 = new Contact("Test");
        }
        catch(Exception e)
        {
            fail("Something went wrong in creating a new Contact object and a exception was thrown with message: " + e.getMessage());
        }
        
        try {
            Contact c2 = new Contact("");
            fail("Name cannot be null or empty.");
        }
        catch(IllegalArgumentException e) {
        }
    }
    
    /**
     * Test of getName method, of class Contact.
     */
    @Test
    public void testGetName() {
        String result = "Jan";
        assertEquals("The name was not the expected name.", result, test1.getName());
    }

    /**
     * Test of addAppointment method, of class Contact.
     */
    @Test
    public void testAddAppointment() {
        assertTrue("Something went wrong with adding the appointment.", test1.addAppointment(testAppointment));
        assertFalse("Shouldn't be possible to add overlapping appointments.", test1.addAppointment(testAppointment2));
        assertTrue("Something went wrong with adding the appointment.", test1.addAppointment(testAppointment3));
        assertTrue("Something went wrong with adding the appointment.", test1.addAppointment(testAppointment4));
        
        Iterator<Appointment> Appointments = test1.appointments();
        
        assertEquals("Appointment added is not the same as appointment returned.", testAppointment, Appointments.next());
        assertEquals("Appointment added is not the same as appointment returned.", testAppointment3, Appointments.next());
        assertEquals("Appointment added is not the same as appointment returned.", testAppointment4, Appointments.next());
    }

    /**
     * Test of removeAppointment method, of class Contact.
     */
    @Test
    public void testRemoveAppointment() {
        test1.addAppointment(testAppointment);
        test1.addAppointment(testAppointment2);       
        test1.addAppointment(testAppointment3);
        test1.addAppointment(testAppointment4);
        test1.removeAppointment(testAppointment);
        test1.removeAppointment(testAppointment2);
        test1.removeAppointment(testAppointment3);
        test1.removeAppointment(testAppointment4);
        
        assertFalse("Expected no elements to be present in appointments.", test1.appointments().hasNext());
    }

    /**
     * Test of appointments method, of class Contact.
     */
    @Test
    public void testAppointments() {        
        test1.addAppointment(testAppointment);
        test1.addAppointment(testAppointment3);
        test1.addAppointment(testAppointment4);
        
        Iterator<Appointment> Appointments = test1.appointments();      
        
        assertEquals("Iterator does not have expected contents.", testAppointment, Appointments.next());
        assertEquals("Iterator does not have expected contents.", testAppointment3, Appointments.next());
        assertEquals("Iterator does not have expected contents.", testAppointment4, Appointments.next());
    }
    
}
