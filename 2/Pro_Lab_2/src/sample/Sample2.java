package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.Classes.Altın;
import sample.Classes.Mantar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Sample2 implements Initializable {

    private int countGold = 0;
    private int countMushroom = 0;

    @FXML
    private AnchorPane anchor2;
    @FXML
    private ImageView  img3;
    @FXML
    private ImageView gargamel;
    @FXML
    private ImageView azman;
    @FXML
    private ImageView sirine;
    @FXML
    private ImageView altın1;
    @FXML
    private ImageView altın2;
    @FXML
    private ImageView altın3;
    @FXML
    private ImageView altın4;
    @FXML
    private ImageView altın5;
    @FXML
    private ImageView mantar;
    @FXML
    private Text scorText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        defineVariables();
        changePic();
        addLines();
        addBlocks();
        enemySpawn();
        createGolds();
    }

    private void createGolds() {

        Main.golds[0] = altın1;
        Main.golds[1] = altın2;
        Main.golds[2] = altın3;
        Main.golds[3] = altın4;
        Main.golds[4] = altın5;


        Timer timerGold = new Timer();
        timerGold.schedule(new TimerTask() {
            @Override
            public void run() {

                createGoldObjects();
                Altın[] golds = Main.goldObjects;

                if (countGold % 2 == 1){
                    for (Altın a : golds) a.setVisible(false);
                    for (ImageView img : Main.golds) img.setVisible(false);
                }
                else{
                    createGoldCoordinates();
                }
                countGold++;
            }
        },0,5000);


        Timer timerMushroom = new Timer();
        timerMushroom.schedule(new TimerTask() {
            @Override
            public void run() {

                if (countMushroom % 2 == 1){
                    mantar.setVisible(false);
                }else{
                    mantar.setVisible(true);
                    createMushroom();
                }
                countMushroom++;
            }
        },0,7000);


        Timer timerEnd = new Timer();
        timerEnd.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        try {
                            checkWinOrLose();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        },0,10);
    }

    public void addLines(){
        for (int i = 0; i < 14; i++) {
            Line line = new Line(i * 50 + 50, 50, i * 50 + 50, 600);
            anchor2.getChildren().addAll(line);
        }
        for (int i = 0; i < 12; i++) {
            Line line = new Line(50, i * 50 + 50, 700, i * 50 + 50);
            anchor2.getChildren().addAll(line);
        }
    }

    public void addBlocks(){

        int k = 0;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 13; j++) {
                if (Main.maze[i][j] == 0){
                    Rectangle rectangle = new Rectangle(j*50+50,i*50+50,50,50);
                    String id = Integer.toString(i*13+j);
                    rectangle.setId(id);
                    rectangle.setFill(Color.BLACK);
                    anchor2.getChildren().addAll(rectangle);
                }else if (Main.maze[i][j] == 2){
                    Rectangle rectangle = new Rectangle(j*50+50,i*50+50,50,50);
                    String id = Integer.toString(i*13+j);
                    rectangle.setId(id);
                    rectangle.setFill(Color.GREEN);
                    anchor2.getChildren().addAll(rectangle);
                }else if (Main.maze[i][j] == 1){
                    Rectangle rectangle = new Rectangle(j*50+50,i*50+50,49,49);
                    String id = Integer.toString(i*13+j);
                    rectangle.setId(id);
                    rectangle.setFill(Color.WHITE);
                    anchor2.getChildren().addAll(rectangle);
                }
                k++;
            }
        }
    }


    private void changePic(){

        File fileSirine = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\Şirine.png");
        Image img = new Image(fileSirine.toURI().toString());
        sirine.setImage(img);

        if (Main.isTembel) {
            File file = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\tembel_50.png");
            Image image = new Image(file.toURI().toString());
            img3.setImage(image);
        }else{
            File file = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\Gözlüklü_50.png");
            Image image = new Image(file.toURI().toString());
            img3.setImage(image);
        }

        sirine.setX(700);
        sirine.setY(400);
        img3.setX(350);
        img3.setY(300);
    }

    private void enemySpawn(){

        File fileAzman = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\azman_50.png");
        File fileGargamel = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\gargamel.png");

        File file = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\sample\\harita.txt");
        try {
            int i =0;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String data = scanner.nextLine();
                if (i<2){
                    String character = data.split(":")[1].split(",")[0];
                    String kapı = data.split(":")[2];

                    if (character.equals("Gargamel")){
                        Image img = new Image(fileGargamel.toURI().toString());
                        gargamel.setImage(img);

                        switch (kapı){
                            case "A":
                                gargamel.setX(200);
                                gargamel.setY(50);
                                break;

                            case "B":
                                gargamel.setX(550);
                                gargamel.setY(50);
                                break;

                            case "C":
                                gargamel.setX(50);
                                gargamel.setY(300);
                                break;

                            case "D":
                                gargamel.setX(200);
                                gargamel.setY(550);
                                break;
                        }
                    }else if(character.equals("Azman")){
                        Image img = new Image(fileAzman.toURI().toString());
                        azman.setImage(img);

                        switch (kapı){
                            case "A":
                                azman.setX(200);
                                azman.setY(50);
                                break;

                            case "B":
                                azman.setX(550);
                                azman.setY(50);
                                break;

                            case "C":
                                azman.setX(50);
                                azman.setY(300);
                                break;

                            case "D":
                                azman.setX(200);
                                azman.setY(550);
                                break;
                        }
                    }
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Main.azmanBeginningCoordinates[0] = (int) azman.getX();
        Main.azmanBeginningCoordinates[1] = (int) azman.getY();
        Main.gargamelBeginningCoordinates[0] = (int) gargamel.getX();
        Main.gargamelBeginningCoordinates[1] = (int) gargamel.getY();
    }


    public void createGoldCoordinates(){

        int[][] coordinates = new int[5][2];
        ArrayList<Integer> xMap = new ArrayList<>();
        ArrayList<Integer> yMap = new ArrayList<>();
        int j = 0;

        Random rand = new Random();

        for (int i = 0; i < 5; i++) {
            while (true){
                int x = rand.nextInt(11);
                int y = rand.nextInt(13);

                if (!xMap.contains(x) && !yMap.contains(y)){
                    if (Main.maze[x][y] == 1){
                        coordinates[j][0] = y;
                        coordinates[j][1] = x;
                        xMap.add(x);
                        yMap.add(y);
                        j++;
                        break;
                    }
                }
            }
        }

        for (ImageView img : Main.golds){
            File fileGold = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\gold.png");
            Image image = new Image(fileGold.toURI().toString());
            img.setImage(image);
        }

        for (int i = 0; i < 5; i++) {
            Main.golds[i].setX((coordinates[i][0]*50)+50);
            Main.golds[i].setY((coordinates[i][1]*50)+50);
            Main.golds[i].setVisible(true);
        }

    }

    private void createMushroom(){
        int[] coordinate = new int[2];
        Random rand = new Random();

        while (true){
            int x = rand.nextInt(11);
            int y = rand.nextInt(13);

            if (Main.maze[x][y] == 1){
                coordinate[0] = y;
                coordinate[1] = x;
                break;
            }
        }

        File file = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\mantar.png");
        Image image = new Image(file.toURI().toString());
        mantar.setImage(image);

        mantar.setX((coordinate[0]*50)+50);
        mantar.setY((coordinate[1]*50)+50);

    }
    
    public void createGoldObjects(){
        for (int i = 0; i < 5; i++) {
            Main.goldObjects[i] = new Altın(5,true,false);
        }
        Main.mantarObject = new Mantar(15,true,false);
    }

    public void defineVariables(){
        Main.playerImage = img3;
        Main.sirine = sirine;
        Main.mantar = mantar;
        Main.scoreText = scorText;
        Main.azmanImage = azman;
        Main.azmanBeginningCoordinates[0] = (int) azman.getX();
        Main.azmanBeginningCoordinates[1] = (int) azman.getY();
        Main.gargamelImage = gargamel;
        Main.gargamelBeginningCoordinates[0] = (int) gargamel.getX();
        Main.gargamelBeginningCoordinates[1] = (int) gargamel.getY();
        scorText.setText("20");
    }

    private void checkWinOrLose() throws IOException {
        if (Main.player.getScor() <= 0){
            AnchorPane pane = FXMLLoader.load(getClass().getResource("sample3.fxml"));
            anchor2.getChildren().setAll(pane);
        }if (sirine.getY() == img3.getY() && sirine.getX()-50 == img3.getX()){
            AnchorPane pane = FXMLLoader.load(getClass().getResource("sample3.fxml"));
            anchor2.getChildren().setAll(pane);
            Main.isWin = true;
        }



    }
}
