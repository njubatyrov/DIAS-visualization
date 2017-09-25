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
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import model.NodeInfoCollection;
import model.Pair;

public class GraphView {
    
    private String layoutName;
    
    private Graph<Node, Edge> g;
    private Layout<Node, Edge> layout;
    private BasicVisualizationServer<Node, Edge> view;
    private NodeInfoCollection collection;
    
    private List <Edge> edgeList;
    
    public GraphView(String layoutName, NodeInfoCollection collection) {
        this.layoutName = layoutName;
        this.collection = collection;
        
        g = new DirectedSparseMultigraph<>();
        initGraphNodes();
        layout = getNewLayout(layoutName);
        view = new BasicVisualizationServer<Node, Edge>(layout);
        
        // paints the vertex color
        Transformer<Node,Paint> vertexPaint = new Transformer<Node,Paint>() {
            public Paint transform(Node i) {
            return Color.GREEN;
            }
        }; 
        // changes the vertex size
        Transformer<Node,Shape> vertexSize = new Transformer<Node,Shape>(){
            public Shape transform(Node i){
                Ellipse2D circle = new Ellipse2D.Double(-7.5, -7.5, 15, 15);
                return circle;
            }
        };
        view.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        view.getRenderContext().setVertexShapeTransformer(vertexSize);
    }
    
    public void updateGraph(int epoch) {
        removeAllEdges();
        
        addPushMessages(epoch);
    }
    void addPushMessages(int epoch) {
        List < Pair > list =  collection.getPushEdgesForEpoch(epoch);
        
        for(Pair entry: list) {
            addEdge(entry.getFirst(), entry.getSecond());
        }
    }
    private void removeAllEdges() {
        
    }
    private void initGraphNodes() {
        for(int i = 0; i <= collection.getNumOfNodes(); i++) {
            Node node = new Node(i);
            g.addVertex(node);
        }
    }
    
    private Layout<Node, Edge> getNewLayout(String layoutName) {
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

    public Graph<Node, Edge> getG() {
        return g;
    }

    public void setG(Graph<Node, Edge> g) {
        this.g = g;
    }

    public Layout<Node, Edge> getLayout() {
        return layout;
    }

    public void setLayout(Layout<Node, Edge> layout) {
        this.layout = layout;
    }

    public BasicVisualizationServer<Node, Edge> getView() {
        return view;
    }

    public void setView(BasicVisualizationServer<Node, Edge> view) {
        this.view = view;
    }
    

}
