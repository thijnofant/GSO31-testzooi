/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32.w2.applicatie;

import calculate.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 *
 * @author Robert
 */
public class KochRunnable implements Runnable, Observer {
    private final KochFractal kochFractal;
    private String protocol;
    private final Socket client;
    private ArrayList<SerializableEdge> edges;
    
    public KochRunnable(Socket client) {
        this.kochFractal = new KochFractal();
        this.kochFractal.addObserver(this);
        this.edges = new ArrayList<>();
        this.client = client;
    }
    
    @Override
    public void run() {
        try {
            try {
                InputStream inStream = client.getInputStream();
                Scanner inScanner = new Scanner(inStream);
                
                boolean validRequest = false;
                while (!validRequest) {
                    protocol = inScanner.nextLine();
                    if (protocol.matches("calc|calcRealTime|zoom")){
                        validRequest = true;
                    }
                }
                
                
            }
            finally {
                client.close();
            }
        }
        catch (IOException e) {
            
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        edges.add((SerializableEdge) o1);
    }
}
