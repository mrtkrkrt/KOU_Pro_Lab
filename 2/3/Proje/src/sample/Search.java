package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;

public class Search {
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextArea searchText;
    @FXML
    private Button searchButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button backButton;

    private VBox vBox = new VBox();
    private Statement st = null;
    private Connection con = null;

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
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vBox.getChildren().removeAll(vBox.getChildren());
                String text = searchText.getText().trim();
                String query = "Select * FROM user WHERE name LIKE '%"+text+"%'";

                try {
                    st = con.createStatement();
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()){
                        String name = rs.getString("name");
                        int id = rs.getInt("id");
                        if(id != Main.userID)
                        addUserButtons(name, id);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                scrollPane.setContent(vBox);
            }
        });
    }

    private void addUserButtons(String name, int id) {
        Button button = new Button();
        button.setText(name);
        button.setId(Integer.toString(id));
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.searchUser = Integer.parseInt(button.getId());
                try {
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("Profile.fxml"));
                    anchor.getChildren().setAll(pane);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        vBox.getChildren().add(button);
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
