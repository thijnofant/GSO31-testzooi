/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32w7.opdracht.pkg2;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

/**
 *
 * @author Robert
 */
public interface IGetDiskFreeSpace extends Library {
    public IGetDiskFreeSpace INSTANCE=(IGetDiskFreeSpace)Native.loadLibrary(
            "Kernel32",IGetDiskFreeSpace.class);
    
    public boolean GetDiskFreeSpaceA(String lpRootPathName, IntByReference lpSectorsPerCluster,
            IntByReference lpBytesPerSector, IntByReference lpNumberOfFreeClusters,
            IntByReference lpTotalNumberOfClusters);
}
