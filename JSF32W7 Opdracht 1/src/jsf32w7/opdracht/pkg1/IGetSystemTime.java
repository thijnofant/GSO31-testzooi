/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32w7.opdracht.pkg1;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 *
 * @author Robert
 */
public interface IGetSystemTime extends Library {
    public IGetSystemTime INSTANCE=(IGetSystemTime)Native.loadLibrary("Kernel32",IGetSystemTime.class);
    
    public void GetSystemTime(SYSTEMTIME result);
}
