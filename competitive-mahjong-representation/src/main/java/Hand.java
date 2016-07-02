import java.util.ArrayList;
import java.util.List;

public class Hand {
    private String playerId;
    private List<Tile> tiles = new ArrayList<Tile>();

    Hand(String playerId){
        this.playerId = playerId;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }




}
