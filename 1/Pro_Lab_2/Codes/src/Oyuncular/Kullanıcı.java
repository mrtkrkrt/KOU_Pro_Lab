package Oyuncular;

public class Kullanıcı extends Oyuncu{


    public Kullanıcı() {
    }

    public Kullanıcı(int oyuncuID, String oyuncuIsim, int skor, Futbolcu[] kartListesi, Basketbolcu[] kartListesi2) {
        super(oyuncuID, oyuncuIsim, skor, kartListesi, kartListesi2);
    }

    @Override
    public Futbolcu kartSec(Futbolcu kart) {
        this.kartKullanFutbol(kart);
        return kart;
    }


    @Override
    public Basketbolcu kartsec1(Basketbolcu kart) {
        this.kartKullanBasketbol(kart);
        return kart;
    }
}
