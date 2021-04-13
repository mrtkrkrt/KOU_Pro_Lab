package sample.Classes;

import java.util.ArrayList;

public abstract class Character {
    private int id;
    private String name;
    private String type;
    private ArrayList<ArrayList<Integer>> coordinates = new ArrayList<>();

    public Character(int id, String name, String type, ArrayList<ArrayList<Integer>> coordinates) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ArrayList<Integer>> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<ArrayList<Integer>> coordinates) {
        this.coordinates = coordinates;
    }
    public abstract ArrayList<Integer> enKısaYol();
}
