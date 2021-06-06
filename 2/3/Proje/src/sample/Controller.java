package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.*;

public class Controller {
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button ok;
    @FXML
    private Button registerButton;
    @FXML
    private Text errorText;

    private Connection con = null;
    private Statement st = null;

    @FXML
    public void initialize() {
        connectDatabase();
    }

    public void logIn(MouseEvent mouseEvent){
        if (checkVariables()){
            loadNextPage();
        }else{
            setErrorText();
        }
    }

    private void setErrorText() {
        String name = username.getText();
        String pass = password.getText();

        if (name.isEmpty() || pass.isEmpty()){
            errorText.setText("Lütfen Tüm Alanları Doldurun!");
        }else{
            errorText.setText("Kullanıcı Adı Veya Parola Yanlış!");
        }
    }

    public void loadRegisterPage(MouseEvent mouseEvent){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInAdmin(MouseEvent mouseEvent){
        connectDatabase();
        String nameUser = username.getText();
        String passUser = password.getText();

        if (nameUser.isEmpty() || passUser.isEmpty()){
            errorText.setText("Lütfen Tüm Alanları Doldurun!");
        }

        String query = "Select * from admin";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                String name = rs.getString("email");
                String pass = rs.getString("password");
                int id = rs.getInt("id");

                if (name.equals(nameUser) && pass.equals(passUser)){
                    Main.userID = id;
                    AnchorPane pane = FXMLLoader.load(getClass().getResource("adminMainPage.fxml"));
                    anchor.getChildren().setAll(pane);
                    return;
                }
            }

        } catch (SQLException throwables) {
            System.out.println("Veriler Çekilemedi!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        errorText.setText("Kullanıcı Adı Veya Parola Yanlış!");
    }

    private void loadNextPage() {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean executeStatement(String name , String pass) {

        String query = "Select * FROM user";

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
                String getName = rs.getString("name");
                String getEmail = rs.getString("email");
                String getPass = rs.getString("password");
                int id = rs.getInt("id");

                if (getPass.equals(pass) && (getName.equals(name) || getEmail.equals(name))){
                    Main.userID = id;
                    return true;
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Sorgu Çalışmadı...");
        }
        return false;
    }

    private boolean checkVariables() {
        String name = username.getText();
        String pass = password.getText();
        
        if (name.equals("") || pass.equals("")){
            return false;
        }
        return executeStatement(name, pass);
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
            errorText.setText("İnternet Bağlantınızı Kontrol Ediniz.");
        }
    }
}
