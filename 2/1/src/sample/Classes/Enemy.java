package sample.Classes;

import java.util.ArrayList;

public abstract class Enemy extends Character{

    public Enemy(int id, String name, String type, ArrayList<ArrayList<Integer>> coordinates) {
        super(id, name, type, coordinates);
    }

}
