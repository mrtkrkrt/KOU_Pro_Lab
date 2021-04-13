package sample.Classes;

public class Edge {
    private Node source;
    private Node destination;
    private boolean isVisited;

    public Edge(Node source, Node destination, boolean isVisited) {
        this.source = source;
        this.destination = destination;
        this.isVisited = isVisited;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }
}
