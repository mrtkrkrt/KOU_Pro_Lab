package sample.Classes;

import java.util.ArrayList;

public abstract class Player extends Character{

    private int scor;
    private int azmanDamage;
    private int gargamelDamage ;

    public Player(int id, String name, String type, ArrayList<ArrayList<Integer>> coordinates, int scor, int azmanDamage, int gargamelDamage) {
        super(id, name, type, coordinates);
        this.scor = scor;
        this.azmanDamage = azmanDamage;
        this.gargamelDamage = gargamelDamage;
    }

    public int getScor() {
        return scor;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public int getAzmanDamage() {
        return azmanDamage;
    }

    public void setAzmanDamage(int azmanDamage) {
        this.azmanDamage = azmanDamage;
    }

    public int getGargamelDamage() {
        return gargamelDamage;
    }

    public void setGargamelDamage(int gargamelDamage) {
        this.gargamelDamage = gargamelDamage;
    }

    public void puaniGoster(){
        System.out.println("Puan = "+this.getScor());
    }

}
