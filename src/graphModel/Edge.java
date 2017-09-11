package graphModel;

public class Edge {
    
    private String id;
    
    public Edge(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    public String toString() {
        return "E" + id;
    }
}
