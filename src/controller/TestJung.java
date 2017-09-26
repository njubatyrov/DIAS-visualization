package controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import model.NodeInfoCollection;
import model.Pair;

public class TestJung extends JFrame {
    
    private JPanel sliderPanel;
    private JSlider slider;
    boolean [][] was = new boolean[111][111];
    
    private Renderer mRenderer;
    NodeInfoCollection collection;
    
    Graph<Integer, String> g;
    Layout<Integer, String> layout;
    BasicVisualizationServer<Integer,String> vv;
    
    public TestJung() throws FileNotFoundException {
        super("KK Layout");
        
        this.setBounds(0, 0, 600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        
        collection = new NodeInfoCollection("web/");
        
        g = new SparseMultigraph<Integer, String>();
        initGraph();
        
        layout = new FRLayout(g);
//        layout.setSize(new Dimension(300,300)); 
        vv = new BasicVisualizationServer<Integer,String>(layout);
//        vv.setPreferredSize(new Dimension(350,350)); 
                
        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
            public Paint transform(Integer i) {
            return Color.GREEN;
            }
            }; 
        Transformer<Integer,Shape> vertexSize = new Transformer<Integer,Shape>(){
            public Shape transform(Integer i){
                Ellipse2D circle = new Ellipse2D.Double(-7.5, -7.5, 15, 15);
                // in this case, the vertex is twice as large
                return circle;
            }
        };
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
        initSliderPanel();
        this.setVisible(true);
    }
   
    private void initSliderPanel() throws FileNotFoundException {
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(3, 3));
       
        slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(collection.getRunDuration());
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        sliderPanel.add(slider);
        sliderPanel.setBackground(Color.WHITE);
        
       
        getContentPane().add(sliderPanel, BorderLayout.NORTH);
        getContentPane().add((Component) vv, BorderLayout.CENTER);
       
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
               
                updateGraph((int)((JSlider)e.getSource()).getValue());
            }
        });
    }
    
    private void initGraph() {
        for(int i = 0; i < 50; i++) {
            g.addVertex(i);
        }
    }
    
    private void removeEdges() {
        for(int i = 0; i < 50; i++) {
            for(int j = i; j < 50; j++) {
                if(was[i][j] == true) {
                    was[i][j] = false;
                    g.removeEdge(i + "$" + j);
                }
            }
        }
        
    }
    
    private void addEdge(int from, int to) {
        if(from > to) {
            int tmp = from;
            from = to;
            to = tmp;
        }

        if(was[from][to] == false) {
            was[from][to] = true;
            g.addEdge(from + "$" + to, from, to);
        }
        
    }
    
    private void updateGraph(int epoch) {
        removeEdges();
        List < Pair > list =  collection.getPushEdgesForEpoch(epoch);
        
        for(Pair entry: list) {
            addEdge(entry.getFirst(), entry.getSecond());
        }
        vv.setGraphLayout(new CircleLayout(g));
        vv.repaint();
//        vv.setGraphLayout(new KKLayout(g));;
    }
    public static void main(String[] args) throws FileNotFoundException {
        TestJung test = new TestJung();
    }
}
