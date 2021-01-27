package Oyuncular;

import javafx.scene.image.ImageView;

public abstract class Sporcu {
    private String sporcuIsim;
    private String sporcuTakim;


    public Sporcu(String sporcuIsim, String sprocuTakim) {
        this.sporcuIsim = sporcuIsim;
        this.sporcuTakim = sporcuTakim;

    }

    public Sporcu(){

    }


    public void sporcuPuaniGoster(){
        System.out.println(sporcuIsim+sporcuTakim);
    }


    public String getSporcuIsim() {
        return sporcuIsim;
    }

    public void setSporcuIsim(String sporcuIsim) {
        this.sporcuIsim = sporcuIsim;
    }

    public String getSprocuTakim() {
        return sporcuTakim;
    }

    public void setSprocuTakim(String sprocuTakim) {
        this.sporcuTakim = sprocuTakim;
    }
}
