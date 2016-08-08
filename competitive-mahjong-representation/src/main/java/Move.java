
public class Move {

    private Player player;
    private Tile tile;
    private String newTileLocation;
    private String typeOfMove;

    Move(Player player,  Tile tile, String newTileLocation, String typeOfMove){
        this.player = player;
        this.tile = tile;
        this.newTileLocation = newTileLocation;
        this.typeOfMove = typeOfMove;
    }


}
