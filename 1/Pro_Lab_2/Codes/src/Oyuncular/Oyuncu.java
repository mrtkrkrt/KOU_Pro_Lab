package Oyuncular;

public abstract class Oyuncu {
    private int oyuncuID;
    private String oyuncuIsim;
    private int Skor;
    private Futbolcu[] kartListesi = new Futbolcu[4];
    private Basketbolcu[] kartListesi2 = new Basketbolcu[4];

    public Oyuncu(){

    }

    public Oyuncu(int oyuncuID, String oyuncuIsim, int skor, Futbolcu[] kartListesi, Basketbolcu[] kartListesi2) {
        this.oyuncuID = oyuncuID;
        this.oyuncuIsim = oyuncuIsim;
        Skor = skor;
        this.kartListesi = kartListesi;
        this.kartListesi2 = kartListesi2;
    }

    public int skorGoster (){
        return this.Skor;
    }
    public int kartSayisiFutbol()
    {
        int count = 0;
        for (Futbolcu kart : this.kartListesi)
        {
            if (kart != null && !(kart.isKartKullanildiMi()))
                count++;
        }
        return count;
    }

    public int kartSayisiBasketbol()
    {
        int count = 0;
        for (Basketbolcu kart : this.kartListesi2)
        {
            if (kart != null && !(kart.isKartKullanildiMi()))
                count++;
        }
        return count;
    }
    public boolean kartVarMiFutbol(Futbolcu hedef)
    {
        if (kartSayisiFutbol() == 0)
            return false;

        for (Futbolcu kart : this.kartListesi)
        {
            if (kart == null)
                continue;

            if (kart.getSporcuIsim() == hedef.getSporcuIsim())
            {
                return !kart.isKartKullanildiMi();
            }
        }

        return false;
    }

    public boolean kartVarMiBasketbol(Basketbolcu hedef)
    {
        if (kartSayisiBasketbol() == 0)
            return false;

        for (Basketbolcu kart : this.kartListesi2)
        {
            if (kart == null)
                continue;

            if (kart.getSporcuIsim() == hedef.getSporcuIsim())
            {
                return !kart.isKartKullanildiMi();
            }
        }

        return false;
    }

    public void kartKullanFutbol(Futbolcu hedef)
    {
        if (kartSayisiFutbol() == 0)
            return;
        if (!kartVarMiFutbol(hedef))
            return;
        for (Futbolcu kart : this.kartListesi)
        {
            if (kart.getSporcuIsim() == hedef.getSporcuIsim())
            {
                kart.kullan();

                break;
            }
        }
    }
    public void kartKullanBasketbol(Basketbolcu hedef)
    {
        if (kartSayisiBasketbol() == 0)
            return;
        if (!kartVarMiBasketbol(hedef))
            return;
        for (Basketbolcu kart : this.kartListesi2)
        {
            if (kart.getSporcuIsim() == hedef.getSporcuIsim())
            {
                //System.out.println(this.getOyuncuAdi() + " " + hedef.getPokemonAdi() + " kartini kullandi.");
                kart.kullan();

                break;
            }
        }
    }

    public void addSkor(int skor)
    {
        Skor += skor;
        //System.out.println(this.getOyuncuAdi() + " " + skor + " skor kazandi!");
    }

    public abstract Futbolcu kartSec(Futbolcu kart);
    public abstract Basketbolcu kartsec1(Basketbolcu kart);

    public int getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(int oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    public String getOyuncuIsim() {
        return oyuncuIsim;
    }

    public void setOyuncuIsim(String oyuncuIsim) {
        this.oyuncuIsim = oyuncuIsim;
    }

    public int getSkor() {
        return Skor;
    }

    public void setSkor(int skor) {
        Skor = skor;
    }

    public Futbolcu[] getKartListesi() {
        return kartListesi;
    }

    public void setKartListesi(Futbolcu[] kartListesi) {
        this.kartListesi = kartListesi;
    }

    public Basketbolcu[] getKartListesi2() {
        return kartListesi2;
    }

    public void setKartListesi2(Basketbolcu[] kartListesi2) {
        this.kartListesi2 = kartListesi2;
    }
}
