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

    public List<Tile> filterWan(){
        List<Tile> wanTiles = hand.getTiles().stream()
                .filter(z -> z.getClass() == WanTile.class)
                .collect(Collectors.toList());
        return wanTiles;
    }

    public List<Tile> filterSuit(Class aClass){
        List<Tile> tiles = hand.getTiles().stream()
                .filter(z -> z.getClass() == aClass)
                .collect(Collectors.toList());
        return tiles;
    }
    public List<SetGroup> findSets( List<Tile> input){
        Map<Integer, Integer> tilenumberToAmount = new HashMap();
        List<SetGroup> sets = new ArrayList<>();
        for (Tile tile : input){
            if(tilenumberToAmount.containsKey(tile.getTileNumber())){
                tilenumberToAmount.replace(tile.getTileNumber(),tilenumberToAmount.get(tile.getTileNumber())+1);
                if(tilenumberToAmount.get(tile.getTileNumber()) == 3){

                    SetGroup newSet = new SetGroup(input.stream().filter(z -> z.getTileNumber() == tile.getTileNumber())
                    .collect(Collectors.toList()));
                    sets.add(newSet);

                }

            } else {
                tilenumberToAmount.put(tile.getTileNumber(),1);
            }
        }

        return sets;

    }

    /**
     * Finds the sequences in the given tile list. The tilelist passed in should be of only one suit.
     * @param tiles
     * @return
     */
    public List<SequenceGroup> findSequences(List<Tile> tiles){
        //tiles.sort((z,x)-> z.getTileNumber());
        final List<Integer> previousTiles = tiles.stream()
                .map(Tile::getTileNumber)
                .collect(Collectors.toList());

        final List<Tile> actualPreviousTiles = tiles;
        Map<String, Tile> stringTileMap = new HashMap<>();

        List<SequenceGroup> possibleSeqGroups = new ArrayList<>();

        List<Tile> newTiles = tiles.stream()
                .peek(z -> stringTileMap.put(z.getTileNumber()+"",z))
                .filter(z -> previousTiles.contains(z.getTileNumber()+1) && previousTiles.contains(z.getTileNumber()-1)
                        || previousTiles.contains(z.getTileNumber()+1) && previousTiles.contains(z.getTileNumber()+2)
                        || previousTiles.contains(z.getTileNumber()-1) && previousTiles.contains(z.getTileNumber()-2))
        .collect(Collectors.toList());


        List<Tile> middleSequenceTiles = tiles.stream()
                .filter(z -> previousTiles.contains(z.getTileNumber()+1) && previousTiles.contains(z.getTileNumber()-1))
                .peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber()+1),z, new Tile(z.getTileNumber()-1))))
                .collect(Collectors.toList());

        List<Tile> risingSequenceTiles = tiles.stream()
                .filter(z -> previousTiles.contains(z.getTileNumber()+1) && previousTiles.contains(z.getTileNumber()+2))
                .peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber()+1),z, new Tile(z.getTileNumber()+2))))
                .collect(Collectors.toList());

        List<Tile> sinkingSequenceTiles = tiles.stream()
                .filter(z -> previousTiles.contains(z.getTileNumber()-1) && previousTiles.contains(z.getTileNumber()-2))
                .peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber()-1),z, new Tile(z.getTileNumber()-2))))
                .collect(Collectors.toList());

        List<Tile> mrsTiles = new ArrayList<>();
        mrsTiles.addAll(middleSequenceTiles);
        mrsTiles.addAll(sinkingSequenceTiles);
        mrsTiles.addAll(risingSequenceTiles);

        List<Tile> newMrsTiles = mrsTiles.stream().distinct().collect(Collectors.toList());

        System.out.println("Printing remaining tiles");
        newMrsTiles.stream().map(Tile::toString).forEach(System.out::println);
        System.out.println("Printing possible sequence groups");
        possibleSeqGroups.stream().map(SequenceGroup::toString).forEach(System.out::println);
        possibleSeqGroups.stream().peek(z -> z.get);

        return possibleSeqGroups;
    }

    public Integer checkForOverlap(List<Group> groupList){
        boolean NOT_VALID = false;
        Integer validGroupCount = 0;
        Map<String, Tile > stringTileMap = new HashMap<>();
        for (Group group : groupList){
            validGroupCount += 1;

            if(stringTileMap.containsKey(group.getFirstMember().toString())){
                NOT_VALID = true;
            } else {
                stringTileMap.put(group.getFirstMember().toString(),group.getFirstMember());
            }

            if(stringTileMap.containsKey(group.getSecondMember().toString())){
                NOT_VALID = true;
            } else {
                stringTileMap.put(group.getSecondMember().toString(), group.getSecondMember());

            }

            if(stringTileMap.containsKey(group.getThirdMember().toString())){
                NOT_VALID = true;
            } else {
                stringTileMap.put(group.getThirdMember().toString(), group.getThirdMember());

            }

            if(NOT_VALID == true){
                validGroupCount -= 1;
            }

        }
        return validGroupCount;
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


    }

