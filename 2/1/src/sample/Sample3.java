package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Sample3 implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private Text overText;
    @FXML
    private Button quitButton;
    @FXML
    private Button playAgainButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        declareGUI();
    }

    public void declareGUI(){
        Main.isButtonPressed = false;

        if (Main.isWin){
            overText.setText("K A Z A N D I N");
        }else{
            overText.setText("K A Y B E T T İ N");
        }

    }

    public void loadFirstScene(ActionEvent actionEvent) {
        System.out.println("Tıklandı...");
    }

    public void quit(ActionEvent actionEvent) {
    }
}
