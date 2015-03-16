/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Iterator;

/**
 *
 * @author Thijn
 */
public class Appointment {
     
    private String subject;
 
    /**
     * In this class a appointment is kept. This can have any number of invited people. but doesn't need them.
     * 
     * @param subject the subject of this appointment. can be empty
     * @param timeSpan the lenght of this appointment. can not be empty
     */
    public Appointment(String subject, ITimeSpan timeSpan){
    }
    
    /**
     * 
     * @return returns the subject of this appointment
     */
    public String getSubject(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @return [return info]
     */
    public ITimeSpan getTimeSpan(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method gets all the invitees and gives them as a iterator
     * 
     * @return [return info]
     */
    public Iterator<Contact> invitees(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method adds a contact that is to be invited.
     * 
     * @param c [parameter1 info]
     * @return [return info]
     */
    public boolean addContact(Contact c){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * This method uninvites a contact from this appointment
     * 
     * @param c [parameter1 info]
     */
    public void removeContact(Contact c){
    }
    
}
