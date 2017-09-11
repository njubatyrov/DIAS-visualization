package graphModel;

public class Edge {
    
    private int id;
    
    public Edge(int id) {
        this.id = id;
    }
    
    public String toString() {
        return "E" + id;
    }
}
