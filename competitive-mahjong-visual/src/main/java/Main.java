
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application
{
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage theStage)
    {
        theStage.setTitle("Hello, World!");
        theStage.show();
        theStage.setMaxHeight(1000);
        theStage.setMaxWidth(1000);
        Game game = new Game();
        Gameboard board = new Gameboard();
        board.startGame();
        displayMainStage(board.getTileSet().getUnusedTiles());
        for(Tile tile : board.getTileSet().getUnusedTiles()){
            System.out.println("Trying to display:" + tile.getTileId());
            ImageView imv = new ImageView();
            imv.setImage(tile.getImage());


        }
    }

    public void displayMainStage(List<Tile> tiles){

        Tile firstTile = tiles.get(0);
        System.out.println(firstTile.getImage().isError());
        ImageView imv = new ImageView(firstTile.getImage());
        Stage mainStage = new Stage();

        Group mainGroup = new Group();

        Scene mainScene = new Scene(mainGroup);

        mainGroup.getChildren().add(imv);

        mainStage.setScene(mainScene);

        mainStage.show();
    }

}
