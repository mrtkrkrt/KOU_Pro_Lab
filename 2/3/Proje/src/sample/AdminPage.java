package sample;

import com.sun.scenario.effect.impl.sw.java.JSWBlend_SCREENPeer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class AdminPage {
    @FXML
    private AnchorPane anchor;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button addAlbum;
    @FXML
    private TextField searchText;

    private VBox musics = new VBox();
    private VBox albums = new VBox();
    private HBox hBox = new HBox();
    private Statement st = null;
    private Statement st2 = null;
    private Statement st3 = null;
    private Connection con = null;

    @FXML
    void initialize(){
        connectDatabase();
        initializeButtons();
    }

    private void initializeButtons() {
        addAlbum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("addAlbum.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.isİslemMüzikEklemeMi = true;
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("adminMusicAdd.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String text = searchText.getText();
                albums.getChildren().removeAll(albums.getChildren());
                musics.getChildren().removeAll(musics.getChildren());
                hBox.getChildren().removeAll(hBox.getChildren());
                scrollPane.setContent(null);
                addMusics(text);
                addAlbums(text);
                setScrollPane();
            }
        });
    }

    private void setScrollPane() {
        Line line = new Line();
        line.setStartY(0);
        line.setEndY(550);
        line.setStrokeWidth(5);
        hBox.getChildren().addAll(musics, line, albums);
        scrollPane.setContent(hBox);
    }

    private void addAlbums(String text) {
        String query = "SELECT * FROM album WHERE name LIKE '%" + text + "%'";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");
                String singer = getSingerTExt(rs.getInt("artistID"));
                String tur = getTypeText(rs.getInt("typeID"));
                int typeID = rs.getInt("typeID"), singerID = rs.getInt("artistID"), albumID = rs.getInt("id");

                HBox hBox = new HBox();
                Button button = new Button();
                button.setText("GÜNCELLE");
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Main.albumSilmeMi = false;
                            Main.albumInfos[0] = typeID;
                            Main.albumInfos[1] = singerID;
                            Main.albumInfos[2] = albumID;
                            AnchorPane pane = FXMLLoader.load(getClass().getResource("addMusicToAlbum.fxml"));
                            anchor.getChildren().setAll(pane);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button button1 = new Button();
                button1.setText("SİL");
                button1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Main.albumSilmeMi = true;
                        Main.albumInfos[0] = typeID;
                        Main.albumInfos[1] = singerID;
                        Main.albumInfos[2] = albumID;
                        AnchorPane pane = null;
                        try {
                            pane = FXMLLoader.load(getClass().getResource("addMusicToAlbum.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        anchor.getChildren().setAll(pane);
                    }
                });
                hBox.getChildren().addAll(getText(name), getText(singer), getText(tur), button, button1);
                albums.getChildren().add(hBox);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getTypeText(int typeID) {
        String query = "SELECT * FROM type WHERE id = " + typeID;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(name);
                if (id == typeID) return name;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    private void addSingers(String text) {
        String query = "SELECT * FROM artist WHERE name LIKE '%" + text + "%'";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");
                HBox hBox = new HBox();
                hBox.getChildren().add(getText(name));
                Button updateButton = new Button();
                updateButton.setText("GÜNCELLE");
                updateButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                    }
                });
                Button deleteButton = new Button();
                deleteButton.setText("SİL");
                hBox.getChildren().addAll(updateButton, deleteButton);
                musics.getChildren().add(hBox);
                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                    }
                });
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    private void deleteMusic(String name, int type, int singerId) {
        String query = "SELECT * FROM music WHERE name = '" + name + "' AND typeId = " + type + " AND singerId = " + singerId;
        try {
            st3 = con.createStatement();
            st2 = con.createStatement();
            ResultSet rs = st3.executeQuery(query);
            while (rs.next()){
                String nm = rs.getString("name");
                int typeID = rs.getInt("typeId");
                int singer = rs.getInt("singerId");

                if (nm.equals(name) && type == typeID && singerId == singer){
                    String deleteQuery = "DELETE FROM playlisti̇çerik WHERE sarkıID = " + getMusicID(name);
                    st2.executeUpdate(deleteQuery);
                    deleteQuery = "DELETE FROM albümi̇çerik WHERE sarkıID = " + getMusicID(name);
                    st2.executeUpdate(deleteQuery);
                    deleteQuery = "DELETE FROM music WHERE name = '" + nm + "' AND typeId = " + typeID + " AND singerId = " + singer;
                    st2.executeUpdate(deleteQuery);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addMusics(String text) {
        String query = "SELECT * FROM music WHERE name LIKE '%" + text + "%'";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int singerID = rs.getInt("singerId");
                int typeID = rs.getInt("typeId");

                HBox hBox = new HBox();
                Button updateButton = new Button();
                updateButton.setText("GÜNCELLE");
                updateButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Main.changeMusicID = id;
                            Main.musicName = name;

                            AnchorPane pane = FXMLLoader.load(getClass().getResource("adminMusicAdd.fxml"));
                            anchor.getChildren().setAll(pane);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Button deleteButton = new Button();
                deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        deleteMusic(name, typeID, singerID);
                    }
                });
                deleteButton.setText("SİL");
                hBox.getChildren().addAll(getText(name), getText(getSingerTExt(singerID)), updateButton, deleteButton);
                musics.getChildren().add(hBox);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private String getSingerTExt(int singerID) {
        String quetry = "SELECT * FROM artist WHERE id = " + singerID;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(quetry);
            while (rs.next()){
                String name = rs.getString("name");
                return name;
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
