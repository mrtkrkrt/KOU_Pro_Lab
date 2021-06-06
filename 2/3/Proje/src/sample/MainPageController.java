package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class MainPageController {
    @FXML
    private AnchorPane anchor;
    @FXML
    private ScrollPane vbox;
    @FXML
    private Button addButton;
    @FXML
    private Button pop;
    @FXML
    private Button caz;
    @FXML
    private Button klasik;
    @FXML
    private TextArea searchText;
    @FXML
    private Button topPop;
    @FXML
    private Button topCazz;
    @FXML
    private Button topKlasik;
    @FXML
    private Button top;
    @FXML
    private Button clearButton;
    @FXML
    private Button top10Turkey;
    @FXML
    private Button top10Av;
    @FXML
    private Button top10Italy;

    private VBox v = new VBox();

    public static ArrayList<String> arr = new ArrayList<>();
    public Connection con = null;
    public Statement st = null;
    private int[] types = {1,2,3};
    private String[] names = {"POP","CAZZ","KLASİK"};

    @FXML
    public void initialize() {
        Main.isTopPlaylist = false;
        Main.topButton = "";
        Main.isTopCountryPlaylist = false;
        initializeButton();
        connectDatabase();
        createPlaylists();
        createPlalistID();
        VeriCek();
    }

    private void createPlalistID() {
        String query = "Select * FROM playlist WHERE userID = " + Integer.toString(Main.userID);
        int[] ids = new int[3];
        int i = 0;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");

                ids[i] = id;
                i++;
            }
            Main.ids = ids;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeButton() {
        Main.isTopPlaylist = false;
        pop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.playlistID = 0;loadNextScene();
            }
        });

        caz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.playlistID = 1;loadNextScene();
            }
        });

        klasik.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.playlistID = 2;loadNextScene();
            }
        });
        searchText.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Search.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        topPop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.isTopPlaylist = true;
                    Main.topButton = "pop";
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        topCazz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.isTopPlaylist = true;
                    Main.topButton = "cazz";
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        topKlasik.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.isTopPlaylist = true;
                    Main.topButton = "klasik";
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        top.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.isTopPlaylist = true;
                    Main.topButton = "top";
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clearChoices();
            }
        });
        top10Turkey.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.isTopPlaylist = true;
                Main.isTopCountryPlaylist = true;
                Main.topButton = "T";
                AnchorPane pane = null;
                try {
                    pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                anchor.getChildren().setAll(pane);
            }
        });
        top10Av.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.isTopPlaylist = true;
                Main.isTopCountryPlaylist = true;
                Main.topButton = "A";
                AnchorPane pane = null;
                try {
                    pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                anchor.getChildren().setAll(pane);
            }
        });
        top10Italy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.isTopPlaylist = true;
                Main.isTopCountryPlaylist = true;
                Main.topButton = "I";
                AnchorPane pane = null;
                try {
                    pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                anchor.getChildren().setAll(pane);
            }
        });
    }

    private void createPlaylists() {
        if (checkPlaylists()){
            for (int i = 0; i < 3; i++) {
                String query = "INSERT INTO playlist(name, typeID, userID) VALUES('"+names[i]+"',"+Integer.toString(types[i])+","+ Integer.toString(Main.userID) +")";
                try {
                    st = con.createStatement();
                    st.executeUpdate(query);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private boolean checkPlaylists() {
        String query = "Select * FROM playlist";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int userID = rs.getInt("userID");
                if (userID == Main.userID) return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    @FXML
    void vericek(ActionEvent actionEvent){
        for (Node hbox : v.getChildren()){
            if (hbox instanceof HBox){
                for(Node checkbox : ((HBox) hbox).getChildren()){
                    if (checkbox instanceof CheckBox){
                        if (((CheckBox) checkbox).isSelected()){
                            addMusicsToPlaylist(arr.get(Integer.parseInt(checkbox.getId())));
                        }
                    }
                }
            }
        }
        clearChoices();
    }

    private void clearChoices() {
        for (Node hbox : v.getChildren()){
            if (hbox instanceof HBox){
                for(Node checkbox : ((HBox) hbox).getChildren()){
                    if (checkbox instanceof CheckBox){
                        ((CheckBox) checkbox).setSelected(false);
                    }
                }
            }
        }
    }

    private void addMusicsToPlaylist(String name) {
        int musicID = 0, typeID = 0, playlistID = 0;
        int[] ids = new int[3];
        String query = "Select * FROM music WHERE name = '" + name + "'";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String nm = rs.getString("name");
                int type = rs.getInt("typeID");
                if (nm.equals(name)) {
                    musicID = id;
                    typeID = type;
                }
            }
            query = "SELECT * FROM playlist WHERE userID = " + Main.userID;
            rs = st.executeQuery(query);
            while (rs.next()){
                int type = rs.getInt("typeID");
                int id = rs.getInt("id");
                if (type == typeID) playlistID = id;
            }
            query = "INSERT INTO playlisti̇çerik(sarkıID, playlistID) VALUES(" + musicID + ", " + playlistID + ")";
            if (!checkMusicExist(musicID, playlistID)){ st.executeUpdate(query);}

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

    private void VeriCek() {
        int j = 0;
        String query = "Select * from music";

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                HBox h = new HBox();

                String name = rs.getString("name");
                int type = rs.getInt("typeId");
                int singer = rs.getInt("singerId");
                String date = rs.getString("date");
                String süre = rs.getString("süre");

                arr.add(name);

                CheckBox c = new CheckBox();
                String id = Integer.toString(j);
                c.setId(id);

                h.getChildren().add(c);
                h.getChildren().add(setText(name));
                h.getChildren().add(setText(findSinger(singer)));
                h.getChildren().add(setText(findType(type)));
                h.getChildren().add(setText(date));
                h.getChildren().add(setText(süre));
                v.getChildren().add(h);

                j++;
            }
            vbox.setContent(v);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Main.veriSayısı = j;
    }

    private String findType(int type) {
        String query = "Select * FROM type";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");

                if (id == type){
                    return name;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private String findSinger(int singer) {
        String query = "Select * FROM artist";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");

                if (id == singer){
                    return name;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private Node setText(String name) {
        name.trim();
        int len = name.length();
        int kalan = 50 - len;
        for (int i = 0; i < kalan; i++) {
            name += " ";
        }
        return new Text(name);
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

    private void loadNextScene(){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Playlist.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
