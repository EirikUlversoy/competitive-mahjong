
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        Pane testPage = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        Stage testStage = new Stage();
        Scene testScene = new Scene(testPage);
        testStage.setScene(testScene);
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
