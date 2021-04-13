package sample.Classes;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.Map;

public class Node {
    private int id;
    private Node previous;
    private ArrayList<Integer> neighbours;

    public Node(int id, Node previous, ArrayList<Integer> neighbours) {
        this.id = id;
        this.previous = previous;
        this.neighbours = neighbours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public ArrayList<Integer> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(ArrayList<Integer> neighbours) {
        this.neighbours = neighbours;
    }
}
