package sample;

import Oyuncular.Basketbolcu;
import Oyuncular.Bilgisayar;
import Oyuncular.Futbolcu;
import Oyuncular.Kullanıcı;
import com.sun.security.jgss.GSSUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Controller {

    @FXML
    private ImageView userImage1;
    @FXML
    private ImageView userImage2;
    @FXML
    private ImageView userImage3;
    @FXML
    private ImageView userImage4;
    @FXML
    private ImageView userImage5;
    @FXML
    private ImageView userImage6;
    @FXML
    private ImageView userImage7;
    @FXML
    private ImageView userImage8;
    @FXML
    private ImageView compImage1;
    @FXML
    private ImageView compImage2;
    @FXML
    private ImageView compImage3;
    @FXML
    private ImageView compImage4;
    @FXML
    private ImageView compImage5;
    @FXML
    private ImageView compImage6;
    @FXML
    private ImageView compImage7;
    @FXML
    private ImageView compImage8;
    @FXML
    private ImageView pcImage;
    @FXML
    private Text pcSkor;
    @FXML
    private Text userSkor;
    @FXML
    private ImageView kapisanKart;
    @FXML
    private Text winText;

    public Controller() {
    }

    public void initialize() {
        gameState = 0;
    }

    private Futbolcu[] kullanıcıFutbol = Main.kullanıcıFutbol;
    private Futbolcu[] bilgisayarFutbol = Main.bilgisayarFutbol;
    private Basketbolcu[] kullanıcıBasketbol = Main.kullanıcıbasketbol;
    private Basketbolcu[] bilgisayarBasketbol = Main.bilgisayarBasketbol;

    private Bilgisayar bilgisayar = new Bilgisayar(1,"Bilgisayar",0,bilgisayarFutbol,bilgisayarBasketbol);
    private Kullanıcı kullanıcı = new Kullanıcı(2,"Kullanıcı",0,kullanıcıFutbol,kullanıcıBasketbol);

    private int kullanıcıPuan = kullanıcı.getSkor();
    private int bilgisayarPuan = bilgisayar.getSkor();
    private int gameState;


    public void userClicked(MouseEvent mouseEvent) {

        ImageView img = (ImageView) mouseEvent.getSource();

        Random rand = new Random();

        Futbolcu[] kapisanKartlar = new Futbolcu[2];
        Basketbolcu[] kapisanKartlar1 = new Basketbolcu[2];
        int pozisyon = rand.nextInt(3)+1;

        if(bilgisayar.kartSayisiFutbol() == 0) gameState = 1;
        else if (bilgisayar.kartSayisiBasketbol() == 0) gameState = 0;


        if(bilgisayar.kartSayisiFutbol()>0 && gameState == 0){

            String id = img.getId().toString().split("e", 3)[2];
            if(Integer.parseInt(id) >  4) return;
            kapisanKartlar[1] = kullanıcı.kartSec(kullanıcı.getKartListesi()[Integer.parseInt(id)-1]);
            int a = rand.nextInt(4);
            Futbolcu kapisan =bilgisayar.kartSec(bilgisayar.getKartListesi()[a]);
            if(kapisan!=null){
                kapisanKartlar[0] = kapisan;
            }
            else{
                while(kapisan!=null){
                    a = rand.nextInt(4);
                    kapisan =bilgisayar.kartSec(bilgisayar.getKartListesi()[a]);
                }
                kapisanKartlar[0] = kapisan;
            }
            System.out.println(kapisanKartlar[1].getSporcuIsim() + " VS "+ kapisanKartlar[0].getSporcuIsim());
            String imgyol = "Images/"+ kapisanKartlar[0].getSporcuIsim() + ".png";
            String imgyol2 = "Images/" + kapisanKartlar[1].getSporcuIsim() +".png";


            if(pozisyon == 1){
                System.out.println("--Pozisyon -> Penaltı");
                //Seçilen Pozisyyon : Penaltı
                if(kapisanKartlar[0].getPenalti() >  kapisanKartlar[1].getPenalti()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    img.setVisible(false);
                    System.out.println("--Bilgisayar 10 Puan Kazandı\n");
                    bilgisayarPuan += 10;
                    gameState = 1;
                }
                else if (kapisanKartlar[0].getPenalti() < kapisanKartlar[1].getPenalti()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    img.setVisible(false);
                    System.out.println("--Kullanıcı 10 Puan Kazandı\n");
                    kullanıcıPuan += 10;
                    gameState = 1;
                }
                else {
                    System.out.println("Berabere Kalındı Lütfen Sıradaki Basketbol Kartını Seçiniz...\n");
                    kapisanKartlar[0].setKartKullanildiMi(false);
                    kapisanKartlar[1].setKartKullanildiMi(false);
                    gameState = 1;
                    pozisyon = rand.nextInt(3)+1;
                }
            }

            else if (pozisyon == 2){
                System.out.println("--Pozisyon -> Serbest Vuruş");
                //Seçilen Pozisyyon : Serbest Atış
                if(kapisanKartlar[0].getSerbestAtis() >  kapisanKartlar[1].getSerbestAtis()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    img.setVisible(false);
                    System.out.println("--Bilgisayar 10 Puan Kazandı\n");
                    bilgisayarPuan += 10;
                    gameState = 1;
                }
                else if (kapisanKartlar[0].getSerbestAtis() < kapisanKartlar[1].getSerbestAtis()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    img.setVisible(false);
                    System.out.println("--Kullanıcı 10 Puan Kazandı\n");
                    kullanıcıPuan += 10;
                    gameState = 1;
                }
                else {
                    System.out.println("Berabere Kalındı Lütfen Sıradaki Basketbol Kartını Seçiniz...\n");
                    kapisanKartlar[0].setKartKullanildiMi(false);
                    kapisanKartlar[1].setKartKullanildiMi(false);
                    gameState = 1;
                    pozisyon = rand.nextInt(3)+1;
                }
            }
            else{
                System.out.println("--Pozisyon -> Kaleci İle Karşı Karşıya");
                //Seçilen Pozisyyon : kaleci ile karşı karşıya
                if(kapisanKartlar[0].getKaleciKarsiKarsiya() >  kapisanKartlar[1].getKaleciKarsiKarsiya()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    img.setVisible(false);
                    System.out.println("--Bilgisayar 10 Puan Kazandı\n");
                    bilgisayarPuan += 10;
                    gameState = 1;
                }
                else if (kapisanKartlar[0].getKaleciKarsiKarsiya() < kapisanKartlar[1].getKaleciKarsiKarsiya()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    img.setVisible(false);
                    System.out.println("--Kullanıcı 10 Puan Kazandı\n");
                    kullanıcıPuan += 10;
                    gameState = 1;
                }
                else {
                    System.out.println("Berabere Kalındı Lütfen Sıradaki Basketbol Kartını Seçiniz...\n");
                    kapisanKartlar[0].setKartKullanildiMi(false);
                    kapisanKartlar[1].setKartKullanildiMi(false);
                    gameState = 1;
                    pozisyon = rand.nextInt(3)+1;
                }
            }

        }

        //Basketbol Durumu
        else {

            String id = img.getId().toString().split("e", 3)[2];
            if (Integer.parseInt(id) < 5) return;
            kapisanKartlar1[1] = kullanıcı.kartsec1(kullanıcı.getKartListesi2()[Integer.parseInt(id) - 5]);
            int a = rand.nextInt(4);
            Basketbolcu kapisan =bilgisayar.kartsec1(bilgisayar.getKartListesi2()[a]);
            if(kapisan!=null){
                kapisanKartlar1[0] = kapisan;
            }
            else{
                while(kapisan!=null){
                    a = rand.nextInt(4);
                    kapisan =bilgisayar.kartsec1(bilgisayar.getKartListesi2()[a]);
                }
                kapisanKartlar1[0] = kapisan;
            }
            System.out.println(kapisanKartlar1[1].getSporcuIsim() + " VS "+ kapisanKartlar1[0].getSporcuIsim());
            String imgyol = "Images/"+ kapisanKartlar1[0].getSporcuIsim() + ".png";
            String imgyol2 = "Images/" + kapisanKartlar1[1].getSporcuIsim() + ".png";

            if(pozisyon == 1){
                System.out.println("--Pozisyon -> İkilik");
                //Seçilen Pozisyon : İkilik
                if(kapisanKartlar1[0].getIkilik() >  kapisanKartlar1[1].getIkilik()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    System.out.println("--Bilgisayar 10 Puan Kazandı\n");
                    img.setVisible(false);
                    bilgisayarPuan += 10;
                    gameState = 0;
                }
                else if (kapisanKartlar1[0].getIkilik() < kapisanKartlar1[1].getIkilik()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    System.out.println("--Kullanıcı 10 Puan Kazandı\n");
                    img.setVisible(false);
                    kullanıcıPuan += 10;
                    gameState = 0;
                }
                else {
                    System.out.println("Berabere Kalındı Lütfen Sıradaki Futbol Kartını Seçiniz...\n");
                    kapisanKartlar1[0].setKartKullanildiMi(false);
                    kapisanKartlar1[1].setKartKullanildiMi(false);
                    gameState = 0;
                    pozisyon = rand.nextInt(3)+1;
                }

            }
            else if (pozisyon == 2){
                System.out.println("--Pozisyon -> Üçlük");
                //Seçilen Pozisyon : Üçlük
                if(kapisanKartlar1[0].getUcluk() >  kapisanKartlar1[1].getUcluk()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    System.out.println("--Bilgisayar 10 Puan Kazandı\n");
                    img.setVisible(false);
                    bilgisayarPuan += 10;
                    gameState = 0;
                }
                else if (kapisanKartlar1[0].getUcluk() < kapisanKartlar1[1].getUcluk()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    System.out.println("--Kullanıcı 10 Puan Kazandı\n");
                    img.setVisible(false);
                    kullanıcıPuan += 10;
                    gameState = 0;
                }
                else {
                    System.out.println("Berabere Kalındı Lütfen Sıradaki Futbol Kartını Seçiniz...\n");
                    kapisanKartlar1[0].setKartKullanildiMi(false);
                    kapisanKartlar1[1].setKartKullanildiMi(false);
                    gameState = 0;
                    pozisyon = rand.nextInt(3)+1;
                }
            }
            else{
                System.out.println("--Pozisyon -> Serbest Atış");
                //Seçilen Pozisyon: Serbest Atış
                if(kapisanKartlar1[0].getSerbestAtis() >  kapisanKartlar1[1].getSerbestAtis()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    System.out.println("--Bilgisayar 10 Puan Kazandı\n");
                    img.setVisible(false);
                    bilgisayarPuan += 10;
                    gameState = 0;
                }
                else if (kapisanKartlar1[0].getSerbestAtis() < kapisanKartlar1[1].getSerbestAtis()){
                    pcImage.setImage(new Image(imgyol));
                    kapisanKart.setImage(new Image(imgyol2));
                    System.out.println("--Kullanıcı 10 Puan Kazandı\n");
                    img.setVisible(false);
                    kullanıcıPuan += 10;
                    gameState = 0;
                }
                else {
                    System.out.println("Berabere Kalındı Lütfen Sıradaki Futbol Kartını Seçiniz...\n");
                    kapisanKartlar1[0].setKartKullanildiMi(false);
                    kapisanKartlar1[1].setKartKullanildiMi(false);
                    gameState = 0;
                    pozisyon = rand.nextInt(3)+1;
                }

            }

        }


        String userSkorText = "K U L L A N I C I  S K O R\n" + "                 "+kullanıcıPuan;
        String pcSkorText = "P C  S K O R\n" + "           "+bilgisayarPuan;

        pcSkor.setText(pcSkorText);
        userSkor.setText(userSkorText);

        if(bilgisayarPuan + kullanıcıPuan == 80){
            if(bilgisayarPuan > kullanıcıPuan){
                winText.setText("B İ L G İ S A Y A R  K A Z A N D I !");
                System.out.println("BİLGİSAYAR KAZANDI...");
            }
            else if(kullanıcıPuan > bilgisayarPuan){
                winText.setText("K U L L A N I C I  K A Z A N D I !");
                System.out.println("KAZANDIN...");
            }
            else{
                winText.setText("B E R A B E R E  K A L I N D I ! ! !");
                System.out.println("BERABERE KALINDI!Bİ DAHA Kİ SEFERE...");
            }
        }

    }
}


