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

public class AdminAddMusic {
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField nameText;
    @FXML
    private TextField typeText;
    @FXML
    private TextField singerText;
    @FXML
    private TextField dateText;
    @FXML
    private Button okButton;
    @FXML
    private Button backButton;
    @FXML
    private TextField süreText;
    @FXML
    private TextField albumText;

    private Connection con = null;
    private Statement st = null;

    @FXML
    void initialize(){
        connectDatabase();
        initializeTextsAndButtons();
    }

    private void initializeTextsAndButtons() {
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = nameText.getText();
                String type = typeText.getText();
                String singer = singerText.getText();
                String date = dateText.getText();
                String süre = süreText.getText();

                int typeID = getTypeID(type);
                int singerID = getSingerID(singer);

                if (Main.isİslemMüzikEklemeMi){
                    String query = "INSERT INTO music(name, typeId, singerId, date, süre) VALUES('" +
                            name + "'," + Integer.toString(typeID) + "," + Integer.toString(singerID) + ",'" + date + "', '" + süre + "'"+ ")";
                    try {
                        st.executeUpdate(query);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else{
                    String query = "UPDATE music SET name = '" + name + "', typeId = " + typeID +", singerId = " + singerID + ", date = '" + date + "', süre = '" + süre + "'"  + " WHERE id = " + Main.changeMusicID;
                    try {
                        st.executeUpdate(query);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.isİslemMüzikEklemeMi=false;
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("adminMainPage.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        if (!Main.isİslemMüzikEklemeMi){

            String query = "SELECT * FROM music WHERE id = " + Main.changeMusicID;
            try {
                st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
                    String name = rs.getString("name");
                    int typeID = rs.getInt("typeId");
                    int singerID = rs.getInt("singerId");
                    String date = rs.getString("date");
                    String süre = rs.getString("süre");

                    nameText.setText(name);
                    typeText.setText(getType(typeID));
                    singerText.setText(getSinger(singerID));
                    dateText.setText(date);
                    süreText.setText(süre);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private String getAlbum(int album) {
        String query = "SELECT * FROM album WHERE id = " + album;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                if (id == album) return name;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private int getSingerID(String singer) {
        String query = "SELECT * FROM artist WHERE name = '" + singer + "'";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String nm = rs.getString("name");
                int id = rs.getInt("id");
                if (nm.equals(singer)) return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private int getTypeID(String type) {
        String query = "SELECT * FROM type WHERE name = '" + type + "'";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String nm = rs.getString("name");
                int id = rs.getInt("id");
                if (nm.equals(type)) return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private String getSinger(int singerID) {
        String query = "SELECT * FROM artist WHERE id = " +singerID;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");
                int id = rs.getInt("id");
                if (singerID == id){
                    return name;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private String getType(int typeID) {
        String query = "SELECT * FROM type WHERE id = " +typeID;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");
                int id = rs.getInt("id");
                if (typeID == id){
                    return name;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
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
