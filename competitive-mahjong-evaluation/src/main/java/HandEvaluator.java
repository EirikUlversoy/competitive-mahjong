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
    public List<SequenceGroup> reduceTileSet(List<Tile> tiles){
        //tiles.sort((z,x)-> z.getTileNumber());
        final List<Integer> previousTiles = tiles.stream()
                .map(Tile::getTileNumber)
                .collect(Collectors.toList());

        final List<Tile> actualPreviousTiles = tiles;

        List<SequenceGroup> sequenceGroupList = new ArrayList<>();

        List<SequenceGroup> possibleSeqGroups = new ArrayList<>();
        List<Tile> sequenceGroupTiles = new ArrayList<>();
        boolean DO_NOT_ADD = false;
        for (Tile tile : tiles){
            sequenceGroupTiles.add(tile);

            for(Tile tile1 : actualPreviousTiles){
                if(sequenceGroupTiles.size() <= 2){
                    if((tile1.getTileNumber() == tile.getTileNumber()+1) || (tile1.getTileNumber() == tile.getTileNumber()-1) ){
                        for (Tile tile2 : sequenceGroupTiles){
                            if(tile2.getTileNumber() == tile1.getTileNumber()){
                                DO_NOT_ADD = true;
                            }

                        }
                        if(!DO_NOT_ADD){
                            sequenceGroupTiles.add(tile1);
                            System.out.println("added " + tile1.toString() + " because of " + tile.getTileNumber()
                                    + "and" + tile1.getTileNumber());
                            System.out.println("Sequence group is now " + sequenceGroupTiles.toString());

                        } else {
                            DO_NOT_ADD = false;
                        }
                        if(sequenceGroupTiles.size() == 3){
                            possibleSeqGroups.add(new SequenceGroup
                                    (sequenceGroupTiles.get(0),sequenceGroupTiles.get(1),sequenceGroupTiles.get(2)));
                            System.out.println("Made a new sequence group!");
                            sequenceGroupTiles.clear();
                            System.out.println("Cleared the sequence, it is now: " + sequenceGroupTiles.toString());
                            sequenceGroupTiles.add(tile);
                        }

                    }

            }
        }}

        System.out.println("Now printing found sequences");
        possibleSeqGroups.stream().map(SequenceGroup::toString).forEach(System.out::println);




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

