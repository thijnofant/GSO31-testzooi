/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf31kochfractalfx;

import calculate.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import timeutil.TimeStamp;

/**
 *
 * @author Nico Kuijpers
 */
public class JSF32W5KochClient extends Application {
    
    // Zoom and drag
    private double zoomTranslateX = 0.0;
    private double zoomTranslateY = 0.0;
    private double zoom = 1.0;
    private double startPressedX = 0.0;
    private double startPressedY = 0.0;
    private double lastDragX = 0.0;
    private double lastDragY = 0.0;

    // Koch manager
    // TO DO: Create class KochManager in package calculate
    // private KochManager kochManager;
    
    // Current level of Koch fractal
    private int currentLevel = 1;
    private List<Edge> edges = new ArrayList<>();
    private List<SerializableEdge> sedges = new ArrayList<>();
    
    // Labels for level, nr edges, calculation time, and drawing time
    private Label labelLevel;
    private Label labelNrEdges;
    private Label labelNrEdgesText;
    private Label labelCalc;
    private Label labelCalcText;
    private Label labelDraw;
    private Label labelDrawText;
    
    // Koch panel and its size
    private Canvas kochPanel;
    private final int kpWidth = 500;
    private final int kpHeight = 500;
    
    @Override
    public void start(Stage primaryStage) {
       
        // Define grid pane
        GridPane grid;
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // For debug purposes
        // Make de grid lines visible
        // grid.setGridLinesVisible(true);
        
        // Drawing panel for Koch fractal
        kochPanel = new Canvas(kpWidth,kpHeight);
        grid.add(kochPanel, 0, 3, 25, 1);
        
        // Labels to present number of edges for Koch fractal
        labelNrEdges = new Label("Nr edges:");
        labelNrEdgesText = new Label();
        grid.add(labelNrEdges, 0, 0, 4, 1);
        grid.add(labelNrEdgesText, 3, 0, 22, 1);
        
        // Labels to present time of calculation for Koch fractal
        labelCalc = new Label("Reading:");
        labelCalcText = new Label();
        grid.add(labelCalc, 0, 1, 4, 1);
        grid.add(labelCalcText, 3, 1, 22, 1);
        
        // Labels to present time of drawing for Koch fractal
        labelDraw = new Label("Drawing:");
        labelDrawText = new Label();
        grid.add(labelDraw, 0, 2, 4, 1);
        grid.add(labelDrawText, 3, 2, 22, 1);
        
        // Label to present current level of Koch fractal
        labelLevel = new Label("Level: " + currentLevel);
        grid.add(labelLevel, 0, 6);
        
        // Bittons die bestanden uitlezen.
        Button openBin = new Button();
        openBin.setText("Open binair");
        openBin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openBinair(event);
            }            
        });
        grid.add(openBin, 3, 4);
        
        Button openBinBuffer = new Button();
        openBinBuffer.setText("Open binair buffer");
        openBinBuffer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openBinairBuffer(event);
            }
        });
        grid.add(openBinBuffer, 4, 4);
        
        Button openTekst = new Button();
        openTekst.setText("Open tekst");
        openTekst.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openTekst(event);
            }
        });
        grid.add(openTekst, 3, 6);
        
        Button openTekstBuffer = new Button();
        openTekstBuffer.setText("Open tekst buffer");
        openTekstBuffer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openTekstBuffer(event);
            }
        });
        grid.add(openTekstBuffer, 4, 6);
        
        Button openMemMapped = new Button();
        openMemMapped.setText("Open memory mapped");
        openMemMapped.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openMemMapped(event);
            }
        });
        grid.add(openMemMapped, 3, 7);
               
        // Add mouse clicked event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_CLICKED,
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    kochPanelMouseClicked(event);
                }
            });
        
        // Add mouse pressed event to Koch panel
        kochPanel.addEventHandler(MouseEvent.MOUSE_PRESSED,
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    kochPanelMousePressed(event);
                }
            });
        
        // Add mouse dragged event to Koch panel
        kochPanel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                kochPanelMouseDragged(event);
            }
        });
        
        // Create Koch manager and set initial level
        resetZoom();
        //kochManager = new KochManager(this);
        //kochManager.changeLevel(currentLevel);
        
        // Create the scene and add the grid pane
        Group root = new Group();
        Scene scene = new Scene(root, kpWidth+50, kpHeight+240);
        root.getChildren().add(grid);
        
        // Define title and assign the scene for main window
        primaryStage.setTitle("Koch Fractal");
        primaryStage.setScene(scene);
        primaryStage.show();
        clearKochPanel();
    }
    
    public void clearKochPanel() {
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        gc.clearRect(0.0,0.0,kpWidth,kpHeight);
        gc.setFill(Color.BLACK);
        gc.fillRect(0.0,0.0,kpWidth,kpHeight);
    }
    
    public void drawEdge(Edge e) {
        // Graphics
        GraphicsContext gc = kochPanel.getGraphicsContext2D();
        
        // Adjust edge for zoom and drag
        Edge e1 = edgeAfterZoomAndDrag(e);
        
        // Set line color
        gc.setStroke(e1.color);
        
        // Set line width depending on level
        if (currentLevel <= 3) {
            gc.setLineWidth(2.0);
        }
        else if (currentLevel <=5 ) {
            gc.setLineWidth(1.5);
        }
        else {
            gc.setLineWidth(1.0);
        }
        
        // Draw line
        gc.strokeLine(e1.X1,e1.Y1,e1.X2,e1.Y2);
    }
    
    public void setTextNrEdges(String text) {
        labelNrEdgesText.setText(text);
    }
    
    public void setTextCalc(String text) {
        labelCalcText.setText(text);
    }
    
    public void setTextDraw(String text) {
        labelDrawText.setText(text);
    }
    
    public void requestDrawEdges() {
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                drawEdges();
            }
        });
    }
    
    private void kochPanelMouseClicked(MouseEvent event) {
        if (Math.abs(event.getX() - startPressedX) < 1.0 && 
            Math.abs(event.getY() - startPressedY) < 1.0) {
            double originalPointClickedX = (event.getX() - zoomTranslateX) / zoom;
            double originalPointClickedY = (event.getY() - zoomTranslateY) / zoom;
            if (event.getButton() == MouseButton.PRIMARY) {
                zoom *= 2.0;
            } else if (event.getButton() == MouseButton.SECONDARY) {
                zoom /= 2.0;
            }
            zoomTranslateX = (int) (event.getX() - originalPointClickedX * zoom);
            zoomTranslateY = (int) (event.getY() - originalPointClickedY * zoom);
            drawEdges();
        }
    }                                      

    private void kochPanelMouseDragged(MouseEvent event) {
        zoomTranslateX = zoomTranslateX + event.getX() - lastDragX;
        zoomTranslateY = zoomTranslateY + event.getY() - lastDragY;
        lastDragX = event.getX();
        lastDragY = event.getY();
        drawEdges();
    }

    private void kochPanelMousePressed(MouseEvent event) {
        startPressedX = event.getX();
        startPressedY = event.getY();
        lastDragX = event.getX();
        lastDragY = event.getY();
    }                                                                        

    private void resetZoom() {
        int kpSize = Math.min(kpWidth, kpHeight);
        zoom = kpSize;
        zoomTranslateX = (kpWidth - kpSize) / 2.0;
        zoomTranslateY = (kpHeight - kpSize) / 2.0;
    }

    private Edge edgeAfterZoomAndDrag(Edge e) {
        return new Edge(
                e.X1 * zoom + zoomTranslateX,
                e.Y1 * zoom + zoomTranslateY,
                e.X2 * zoom + zoomTranslateX,
                e.Y2 * zoom + zoomTranslateY,
                e.color);
    }
    
    private void openBinair(ActionEvent event) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                "/home/jsfmountpoint/bingeenbuffer"));
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Begin lezen");
            currentLevel = in.readInt();
            int edgeAmount = in.readInt();
            edges.clear();
            sedges.clear();
            for (int i = 0; i < edgeAmount; i++) {
                sedges.add((SerializableEdge)in.readObject());
            }
            in.close();
            ts.setEnd("eind lezen");
            labelCalc.setText(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        converteerEdges();
        drawEdges();
    }
    
    private void openBinairBuffer(ActionEvent event) {
        try {
            BufferedInputStream bin = new BufferedInputStream(new FileInputStream(
                "/home/jsfmountpoint/binbuffer"));
            ObjectInputStream in = new ObjectInputStream(bin);
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Begin lezen");
            currentLevel = in.readInt();
            int edgeAmount = in.readInt();
            edges.clear();
            sedges.clear();
            for (int i = 0; i < edgeAmount; i++) {
                sedges.add((SerializableEdge)in.readObject());
            }
            in.close();
            bin.close();
            ts.setEnd("eind lezen");
            labelCalc.setText(ts.toString());          
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        converteerEdges();
        drawEdges();
    }
    
    private void openTekst(ActionEvent event) {
        try {
            FileReader fr = new FileReader("/home/jsfmountpoint/tekstgeenbuffer");
            Scanner sc = new Scanner(fr);
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Begin lezen");
            currentLevel = sc.nextInt();
            int edgeAmount = sc.nextInt();
            edges.clear();
            sedges.clear();
            for (int i = 0; i < edgeAmount; i++) {
                sedges.add(new SerializableEdge(sc.nextDouble(), sc.nextDouble(),
                    sc.nextDouble(), sc.nextDouble(), 
                    Color.color(sc.nextDouble(), sc.nextDouble(), sc.nextDouble())));
            }
            sc.close();
            fr.close();
            ts.setEnd("eind lezen");
            labelCalc.setText(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        converteerEdges();
        drawEdges();
    }
    
    private void openTekstBuffer(ActionEvent event) {
        try {
            FileReader fr = new FileReader("/home/jsfmountpoint/tekstbuffer");
            BufferedReader bfr = new BufferedReader(fr);
            Scanner sc = new Scanner(bfr);
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Begin lezen");
            currentLevel = sc.nextInt();
            int edgeAmount = sc.nextInt();
            edges.clear();
            sedges.clear();
            for (int i = 0; i < edgeAmount; i++) {
                sedges.add(new SerializableEdge(sc.nextDouble(), sc.nextDouble(),
                    sc.nextDouble(), sc.nextDouble(), 
                    Color.color(sc.nextDouble(), sc.nextDouble(), sc.nextDouble())));
            }
            sc.close();
            fr.close();
            ts.setEnd("eind lezen");
            labelCalc.setText(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        converteerEdges();
        drawEdges();
    }
    
    private void openMemMapped(ActionEvent event) {
        try {
            RandomAccessFile ras = 
                    new RandomAccessFile("/home/jsfmountpoint/memorymapped", "r");
            FileChannel fc = ras.getChannel();
            MappedByteBuffer out = fc.map(MapMode.READ_ONLY, 0, ras.length());
            TimeStamp ts = new TimeStamp();
            ts.setBegin("Begin lezen");
            currentLevel = out.getInt();
            int edgeAmount = out.getInt();
            for(int i = 0; i < edgeAmount; i++) {
                sedges.add(new SerializableEdge(out.getDouble(), out.getDouble(),
                    out.getDouble(), out.getDouble(), Color.color(out.getDouble(),
                    out.getDouble(), out.getDouble())));
            }
            fc.close();
            ras.close();
            ts.setEnd("eind lezen");
            labelCalc.setText(ts.toString());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        converteerEdges();
        drawEdges();
    }
    
    private void converteerEdges() {
        for (SerializableEdge se : sedges) {
            edges.add(new Edge(se.X1, se.Y1, se.X2, se.Y2, Color.color(se.r, se.g, se.b)));
        }
    }
    
    private void drawEdges() {
        labelNrEdges.setText(Integer.toString(sedges.size()));
        labelLevel.setText("Level: " + currentLevel);
        clearKochPanel();
        TimeStamp ts = new TimeStamp();
        ts.setBegin("Begin draw");
        for (Edge e: edges) {
            drawEdge(e);
        }
        ts.setEnd("end draw");
        labelDraw.setText(ts.toString());
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
