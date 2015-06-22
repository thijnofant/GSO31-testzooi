/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32w7.opdracht.pkg1;

import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Robert
 */
public class SYSTEMTIME extends Structure {
    public short wYear;
    public short wMonth;
    public short wDayOfWeek;
    public short wDay;
    public short wHour;
    public short wMinute;
    public short wSecond;
    public short wMilliseconds;
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Year: " + this.wYear + "\n");
        sb.append("Month: " + this.wMonth + "\n");
        sb.append("Day of week: " + this.wDayOfWeek + "\n");
        sb.append("Day: " + this.wDay + "\n");
        sb.append("Hour: " + this.wHour + "\n");
        sb.append("Minute: " + this.wMinute + "\n");
        sb.append("Second: " + this.wSecond + "\n");
        sb.append("Milliseconds: " + this.wMilliseconds + "\n");
        return sb.toString();
    }
    
    public int getHour() {
        return wHour;
    }
    
    public int getMinute() {
        return wMinute;
    }
    
    public int getSecond() {
        return wSecond;
    }
    
    public int getMilliseconds() {
        return wMilliseconds;
    }
    
    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"wYear", "wMonth", "wDayOfWeek", 
            "wDay", "wHour", "wMinute", "wSecond", "wMilliseconds"});
    }
}
