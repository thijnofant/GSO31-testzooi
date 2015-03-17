/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Iterator;

/**
 *
 * @author Robert
 */
public class Contact {
    /*
    b)	een agenda met Appointment-objecten waar deze contactpersoon bij is betrokken..
    */
    
    private String name;
    
    /**
     * This class holds the name of a contact and the appoinments he is part of..
     * 
     * @param name Name of this contact
     */
    public Contact(String name)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @return Returns the name for this contact
     */
    public String getName()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method adds a appointment to this contact's agenda.
     * 
     * @param a the appointment to be added.
     * @return returns whether the method failed or succeeded.
     */
    public boolean addAppointment(Appointment a)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method removes a appointment from this contact's agenda.
     * 
     * @param a the appointment to be removed.
     */
    public void removeAppointment(Appointment a)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method returns all appointments in this contact's agende.
     * 
     * @return returns a Iterator of the agenda.
     */
    public Iterator<Appointment> appointments()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Template.
    }
}
