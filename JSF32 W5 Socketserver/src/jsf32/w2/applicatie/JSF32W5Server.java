/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf32.w2.applicatie;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author jsf3
 */
public class JSF32W5Server {
    public static int PORT = 1100;
    public ExecutorService threadPool = Executors.newCachedThreadPool();
    
    public static void main(String[] args) {
        JSF32W5Server server = new JSF32W5Server();
        server.run();
    }
    
    public void run() {
        while (true) {
            try {
                ServerSocket listener = new ServerSocket(PORT);
                Socket client = listener.accept();
                threadPool.submit(new KochRunnable(client));
            }
            catch (IOException e) {               
            }
        } 
    }
    
//    private void schrijfObjectStreamBuffer() {
//        try {
//            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(
//                "/home/jsfmountpoint/binbuffer"));
//            ObjectOutputStream out = new ObjectOutputStream(bout);
//            TimeStamp ts = new TimeStamp();
//            ts.setBegin("Edges schrijven met ObjectStream met buffer.");
//            out.writeInt(kochLevel);
//            out.writeInt(edges.size());
//            for (SerializableEdge e : edges) {
//                out.writeObject(e);
//            }
//            out.close();
//            bout.close();
//            ts.setEnd("Schrijven klaar.");
//            System.out.println(ts.toString());
//        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
