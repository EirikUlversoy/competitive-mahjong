import java.util.ArrayList;
import java.util.List;

public class Hand {
    private Integer playerId;
    private List<Tile> tiles = new ArrayList<Tile>();
    private List<Tile> leftoverTiles = new ArrayList<>();
    private List<Group> tileGroups = new ArrayList<>();

    private boolean closed = true;
    private Integer handsize = 13;
    Hand(Integer playerId){
        this.playerId = playerId;
    }
    private boolean isInRichiiState = false;

    Hand(Integer playerId, Integer amount){
        this.playerId = playerId;
        this.handsize = amount;

    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public void drawTile(Tile tile){
        if(tiles.size() <= 13){
            tiles.add(tile);
        }
    }

    public void discardTile(Tile tile){
        tiles.remove(tile);
    }

    public void initializeHand(TileSet tileSet){
        tiles = tileSet.getRandomTiles(handsize);
    }





}
