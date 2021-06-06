package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.print.DocFlavor;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class AddMusicToAlbum {
    @FXML
    private AnchorPane anchor;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;

    private Statement st = null;
    private Statement st2 = null;
    private Connection con = null;
    private ArrayList<String> şarkılar = new ArrayList<>();
    private VBox vBox = new VBox();

    @FXML
    void initialize(){
        if (Main.albumSilmeMi) addButton.setText("SİL");
        connectDatabase();
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.albumSilmeMi = false;
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
                if (!Main.albumSilmeMi)addMusicsToAlbum();
                else deleteMusicsToAlbum();
            }
        });
        if (!Main.albumSilmeMi)addMusics();
        else addMusic();

    }

    private void addMusic() {
        scrollPane.setContent(null);
        int i = 0;
        String query = "SELECT * FROM albümi̇çerik WHERE albumID = " + Main.albumInfos[2];
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int sarkıID = rs.getInt("sarkıID");
                String[] bilgiler = getMusicInfos(sarkıID);
                şarkılar.add(bilgiler[0]);

                CheckBox checkBox = new CheckBox();
                checkBox.setId(String.valueOf(i));
                i++;
                HBox hBox = new HBox();
                hBox.getChildren().addAll(checkBox, new Text(bilgiler[0]));
                vBox.getChildren().add(hBox);
            }
            scrollPane.setContent(vBox);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void deleteMusicsToAlbum() {
        for (Node hbox : vBox.getChildren()){
            if (hbox instanceof HBox){
                for (Node checkBox : ((HBox) hbox).getChildren()){
                    if (checkBox instanceof CheckBox && ((CheckBox) checkBox).isSelected()){
                        String name = şarkılar.get(Integer.parseInt(checkBox.getId()));
                        int albumID = Main.albumInfos[2];
                        String query = "DELETE FROM albümi̇çerik WHERE albumID =" + albumID + " AND sarkıID = " + getMusicID(name);
                        try {
                            st = con.createStatement();
                            st.executeUpdate(query);
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private int getMusicID(String name) {
        String query = "SELECT * FROM music";
        try {
            st2 = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String nm = rs.getString("name");
                int id = rs.getInt("id");
                if (nm.equals(name)) return id;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    private void addMusicsToAlbum() {
        for (Node hbox : vBox.getChildren()){
            if (hbox instanceof HBox){
                for (Node checkBox : ((HBox) hbox).getChildren()){
                    if (checkBox instanceof CheckBox && ((CheckBox) checkBox).isSelected()){
                        String name = şarkılar.get(Integer.parseInt(checkBox.getId()));
                        int musicID = getMusicID(name);
                        try {
                            st= con.createStatement();
                            if (!checkMusicExist(musicID, Main.albumInfos[2])){
                                String query = "INSERT INTO albümi̇çerik(sarkıID, albumID) VALUES(" + musicID + ", " + Main.albumInfos[2] + ")";
                                st.executeUpdate(query);
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private boolean checkMusicExist(int musicID, int playlistID) {
        String query = "SELECT * FROM albümi̇çerik";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int music = rs.getInt("sarkıID");
                int playlist = rs.getInt("albumID");
                if (music == musicID && playlist == playlistID) return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private String[] getMusicInfos(int musicID) {
        String[] infos = new String[3];
        String query = "SELECT * From music WHERE id = "  + musicID;
        try {
            st2 = con.createStatement();
            ResultSet rs = st2.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int singer = rs.getInt("singerId");
                String süre = rs.getString("süre");

                if (id == musicID){
                    infos[0] = name;
                    infos[1] = getSingerName(singer);
                    infos[2] = süre;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return infos;
    }

    private String getSingerName(int id) {
        String query = "Select * FROM artist";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int sanatciID = rs.getInt("id");
                String name = rs.getString("name");
                if (sanatciID == id) {
                    return name;
                }}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private void addMusics() {
        int i = 0;
        scrollPane.setContent(null);
        String query = "SELECT * FROM music WHERE (typeId = " + Main.albumInfos[0] + " AND singerId = " + Main.albumInfos[1] + ")";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String[] bilgiler = getMusicInfos(id);
                şarkılar.add(bilgiler[0]);

                CheckBox checkBox = new CheckBox();
                checkBox.setId(Integer.toString(i));
                HBox hBox = new HBox();
                hBox.getChildren().addAll(checkBox, new Text(bilgiler[0]));
                vBox.getChildren().add(hBox);
                i++;
            }
            scrollPane.setContent(vBox);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
