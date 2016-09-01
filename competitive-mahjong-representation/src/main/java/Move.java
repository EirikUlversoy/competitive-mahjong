
public class Move {

    private Player player;
    private Tile tile;
    private Position newTileLocation;
    private Position oldTileLocation;
    private String typeOfMove;

    Move(Player player,  Tile tile,Position oldTileLocation, Position newTileLocation, String typeOfMove){
        this.player = player;
        this.tile = tile;
        this.oldTileLocation = oldTileLocation;
        this.newTileLocation = newTileLocation;
        this.typeOfMove = typeOfMove;
    }


}
