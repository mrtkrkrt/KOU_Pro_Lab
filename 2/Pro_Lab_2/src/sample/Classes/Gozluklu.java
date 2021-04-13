package sample.Classes;

import java.util.ArrayList;

public class Gozluklu extends Player{

    private int step;

    public Gozluklu(int id, String name, String type, ArrayList<ArrayList<Integer>> coordinates, int scor, int azmanDamage, int gargamelDamage, int step) {
        super(id, name, type, coordinates, scor, azmanDamage, gargamelDamage);
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public ArrayList<Integer> enKısaYol() {
        return new ArrayList<>();
    }
}
