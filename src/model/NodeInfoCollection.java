package model;
/**
 * Representation of a network, collection of NodeInfos.
 * @author jubatyrn
 */
import java.util.LinkedList;
import java.util.List;

import dataProcessing.Parser;

public class NodeInfoCollection {
    
    //TODO: at the end remove initial filePath name
    private String filePath = "web/";
    private int numOfNodes;
    private int runDuration;
    
    private NodeInfo[][] data;
    
    public NodeInfoCollection(String filePath) {
        this.filePath = filePath;
        Parser parser = new Parser(filePath);
        
        this.numOfNodes = parser.getNumOfNodes();
        this.runDuration = parser.getRunDuration();
        this.data = parser.getNodesData();
    }
    
    public List<Pair> getPushEdgesForEpoch(int epoch) {
        List<Pair> list = new LinkedList<>();
        
        if (epoch < 0 || epoch > runDuration) {
            return list;
        }
        
        for (int i = 0; i <= numOfNodes; i++) {
            List<Integer> pushNodes = data[epoch][i].getPush();
            
            for (int to: pushNodes) {
                list.add(new Pair(i, to));
            }
        }
        return list;
    }
    
    public List<Pair> getPullEdgesForEpoch(int epoch) {
        List<Pair> list = new LinkedList<>();
        
        if (epoch < 0 || epoch > runDuration) {
            return list;
        }
        
        for (int i = 0; i <= numOfNodes; i++) {
            List<Integer> pullNodes = data[epoch][i].getPull();
            
            for (int to: pullNodes) {
                list.add(new Pair(i, to));
            }
        }
        return list;
    }
    
    public List<Pair> getPSSEdgesForEpoch(int epoch) {
        List<Pair> list = new LinkedList<>();
        
        if (epoch < 0 || epoch > runDuration) {
            return list;
        }
        
        for (int i = 0; i <= numOfNodes; i++) {
            List<Integer> pss0Nodes = data[epoch][i].getPss0();
            List<Integer> pss1Nodes = data[epoch][i].getPss1();
            List<Integer> pss2Nodes = data[epoch][i].getPss2();
            List<Integer> pss3Nodes = data[epoch][i].getPss3();
            
            for (int to: pss0Nodes) {
                list.add(new Pair(i, to));
            }
            for (int to: pss1Nodes) {
                list.add(new Pair(i, to));
            }
            for (int to: pss2Nodes) {
                list.add(new Pair(i, to));
            }
            for (int to: pss3Nodes) {
                list.add(new Pair(i, to));
            }
        }
        return list;
    }
    
    public int getRunDuration() {
        return runDuration;
    }
    public int getNumOfNodes() {
        return numOfNodes;
    }
    public String toString() {
        return "Epochs: " + runDuration + ", Nodes: " + numOfNodes + "\n";
    }
    
    public static void main(String[] args) {
        NodeInfoCollection nodesCollection = new NodeInfoCollection("web/");
        System.out.println(nodesCollection.toString());
    }
}
