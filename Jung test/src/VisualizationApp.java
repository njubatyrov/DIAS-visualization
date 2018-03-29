

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class VisualizationApp {
	
	private HashMap < Integer, HashMap < Integer, ArrayList < Integer > > > pushLogs;
	private HashMap < Integer, Double > epochSum;
	private HashMap < Integer, HashMap < Integer, Double > > sumLogs;
	
	private int N;
	private int runDuration;
	
	public VisualizationApp() {
		
		this.pushLogs = new HashMap<>();
		this.epochSum = new HashMap<>();
		this.sumLogs = new HashMap<>();
		this.N = 0;
		this.runDuration = 0;
		
		try {
			readJSONFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Reads JSON files from the web/ directory to obtain PUSH messages per epoch and per user.
	 * 
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	private void readJSONFiles() throws JsonProcessingException, IOException {
		File dir = new File("web/");
		File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
		
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i = 0; i < files.length; i++) {
			
			JsonNode root = mapper.readTree(files[i]);
			
			JsonNode pushNode = root.path("PUSH");
			int epoch = root.path("epoch").asInt();
			int id = root.path("id").asInt();
			
			N = Math.max(N, id);
			runDuration = Math.max(runDuration, epoch);
			
			double state = root.path("state").asDouble();
			double sum = root.path("sum").asDouble();
			
			epochSum.put(epoch, epochSum.getOrDefault(epoch, 0.0) + state);
			
			if(sumLogs.containsKey(epoch) == true) {
				HashMap < Integer, Double > node = sumLogs.get(epoch);
				node.put(id, sum);
			} else {
				HashMap < Integer, Double > node = new HashMap<>();
				node.put(id, sum);
				sumLogs.put(epoch, node);
			}
			
			Iterator <JsonNode> it = pushNode.elements();
			ArrayList < Integer > edges = new ArrayList<>();
			
			while(it.hasNext()) {
				JsonNode j = it.next();
				edges.add(j.asInt());
			}
			
			if(pushLogs.containsKey(epoch) == true) {
				HashMap < Integer, ArrayList < Integer > > node = pushLogs.get(epoch);
				node.put(id, edges);
			} else {
				HashMap < Integer, ArrayList < Integer > > node = new HashMap<>();
				node.put(id, edges);
				pushLogs.put(epoch, node);
			}
		}
	}
	
	/**
	 * Shows the graph of PUSH messages per epoch.
	 * @throws IOException 
	 */
	public void displayView() throws IOException {
		List < Integer > permutation = new ArrayList<>();
		
//		FileSinkImages pic = new FileSinkImages(OutputType.PNG, Resolutions.HD720);
//		pic.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
		
		for(int i = 0; i < N; i++) {
			permutation.add(i);
		}
		
		for(int epoch = 0; epoch <= runDuration; epoch++) {
			HashMap < Integer, ArrayList < Integer > > nodes = pushLogs.getOrDefault(epoch, null);
			if(nodes == null) {
				continue;
			}
			
			
			Collections.shuffle(permutation);
			for(int i = 0; i < N; i++) {
				int nodeId = permutation.get(i);
				ArrayList < Integer > edges = nodes.getOrDefault(nodeId, null);
				
				if(edges == null) {
					continue;
				}
				for(int j = 0; j < edges.size(); j++) {
					try{Thread.sleep(5);} catch(Exception e) {}
				}
				
			}
//			pic.writeAll(graphManager.getGraph(), "snaps/" + epoch + ".png");
//			System.out.println(epoch);
			try{Thread.sleep(100);} catch(Exception e) {}
		}
		
	}
	
	public void displayEpoch(int epoch) {
	    
	    List < Integer > permutation = new ArrayList<>();
	    
	    for(int i = 0; i < N; i++) {
            permutation.add(i);
        }
        
        HashMap < Integer, ArrayList < Integer > > nodes = pushLogs.getOrDefault(epoch, null);
        if(nodes == null) {
                return;
        }
        
        Collections.shuffle(permutation);
        for(int i = 0; i < N; i++) {
            int nodeId = permutation.get(i);
            ArrayList < Integer > edges = nodes.getOrDefault(nodeId, null);
            if(edges == null) {
                continue;
            }
            for(int j = 0; j < edges.size(); j++) {
//                try{Thread.sleep(5);} catch(Exception e) {}
            }
        }
	}
	public List < Pair > printEpoch(int epoch) {
        List < Pair > list = new LinkedList<>();
        
	    List < Integer > permutation = new ArrayList<>();
        
        for(int i = 0; i < N; i++) {
            permutation.add(i);
        }
        
        HashMap < Integer, ArrayList < Integer > > nodes = pushLogs.getOrDefault(epoch, null);
        
        if(nodes == null) {
                return list;
        }
        
        Collections.shuffle(permutation);
        for(int i = 0; i < N; i++) {
            int nodeId = permutation.get(i);
            ArrayList < Integer > edges = nodes.getOrDefault(nodeId, null);
            
            if(edges == null) {
                continue;
            }
            for(int j = 0; j < edges.size(); j++) {
                list.add(new Pair(nodeId, edges.get(j)));
            }
        }
        return list;
	}
	public int getNumberOfPushMessages(int epoch) {
	    HashMap < Integer, ArrayList < Integer > > nodes = pushLogs.getOrDefault(epoch, null);
	    int result = 0;
	    if(nodes == null) {
	        return 0;
	    }
	    for(int i = 0; i < N; i++) {
            ArrayList < Integer > edges = nodes.getOrDefault(i, null);
            if (edges != null) {
                result += edges.size();
            }
        }
	    return result;
	}
	
	
	public int getN() {
	    return N;
	}
	
	public int getRunDuration() {
	    return runDuration;
	}
	
	public static void main(String[] args) throws JsonProcessingException, IOException {
		VisualizationApp app = new VisualizationApp();
		app.printEpoch(15);
	}
	
}

