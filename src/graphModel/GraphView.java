package graphModel;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.List;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import model.NodeInfoCollection;
import model.Pair;
import view.MainView;

public class GraphView {
    
    private String layoutName;
    
    private MainView mainView;
    
    private Graph<Integer, String> g;
    private Layout<Integer, String> layout;
    private BasicVisualizationServer<Integer, String> view;
    private NodeInfoCollection collection;
    
    private List <String> edgeList;
    
    boolean [][] was1 = new boolean[111][111];
    boolean [][] was2 = new boolean[111][111];
    
    public GraphView(String layoutName, NodeInfoCollection collection, MainView mainView) {
        this.mainView = mainView;
        this.layoutName = layoutName;
        this.collection = collection;
        
        g = new SparseMultigraph<>();
        initGraphNodes();
        layout = getNewLayout(layoutName);
        view = new BasicVisualizationServer<Integer, String>(layout);
        
        // paints the vertex color
        Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
            public Paint transform(Integer i) {
            return Color.GREEN;
            }
        }; 
        // changes the vertex size
        Transformer<Integer,Shape> vertexSize = new Transformer<Integer,Shape>(){
            public Shape transform(Integer i){
                Ellipse2D circle = new Ellipse2D.Double(-7.5, -7.5, 15, 15);
                return circle;
            }
        };
        Transformer<String, Paint> edgePaint = new Transformer<String, Paint>() {
            @Override
            public Paint transform(String s) {    // s represents the edge
                     if (s.charAt(0) == 2){    // your condition
                         return Color.RED;
                     }
                     else {
                         return Color.DARK_GRAY;
                     }
                }
            };

         // vv is the VirtualizationViewer
        view.getRenderContext().setEdgeDrawPaintTransformer(edgePaint);
        view.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        view.getRenderContext().setVertexShapeTransformer(vertexSize);
    }
    
    private void initGraphNodes() {
        for(int i = 0; i <= collection.getNumOfNodes(); i++) {
            g.addVertex(i);
        }
    }
    
    private void removeEdges() {
        for(int i = 0; i < 50; i++) {
            for(int j = i; j < 50; j++) {
                if(was1[i][j] == true) {
                    was1[i][j] = false;
                    g.removeEdge(1 + i + "$" + j);
                }
                if (was2[i][j] == true) {
                    was2[i][j] = false;
                    g.removeEdge(2 + i + "$" + j);
                }
            }
        }
        
    }
    
    private void addEdge(int type, int from, int to) {
        if(from > to) {
            int tmp = from;
            from = to;
            to = tmp;
        }

        if(was1[from][to] == false && type == 1) {
            was1[from][to] = true;
            g.addEdge(type + from + "$" + to, from, to);
        }
        
        if ( was2[from][to] == false && type == 2) {
            was2[from][to] = true;
            g.addEdge(type + from + "$" + to, from, to);
        }
        
    }
    
    public void updateGraph(int epoch) {
        
        removeEdges();
        if (mainView.getPushRadioButton().isSelected()) {
            List < Pair > list =  collection.getPushEdgesForEpoch(epoch);
            for(Pair entry: list) {
                addEdge(1, entry.getFirst(), entry.getSecond());
            }
        }
        
        if (mainView.getPullRadioButton().isSelected()) {
            List < Pair > list =  collection.getPullEdgesForEpoch(epoch);
            for(Pair entry: list) {
                addEdge(2, entry.getFirst(), entry.getSecond());
            } 
        }
        view.repaint();
//        vv.setGraphLayout(new KKLayout(g));;
    }
    
    private Layout<Integer, String> getNewLayout(String layoutName) {
        switch(layoutName) {
            case "KKLayout":
                return new KKLayout(g);
            case "CircleLayout":
                return new CircleLayout(g);
            case "FRLayout":
                return new FRLayout(g);
            case "SpringLayout":
                return new SpringLayout(g);
            default:
                return new CircleLayout(g);
        }
    }

    public String getLayoutName() {
        return layoutName;
    }

    public void setLayoutName(String layoutName) {
        this.layoutName = layoutName;
    }

    public Graph<Integer, String> getG() {
        return g;
    }

    public void setG(Graph<Integer, String> g) {
        this.g = g;
    }

    public Layout<Integer, String> getLayout() {
        return layout;
    }

    public void setLayout(Layout<Integer, String> layout) {
        this.layout = layout;
    }

    public BasicVisualizationServer<Integer, String> getView() {
        return view;
    }

    public void setView(BasicVisualizationServer<Integer, String> view) {
        this.view = view;
    }
    

}
