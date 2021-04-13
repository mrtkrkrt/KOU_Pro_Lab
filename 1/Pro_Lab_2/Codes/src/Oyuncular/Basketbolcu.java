package Oyuncular;

public class Basketbolcu extends Sporcu{
    private int ikilik;
    private int ucluk;
    private int serbestAtis;
    private boolean kartKullanildiMi;

    public Basketbolcu(String sporcuIsim, String sprocuTakim, int ikilik, int ucluk, int serbestAtis, boolean kartKullanildiMi) {
        super(sporcuIsim, sprocuTakim);
        this.ikilik = ikilik;
        this.ucluk = ucluk;
        this.serbestAtis = serbestAtis;
        this.kartKullanildiMi = kartKullanildiMi;
    }

    public Basketbolcu(){


    }

    public void kullan(){

        this.kartKullanildiMi = true;
    }

    public void sporcuPuaniGoster(){
        System.out.println(getSporcuIsim()+" "+getSprocuTakim()+ "İkilik::"+ikilik+"Üçlük:"+ucluk+"Serbest Atış:"+serbestAtis);
    }

    public int getIkilik() {
        return ikilik;
    }

    public void setIkilik(int ikilik) {
        this.ikilik = ikilik;
    }

    public int getUcluk() {
        return ucluk;
    }

    public void setUcluk(int ucluk) {
        this.ucluk = ucluk;
    }

    public int getSerbestAtis() {
        return serbestAtis;
    }

    public void setSerbestAtis(int serbestAtis) {
        this.serbestAtis = serbestAtis;
    }

    public boolean isKartKullanildiMi() {
        return kartKullanildiMi;
    }

    public void setKartKullanildiMi(boolean kartKullanildiMi) {
        this.kartKullanildiMi = kartKullanildiMi;
    }
}
