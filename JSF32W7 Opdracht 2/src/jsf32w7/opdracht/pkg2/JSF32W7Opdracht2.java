/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32w7.opdracht.pkg2;

import com.sun.jna.ptr.IntByReference;

/**
 *
 * @author Robert
 */
public class JSF32W7Opdracht2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IGetDiskFreeSpace lib = IGetDiskFreeSpace.INSTANCE;
        IntByReference sectorsPerCluster = new IntByReference();
        IntByReference bytesPerSector = new IntByReference();
        IntByReference freeClusters = new IntByReference();
        IntByReference totalClusters = new IntByReference();
        int totalFreeSpace;
        
        lib.GetDiskFreeSpaceA("C:\\", sectorsPerCluster, bytesPerSector, 
                freeClusters, totalClusters);
        
        totalFreeSpace = freeClusters.getValue() * sectorsPerCluster.getValue()
                * (bytesPerSector.getValue()/1000);
        
        System.out.println("Free clusters: " + freeClusters.getValue() + "\n"
                            + "Sectors per cluster: " + sectorsPerCluster.getValue() + "\n"
                            + "Bytes per sector: " + bytesPerSector.getValue() + "\n");
        System.out.println("Free disk space = " + totalFreeSpace + " kbytes");
    }
    
}
