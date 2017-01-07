
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends Application
{
    private Gameboard board;
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage theStage) throws IOException
    {
        //theStage.setTitle("Hello, World!");
        //theStage.show();
        //theStage.setMaxHeight(1000);
        //theStage.setMaxWidth(1000);
        Game game = new Game();
        board = new Gameboard();
        board.startGame();
        displayAltStage(board.getTileSet().getUnusedTiles());
    }

    public void displayAltStage(List<Tile> tiles) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("sample.fxml"));
        Pane testPage = fxmlLoader.load();
        MainController main = fxmlLoader.getController();
        //Pane testPage = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        Stage testStage = new Stage();
        Scene testScene = new Scene(testPage);
        testStage.setScene(testScene);
        TileSet testTileSet = new TileSet();
        testTileSet.initializeTiles();
        testTileSet.getUnusedTiles().forEach(z -> z.fixImage());
        List<Tile> initialTiles = testTileSet.getRandomTiles(14);
        initialTiles.stream().forEach(z -> System.out.println(z.getSuit().getIdentifier()+z.getTileNumber()));
        Map<Rectangle, Tile> rectangleTileMap = new HashMap<>();
        TilesFromFile tilesFromFile = new TilesFromFile();

        List<Tile> altInitialTiles = tilesFromFile.analyzeString("W12345678911332");
        altInitialTiles.forEach(z -> z.fixImage());
        //rectangleTileMap = main.fillHandRectangles(initialTiles);
        rectangleTileMap = main.fillHandRectangles(initialTiles);

        Map<Tile, Rectangle> tileRectangleMap = new HashMap<>();
        Map<Rectangle, Tile> copyRectangleTileMap = rectangleTileMap;
        rectangleTileMap.keySet().stream().forEach(z -> tileRectangleMap.put(copyRectangleTileMap.get(z),z));

        testStage.show();
    }
    public void displayMainStage(List<Tile> tiles) throws IOException{
        Pane page = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        Tile firstTile = tiles.get(0);
        //System.out.println(firstTile.getImage().isError());
        ImageView imv = new ImageView(firstTile.getImage());
        ImageView otherImv = new ImageView(tiles.get(1).getImage());
        Stage mainStage = new Stage();

        Group mainGroup = new Group();

        Scene mainScene = new Scene(page);

        mainGroup.getChildren().add(imv);
        mainGroup.getChildren().add(otherImv);

        mainStage.setScene(mainScene);

        mainStage.show();


    }

}
