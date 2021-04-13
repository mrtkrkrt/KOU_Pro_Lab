package Oyuncular;

import javafx.scene.image.ImageView;

public class Futbolcu extends Sporcu{
    private int penalti;
    private int serbestAtis;
    private int kaleciKarsiKarsiya;
    private boolean kartKullanildiMi;


    public Futbolcu(String sporcuIsim, String sprocuTakim, int penalti, int serbestAtis, int kaleciKarsiKarsiya, boolean kartKullanildiMi) {
        super(sporcuIsim, sprocuTakim);
        this.penalti = penalti;
        this.serbestAtis = serbestAtis;
        this.kaleciKarsiKarsiya = kaleciKarsiKarsiya;
        this.kartKullanildiMi = kartKullanildiMi;

    }

    public Futbolcu(){

    }

    public void kullan(){
        this.kartKullanildiMi = true;
    }

    public void sporcuPuaniGoster(){
        System.out.println(getSporcuIsim()+":"+getSprocuTakim()+" Penaltı:"+penalti+" Serbest Atış:"+serbestAtis+"Kaleci ile karşı karşıya :"+kaleciKarsiKarsiya);
    }

    public int getPenalti() {
        return penalti;
    }

    public void setPenalti(int penalti) {
        this.penalti = penalti;
    }

    public int getSerbestAtis() {
        return serbestAtis;
    }

    public void setSerbestAtis(int serbestAtis) {
        this.serbestAtis = serbestAtis;
    }

    public int getKaleciKarsiKarsiya() {
        return kaleciKarsiKarsiya;
    }

    public void setKaleciKarsiKarsiya(int kaleciKarsiKarsiya) {
        this.kaleciKarsiKarsiya = kaleciKarsiKarsiya;
    }

    public boolean isKartKullanildiMi() {
        return kartKullanildiMi;
    }

    public void setKartKullanildiMi(boolean kartKullanildiMi) {
        this.kartKullanildiMi = kartKullanildiMi;
    }
}
