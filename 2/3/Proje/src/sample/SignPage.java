package sample;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.*;

public class SignPage {
    @FXML
    private AnchorPane anchor;
    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private Text errorText;
    @FXML
    private CheckBox premium;

    private Connection con = null;
    private Statement st = null;
    private boolean control = false;

    public void signUp(MouseEvent mouseEvent){
        connectDatabase();
        checkInfos();
    }

    private void checkInfos() {
        String name = username.getText();
        String mail = email.getText();
        String pass = password.getText();
        String confirm = confirmPassword.getText();
        String type = "";

        if (premium.isSelected()) type = "Premium";
        else type = "Normal";

        if (name.isEmpty() || mail.isEmpty() || pass.isEmpty() || confirm.isEmpty()){
            errorText.setText("Lütfen Tüm Alanları Doldurun!");
            return;
        }else{
            if (pass.equals(confirm)){
                String query = "INSERT INTO user(name, password, email, type) Values(" + "'" + name + "'," + "'" + pass + "'," + "'" + mail + "'," + "'" + type + "')";
                try {
                    st = con.createStatement();

                    String checkQuery = "SELECT email FROM user";
                    ResultSet rs = st.executeQuery(checkQuery);

                    while (rs.next()){
                        if (rs.getString("email").equals(mail)){
                            errorText.setText("Bu Mail İle Kullanıcı Var!");
                            control = true;
                            break;
                        }
                    }

                    if (!control){
                        st.executeUpdate(query);
                        System.out.println("Kullanıcı Eklendi");
                        loadLogInPage();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private void loadLogInPage() {
        errorText.setText("Kaydoldunuz.Giriş Ekranına Yönlendiriliyosunuz!");
        try {
            Thread.sleep(3000);
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Login_Page.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void backLogIn(MouseEvent mouseEvent){
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("Login_Page.fxml"));
            anchor.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void connectDatabase(){
        String url = "jdbc:mysql://" + "localhost" + ":" + "3306" + "/" + "spotify" + "?useUnicode=true&characterEncoding=utf8";

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
