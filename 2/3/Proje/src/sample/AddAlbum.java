package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;

public class AddAlbum {
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button backButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField albumText;
    @FXML
    private TextField dateText;
    @FXML
    private TextField singerText;
    @FXML
    private TextField typeText;

    private Connection con = null;
    private Statement st = null;

    @FXML
    void initialize(){
        connectDatabase();
        initializeButtons();
    }

    private void initializeButtons() {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("adminMainPage.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = albumText.getText();
                String şarkıcı = singerText.getText();
                String tür = typeText.getText();
                String tarih = dateText.getText();

                if (!checkAlbumExist(name, getSinger(şarkıcı), getTypeID(tür), tarih) && !name.equals("") && !şarkıcı.equals("") && !tür.equals("") && !tarih.equals("")){
                    String query = "INSERT INTO album(name, artistID, typeID, date) VALUES('" + name + "'," + getSinger(şarkıcı) + "," + getTypeID(tür) + ",'" + tarih + "')";
                    try {
                        st = con.createStatement();
                        st.executeUpdate(query);
                          
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else{
                    System.out.println("Girmedi...");
                }
            }
        });
    }

    private int getTypeID(String tür) {
        String query = "SELECT * FROM type";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");
                int id = rs.getInt("id");
                if (name.equals(tür)) return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private int getSinger(String şarkıcı) {
        String query = "SELECT * FROM artist";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");
                int id = rs.getInt("id");
                if (name.equals(şarkıcı)) return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private boolean checkAlbumExist(String name, int şarkıcı, int tür, String tarih) {
        String query = "SELECT * FROM album";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String nm = rs.getString("name");
                int singer = rs.getInt("artistID");
                int type = rs.getInt("typeID");
                String date = rs.getString("date");
                if (nm.equals(name) && tarih.equals(date) && type == tür && şarkıcı == singer){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private void connectDatabase(){
        String url = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "spotify";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Can not Found...");
        }

        try {
            con = DriverManager.getConnection(url,"root","");
        } catch (SQLException throwables) {
            System.out.println("Connection Failed...");
        }
    }
}
