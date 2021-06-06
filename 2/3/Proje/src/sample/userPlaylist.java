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

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class userPlaylist {
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button backMainMenu;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;
    @FXML
    private ScrollPane scrollPane;

    private ArrayList<String> şarkılar = new ArrayList<>();
    private VBox vBox = new VBox();

    private Statement st = null;
    private Statement st2 = null;
    private Connection con = null;

    @FXML
    void initialize(){
        connectDatabase();
        initializeButtons();
        setScrollPane();
    }

    private void setScrollPane() {
        int i = 0;
        String query = "SELECT * FROM playlisti̇çerik WHERE playlistID = " + Main.searchUserPlaylistID;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int musicID = rs.getInt("sarkıID");
                int palylistID = rs.getInt("playlistID");
                String[] bilgiler = getMusicInfos(musicID);

                HBox hBox = new HBox();

                CheckBox checkBox = new CheckBox();
                checkBox.setId(Integer.toString(i));
                hBox.getChildren().add(checkBox);
                şarkılar.add(bilgiler[0]);
                hBox.getChildren().add(getText(bilgiler[0]));
                hBox.getChildren().add(getText(bilgiler[1]));
                hBox.getChildren().add(getText(bilgiler[2]));
                vBox.getChildren().add(hBox);
                i++;
            }
            scrollPane.setContent(vBox);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    private String getSingerName(int singerId) {
        String query = "Select * FROM artist";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int sanatciID = rs.getInt("id");
                String name = rs.getString("name");
                if (sanatciID == singerId){
                    return name;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private Node getText(String name) {
        int len = name.length();
        int kalan = 30 - len;
        for (int i = 0; i < kalan; i++) {
            name += " ";
        }
        return new Text(name);
    }

    private void initializeButtons() {
        backMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vBox.getChildren().removeAll(vBox.getChildren());
                setScrollPane();
                clearChoice();
            }
        });

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                addMusicsToPlaylist();
            }
        });
    }

    private void clearChoice() {
    }

    private void addMusicsToPlaylist() {
        for (Node hbox : vBox.getChildren()){
            if (hbox instanceof HBox){
                for (Node checkBox : ((HBox) hbox).getChildren()){
                    if (checkBox instanceof CheckBox && ((CheckBox) checkBox).isSelected()){
                        String name = şarkılar.get(Integer.parseInt(checkBox.getId()));
                        addMusic(name);
                    }
                }
            }
        }

    }

    private void addMusic(String name) {
        String query = "SELECT * FROM music WHERE name = '" + name +"'";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                String nm =rs.getString("name");
                int type = rs.getInt("typeId");
                int id = rs.getInt("id");

                int playlistID = Main.ids[type-1];

                if (!checkMusicExist(id, playlistID) && nm.equals(name)){
                    System.out.println("girdi");
                    query = "INSERT INTO playlisti̇çerik(sarkıID, playlistID) VALUES(" + id + ", " + playlistID + ")";
                    st.executeUpdate(query);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private boolean checkMusicExist(int musicID, int playlistID) {
        String query = "SELECT * FROM playlisti̇çerik";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int music = rs.getInt("sarkıID");
                int playlist = rs.getInt("playlistID");
                if (music == musicID && playlist == playlistID) return true;
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
