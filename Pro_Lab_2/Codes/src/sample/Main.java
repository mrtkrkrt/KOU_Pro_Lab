package sample;

import Oyuncular.Basketbolcu;
import Oyuncular.Futbolcu;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Random;

public class Main extends Application {
    public static Futbolcu[] kullanıcıFutbol = new Futbolcu[4];
    public static Futbolcu[] bilgisayarFutbol = new Futbolcu[4];
    public static Basketbolcu[] kullanıcıbasketbol = new Basketbolcu[4];
    public static Basketbolcu[] bilgisayarBasketbol = new Basketbolcu[4];

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Sporcu Kart Oyunu");


        Scene scene = new Scene(root,1366,768);

        primaryStage.setScene(scene);



        primaryStage.show();

        Futbolcu[] futbolcular = new Futbolcu[8];
        Basketbolcu[] basketbolcular = new Basketbolcu[8];
        futbolcular[0] = new Futbolcu("Lionel Messi", "Barcelona", 90, 85, 80, false);
        futbolcular[1] = new Futbolcu("Cristiano Ronaldo", "Juventus", 95, 80, 95, false);
        futbolcular[2] = new Futbolcu("Neymar da Silva Santos Júnior", "Paris Saint-Germain", 90, 85, 90, false);
        futbolcular[3] = new Futbolcu("Zlatan Ibrahimovic", "Milan", 95, 90, 85, false);
        futbolcular[4] = new Futbolcu("Hakan Çalhanoğlu", "Milan", 90, 95, 85, false);
        futbolcular[5] = new Futbolcu("Ozan Tufan", "Fenerbahçe", 50, 65, 70, false);
        futbolcular[6] = new Futbolcu("Sergen Yalçın", "Beşiktaş", 75, 70, 80, false);
        futbolcular[7] = new Futbolcu("Sabri Sarıoğlu", "Galatasaray", 65, 35, 30, false);



        basketbolcular[0] = new Basketbolcu("Bogdan Bogdanovic","Sacramento Kings",85,80,75,false);
        basketbolcular[1] = new Basketbolcu("Cedi Osman","Clevelan Cavaliers",85,85,90,false);
        basketbolcular[2] = new Basketbolcu("Stephen Curry","Golden State",95,95,95,false);
        basketbolcular[3] = new Basketbolcu("Kobe Bryant","Lakers",95,90,95,false);
        basketbolcular[4] = new Basketbolcu("Göksenin Köksal","Galatasaray",75,70,75,false);
        basketbolcular[5] = new Basketbolcu("Lebron James","Lakers",90,90,95,false);
        basketbolcular[6] = new Basketbolcu("Melih Mahmutoğlu","Fenerbahçe",85,85,90,false);
        basketbolcular[7] = new Basketbolcu("Şehmus Hazer","Beşiktaş",65,70,65,false);



        Random rand = new Random();
        int[] used = {-1,-1,-1,-1};
        //Futbolcu[] kullanıcıFutbol = new Futbolcu[4];
        //Futbolcu[] bilgisayarFutbol = new Futbolcu[4];
        int controlPc =0;

        for (int i = 0; i < 4; i++) {
            int temp = rand.nextInt(8);
            int control = 1;
            for (int j = 0; j < 4; j++) {
                if (used[j] == temp) control = 0;
            }
            if (control == 1){
                kullanıcıFutbol[i] = futbolcular[temp];
                used[i] = temp;
            }
            else i--;
        }

       /* for (int i = 0; i < 4; i++) {
            System.out.println(kullanıcıFutbol[i].getSporcuIsim());
        }*/

        for (int i = 0; i < 8; i++) {
            int temp=0;
            for (int j = 0; j < 4; j++) {
                if (used[j] == i) temp =1;
            }
            if (temp == 0){
                bilgisayarFutbol[controlPc] = futbolcular[i];
                controlPc++;
            }
        }

      /*  System.out.println("//////////////////////////////////////////////");
        for (int i = 0; i < 4; i++) {
            System.out.println(bilgisayarFutbol[i].getSporcuIsim());
        }*/


        int[] used2 = {-1,-1,-1,-1};
        controlPc =0;

        for (int i = 0; i < 4; i++) {
            int temp = rand.nextInt(8);
            int control = 1;
            for (int j = 0; j < 4; j++) {
                if (used2[j] == temp) control = 0;
            }
            if (control == 1){
                kullanıcıbasketbol[i] = basketbolcular[temp];
                used2[i] = temp;
            }
            else i--;

        }

       /* for (int i = 0; i < 4; i++) {
            System.out.println(kullanıcıbasketbol[i].getSporcuIsim());
        }*/

        for (int i = 0; i < 8; i++) {
            int temp=0;
            for (int j = 0; j < 4; j++) {
                if (used2[j] == i) temp =1;
            }
            if (temp == 0){
                bilgisayarBasketbol[controlPc] = basketbolcular[i];
                controlPc++;
            }

        }

        /*for (int i = 0; i < 4; i++) {
            System.out.println(bilgisayarBasketbol[i].getSporcuIsim());
        }*/


       /* String imgyol= "Images/"+ kullanıcıFutbol[0].getSporcuIsim()+".png";
        String a=""+1;
        String isim = "#userImage"+(a);
        ImageView kart1 = (ImageView) primaryStage.getScene().lookup(isim);

        kart1.setImage(new Image(imgyol));*/

        System.out.println("KULLANICI KARTLARI:");

        for (int i = 0; i < 4; i++) {
            String imgyol= "Images/"+ kullanıcıFutbol[i].getSporcuIsim()+".png";
            String a=""+(i+1);
            String isim = "#userImage"+(a);
            ImageView kart1 = (ImageView) primaryStage.getScene().lookup(isim);
            kart1.setImage(new Image(imgyol));
            System.out.println(kullanıcıFutbol[i].getSporcuIsim());
        }
        for (int i = 0; i < 4; i++) {
            String imgyol= "Images/"+ kullanıcıbasketbol[i].getSporcuIsim()+".png";
            String a=""+(i+5);
            String isim = "#userImage"+(a);
            ImageView kart1 = (ImageView) primaryStage.getScene().lookup(isim);
            kart1.setImage(new Image(imgyol));

            System.out.println(kullanıcıbasketbol[i].getSporcuIsim());
        }
        System.out.println("\n");
        System.out.println("BİLGİSAYAR KARTLARI");
        for (int i = 0; i < 4; i++) {
            String imgyol= "Images/kapalıkart.png";
            String a=""+(i+1);
            String isim = "#compImage"+(a);
            ImageView kart1 = (ImageView) primaryStage.getScene().lookup(isim);
            kart1.setImage(new Image(imgyol));
            System.out.println(bilgisayarFutbol[i].getSporcuIsim());
        }
        for (int i = 0; i < 4; i++) {
            String imgyol= "Images/kapalıbasket.png";
            String a=""+(i+5);
            String isim = "#compImage"+(a);
            ImageView kart1 = (ImageView) primaryStage.getScene().lookup(isim);
            kart1.setImage(new Image(imgyol));
            System.out.println(bilgisayarBasketbol[i].getSporcuIsim());
        }

        System.out.println("\n\n");


    }

    public static void main(String[] args) {







        launch(args);
    }
}
