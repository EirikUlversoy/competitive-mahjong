import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Optional;

public class GraphicalTile extends Rectangle {
    private Gameboard board;
    public int x;
    public int y;

    public Optional<Tile> tile = Optional.empty();

    public GraphicalTile(int x, int y, Gameboard board, Tile tile){
        super(30,30);
        this.x = x;
        this.y = y;

        this.board = board;
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
        this.tile = Optional.of(tile);
    }
}
