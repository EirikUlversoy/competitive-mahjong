import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Should assess value of final hand
 */
public class HandEvaluator {
    private Hand hand;
    private int[] tileNumbers = {1,2,3,4,5,6,7,8,9};

    HandEvaluator(Hand hand){
        this.hand = hand;
    }

    public List<Tile> filterSou(){
        List<Tile> souTiles = hand.getTiles().stream().filter(z -> z.getClass() == SouTile.class).collect(Collectors.toList());
        return souTiles;
    }

    public List<Tile> filterPin(){
        List<Tile> pinTiles = hand.getTiles().stream().filter(z -> z.getClass() == PinTile.class).collect(Collectors.toList());
        return pinTiles;
    }

    public List<Tile> filterWan(){
        List<Tile> wanTiles = hand.getTiles().stream().filter(z -> z.getClass() == WanTile.class).collect(Collectors.toList());
        return wanTiles;
    }
    public List<SequenceGroup> findValidSequenceGroups(){
        return null;
    }
    public List<SequenceGroup> findSequences(List<Tile> tiles) {
        return null;
    }
}
