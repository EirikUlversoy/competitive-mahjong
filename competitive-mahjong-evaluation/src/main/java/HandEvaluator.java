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

    public List<Tile> reduceTileSet(List<Tile> tiles){
        List<Tile> newTiles = tiles.stream().sorted((z,x) -> z.getTileNumber()
                .compareTo(x.getTileNumber()))
                .collect(Collectors.toList());
        newTiles.stream().forEach(z -> System.out.println(z.toString()));
        return newTiles;
    }
    public List<SequenceGroup> findValidSequenceGroups(int[] numbers){
        List<SequenceGroup> validSequenceGroups = new ArrayList<>();
        for (int number: numbers){
            if(number <= 7){
                Tile tile = new Tile();
                tile.setTileNumber(number);
                Tile secondTile = new Tile();
                secondTile.setTileNumber(number+1);
                Tile thirdTile = new Tile();
                thirdTile.setTileNumber(number+2);
                validSequenceGroups.add(new SequenceGroup(tile,secondTile,thirdTile));

            }


        }
        return validSequenceGroups;
    }
    public List<SequenceGroup> findSequences(List<Tile> tiles, List<SequenceGroup> validSequenceGroups) {
    return null;
    }
}
