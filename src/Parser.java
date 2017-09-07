

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Parser {

    private String filePath;
    private int numOfNodes;
    private int runDuration;
    
    private NodeInfo[][] nodesData;
    
    Parser (String filePath) {
        this.filePath = filePath;
        this.numOfNodes = 0;
        this.runDuration = 0;
        
        processJSONFiles();
    }
    
    private void getConfigurations(File[] files) {
        ObjectMapper mapper = new ObjectMapper();
        
        for (File file: files) {
            String[] tokens = file.getName().split("[-.]");
            int epoch = Integer.parseInt(tokens[0]);
            int nodeId = Integer.parseInt(tokens[1]);
            
            if (epoch > runDuration) {
                runDuration = epoch;
            }
            if (nodeId > numOfNodes) {
                numOfNodes = nodeId;
            }
        }
        
        nodesData = new NodeInfo[runDuration + 1][numOfNodes + 1];
    }
    
    private void processJSONFiles() {
        
        File dir = new File(filePath);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        ObjectMapper mapper = new ObjectMapper();
        
        getConfigurations(files);
        
        for(File file: files) {
            try {
                NodeInfo node = mapper.readValue(file, NodeInfo.class);
                int epoch = node.getEpoch();
                int id = node.getId();
                
                nodesData[epoch][id] = node;
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public int getNumOfNodes() {
        return numOfNodes;
    }
    
    public int getRunDuration() {
        return runDuration;
    }
    
    public NodeInfo[][] getNodesData() {
        return nodesData;
    }
    
    public static void main(String[] args) {
        Parser parse = new Parser("web/");
    }
}
