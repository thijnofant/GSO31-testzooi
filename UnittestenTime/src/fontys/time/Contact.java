/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Robert
 */
public class Contact {
    /*
    b)	een agenda met Appointment-objecten waar deze contactpersoon bij is betrokken..
    */
    private String name;
    private List<Appointment> agenda;
    
    /**
     * This class holds the name of a contact and the appoinments he is part of..
     * 
     * @param name Name of this contact
     */
    public Contact(String name)
    {
        if(name == null || name.equals("")) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
            
        this.name = name;
        this.agenda = new ArrayList<Appointment>();
    }
    
    /**
     * 
     * @return Returns the name for this contact
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * This method adds a appointment to this contact's agenda.
     * 
     * @param a the appointment to be added.
     * @return returns whether the method failed or succeeded.
     */
    public boolean addAppointment(Appointment a)
    {
        for(Appointment appointment : agenda) {
            if((appointment.getTimeSpan().unionWith(a.getTimeSpan())) != null) {
                return false;
            }
        }
        this.agenda.add(a);
        return true;
    }
    
    /**
     * This method removes a appointment from this contact's agenda.
     * 
     * @param a the appointment to be removed.
     */
    public void removeAppointment(Appointment a)
    {
        this.agenda.remove(a);
    }
    
    /**
     * This method returns all appointments in this contact's agende.
     * 
     * @return returns a Iterator of the agenda.
     */
    public Iterator<Appointment> appointments()
    {
        return this.agenda.iterator();
    }
}
