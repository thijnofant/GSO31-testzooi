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
public class TimeSpan2Test {
    
    public TimeSpan2Test() {
    }
    public Time TestTime1 = null;
    public Time TestTime2 = null;
    public TimeSpan2 TestTimeSpan = null;
    
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
        TimeSpan2 temp = new TimeSpan2(time1, time2);
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
            TimeSpan2 testSpan = new TimeSpan2(time1, time2);
        }
        catch(Exception e)
        {
            fail("Something went wrong in creating a new timespan object and a exception was thrown with message: " + e.getMessage());
        }
        
        try{
            TimeSpan2 testSpan = new TimeSpan2(time2, time1);
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
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (jaar)",TestTime2.getYear(), TestTimeSpan.getEndTime().getYear());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (maand)",TestTime2.getMonth(), TestTimeSpan.getEndTime().getMonth());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (dag)",TestTime2.getDay(), TestTimeSpan.getEndTime().getDay());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (uur)",TestTime2.getHours(), TestTimeSpan.getEndTime().getHours());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (minuut)",TestTime2.getMinutes(), TestTimeSpan.getEndTime().getMinutes());
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
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (jaar)",changedTime.getYear(), TestTimeSpan.getEndTime().getYear());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (maand)",changedTime.getMonth(), TestTimeSpan.getEndTime().getMonth());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (dag)",changedTime.getDay(), TestTimeSpan.getEndTime().getDay());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (uur)",changedTime.getHours(), TestTimeSpan.getEndTime().getHours());
        assertEquals("De eindtijd komt niet overeen met de verwachte tijd. (minuut)",changedTime.getMinutes(), TestTimeSpan.getEndTime().getMinutes());
        
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
        TimeSpan2 temp = new TimeSpan2(time1, time2);
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
        TimeSpan2 timeSpan1 = new TimeSpan2(time1, time2);
        
        Time time3 = new Time(2015,4,21,13,2);
        Time time4 = new Time(2015,4,21,13,19);
        TimeSpan2 timeSpan2 = new TimeSpan2(time3, time4);
        
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
        
        Time timeAst = new Time(2015,4,21,11,10);
        Time timeAet = new Time(2015,4,21,13,10);
        TimeSpan2 timeSpanA = new TimeSpan2(timeAst, timeAet);
        
        Time timeBst = new Time(2015,4,21,11,10);
        Time timeBet = new Time(2015,4,21,13,15);
        TimeSpan2 timeSpanB = new TimeSpan2(timeBst, timeBet);
        
        Time timeCst = new Time(2015,4,21,13,15);
        Time timeCet = new Time(2015,4,21,13,20);
        TimeSpan2 timeSpanC = new TimeSpan2(timeCst, timeCet);
        
        Time timeDst = new Time(2015,4,21,13,5);
        Time timeDet = new Time(2015,4,21,13,35);
        TimeSpan2 timeSpanD = new TimeSpan2(timeDst, timeDet);
        
        Time timeEst = new Time(2015,4,21,13,30);
        Time timeEet = new Time(2015,4,21,13,35);
        TimeSpan2 timeSpanE = new TimeSpan2(timeEst, timeEet);
        
        Time timeFst = new Time(2015,4,21,13,35);
        Time timeFet = new Time(2015,4,21,13,40);
        TimeSpan2 timeSpanF = new TimeSpan2(timeFst, timeFet);
        
        Time timePst = new Time(2015,4,21,13,10);
        Time timePet = new Time(2015,4,21,13,30);
        TimeSpan2 timeSpanP = new TimeSpan2(timePst, timePet);
        
        TimeSpan2 timeSpanRes2 = new TimeSpan2(timeAst, timePet);
        assertEquals("De begin tijd zou de begin tijd van A moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.unionWith(timeSpanA).getBeginTime());
        assertEquals("Er is iets niet goed gegaan met de union (jaar)", timeSpanRes2.getEndTime().getYear(), timeSpanP.unionWith(timeSpanA).getEndTime().getYear());
        assertEquals("Er is iets niet goed gegaan met de union (maand)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.unionWith(timeSpanA).getEndTime().getMonth());
        assertEquals("Er is iets niet goed gegaan met de union (dag)", timeSpanRes2.getEndTime().getDay(), timeSpanP.unionWith(timeSpanA).getEndTime().getDay());
        assertEquals("Er is iets niet goed gegaan met de union (uur)", timeSpanRes2.getEndTime().getHours(), timeSpanP.unionWith(timeSpanA).getEndTime().getHours());
        assertEquals("Er is iets niet goed gegaan met de union (minuut", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.unionWith(timeSpanA).getEndTime().getMinutes());
        
        timeSpanRes2 = new TimeSpan2(timeBst, timePet);
        assertEquals("De begin tijd zou de begin tijd van B moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.unionWith(timeSpanB).getBeginTime());
        assertEquals("Er is iets niet goed gegaan met de union (jaar)", timeSpanRes2.getEndTime().getYear(), timeSpanP.unionWith(timeSpanB).getEndTime().getYear());
        assertEquals("Er is iets niet goed gegaan met de union (maand)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.unionWith(timeSpanB).getEndTime().getMonth());
        assertEquals("Er is iets niet goed gegaan met de union (dag)", timeSpanRes2.getEndTime().getDay(), timeSpanP.unionWith(timeSpanB).getEndTime().getDay());
        assertEquals("Er is iets niet goed gegaan met de union (uur)", timeSpanRes2.getEndTime().getHours(), timeSpanP.unionWith(timeSpanB).getEndTime().getHours());
        assertEquals("Er is iets niet goed gegaan met de union (minuut", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.unionWith(timeSpanB).getEndTime().getMinutes());

        timeSpanRes2 = new TimeSpan2(timePst, timePet);
        assertEquals("De begin tijd zou de begin tijd van P moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.unionWith(timeSpanC).getBeginTime());
        assertEquals("Er is iets niet goed gegaan met de union (jaar)", timeSpanRes2.getEndTime().getYear(), timeSpanP.unionWith(timeSpanC).getEndTime().getYear());
        assertEquals("Er is iets niet goed gegaan met de union (maand)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.unionWith(timeSpanC).getEndTime().getMonth());
        assertEquals("Er is iets niet goed gegaan met de union (dag)", timeSpanRes2.getEndTime().getDay(), timeSpanP.unionWith(timeSpanC).getEndTime().getDay());
        assertEquals("Er is iets niet goed gegaan met de union (uur)", timeSpanRes2.getEndTime().getHours(), timeSpanP.unionWith(timeSpanC).getEndTime().getHours());
        assertEquals("Er is iets niet goed gegaan met de union (minuut", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.unionWith(timeSpanC).getEndTime().getMinutes());
        
        timeSpanRes2 = new TimeSpan2(timeDst, timeDet);
        assertEquals("De begin tijd zou de begin tijd van D moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.unionWith(timeSpanD).getBeginTime());
        assertEquals("Er is iets niet goed gegaan met de union (jaar)", timeSpanRes2.getEndTime().getYear(), timeSpanP.unionWith(timeSpanD).getEndTime().getYear());
        assertEquals("Er is iets niet goed gegaan met de union (maand)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.unionWith(timeSpanD).getEndTime().getMonth());
        assertEquals("Er is iets niet goed gegaan met de union (dag)", timeSpanRes2.getEndTime().getDay(), timeSpanP.unionWith(timeSpanD).getEndTime().getDay());
        assertEquals("Er is iets niet goed gegaan met de union (uur)", timeSpanRes2.getEndTime().getHours(), timeSpanP.unionWith(timeSpanD).getEndTime().getHours());
        assertEquals("Er is iets niet goed gegaan met de union (minuut", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.unionWith(timeSpanD).getEndTime().getMinutes());
        
        timeSpanRes2 = new TimeSpan2(timePst, timeEet);
        assertEquals("De begin tijd zou de begin tijd van P moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.unionWith(timeSpanE).getBeginTime());
        assertEquals("Er is iets niet goed gegaan met de union (jaar)", timeSpanRes2.getEndTime().getYear(), timeSpanP.unionWith(timeSpanE).getEndTime().getYear());
        assertEquals("Er is iets niet goed gegaan met de union (maand)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.unionWith(timeSpanE).getEndTime().getMonth());
        assertEquals("Er is iets niet goed gegaan met de union (dag)", timeSpanRes2.getEndTime().getDay(), timeSpanP.unionWith(timeSpanE).getEndTime().getDay());
        assertEquals("Er is iets niet goed gegaan met de union (uur)", timeSpanRes2.getEndTime().getHours(), timeSpanP.unionWith(timeSpanE).getEndTime().getHours());
        assertEquals("Er is iets niet goed gegaan met de union (minuut", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.unionWith(timeSpanE).getEndTime().getMinutes());
        
        assertNull("Deze union zou geen resultaat moeten terugeven", timeSpanP.unionWith(timeSpanF));
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
                

        
        Time timeAst = new Time(2015,4,21,11,10);
        Time timeAet = new Time(2015,4,21,13,10);
        TimeSpan2 timeSpanA = new TimeSpan2(timeAst, timeAet);
        
        Time timeBst = new Time(2015,4,21,11,10);
        Time timeBet = new Time(2015,4,21,13,15);
        TimeSpan2 timeSpanB = new TimeSpan2(timeBst, timeBet);
        
        Time timeCst = new Time(2015,4,21,13,15);
        Time timeCet = new Time(2015,4,21,13,20);
        TimeSpan2 timeSpanC = new TimeSpan2(timeCst, timeCet);
        
        Time timeDst = new Time(2015,4,21,13,5);
        Time timeDet = new Time(2015,4,21,13,35);
        TimeSpan2 timeSpanD = new TimeSpan2(timeDst, timeDet);
        
        Time timeEst = new Time(2015,4,21,13,30);
        Time timeEet = new Time(2015,4,21,13,35);
        TimeSpan2 timeSpanE = new TimeSpan2(timeEst, timeEet);
        
        Time timeFst = new Time(2015,4,21,13,35);
        Time timeFet = new Time(2015,4,21,13,40);
        TimeSpan2 timeSpanF = new TimeSpan2(timeFst, timeFet);
        
        Time timePst = new Time(2015,4,21,13,10);
        Time timePet = new Time(2015,4,21,13,30);
        TimeSpan2 timeSpanP = new TimeSpan2(timePst, timePet);
        
        assertNull("A en P hebben geen intersection", timeSpanP.intersectionWith(timeSpanA));
        
        TimeSpan2 timeSpanRes2 = new TimeSpan2(timePst, timeBet);
        assertEquals("De begin tijd zou de start tijd van P moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.intersectionWith(timeSpanB).getBeginTime());
        assertEquals("De eind tijd zou de eindtijd van B moeten zijn (Year)", timeSpanRes2.getEndTime().getYear(), timeSpanP.intersectionWith(timeSpanB).getEndTime().getYear());
        assertEquals("De eind tijd zou de eindtijd van B moeten zijn (Month)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.intersectionWith(timeSpanB).getEndTime().getMonth());
        assertEquals("De eind tijd zou de eindtijd van B moeten zijn (Day)", timeSpanRes2.getEndTime().getDay(), timeSpanP.intersectionWith(timeSpanB).getEndTime().getDay());
        assertEquals("De eind tijd zou de eindtijd van B moeten zijn (Hour)", timeSpanRes2.getEndTime().getHours(), timeSpanP.intersectionWith(timeSpanB).getEndTime().getHours());
        assertEquals("De eind tijd zou de eindtijd van B moeten zijn (Min)", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.intersectionWith(timeSpanB).getEndTime().getMinutes());
        
        timeSpanRes2 = new TimeSpan2(timeCst, timeCet);
        assertEquals("De begin tijd zou de start tijd van C moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.intersectionWith(timeSpanC).getBeginTime());
        assertEquals("De eind tijd zou de eindtijd van C moeten zijn (Year)", timeSpanRes2.getEndTime().getYear(), timeSpanP.intersectionWith(timeSpanC).getEndTime().getYear());
        assertEquals("De eind tijd zou de eindtijd van C moeten zijn (Month)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.intersectionWith(timeSpanC).getEndTime().getMonth());
        assertEquals("De eind tijd zou de eindtijd van C moeten zijn (Day)", timeSpanRes2.getEndTime().getDay(), timeSpanP.intersectionWith(timeSpanC).getEndTime().getDay());
        assertEquals("De eind tijd zou de eindtijd van C moeten zijn (Hour)", timeSpanRes2.getEndTime().getHours(), timeSpanP.intersectionWith(timeSpanC).getEndTime().getHours());
        assertEquals("De eind tijd zou de eindtijd van C moeten zijn (Min)", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.intersectionWith(timeSpanC).getEndTime().getMinutes());
        
        timeSpanRes2 = new TimeSpan2(timePst, timePet);
        assertEquals("De begin tijd zou de begin tijd van P moeten zijn", timeSpanRes2.getBeginTime(), timeSpanP.intersectionWith(timeSpanD).getBeginTime());
        assertEquals("De eind tijd zou de eindtijd van P moeten zijn (Year)", timeSpanRes2.getEndTime().getYear(), timeSpanP.intersectionWith(timeSpanD).getEndTime().getYear());
        assertEquals("De eind tijd zou de eindtijd van P moeten zijn (Month)", timeSpanRes2.getEndTime().getMonth(), timeSpanP.intersectionWith(timeSpanD).getEndTime().getMonth());
        assertEquals("De eind tijd zou de eindtijd van P moeten zijn (Day)", timeSpanRes2.getEndTime().getDay(), timeSpanP.intersectionWith(timeSpanD).getEndTime().getDay());
        assertEquals("De eind tijd zou de eindtijd van P moeten zijn (Hour)", timeSpanRes2.getEndTime().getHours(), timeSpanP.intersectionWith(timeSpanD).getEndTime().getHours());
        assertEquals("De eind tijd zou de eindtijd van P moeten zijn (Min)", timeSpanRes2.getEndTime().getMinutes(), timeSpanP.intersectionWith(timeSpanD).getEndTime().getMinutes());
                
        assertNull("E en P hebben geen intersection", timeSpanP.intersectionWith(timeSpanE));
        
        assertNull("F en P hebben geen intersection", timeSpanP.intersectionWith(timeSpanF));
    }
    
}
