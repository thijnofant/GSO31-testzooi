/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32w7.opdracht.pkg1;

import java.io.Console;

/**
 *
 * @author Robert
 */
public class JSF32W7Opdracht1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IGetSystemTime lib = IGetSystemTime.INSTANCE;      
        SYSTEMTIME systemtime = new SYSTEMTIME();
        
        lib.GetSystemTime(systemtime);
        System.out.println("Begin: " + systemtime.getHour() + ":" + systemtime.getMinute() +
                ":" + systemtime.getSecond() + ":" + systemtime.getMilliseconds());
        for(int i=0;i<1000000000;i++) { }
        lib.GetSystemTime(systemtime);
        System.out.println("End: " + systemtime.getHour() + ":" + systemtime.getMinute() +
                ":" + systemtime.getSecond() + ":" + systemtime.getMilliseconds());
        
        System.out.println(System.nanoTime());
        for(int i=0;i<1000000000;i++) { }
        System.out.println(System.nanoTime());
    }
}
