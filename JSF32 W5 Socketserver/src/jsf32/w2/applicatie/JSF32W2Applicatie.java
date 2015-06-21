/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32.w2.applicatie;
import calculate.*;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import timeutil.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author jsf3
 */
public class JSF32W2Applicatie implements Observer {
    private int kochLevel = 0;
    private int schrijfModus = 0;
    private KochFractal kochFractal = new KochFractal();
    private List<SerializableEdge> edges = new ArrayList<>();
    private final Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {
        JSF32W2Applicatie applicatie = new JSF32W2Applicatie();
        applicatie.run();
    }
    
    public void run() {
        kochFractal.addObserver(this);
        
        while (kochLevel < 1 || kochLevel > 11) {
            System.out.print("Voer een geldig koch level in: ");
            try {
                kochLevel = in.nextInt();
                Thread.sleep(1000);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }         
        }
        
        kochFractal.setLevel(kochLevel);
        
        while (schrijfModus < 1 || schrijfModus > 5) {
            System.out.print("1 = Binary zonder buffer \n"
                    + "2 = Binary met buffer \n"
                    + "3 = Tekst zonder buffer \n"
                    + "4 = Tekst met buffer \n"
                    + "5 = Memory mapped file \n"
                    + "Voer een geldige schrijfmodus in: ");
            try {
                schrijfModus = in.nextInt();
                Thread.sleep(1000);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }         
        }
        
        calculeerEdges();
        
        switch (schrijfModus) {
            case 1: schrijfObjectStream();
                    break;
            case 2: schrijfObjectStreamBuffer();
                    break;
            case 3: schrijfFileWriter();
                    break;
            case 4: schrijfFileWriterBuffer();
                    break;
            case 5: schrijfMemoryMapped();
        }
    }
    
    private void schrijfObjectStream() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
                "/home/jsfmountpoint/bingeenbuffer"));
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Edges schrijven met ObjectStream zonder buffer.");
            out.writeInt(kochLevel);
            out.writeInt(edges.size());
            for (SerializableEdge e : edges) {
                out.writeObject(e);
            }
            out.close();
            ts.setEnd("Schrijven klaar.");
            System.out.println(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void schrijfObjectStreamBuffer() {
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(
                "/home/jsfmountpoint/binbuffer"));
            ObjectOutputStream out = new ObjectOutputStream(bout);
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Edges schrijven met ObjectStream met buffer.");
            out.writeInt(kochLevel);
            out.writeInt(edges.size());
            for (SerializableEdge e : edges) {
                out.writeObject(e);
            }
            out.close();
            bout.close();
            ts.setEnd("Schrijven klaar.");
            System.out.println(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void schrijfFileWriter() {
        try {
            FileWriter fw = new FileWriter("/home/jsfmountpoint/tekstgeenbuffer");
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Edges schrijven met FileWriter zonder buffer.");
            fw.write(kochLevel + " ");
            fw.write(edges.size() + " ");
            for (SerializableEdge e : edges) {
                fw.write(e.toString());
            }
            fw.close();
            ts.setEnd("Schrijven klaar.");
            System.out.println(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void schrijfFileWriterBuffer() {
        try {
            FileWriter fw = new FileWriter("/home/jsfmountpoint/tekstbuffer");
            BufferedWriter bfw = new BufferedWriter(fw); 
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Edges schrijven met FileWriter met buffer.");
            bfw.write(kochLevel + " ");
            bfw.write(edges.size() + " ");
            for (SerializableEdge e : edges) {
                bfw.write(e.toString());
            }
            bfw.close();
            fw.close();
            ts.setEnd("Schrijven klaar.");
            System.out.println(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void schrijfMemoryMapped() {
        try {
            RandomAccessFile raf = 
                    new RandomAccessFile("/home/jsfmountpoint/memorymapped", "rw");
            FileChannel fc = raf.getChannel();
            MappedByteBuffer out = fc.map(MapMode.READ_WRITE, 0, (8 + (edges.size()*56)));
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Edges schrijven in memory mapped file.");
            out.putInt(kochLevel);
            out.putInt(edges.size());
            for (SerializableEdge e : edges) {
                out.putDouble(e.X1);
                out.putDouble(e.Y1);
                out.putDouble(e.X2);
                out.putDouble(e.Y2);
                out.putDouble(e.r);
                out.putDouble(e.g);
                out.putDouble(e.b);
            }
            fc.close();
            raf.close();
            ts.setEnd("Schrijven klaar.");
            System.out.println(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void calculeerEdges() {
        TimeStamp ts = new TimeStamp();
        System.out.println("Edges berekenen...");
        ts.setBegin("Begin edges berekenen.");
        kochFractal.generateBottomEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateRightEdge();
        ts.setEnd("Berekenen klaar.");
        System.out.println(ts.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        edges.add((SerializableEdge)arg);
    }
}
