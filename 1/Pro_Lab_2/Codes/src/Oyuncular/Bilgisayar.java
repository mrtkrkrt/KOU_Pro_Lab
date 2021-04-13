package Oyuncular;

import java.util.Random;

public class Bilgisayar extends Oyuncu{

    public Bilgisayar(){

    }

    public Bilgisayar(int oyuncuID, String oyuncuIsim, int skor, Futbolcu[] kartListesi, Basketbolcu[] kartListesi2) {
        super(oyuncuID, oyuncuIsim, skor, kartListesi, kartListesi2);

    }


    @Override
    public Futbolcu kartSec(Futbolcu kart) {
        Random rand = new Random();
        int rnd = rand.nextInt(this.kartSayisiFutbol());
        int count = 0;
        for (int i = 0; i < this.getKartListesi().length; i++)
        {
            if (this.getKartListesi()[i] == null || this.getKartListesi()[i].isKartKullanildiMi())
                continue;

            if (count == rnd)
            {
                this.kartKullanFutbol(this.getKartListesi()[i]);
                return this.getKartListesi()[i];
            }
            count++;
        }
        return null;
    }

    @Override
    public Basketbolcu kartsec1(Basketbolcu kart) {
        Random rand = new Random();
        int rnd = rand.nextInt(this.kartSayisiBasketbol());
        int count = 0;
        for (int i = 0; i < this.getKartListesi2().length; i++)
        {
            if (this.getKartListesi2()[i] == null || this.getKartListesi2()[i].isKartKullanildiMi())
                continue;

            if (count == rnd)
            {
                this.kartKullanBasketbol(this.getKartListesi2()[i]);
                return this.getKartListesi2()[i];
            }
            count++;
        }
        return null;
    }




}
