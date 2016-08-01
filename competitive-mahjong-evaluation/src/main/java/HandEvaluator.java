import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
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
        List<Tile> souTiles = hand.getTiles().stream()
                .filter(z -> z.getClass() == SouTile.class)
                .collect(Collectors.toList());
        return souTiles;
    }

    public List<Tile> filterPin(){
        List<Tile> pinTiles = hand.getTiles().stream()
                .filter(z -> z.getClass() == PinTile.class)
                .collect(Collectors.toList());
        return pinTiles;
    }

    public List<SetGroup> checkForSets( List<Tile> input){
        Map<Integer, Integer> test = new HashMap();
        List<SetGroup> sets = new ArrayList<>();
        for (Tile tile : input){
            if(test.containsKey(tile.getTileNumber())){
                test.replace(tile.getTileNumber(),test.get(tile.getTileNumber())+1);
                if(test.get(tile.getTileNumber()) >= 3){

                    SetGroup newSet = new SetGroup(input.stream().filter(z -> z.getTileNumber() == tile.getTileNumber())
                    .collect(Collectors.toList()));
                    sets.add(newSet);
                }

            } else {
                test.put(tile.getTileNumber(),1);
            }
        }

        return sets;

    }
    public List<Tile> filterWan(){
        List<Tile> wanTiles = hand.getTiles().stream().filter(z -> z.getClass() == WanTile.class).collect(Collectors.toList());
        return wanTiles;
    }
    public List<Tile> reduceTileSet(List<Tile> tiles){
        //tiles.sort((z,x)-> z.getTileNumber());
        final List<Integer> previousTiles = tiles.stream()
                .map(Tile::getTileNumber)
                .collect(Collectors.toList());

        for( Tile tile : tiles){

        }
        List<SequenceGroup> sequenceGroupList = new ArrayList<>();

        List<Tile> newTiles =  tiles.stream()
                .filter( z -> previousTiles.contains(z.getTileNumber()-1) && previousTiles.contains(z.getTileNumber()+1))
                .sorted((z,x) -> Integer.compare(z.getTileNumber(),x.getTileNumber()) )
                .collect(Collectors.toList());

        System.out.println("should be sorted now...");
        newTiles.stream().map(Tile::toString).forEach(System.out::println);

        //newTiles.stream().forEach();



        //List<Tile> newTiles = tiles.stream().sorted((z,x) -> z.getTileNumber()
        //        .compareTo(x.getTileNumber()))
         //       .collect(Collectors.toList());
        //newTiles.stream().forEach(z -> System.out.println(z.toString()));
        return tiles;
    }
    private List<SequenceGroup> findValidSequenceGroups(int[] numbers){
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
    public List<SequenceGroup> findSequences(List<Tile> tiles) {

        List<SequenceGroup> validSequenceGroups = findValidSequenceGroups(this.tileNumbers);
        List<SequenceGroup> possibleGroups = new ArrayList<>();
        List<Tile> tileHolder = new ArrayList<>();

        for(Tile tile: tiles){
            Boolean firstHit = false;
            Boolean secondHit = false;
            Boolean thirdHit = false;

            }
        return null;

    }


    }

