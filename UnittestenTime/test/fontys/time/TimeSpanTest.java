/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thijn van Dijk
 */
public class TimeSpanTest {
    
    public TimeSpanTest() {
    }
    public Time TestTime1 = null;
    public Time TestTime2 = null;
    public TimeSpan TestTimeSpan = null;
    
    /**
        public TimeSpan(ITime bt, ITime et) {
            if (bt.compareTo(et) <= 0) {
                throw new IllegalArgumentException("begin time "
                        + bt + " must be earlier than end time " + et);
            }

            this.bt = bt;
            this.et = et;
        }
     * 
     * 
     * @param bt must be earlier than et
     * @param et 
     */
    @Before
    public void  setUp()
    {
        Time time1 = new Time(2015,4,21,13,5);
        Time time2 = new Time(2015,4,21,13,10);
        TimeSpan temp = new TimeSpan(time1, time2);
        this.TestTime1 = time1;
        this.TestTime2 = time2;
        this.TestTimeSpan = temp;
    }
    
    @Test
    public void newTimeSpanTest()
    {
        Time time1 = new Time(2015,3,21,12,5);
        Time time2 = new Time(2015,4,21,13,5);
        
        try{
            TimeSpan testSpan = new TimeSpan(time1, time2);
        }
        catch(Exception e)
        {
            fail("Something went wrong in creating a new timespan object and a exception was thrown with message: " + e.getMessage());
        }
        
        try{
            TimeSpan testSpan = new TimeSpan(time2, time1);
            fail("De eindtijd mag niet voor de begintijd liggen");
        }
        catch(IllegalArgumentException e){
        }   
    }
    
    @Test
    public void getBtAndEtTest()
    {
        /**
     * getBeginTime(): ITime
     * @return the begin time of this time span
     */       
        assertEquals("De begintijd komt niet overeen met de verwachte tijd.",TestTime1,  TestTimeSpan.getBeginTime());
        
        /**
     * getEndTime(): ITime
     * @return the end time of this time span
     */
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd.",TestTime2, TestTimeSpan.getEndTime());
    }
    
    @Test
    public void setBtAndEtTest()
    {
        /**
         * setBeginTime(ITime beginTime): void
        * beginTime will be the new begin time of this time span
        * @param beginTime must be earlier than the current end time
        * of this time span
        */
        //test tijd na eindtijd
        Time changedTime = new Time(2015,4,21,13,15);
        try{
            TestTimeSpan.setBeginTime(changedTime);
            fail("De begin tijd mag niet na de eindtijd liggen.");
        }
        catch(IllegalArgumentException e){}
        
        //test tijd voor eindtijd
        changedTime = new Time(2015,4,21,13,1);
        TestTimeSpan.setBeginTime(changedTime);
        assertEquals("De begintijd komt niet overeen met de nieuwe tijd.",changedTime,  TestTimeSpan.getBeginTime());
        
        /**
        * setEndTime(ITime endTime): void
        * endTime will be the new end time of this time span
        * @param endTime must be later than the current begin time
        * of this time span
        */
        
        //test tijd voor begintijd        
        changedTime = new Time(2015,4,21,13,1);
        try{
            TestTimeSpan.setEndTime(changedTime);
            fail("De begin tijd moet na de begintijd liggen.");
        }
        catch(IllegalArgumentException e){}
        
        //test tijd na begintijd
        changedTime = new Time(2015,4,21,13,20);
        TestTimeSpan.setEndTime(changedTime);
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd.",changedTime, TestTimeSpan.getEndTime());
        
    }
    
    @Test
    public void lenghtTest()
    {
        /**
        * length()
        * @return the length of this time span expressed in minutes (always positive)
        */
        
        Time time1 = new Time(2015,4,21,13,5);
        Time time2 = new Time(2015,4,21,13,10);
        TimeSpan temp = new TimeSpan(time1, time2);
        assertEquals("Het verschil is niet hetzelfde als de verwachte", 5, temp.length());
    }
    
    @Test
    public void moveTest()
    {
        /**
        * move(int minutes): void
        * the begin and end time of this time span will be moved up both with [minutes]
        * minutes
        * @param minutes (a negative value is allowed)
        */
        
        Time time1 = new Time(2015,4,21,13,5);
        Time time2 = new Time(2015,4,21,13,10);
        
        TestTimeSpan.move(5);
        assertEquals("De begintijd komt niet overeen met de nieuwe hogere verwachte tijd.",time1.getMinutes() + 5,  TestTimeSpan.getBeginTime().getMinutes());
        assertEquals("De eindtijd komt niet overeen met de nieuwe hogere verwachte tijd.",time2.getMinutes() + 5, TestTimeSpan.getEndTime().getMinutes());
        
        TestTimeSpan.move(-3);
        assertEquals("De begintijd komt niet overeen met de nieuwe lagere verwachte tijd.",time1.getMinutes() + 2,  TestTimeSpan.getBeginTime().getMinutes());
        assertEquals("De eindtijd komt niet overeen met de nieuwe lagere verwachte tijd.",time2.getMinutes() + 2, TestTimeSpan.getEndTime().getMinutes());
    }
    
    @Test
    public void changeLenghtTest()
    {
        /**
        * changeLengthWith(int minutes): void
        * the end time of this time span will be moved up with [minutes] minutes
        * @param minutes  minutes + length of this period must be positive  
        */
        
        try{
            this.TestTimeSpan.changeLengthWith(-10);
        }
        catch(IllegalArgumentException e){}
        
        int res = this.TestTimeSpan.length() + 10;
        this.TestTimeSpan.changeLengthWith(10);
        assertEquals("lengte verandering klopt niet", res , this.TestTimeSpan.length());
        
        
    }

    @Test
    public void isPartOfTest()
    {
        /**
        * isPartOf(ITimeSpan timeSpan): bool
        * @param timeSpan
        * @return true if all moments within this time span are included within [timeSpan], 
        * otherwise false
        */
        
        
        Time time1 = new Time(2015,4,21,13,1);
        Time time2 = new Time(2015,4,21,13,20);
        TimeSpan timeSpan1 = new TimeSpan(time1, time2);
        
        Time time3 = new Time(2015,4,21,13,2);
        Time time4 = new Time(2015,4,21,13,19);
        TimeSpan timeSpan2 = new TimeSpan(time3, time4);
        
        assertTrue("timespan2 hoort onderdeel te zijn van timespan1", timeSpan1.isPartOf(timeSpan2));
        assertFalse("timespan1 hoort niet onderdeel te zijn van timespan2", timeSpan2.isPartOf(timeSpan1));
    }
    
    
    @Test
    public void unionWithTest()
    {
        /**
        * unionWith(ITimeSpan timeSpan): ITimeSpan
        * 
        * @param timeSpan
        * @return if this time span and [timeSpan] are consecutive or possess a
        * common intersection, then the smallest time span ts will be returned, 
        * whereby this time span and [timeSpan] are part of ts, 
        otherwise null will be returned 
        */

        Time time1 = new Time(2015,4,21,13,5);
        Time time2 = new Time(2015,4,21,13,15);
        Time time3 = new Time(2015,4,21,13,10);
        Time time4 = new Time(2015,4,21,13,20);
        
        TimeSpan timeSpan1 = new TimeSpan(time1, time2);
        TimeSpan timeSpan2 = new TimeSpan(time3, time4);
        TimeSpan timeSpanRes = new TimeSpan(time1, time4);
        
        assertEquals("Er is iets niet goed gegaan met de union", timeSpanRes.getBeginTime(), timeSpan1.unionWith(timeSpan2).getBeginTime());
        assertEquals("Er is iets niet goed gegaan met de union", timeSpanRes.getEndTime(), timeSpan1.unionWith(timeSpan2).getEndTime());
        
        timeSpan1 = new TimeSpan(time1, time3);
        timeSpan2 = new TimeSpan(time2, time4);
        assertNull("er is een union terug gegeven terwijl er null moet zijn", timeSpan1.unionWith(timeSpan2));               
    }
    
    @Test
    public void intersectionWithTest()
    {
        /**
     * intersectionWith(ITimeSpan timeSpan): ITimeSpan
     * @param timeSpan
     * @return the largest time span which is part of this time span 
     * and [timeSpan] will be returned; if the intersection is empty null will 
     * be returned
     */
                
        Time time1 = new Time(2015,4,21,13,5);
        Time time2 = new Time(2015,4,21,13,15);
        Time time3 = new Time(2015,4,21,13,10);
        Time time4 = new Time(2015,4,21,13,20);
        
        TimeSpan timeSpan1 = new TimeSpan(time1, time2);
        TimeSpan timeSpan2 = new TimeSpan(time3, time4);
        TimeSpan timeSpanRes = new TimeSpan(time3, time2);
        
        assertEquals("De juiste overlap is niet teruggegeven", timeSpanRes.getBeginTime(), timeSpan1.intersectionWith(timeSpan2).getBeginTime());
        assertEquals("De juiste overlap is niet teruggegeven", timeSpanRes.getEndTime(), timeSpan1.intersectionWith(timeSpan2).getEndTime());
        
        timeSpan1 = new TimeSpan(time1, time3);
        timeSpan2 = new TimeSpan(time2, time4);
        assertNull("er is een resultaat teruggegeven terwijl er null moet zijn vanwege geen intersection", timeSpan1.intersectionWith(timeSpan2));
    }
    
}
