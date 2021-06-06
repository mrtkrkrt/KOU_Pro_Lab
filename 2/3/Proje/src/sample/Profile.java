package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.control.*;

import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;

public class Profile {
    @FXML
    private AnchorPane anchor;
    @FXML
    private Text infoText;
    @FXML
    private Text nameText;
    @FXML
    private javafx.scene.control.Button mainMenu;
    @FXML
    private javafx.scene.control.Button pop;
    @FXML
    private javafx.scene.control.Button cazz;
    @FXML
    private javafx.scene.control.Button klasik;
    @FXML
    private Button follow;

    private Statement st = null;
    private Connection con = null;
    private Timer timer = new Timer();

    @FXML
    void initialize(){
        connectDatabase();
        initializeButtons();
        initializeTexts();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (follow.getText().equals("Takip Et")){
                    pop.setVisible(false);
                    cazz.setVisible(false);
                    klasik.setVisible(false);
                }else{
                    pop.setVisible(true);
                    cazz.setVisible(true);
                    klasik.setVisible(true);
                }
            }
        },0,500);
    }

    private void initializeButtons() {
        mainMenu.setOnAction(new EventHandler<ActionEvent>() {
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

        pop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String query = "Select * FROM playlist WHERE name = 'POP' and userID = " + Integer.toString(Main.searchUser);
                searchForUser(query);
            }
        });

        cazz.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String query = "Select * FROM playlist WHERE name = 'CAZZ' and userID = " + Integer.toString(Main.searchUser);
                searchForUser(query);
            }
        });

        klasik.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String query = "Select * FROM playlist WHERE name = 'KLASİK' and userID = " + Integer.toString(Main.searchUser);
                searchForUser(query);
            }
        });

        follow.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (follow.getText().equals("Takibi Bırak")){
                    String query = "DELETE FROM follower WHERE takipEdenID = " + Main.userID + " and takipEdilenID = "+ Main.searchUser;
                    try {
                        st = con.createStatement();
                        st.executeUpdate(query);
                        follow.setText("Takip Et");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }else{
                    String query = "INSERT INTO follower(takipedenID, takipEdilenID) VALUES("+Main.userID +", " +Main.searchUser +")";
                    try {
                        st = con.createStatement();
                        st.executeUpdate(query);
                        follow.setText("Takibi Bırak");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
    }

    private void searchForUser(String query){
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int id = rs.getInt("id");
                Main.searchUserPlaylistID = id;
            }
            timer.cancel();
            AnchorPane pane = FXMLLoader.load(getClass().getResource("userPlaylist.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeTexts() {
        String query = "Select * FROM user WHERE id = " + Integer.toString(Main.searchUser);
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String name = rs.getString("name");
                String type = rs.getString("type");
                if (type.equals("Normal")) follow.setVisible(false);
                nameText.setText(name + "  (" + type +")");
                setFollowerCount();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void setFollowerCount() {
        int countFollow = 0, countFollowers = 0;
        String query = "Select * FROM follower";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                int userid = rs.getInt("takipEdenID");
                int profileid = rs.getInt("takipEdilenID");

                if (userid == Main.userID && profileid == Main.searchUser){
                    follow.setText("Takibi Bırak");
                }

                if (userid == Main.searchUser) countFollow++;
                else if (profileid == Main.searchUser) countFollowers++;
            }
            String s = countFollow + " Takip Edilen\n "+ countFollowers + " Takipçi";
            infoText.setText(s);
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
