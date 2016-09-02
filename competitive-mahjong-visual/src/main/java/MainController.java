import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MainController implements Initializable {
    @FXML private Pane mainStage;
    @FXML private Button Win;

    @FXML private Pane southPond;
    @FXML private Pane northPond;
    @FXML private Pane eastPond;
    @FXML private Pane westPond;

    @FXML private Pane northWall;
    @FXML private Pane eastWall;
    @FXML private Pane westWall;
    @FXML private Pane southWall;

    @FXML private Pane northCallArea;
    @FXML private Pane southCallArea;
    @FXML private Pane eastCallArea;
    @FXML private Pane westCallArea;

    @FXML private Rectangle card;

    private TileSet tileset;

    public Pane getMainStage() {
        return mainStage;
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert Win != null : "fx:id=\"myButton\" was not injected: check your FXML file 'simple.fxml'.";
        Win.setOnAction(this::handleButtonAction);
        southPond.setOnMouseClicked(this::handlePondClick);
        setDefaultBackgrounds();
        // initialize your logic here: all @FXML variables will have been injected

    }

    public void setDefaultBackgrounds(){
        northPond.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        westPond.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        eastPond.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        southPond.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        northWall.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));
        westWall.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));
        eastWall.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));
        southWall.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, CornerRadii.EMPTY, Insets.EMPTY)));

        //northCallArea.toString();
        northCallArea.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        westCallArea.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        eastCallArea.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        southCallArea.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        //northPond.setStyle("-fx-background-image: url(http://www.bogotobogo.com/images/java/tutorial/java_images/Duke256.png");




        card.setHeight(600);

        System.out.println(card.getHeight());

    }
    public void changeMainStage(){
        Win.getOnMouseClicked();
    }

    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something...
        System.out.println("test");
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
        southPond.setBackground(new Background(new BackgroundFill(Color.web("#" + "abc"), CornerRadii.EMPTY, Insets.EMPTY)));

    }
}

