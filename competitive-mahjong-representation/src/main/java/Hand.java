import java.util.ArrayList;
import java.util.List;

public class Hand {
    private Integer playerId;
    private List<Tile> tiles = new ArrayList<Tile>();
    private List<Tile> leftoverTiles = new ArrayList<>();
    private List<SetGroup> setGroups = new ArrayList<>();
    private List<SequenceGroup> sequenceGroups = new ArrayList<>();

    private Pair pair;
    public boolean closed = true;
    public boolean tsumo = true;
    public boolean menzenkafu = true;
    public boolean singleWait = true;
    public boolean openPinfu = true;
    public boolean sevenPairs = true;

    private Integer handsize = 13;
    Hand(Integer playerId){
        this.playerId = playerId;
    }
    private boolean isInRichiiState = false;

    public List<SetGroup> getSetGroups() {
        return setGroups;
    }

    public void setConditions(boolean closed, boolean tsumo, boolean menzenkafu, boolean singleWait, boolean openPinfu
    , boolean sevenPairs){
        this.closed = closed;
        this.tsumo = tsumo;
        this.menzenkafu = menzenkafu;
        this.singleWait = singleWait;
        this.openPinfu = openPinfu;
        this.sevenPairs = sevenPairs;

    }


    public void setSetGroups(List<SetGroup> setGroups) {
        this.setGroups = setGroups;
    }

    public void addSetGroup(SetGroup s){
        this.setGroups.add(s);
    }

    public void addSequenceGroup(SequenceGroup s){
        this.sequenceGroups.add(s);
    }

    public List<SequenceGroup> getSequenceGroups() {
        return sequenceGroups;
    }

    public void setSequenceGroups(List<SequenceGroup> sequenceGroups) {
        this.sequenceGroups = sequenceGroups;
    }

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

    public Pair getPair() {
        return pair;
    }

    public void setPair(Pair pair) {
        this.pair = pair;
    }
}
