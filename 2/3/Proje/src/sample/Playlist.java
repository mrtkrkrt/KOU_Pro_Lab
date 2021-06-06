package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Playlist {
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button backMainMenu;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button clearButton;
    @FXML
    private ScrollPane scrollPane;

    private VBox şarkıListesi = new VBox();
    private ArrayList<String> topŞarkılar = new ArrayList<>();
    private int currentPlaylistID = Main.ids[Main.playlistID];
    private ArrayList<String> şarkılar = new ArrayList<>();
    private Map<String, Integer> infos = new HashMap<>();
    private Statement st2 = null;
    private Statement st = null;
    private Connection con = null;

    @FXML
    public void initialize(){
        scrollPane.setContent(null);
        şarkıListesi.getChildren().removeAll(şarkıListesi.getChildren());
        connectDatabase();
        connectDatabase();
        if (!Main.isTopPlaylist)addChildren();
        if (Main.isTopPlaylist){
            if (Main.isTopCountryPlaylist) addChildrenTopCountry();
            else addChildrenTop();
        }
        initializeButtons();
    }

    private void addChildrenTopCountry() {
        if (Main.topButton.equals("T")) addMusics("Turkey");
        else if (Main.topButton.equals("I")) addMusics("Italy");
        else addMusics("Avusturya");
    }

    private void addMusics(String country) {
        int j = 0;
        String query;
        try {
            st2 = con.createStatement();
            query = "SELECT m.name, m.dinlenme AS listened FROM music m \n" +
                    "LEFT JOIN artist s ON s.id = m.singerId \n" +
                    "WHERE s.country = '"+ country + "'\n" +
                    "ORDER BY listened DESC";
            ResultSet rs = st2.executeQuery(query);
            while (rs.next() && j<10){
                String name = rs.getString("name");
                String[] bilgiler = getMusicInfos(getMusicID(name));

                topŞarkılar.add(name);
                int dinlenme = rs.getInt("listened");

                HBox hBox = new HBox();
                CheckBox checkBox = new CheckBox();
                Button button = new Button();
                button.setText("OYNAT");
                button.setId(Integer.toString(j));
                boolean finalIsTop = false;
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        oynatTop(button.getId(), finalIsTop);
                    }
                });
                checkBox.setId(Integer.toString(j));
                hBox.getChildren().add(checkBox);
                hBox.getChildren().addAll(getText(name), getText(bilgiler[1]), getText(bilgiler[2]), new Text(Integer.toString(dinlenme)), button);
                şarkıListesi.getChildren().add(hBox);
                j++;
            }
            scrollPane.setContent(şarkıListesi);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        /*try {
            st = con.createStatement();
            query = "SELECT m.name, m.dinlenme AS listened FROM music m \n" +
                    "LEFT JOIN artist s ON s.id = m.singerId \n" +
                    "WHERE s.country = '"+ country + "'\n" +
                    "ORDER BY listened DESC";

            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int listened = rs.getInt("listened");
                String name = rs.getString("name");

                if (infos.containsKey(name)){
                    int temp = infos.get(name) + listened;
                    infos.remove(name);
                    infos.put(name, temp);
                }else{
                    infos.put(name, listened);
                }
            }
            Map<String, Integer> sortedMap = infos.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
            String[] songArray = sortedMap.keySet().toArray(new String[0]);
            if (songArray.length>0){
                for (int i = songArray.length-1;i>songArray.length-10 && i>0;i--){
                    String[] bilgiler = getMusicInfos(getMusicID(songArray[i]));
                    HBox hBox = new HBox();
                    CheckBox checkBox = new CheckBox();
                    Button button = new Button();
                    button.setText("OYNAT");
                    button.setId(Integer.toString(j));
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            oynatTop(button.getId(), false);
                        }
                    });
                    checkBox.setId(Integer.toString(j));
                    hBox.getChildren().add(checkBox);
                    topŞarkılar.add(bilgiler[0]);
                    hBox.getChildren().add(getText(bilgiler[0]));
                    hBox.getChildren().add(getText(bilgiler[1]));
                    hBox.getChildren().add(getText(bilgiler[2]));
                    hBox.getChildren().add(getDinlenme(getMusicID(bilgiler[0])));
                    hBox.getChildren().add(button);
                    şarkıListesi.getChildren().add(hBox);
                }
                scrollPane.setContent(şarkıListesi);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    private void addChildrenTop() {
        if (Main.topButton.equals("top")) setResultsToScene("Top");
        else if (Main.topButton.equals("pop")) setResultsToScene("Pop");
        else if (Main.topButton.equals("cazz")) setResultsToScene("Cazz");
        else if (Main.topButton.equals("klasik")) setResultsToScene("Klasik");
    }

    private void setResultsToScene(String tür) {
        String query;
        if (!tür.equals("Top")){
            query = "SELECT m.name, m.dinlenme FROM music m\n" +
                    "LEFT JOIN type t ON t.id = m.typeId\n"+
                    "WHERE t.name = '" + tür + "' ORDER BY m.dinlenme DESC";
        }else{
            query = "SELECT m.name, m.dinlenme FROM music m\n" +
                    "LEFT JOIN type t ON t.id = m.typeId\n"+
                    "ORDER BY m.dinlenme DESC";
        }
        int j = 0;
        try {
            st2 = con.createStatement();
            ResultSet rs = st2.executeQuery(query);
            while (rs.next() && j<10){
                String name = rs.getString("name");
                String[] bilgiler = getMusicInfos(getMusicID(name));

                topŞarkılar.add(name);
                int dinlenme = rs.getInt("dinlenme");

                HBox hBox = new HBox();
                CheckBox checkBox = new CheckBox();
                Button button = new Button();
                button.setText("OYNAT");
                button.setId(Integer.toString(j));
                boolean finalIsTop = false;
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        oynatTop(button.getId(), finalIsTop);
                    }
                });
                checkBox.setId(Integer.toString(j));
                hBox.getChildren().add(checkBox);
                hBox.getChildren().addAll(getText(name), getText(bilgiler[1]), getText(bilgiler[2]), new Text(Integer.toString(dinlenme)), button);
                şarkıListesi.getChildren().add(hBox);
                j++;
            }
            scrollPane.setContent(şarkıListesi);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Node getDinlenme(int musicID) {
        String query = "Select * FROM music WHERE id = " + musicID;
        try {
            st2 = con.createStatement();
            ResultSet rs = st2.executeQuery(query);
            int toplam=0;
            while(rs.next()){
                int id = rs.getInt("id");
                int dinlenme = rs.getInt("dinlenme");
                if (id==musicID){
                    toplam+=dinlenme;
                }
            }
            return new Text(Integer.toString(toplam));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Text();
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

    private void oynatTop(String id, boolean isTop) {
        String query;
        String şarkıİsmi = topŞarkılar.get(Integer.parseInt(id));
        if (isTop) query = "UPDATE music SET dinlenme = dinlenme + 1 WHERE id = " + getMusicID(şarkıİsmi);
        else query = "UPDATE music SET dinlenme = dinlenme + 1 WHERE id = " + getMusicID(şarkıİsmi);

        try {
            st = con.createStatement();
            st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addChildren() {

        String space = " ";
        int playlistID = Main.ids[Main.playlistID];

        VBox all = new VBox();
        int i = 0;

        String query = "Select * From playlisti̇çerik";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                HBox hBox = new HBox();
                int id = rs.getInt("playlistID");
                int musicId = rs.getInt("sarkıID");

                if (playlistID == id){
                    String[] bilgiler = getMusicInfos(musicId);
                    CheckBox checkBox = new CheckBox();
                    Button button = new Button();
                    button.setText("OYNAT");
                    button.setId(Integer.toString(i));
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            oynat(button.getId());
                        }
                    });
                    checkBox.setId(Integer.toString(i));
                    hBox.getChildren().add(checkBox);
                    şarkılar.add(bilgiler[0]);
                    hBox.getChildren().add(getText(bilgiler[0]));
                    hBox.getChildren().add(getText(bilgiler[1]));
                    hBox.getChildren().add(getText(bilgiler[2]));
                    hBox.getChildren().add(button);
                    all.getChildren().add(hBox);
                    i++;
                }
            }
            scrollPane.setContent(all);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        şarkıListesi = all;
    }

    private void oynat(String id) {
        String şarkıİsmi = şarkılar.get(Integer.parseInt(id));
        String query = "UPDATE music SET dinlenme = dinlenme + 1 WHERE id = " + getMusicID(şarkıİsmi);

        try {
            st = con.createStatement();
            st.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private int getMusicID(String id) {
        String query = "Select * from music WHERE name = '" +id+"'";
        try {
            st2 = con.createStatement();
            ResultSet rs = st2.executeQuery(query);
            while (rs.next()){
                int ids = rs.getInt("id");
                String name = rs.getString("name");
                if (name.equals(id)){
                    return ids; }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
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

    private Node getText(String name) {
        int len = name.length();
        int kalan = 30 - len;
        for (int i = 0; i < kalan; i++) {
            name += " ";
        }
        return new Text(name);
    }

    private void updateScene() {
        String query = "Select * FROM playlist WHERE id = " + Integer.toString(Main.ids[Main.playlistID]);
        int id = 0;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){ id = rs.getInt("id"); }

            query = "SELECT * FROM music WHERE playlistID = " + Integer.toString(id);

            rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initializeButtons() {
        backMainMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Node hBox : şarkıListesi.getChildren()){
                    for (Node checkBox : ((HBox) hBox).getChildren()){
                        if (checkBox instanceof CheckBox){
                            ((CheckBox) checkBox).setSelected(false);
                        }
                    }
                }
            }
        });

        updateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                şarkıListesi.getChildren().removeAll(şarkıListesi.getChildren());
                addChildren();
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for (Node hBox : şarkıListesi.getChildren()){
                    for (Node checkBox : ((HBox) hBox).getChildren()){
                        if (checkBox instanceof CheckBox && ((CheckBox) checkBox).isSelected()){
                            String query = "DELETE FROM playlisti̇çerik WHERE playlistID = " + Main.ids[Main.playlistID]  + " AND sarkıID = " + getMusicID(şarkılar.get(Integer.parseInt(checkBox.getId())));

                            try {
                                st = con.createStatement();
                                st.executeUpdate(query);
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
                clearButton.fire();
            }
        });
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
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
