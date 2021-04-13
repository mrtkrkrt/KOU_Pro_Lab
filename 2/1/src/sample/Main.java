package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;
import sample.Classes.*;
import sample.Classes.Object;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.*;

public class Main extends Application {

    //Control Variables
    public static boolean isButtonPressed = false;
    public static int[][] maze = new int[11][13];
    public static boolean isTembel = false;
    public static boolean isGozluklu = false;
    public static boolean isWin = false;

    //Paths
    String tembelPath = "C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\tembel_50.png";
    String gözlukluPath = "C:\\Users\\mrtkr\\Desktop\\a\\src\\Images\\Gözlüklü_50.png";

    //GUI
    public static ImageView[] golds = new ImageView[5] ;
    public static int[][] coordinates;
    public static ImageView playerImage;
    public static ImageView sirine;
    public static ImageView mantar;
    public static ImageView azmanImage;
    public static int[] azmanBeginningCoordinates = new int[2];
    public static ImageView gargamelImage;
    public static int[] gargamelBeginningCoordinates = new int[2];
    public static Text scoreText;

    //Player
    Gozluklu gozluklu = new Gozluklu(1,"Gözlüklü","Player",new ArrayList<>(),20,5,15,2);
    Tembel tembel = new Tembel(2,"Tembel","Player",new ArrayList<>(),200,5,15,1);
    public static Player[] players = new Player[2];
    public static Player player;
    public boolean isBeginning =false;

    //Enemy
    public static Azman azman = new Azman(1,"Azman","Enemy",new ArrayList<>(),1,false);
    public static Gargamel gargamel = new Gargamel(2,"Gargamel","Enemy",new ArrayList<>(),2,true);

    //Class Variables
    public static Altın[] goldObjects = new Altın[5];
    public static Mantar mantarObject;

    //Dijkstra
    public static ArrayList<Node>  nodes = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root, 750, 650);
        primaryStage.setTitle("Smurfs Maze");
        primaryStage.setScene(scene);
        primaryStage.show();

        createMaze();
        createGraph();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isButtonPressed && !isWin) {
                    if (player.getName().equals("Gözlüklü")) Move(keyEvent);
                    updateScene(scene);
                    checkGetDamage();
                    if (!Move(keyEvent)) {
                        paintDijkstra(scene, azman.enKısaYol(), 1);
                        paintDijkstra(scene, gargamel.enKısaYol(), 2);
                    }
                    checkPickGold();
                    checkPickMushroom();
                    setHealthText();
                }
            }
        });
    }

    public static void main(String[] args) { launch(args); }

    public void createMaze() {
        File file = new File("C:\\Users\\mrtkr\\Desktop\\a\\src\\sample\\harita.txt");
        try {
            int i = 0, z = 0;
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                String[] datas = data.split(" ");
                if (i >= 2) {
                    for (int j = 0; j < datas.length; j++) {
                        maze[z][j] = Integer.parseInt(datas[j]);
                    }
                    z++;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Bir Hata Oluştu...");
            e.printStackTrace();
        }
        players[0] = gozluklu;
        players[1] = tembel;

    }

    public boolean Move(KeyEvent keyEvent){

            if (keyEvent.getCode() == KeyCode.UP){
                int x = (int) (playerImage.getX()-50)/50;
                int y = (int) (playerImage.getY()-50)/50;

                if (y>0 && maze[y-1][x] != 0){
                    playerImage.setY(playerImage.getY()-50);
                }
            }else if(keyEvent.getCode() == KeyCode.DOWN){
                int x = (int) (playerImage.getX()-50)/50;
                int y = (int) (playerImage.getY()-50)/50;

                if (y<10 && maze[y+1][x] != 0){
                    playerImage.setY(playerImage.getY()+50);
                }
            }else if (keyEvent.getCode() == KeyCode.LEFT){
                int x = (int) (playerImage.getX()-50)/50;
                int y = (int) (playerImage.getY()-50)/50;

                if (x>0 && maze[y][x-1] != 0){
                    playerImage.setX(playerImage.getX()-50);
                }
            }else if(keyEvent.getCode() == KeyCode.RIGHT){
                int x = (int) (playerImage.getX()-50)/50;
                int y = (int) (playerImage.getY()-50)/50;

                if (x<12 && maze[y][x+1] != 0){
                    playerImage.setX(playerImage.getX()+50);
                }
            }else{
                boolean statement = checkAfterMove();
                return true;
            }
            boolean statement = checkAfterMove();
            return statement;
    }

    private boolean checkAfterMove(){
        if (playerImage.getX() == azmanImage.getX() && playerImage.getY() == azmanImage.getY()){
            player.setScor(player.getScor()-player.getAzmanDamage());
            azmanImage.setX(azmanBeginningCoordinates[0]);
            azmanImage.setY(azmanBeginningCoordinates[1]);
            return  true;
        }if (playerImage.getX() == gargamelImage.getX() && playerImage.getY() == gargamelImage.getY()){
            player.setScor(player.getScor()-player.getGargamelDamage());
            gargamelImage.setX(gargamelBeginningCoordinates[0]);
            gargamelImage.setY(gargamelBeginningCoordinates[1]);
            return true;
        }
        return false;
    }


    private void paintDijkstra(Scene scene,ArrayList<Integer> arr,int color) {
        if (color == 1){
            for (int i = 0; i < arr.size(); i++) {
                String id = "#" + Integer.toString(arr.get(i));
                Rectangle rect = (Rectangle) scene.lookup(id);
                rect.setFill(Color.LIGHTGREEN);
            }
            String id = "#" + Integer.toString(arr.get(arr.size()-1));
            Rectangle rect = (Rectangle) scene.lookup(id);

            if (((azmanImage.getX() + 50 == gargamelImage.getX()) && (azmanImage.getY() == gargamelImage.getY())) || (azmanImage.getX() - 50 == gargamelImage.getX() && (azmanImage.getY() == gargamelImage.getY())) || ((azmanImage.getX() == gargamelImage.getX()) && (azmanImage.getY() + 50 == gargamelImage.getY())) || ((azmanImage.getX() == gargamelImage.getX()) && (azmanImage.getY() + 50 == gargamelImage.getY()))){

            }else{
                azmanImage.setX(rect.getX());
                azmanImage.setY(rect.getY());
            }


            if (azmanImage.getX() == playerImage.getX() && azmanImage.getY() == playerImage.getY()){
                azmanImage.setX(azmanBeginningCoordinates[0]);
                azmanImage.setY(azmanBeginningCoordinates[1]);
                player.setScor(player.getScor()-player.getAzmanDamage());
            }
        }else if (color==2){
            for (int i = 0; i < arr.size()-1; i++) {
                String id = "#" + Integer.toString(arr.get(i));
                Rectangle rect = (Rectangle) scene.lookup(id);
                rect.setFill(Color.LIGHTBLUE);
            }
            String id = "#" + Integer.toString(arr.get(arr.size()-1));
            Rectangle rect = (Rectangle) scene.lookup(id);
            gargamelImage.setX(rect.getX());
            gargamelImage.setY(rect.getY());
            if((rect.getX() == playerImage.getX() && rect.getY() == playerImage.getY())){
                gargamelImage.setX(gargamelBeginningCoordinates[0]);
                gargamelImage.setY(gargamelBeginningCoordinates[1]);
                player.setScor(player.getScor()-player.getGargamelDamage());
                //isBeginning = true;
            }else{
                String id1 = "#" + Integer.toString(arr.get(arr.size()-2));
                Rectangle rect1 = (Rectangle) scene.lookup(id1);
                gargamelImage.setX(rect1.getX());
                gargamelImage.setY(rect1.getY());
                if((rect1.getX() == playerImage.getX() && rect1.getY() == playerImage.getY())) {
                    gargamelImage.setX(gargamelBeginningCoordinates[0]);
                    gargamelImage.setY(gargamelBeginningCoordinates[1]);
                    player.setScor(player.getScor() - player.getGargamelDamage());
                }
            }
        }
    }

    public void checkPickGold(){

        for (int i = 0; i < 5; i++) {
            if (golds[i].getX() == playerImage.getX() && golds[i].getY() == playerImage.getY()){
                if (goldObjects[i].isPicked() == false && goldObjects[i].isVisible() == true){
                    golds[i].setVisible(false);
                    goldObjects[i].setVisible(false);
                    goldObjects[i].setPicked(true);
                    player.setScor(player.getScor()+5);
                    return;
                }
            }
        }
    }

    public void checkPickMushroom(){
        if (playerImage.getX() == mantar.getX() && playerImage.getY() == mantar.getY()){
            if (mantarObject.isPicked() == false && mantar.isVisible() == true){
                mantar.setVisible(false);
                mantarObject.setVisible(false);
                mantarObject.setPicked(true);
                player.setScor(player.getScor()+50);
                return;
            }
        }
    }

    public void updateScene(Scene scene){
        for (int i = 0; i <11 ; i++) {
            for (int j = 0; j < 13; j++) {
                String id = "#" +Integer.toString(i*13+j);
                if (maze[i][j] == 2){
                    Rectangle rectangle = (Rectangle) scene.lookup(id);
                    rectangle.setFill(Color.LIGHTGREEN);
                }else if (maze[i][j] == 1){
                    Rectangle rectangle = (Rectangle) scene.lookup(id);
                    rectangle.setFill(Color.WHITE);
                }else{
                    Rectangle rectangle = (Rectangle) scene.lookup(id);
                    rectangle.setFill(Color.BLACK);
                }
            }
        }
    }

    public void checkGetDamage(){
        if (azmanImage.getX() == playerImage.getX() && azmanImage.getY() == playerImage.getY()){
            player.setScor(player.getScor()-player.getAzmanDamage());
            azmanImage.setX(azmanBeginningCoordinates[0]);
            azmanImage.setY(azmanBeginningCoordinates[1]);
        }
        /*if (gargamelImage.getX() - playerImage.getX() <= 100 || gargamelImage.getY() - playerImage.getY() <= 100){
            player.setScor(player.getScor() - player.getGargamelDamage());
            gargamelImage.setX(gargamelBeginningCoordinates[0]);
            gargamelImage.setY(gargamelBeginningCoordinates[1]);
        }*/
    }

    public void setHealthText(){
        scoreText.setText(String.valueOf(player.getScor()));
    }

    public void createGraph(){
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 13; j++) {
                if (maze[i][j] == 1){
                    int id = i*13 + j;
                    Node node = new Node(id,null,new ArrayList<>());
                    createNeighours(node,i,j);
                    nodes.add(node);
                }else if (maze[i][j] == 0){
                    int id = i*13 + j;
                    Node node = new Node(id,null,new ArrayList<>());
                    nodes.add(node);
                }
            }
        }
    }

    public void createNeighours(Node node,int i,int j){
        if (i!=0 && i!=10 && j!=0 && j!=12){
            if (maze[i+1][j] == 1) node.getNeighbours().add((i+1)*13+j);

            if (maze[i-1][j] == 1) node.getNeighbours().add((i-1)*13+j);

            if (maze[i][j-1] == 1) node.getNeighbours().add(i*13+j-1);

            if (maze[i][j+1] == 1) node.getNeighbours().add(i*13+j+1);
        }else if(i==0){
            if (maze[i+1][j] == 1) node.getNeighbours().add((i+1)*13+j);

            if (maze[i][j-1] == 1) node.getNeighbours().add(i*13+j-1);

            if (maze[i][j+1] == 1) node.getNeighbours().add(i*13+j+1);
        }else if (j==0){
            if (maze[i+1][j] == 1) node.getNeighbours().add((i+1)*13+j);

            if (maze[i-1][j] == 1) node.getNeighbours().add((i-1)*13+j);

            if (maze[i][j+1] == 1) node.getNeighbours().add(i*13+j+1);
        }else if (i==10){
            if (maze[i-1][j] == 1) node.getNeighbours().add((i-1)*13+j);

            if (maze[i][j-1] == 1) node.getNeighbours().add(i*13+j-1);

            if (maze[i][j+1] == 1) node.getNeighbours().add(i*13+j+1);
        }else if (j==12){
            if (maze[i+1][j] == 1) node.getNeighbours().add((i+1)*13+j);

            if (maze[i-1][j] == 1) node.getNeighbours().add((i-1)*13+j);

            if (maze[i][j-1] == 1) node.getNeighbours().add(i*13+j-1);
        }
    }

    public static void dijkstra(int enemyId,int playerId){
        ArrayList<Node> clone = new ArrayList<>(nodes);
        ArrayList<Integer> distance = new ArrayList<>();
        ArrayList<Boolean> visited =  new ArrayList<>();

        for (int i = 0; i < clone.size(); i++) {
            distance.add(Integer.MAX_VALUE);
            visited.add(false);
        }
        distance.set(enemyId,0);

        for (int i = 0; i < clone.size(); i++) {
            int min = Integer.MAX_VALUE;
            int minIndex = 0;

            for (int j = 0; j < distance.size(); j++) {
                if (distance.get(j) < min && visited.get(j) != true){
                    minIndex=j;
                    min = distance.get(j);
                }
            }

            if (min == Integer.MAX_VALUE ) continue;
            visited.set(minIndex,true);
            for (Integer id : clone.get(minIndex).getNeighbours()){
                int alt = distance.get(minIndex) + 1;
                if (alt<distance.get(id)){
                    distance.set(id,alt);
                    nodes.get(id).setPrevious(nodes.get(minIndex));
                }
            }
        }
    }
}
