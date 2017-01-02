import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainController implements Initializable {


    @FXML private Rectangle card;

    @FXML private HBox pond;
    @FXML private GridPane testPond;
    @FXML private GridPane p1hand;
    @FXML private HBox p1handbox;
    private List<Rectangle> pondRectangles = new ArrayList<>();
    //@FXML private List<Rectangle> rectangles;
    private Node currentNode;
    private Node secondNode;
    private TileSet tileset;

   // public Pane getMainStage() {
    //    return mainStage;
   // }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
       // assert Win != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";
       // Win.setOnAction(this::handleButtonAction);
        testPond.getChildren().stream().forEach(z -> pondRectangles.add((Rectangle)z));

        //southPond.setOnMouseClicked(this::handlePondClick);
        //setDefaultBackgrounds();
        testPond.setOnMouseClicked(this::test);
        testPond.setOnMouseEntered(this::testTwo);
        //testPond.setOnMouseEntered(this::lift);
       testPond.getChildren().stream().forEach(z -> {
            z.setOnMouseClicked(this::lift);
           z.setVisible(false);
            z.setDisable(true);
           //z.setDisable(true);
           //z.setOnMouseExited(this::sink);

        });
        Image image = new Image("/images/1Char.png");
        Rectangle rekt = (Rectangle)p1hand.getChildren().get(0);
        rekt.setFill(new ImagePattern(image));
        activateDiscardSlot(1);
        activateDiscardSlot(0);
        p1hand.getChildren().stream().forEach(z -> {
            z.setOnMouseClicked(this::liftHand);

        });
        //testPond.getChildren().removeIf(z -> true);

        setupTestStage();
        // initialize your logic here: all @FXML variables will have been injected

    }
    public void activateDiscardSlot(Integer slot){
        testPond.getChildren().get(slot).setVisible(true);
        testPond.getChildren().get(slot).setDisable(false);
    }
    public void setupTestStage(){

    }

    public void test(MouseEvent event){
        /*System.out.println("zzzz");
        testPond.relocate(10,10);
        pond.relocate(10,10);
        pond.resize(10,10);
    */
        //pond.translateYProperty().setValue(25);
    }

    public void sink(MouseEvent event){
        Rectangle target = (Rectangle)event.getSource();
        if(this.secondNode == target){

        } else {
            target.setTranslateY(0);
            this.secondNode = target;
        }
    }

    public void liftHand(MouseEvent event){
        if(event.getSource().getClass() != HBox.class){
            Rectangle target = (Rectangle)event.getSource();
            if(target.getTranslateY() == -25){
                target.setTranslateY(0);
                target.setStroke(Paint.valueOf("black"));
            } else {
                p1hand.getChildren().stream().forEach(z -> {
                    z.setTranslateY(0);
                    Rectangle newZ = (Rectangle)z;
                    newZ.setStroke(Paint.valueOf("black"));
                });

                target.setTranslateY(-25);
                target.setStroke(Paint.valueOf("red"));
                target.toFront();
            }
        }

    }
    public void lift(MouseEvent event){


        Rectangle target = (Rectangle)event.getSource();
        if(target.getTranslateY() == -25){
            target.setTranslateY(0);
            target.setStroke(Paint.valueOf("black"));

            //this.currentNode = null;
        } else {
            testPond.getChildren().stream().forEach(z ->{
                z.setTranslateY(0);
                Rectangle newZ = (Rectangle)z;
                newZ.setStroke(Paint.valueOf("black"));
            });
            target.setTranslateY(-25);
            target.setStroke(Paint.valueOf("red"));
            //this.currentNode = target;
            target.toFront();

        }


    }

    public void testTwo(MouseEvent event){

    }
    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("test");
    }
    public void fillHandRectangles(List<Tile> tiles){
        p1hand.getChildren().stream().map(z -> (Rectangle)z).forEach(z ->{
            if(tiles.size() != 0){
                z.setFill(new ImagePattern(tiles.get(0).getImage()));
                tiles.remove(0);
            }

        });
    }
    private void handleCardClick(MouseEvent event) {

        Game game = new Game();
        Gameboard board = new Gameboard();
        board.startGame();
        List<GraphicalTile> graphicalTiles = board.getTileSet().getUnusedTiles().stream()
                .map(z -> new GraphicalTile(30,30,board,z))
                .collect(Collectors.toList());
        int x = 500;
        int y = 500;

        for (GraphicalTile graphicalTile : graphicalTiles){
            x += 30;
            graphicalTile.x = x;
            graphicalTile.y = y;

        }


    }

    private void handlePondClick(MouseEvent event) {

    }

    private void handleTileMouseOver(MouseEvent event){

    }
}

