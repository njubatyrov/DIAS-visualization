package controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Paint;
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
import edu.uci.ics.jung.algorithms.layout.KKLayout;
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

    private JLabel epochLabel;
    private JLabel pushMessagesLabel;
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
        this.setLayout(new BorderLayout());
        
        collection = new NodeInfoCollection("web/");
        
        g = new SparseMultigraph<Integer, String>();
        initGraph();
        
        layout = new KKLayout(g);
//        layout.setSize(new Dimension(300,300)); 
        vv = new BasicVisualizationServer<Integer,String>(layout);
//        vv.setPreferredSize(new Dimension(350,350)); 
                
        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
            public Paint transform(Integer i) {
            return Color.GREEN;
            }
            }; 
        
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        initSliderPanel();
        this.setVisible(true);
    }
   
    private void initSliderPanel() throws FileNotFoundException {
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(3, 1));
       
        slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(collection.getRunDuration());
        slider.setMinorTickSpacing(2);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        
        sliderPanel.add(slider);
        sliderPanel.setBackground(Color.WHITE);
        
        epochLabel = new JLabel("Epoch: ", JLabel.CENTER);
        
        pushMessagesLabel = new JLabel("Push messages: ", JLabel.CENTER);
        
        sliderPanel.add(epochLabel);
        sliderPanel.add(pushMessagesLabel);
        
        
       
        this.add(sliderPanel, BorderLayout.NORTH);
        this.add((Component) vv, BorderLayout.CENTER);
       
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                epochLabel.setText("Epoch:" + ((JSlider)e.getSource()).getValue());
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
        List < Pair > list =  collection.getPSSEdgesForEpoch(epoch);
        
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
