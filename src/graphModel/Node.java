package graphModel;

public class Node {
    
    private int id;
    
    public Node(int id) {
        this.id = id;
    }
    
    public String toString() {
        return "V" + id;
    }
}
