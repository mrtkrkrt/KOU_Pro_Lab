package sample.Classes;

public class Mantar extends Object{
    private int scor = 50;
    private boolean isVisible;
    private boolean isPicked;

    public Mantar(int scor, boolean isVisible, boolean isPicked) {

        this.scor = scor;
        this.isVisible = isVisible;
        this.isPicked = isPicked;
    }


    public int getScor() {
        return scor;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }
}
