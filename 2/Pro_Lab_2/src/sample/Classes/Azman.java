package sample.Classes;

import sample.Main;

import java.util.ArrayList;

public class Azman extends Enemy{

    private int step = 1;
    private boolean isJump = false;

    public Azman(int id, String name, String type, ArrayList<ArrayList<Integer>> coordinates, int step, boolean isJump) {
        super(id, name, type, coordinates);
        this.step = step;
        this.isJump = isJump;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }

    @Override
    public ArrayList<Integer> enKısaYol() {
        int id = (int) ((Main.azmanImage.getX()-50)/50  + ((Main.azmanImage.getY()-50)/50)*13) ;
        int playerID = (int)((Main.playerImage.getX()-50)/50  + ((Main.playerImage.getY()-50)/50)*13);

        Main.dijkstra(id,playerID);
        ArrayList<Integer> arr = new ArrayList<>();

        int i = playerID;
        while (i!=id){
            arr.add(i);
            //.out.print(Main.nodes.get(i).getId()+" ");
            i = Main.nodes.get(i).getPrevious().getId();
        }
        return arr;
     }

}