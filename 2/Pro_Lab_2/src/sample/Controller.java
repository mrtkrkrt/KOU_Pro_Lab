package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public Rectangle rct1;
    @FXML
    public Rectangle rct2;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Button button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void load(javafx.event.ActionEvent actionEvent) throws IOException {

        if (!Main.isTembel && !Main.isGozluklu) {
            Main.isTembel = true;
            Main.isGozluklu = false;
            Main.player = Main.players[1];
        }
        Main.isButtonPressed = true;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        anchor.getChildren().setAll(pane);
    }

    public void choose(MouseEvent mouseEvent){
        ImageView img = (ImageView) mouseEvent.getSource();

        if (img.getId().equals("img1")){
            Main.isTembel = true;
            Main.isGozluklu = false;
            rct1.setStrokeWidth(5);
            rct2.setStrokeWidth(0);
            Main.player = Main.players[1];
        }
        else if(img.getId().equals("img2")){
            Main.isTembel = false;
            Main.isGozluklu = true;
            rct2.setStrokeWidth(5);
            rct1.setStrokeWidth(0);
            Main.player = Main.players[0];
        }
    }
}
