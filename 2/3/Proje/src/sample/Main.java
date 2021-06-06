package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    //MYSQL
    private static Connection con = null;
    public static int veriSayısı;
    public static Scene scene = null;
    public static int userID = 0;
    //GUI
    public static int[] ids = new int[3];
    public static int playlistID = 0;
    //for search
    public static int searchUser = 0;
    public static int searchUserPlaylistID = 0;
    //for playlist
    public static boolean isTopPlaylist = false;
    public static String topButton = "";
    public static boolean isTopCountryPlaylist = false;
    //for admin
    public static int changeMusicID = 0;
    public static boolean islemEklemeMi = false;
    public static boolean islemMuzikMi = false;
    public static int[] albumInfos = {0,0,0};
    //for admin2
    public static boolean isİslemMüzikEklemeMi = false;
    public static String musicName = "";
    public static boolean albumSilmeMi = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login_Page.fxml"));
        primaryStage.setTitle("Müzik Dosyam");
        scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        connectDatabase();
        launch(args);
    }

    private static void connectDatabase(){
        String url = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "deneme";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Can not Found...");
        }

        try {
            con = DriverManager.getConnection(url,"root","");
            System.out.println("Connection Succesful...");
        } catch (SQLException throwables) {
            System.out.println("Connection Failed...");
        }
    }
}
