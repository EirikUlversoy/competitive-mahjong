import java.util.*;
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

    public Map<Integer, List<Tile>> findTileCount(List<Tile> tiles){
        Map<Integer, Integer> tilesCount = new HashMap<>();
        Map<Integer, List<Tile>> numberToTile = new HashMap<>();

        for (int i = 1; i<= 9; i++){
            tilesCount.put(i,0);
            numberToTile.put(i,new ArrayList<>());
        }


        Map<Integer,Integer> newTilesCount = tilesCount;

        tiles.stream()
                .peek(z -> numberToTile.forEach( (x,c) -> {
                    if(x.equals(z.getTileNumber())){
                        List<Tile> newList = new ArrayList<Tile>();
                        newList.addAll(c);
                        newList.add(z);
                        numberToTile.replace(x,newList);


                    }}))
                .forEach(z -> tilesCount.forEach( (x,c) -> {
                            if(x.equals(z.getTileNumber())){
                                newTilesCount.replace(x,c,c+1);


                            }
                            }));

        return numberToTile;
    }
    /**
     * Finds the sequences in the given tile list. The tilelist passed in should be of only one suit.
     * @param tiles
     * @return
     */
    public List<SequenceGroup> findSequences(List<Tile> tiles){
        final List<Integer> previousTiles = tiles.stream()
                .map(Tile::getTileNumber)
                .collect(Collectors.toList());

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
                //.peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber()+1),z, new Tile(z.getTileNumber()-1))))
                .collect(Collectors.toList());

        List<Tile> risingSequenceTiles = tiles.stream()
                .filter(z -> previousTiles.contains(z.getTileNumber()+1) && previousTiles.contains(z.getTileNumber()+2))
                //.peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber()+1),z, new Tile(z.getTileNumber()+2))))
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

        Map<Integer, List<Tile>> tilemap = this.findTileCount(mrsTiles);

        List<SequenceGroup> newPossibleSeqGroups = possibleSeqGroups;
        Integer i = 0;
        possibleSeqGroups.stream().forEach(z -> {
            //Integer index = possibleSeqGroups.indexOf(z);
            try{
                z.setFirstMember(tilemap.get(z.getSecondMember().getTileNumber()-1).get(0));
                z.setThirdMember(tilemap.get(z.getSecondMember().getTileNumber()-2).get(0));
            } catch (IndexOutOfBoundsException IOOBE) {
                System.out.println("no group possible, out of tiles");
            }

        });
        possibleSeqGroups.stream().map(SequenceGroup::getSecondMember).forEach( z->
        {
            try {
                z = tilemap.get(z.getTileNumber()).get(0);
            } catch (IndexOutOfBoundsException IOOBE) {
                System.out.println("no group possible, out of tiles");
            }
        });
        possibleSeqGroups.stream().map(SequenceGroup::getThirdMember).forEach( z->{
            try{
                z = tilemap.get(z.getTileNumber()).get(0);
            } catch (IndexOutOfBoundsException IOOBE) {
                System.out.println("no group possible, out of tiles");
            }
        });

        System.out.println("Printing remaining tiles");
        newMrsTiles.stream().map(Tile::toString).forEach(System.out::println);
        System.out.println("Printing possible sequence groups");
        possibleSeqGroups.stream().map(SequenceGroup::toString).forEach(System.out::println);

        return possibleSeqGroups;
    }
    public List<SequenceGroup> findMaxValidSequences(List<SequenceGroup> possibleSequences, List<Tile> tiles){
        List<SequenceGroup> validSequences = new ArrayList<>();
        List<Tile> usedTiles = new ArrayList<>();

        System.out.println("Sequence list before sorting: ");
        possibleSequences.stream().map(z -> z.getThirdMember().getTileNumber()).forEach(System.out::println);
        Collections.sort(possibleSequences, (SequenceGroup s1, SequenceGroup s2) -> s1.getThirdMember().getTileNumber().compareTo(s2.getThirdMember().getTileNumber()));
        System.out.println("Sequence list after sorting: ");
        possibleSequences.stream().map(z -> z.getThirdMember().getTileNumber()).forEach(System.out::println);

        possibleSequences.stream().forEach(z -> {
            if(usedTiles.contains(z.getFirstMember()) ||
                    usedTiles.contains(z.getSecondMember()) ||
                    usedTiles.contains(z.getThirdMember())) {
                System.out.println("One or more tiles used already");
            } else {
                validSequences.add(z);
                usedTiles.add(z.getFirstMember());
                usedTiles.add(z.getSecondMember());
                usedTiles.add(z.getThirdMember());

                tiles.removeIf(x ->
                        z.getFirstMember().getTileNumber() == x.getTileNumber()
                                && z.getFirstMember().getTileId() == x.getTileId() );
                tiles.removeIf(x ->
                        z.getSecondMember().getTileNumber() == x.getTileNumber()
                                && z.getSecondMember().getTileId() == x.getTileId() );
                tiles.removeIf(x ->
                        z.getThirdMember().getTileNumber() == x.getTileNumber()
                                && z.getThirdMember().getTileId() == x.getTileId() );
            }


        });
        System.out.println("Leftover tiles are : ");
        tiles.stream().map(Tile::toString).forEach(System.out::println);
        return validSequences;
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

