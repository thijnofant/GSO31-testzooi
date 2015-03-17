/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Thijn
 */
public class Appointment {
     
    private String subject;
    private ITimeSpan timeSpan;
    private List<Contact> Invitees;
 
    /**
     * In this class a appointment is kept. This can have any number of invited people. but doesn't need them.
     * 
     * @param subject the subject of this appointment. can be empty
     * @param timeSpan the lenght of this appointment. can not be empty
     */
    public Appointment(String subject, ITimeSpan timeSpan){
        if (!(subject == null)) {
            this.subject = subject;
        }
        else
        {
            this.subject = "";
        }
        
        if (!(timeSpan == null)) {
            this.timeSpan = timeSpan;
        }
        else
        {
            throw new IllegalArgumentException("The timespan can not be null");
        }
        
        Invitees = new ArrayList<>();
    }
    
    /**
     * 
     * @return returns the subject of this appointment
     */
    public String getSubject(){
        return this.subject;
    }
    
    /**
     * 
     * @return returns the timespan of this appointment
     */
    public ITimeSpan getTimeSpan(){
        return this.timeSpan;
    }
    
    /**
     * This method gets all the invitees and gives them as a iterator
     * 
     * @return returns the contacts that are invited
     */
    public Iterator<Contact> invitees(){        
        return Invitees.iterator();
    }
    
    /**
     * This method adds a contact that is to be invited.
     * 
     * @param c The contact that is to be added.
     * @return returns if the method succeeded or failed
     */
    public boolean addContact(Contact c){
        if(c.addAppointment(this))
        {
            Invitees.add(c);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * This method uninvites a contact from this appointment
     * 
     * @param c The contact that is to be deleted.
     */
    public void removeContact(Contact c){
        c.removeAppointment(this);
        Invitees.remove(c);
    }
    
}
