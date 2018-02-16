package model;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;

public class GraphManager {
    private Graph<Integer, String> graph;
    private NodeInfoCollection collection;
    private ArrayList<String> pushMessages;
    
    private boolean hasPush;
    private boolean hasPull;
    
    public GraphManager(NodeInfoCollection collection) {
        this.collection = collection;
        pushMessages = new ArrayList<>();
        graph = new SparseMultigraph<>();
        
        hasPush = false;
        hasPull = false;
        
        initGraph();
    }
    
    public void removeEdges() {
        for (String str: pushMessages) {
            graph.removeEdge(str);
        }
        pushMessages.clear();
    }
    
    public void addEdge(String c, int from, int to) {
        pushMessages.add(c + from + "$" + to);
        graph.addEdge(c + from + "$" + to, from, to);
    }
    
    public void updateGraph(int epoch) {
        removeEdges();
        if (hasPush) {
            List < Pair > list =  collection.getPushEdgesForEpoch(epoch);
            for (Pair entry: list) {
                addEdge("@", entry.getFirst(), entry.getSecond());
            }
        }
        
        if (hasPull) {
            List < Pair > list =  collection.getPullEdgesForEpoch(epoch);
            for (Pair entry: list) {
                addEdge("#", entry.getFirst(), entry.getSecond());
            }
        }
    }
    
    private void initGraph() {
        for(int i = 0; i < collection.getNumOfNodes(); i++) {
            graph.addVertex(i);
        }
    }

    
    public Graph<Integer, String> getGraph() {
        return graph;
    }
    public void setHasPush(boolean value) {
        hasPush = value;
    }
    public void setHasPull(boolean value) {
        hasPull = value;
    }
}
