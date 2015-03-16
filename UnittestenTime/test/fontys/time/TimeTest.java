/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.GregorianCalendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robert Horvers
 */
public class TimeTest {
    
    public Time time;
    
    @Before
    public void setUp() {
        time = new Time(2015,3,21,12,5);
    }
    
    @Test
    public void YearTest()
    {        
        //Test to see if the year that was entered is correct.
        assertEquals("het Jaar van de set-up is niet gelijk aan het verwachte Jaar", 2015, time.getYear());
    }
    
    @Test
    public void maandTest()
    {
        //Test to see if a month can be more than 12
        try {
            Time timenotright = new Time(5,13,20,12,5);
            fail("There was no Exception thrown for a wrong month that is too large");
        } catch (IllegalArgumentException e) {
        }
        
        //Test to see if a month can be less than 1
        try {
            Time timenotright = new Time(5,0,20,12,5);
            fail("There was no Exception thrown for a wrong month that is too small");
        } catch (IllegalArgumentException e) {
        }
        
        assertEquals("De Maand van het set-up is niet gelijk aan de verwachte maand", 3, time.getMonth());
    }
    
    @Test
    public void dagTest()
    {
        //Test to see if a day can be more than 31
        try {
            Time timenotright = new Time(5,3,32,12,5);
            fail("There was no Exception thrown for a wrong day that is too large");
        } catch (IllegalArgumentException e) {
        }
        
        //Test to see if a day can be less than 1
        try {
            Time timenotright = new Time(5,3,0,12,5);
            fail("There was no Exception thrown for a wrong day that is too small");
        } catch (IllegalArgumentException e) {
        }
        
        assertEquals("De Dag van het set-up is niet gelijk aan de verwachte Dag", 21, time.getDay());
        assertEquals("De weekdag is niet gelijk aan het verwachte resultaat", DayInWeek.SAT, time.getDayInWeek() );
    }
    
    @Test
    public void hourTest()
    {
        //Test to see if a day can be more than 23
        try {
            Time timenotright = new Time(5,3,20,24,5);
            fail("There was no Exception thrown for a wrong hour that is too large");
        } catch (IllegalArgumentException e) {
        }
        
        //Test to see if a day can be less than 1
        try {
            Time timenotright = new Time(5,3,20,-1,5);
            fail("There was no Exception thrown for a wrong hour that is too small");
        } catch (IllegalArgumentException e) {
        }
        
        assertEquals("Het Uur van de set-up is niet gelijk aan het verwachte Uur", 12, time.getHours());
    }
    
    @Test
    public void MinuteTest()
    {
        //Test to see if a day can be more than 23
        try {
            Time timenotright = new Time(5,3,20,12,60);
            fail("There was no Exception thrown for a wrong Minute that is too large");
        } catch (IllegalArgumentException e) {
        }
        
        //Test to see if a day can be less than 1
        try {
            Time timenotright = new Time(5,3,20,12,-1);
            fail("There was no Exception thrown for a wrong Minute that is too small");
        } catch (IllegalArgumentException e) {
        }
        
        assertEquals("De minuut van de set-up is niet gelijk aan de verwachte minuut", 5, time.getMinutes());
    } 
    
    @Test
    public void addMinuteTest()
    {
        assertEquals("het nieuwe aantal minuten is niet goed",this.time.getMinutes() + 10,this.time.plus(10).getMinutes());
        //assertEquals("het nieuwe aantal minuten is niet goed",this.time.getMinutes() - 10,this.time.plus(-10).getMinutes());
    }
    
    @Test
    public void compareToTest()
    {
        Time time1 = new Time(2015,4,21,13,5);
        Time time2 = new Time(2015,4,21,13,10);
        Time time3 = new Time(2015,4,21,13,5);
        assertEquals("Tijd 2 moet groter zijn dan tijd 1",-1,time1.compareTo(time2));
        assertEquals("Tijd 1 moet kleiner zijn dan tijd 2",1,time2.compareTo(time1));
        assertEquals("De tijden moeten gelijk zijn",0, time1.compareTo(time3));
    }
    
//    @param time
//     * @return the difference between this time and [time] expressed in minutes
    @Test
    public void differenceTest()
    {
        Time time1 = new Time(2015,4,21,13,5);
        Time time2 = new Time(2015,4,21,13,10);
        assertEquals("Het verschil is niet gelijk", -5, time1.difference(time2));
    }
}
