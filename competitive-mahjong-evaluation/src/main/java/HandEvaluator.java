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

    public List<Tile> filterSou(List<Tile> tiles){
        List<Tile> souTiles = tiles.stream()
                .filter(z -> z.getClass() == SouTile.class)
                .collect(Collectors.toList());
        return souTiles;
    }

    public List<Tile> filterPin(List<Tile> tiles){
        List<Tile> pinTiles = tiles.stream()
                .filter(z -> z.getClass() == PinTile.class)
                .collect(Collectors.toList());
        return pinTiles;
    }

    public List<Tile> filterWan(List<Tile> tiles){
        List<Tile> wanTiles = tiles.stream()
                .filter(z -> z.getClass() == WanTile.class)
                .collect(Collectors.toList());
        return wanTiles;
    }

    public List<Tile> filterSuit(Class aClass, List<Tile> tiles){
        List<Tile> newTiles = tiles.stream()
                .filter(z -> z.getClass() == aClass)
                .collect(Collectors.toList());
        return newTiles;
    }

    public List<SetGroup> findSets( List<Tile> input){
        List<Tile> souTiles = filterSou(input);
        List<Tile> wanTiles = filterWan(input);
        List<Tile> pinTiles = filterPin(input);
        List<Tile> colorTiles = filterSuit(ColorTile.class,input);
        List<Tile> windTiles = filterSuit(WindTile.class,input);

        List<SetGroup> setGroups = findSetsInSuit(souTiles);
        setGroups.addAll(findSetsInSuit(wanTiles));
        setGroups.addAll(findSetsInSuit(pinTiles));
        setGroups.addAll(findSetsInSuit(colorTiles));
        setGroups.addAll(findSetsInSuit(windTiles));

        return setGroups;
    }
    public List<SetGroup> findSetsInSuit( List<Tile> input){
        Map<Integer, Integer> tilenumberToAmount = new HashMap();
        List<SetGroup> sets = new ArrayList<>();
        for (Tile tile : input){
            if(tilenumberToAmount.containsKey(tile.getTileNumber())){
                tilenumberToAmount.replace(tile.getTileNumber(),tilenumberToAmount.get(tile.getTileNumber())+1);
                if(tilenumberToAmount.get(tile.getTileNumber()) == 3){

                    SetGroup newSet = new SetGroup(input.stream().filter(z -> z.getTileNumber() == tile.getTileNumber()
                            && z.getSuit().getIdentifier() == tile.getSuit().getIdentifier())
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
    public Optional<Pair> findPair(List<Tile> tiles){
        List<Optional<Pair>> potentialPairs = new ArrayList<>();

        List<Tile> wanTiles = filterWan(tiles);
        List<Tile> souTiles = filterSou(tiles);
        List<Tile> pinTiles = filterPin(tiles);
        List<Tile> colorTiles = filterSuit(ColorTile.class, tiles);
        List<Tile> windTiles = filterSuit(WindTile.class, tiles);

        potentialPairs = findPairInSuit(wanTiles, WanTile.class);
        potentialPairs.addAll(findPairInSuit(souTiles,SouTile.class));
        potentialPairs.addAll(findPairInSuit(pinTiles,PinTile.class));
        potentialPairs.addAll(findPairInSuit(colorTiles,ColorTile.class));
        potentialPairs.addAll(findPairInSuit(windTiles,WindTile.class));

        potentialPairs.stream().map(z -> z.get()).map(Pair::toString).forEach(System.out::println);


    }
    public List<Optional<Pair>> findPairInSuit(List<Tile> tiles, Class aClass){
        List<Optional<Pair>> potentialPairs = new ArrayList<>();
        if(tiles.size() != 14){
            potentialPairs.add(Optional.empty());
            return potentialPairs;
        }

        Map<Integer, List<Tile>> integerTileMap= tiles.stream()
                .filter(z -> z.getClass() == aClass)
                .collect(Collectors.groupingBy(Tile::getTileNumber));

        integerTileMap.keySet().stream().map(integerTileMap::get).peek( z -> {
            if(z.size() == 2){
                potentialPairs.add(Optional.ofNullable(new Pair(z.get(0),z.get(1))));
            }
        });
        if(potentialPairs.size() == 0){
            potentialPairs.add(Optional.empty());
        }
        return potentialPairs;



    }
    /**
     * Finds the sequences in the given tile list. The tilelist passed in should be of only one suit.
     * @param tiles
     * @return
     */
    public List<SequenceGroup> findSequences(List<Tile> tiles){
        System.out.println("am I here?");
        tiles.stream().map(Tile::toString).forEach(System.out::println);
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

        List<Group> groups = new ArrayList<>();
        groups.addAll(possibleSeqGroups);

        groups = ValuationHan.filterOutHonors(groups);
        possibleSeqGroups.clear();

        groups.stream().forEach(z -> {
            possibleSeqGroups.add(new SequenceGroup(z.getFirstMember(),z.getSecondMember(),z.getThirdMember()));
        });
        return possibleSeqGroups;
    }

    public List<SequenceGroup> findMaxValidSequences(List<SequenceGroup> possibleSequences, List<Tile> tiles){
        List<Tile> wanTiles = filterWan(tiles);
        List<Tile> pinTiles = filterPin(tiles);
        List<Tile> souTiles = filterSou(tiles);

        List<SequenceGroup> wanSequences = findSequences(wanTiles);
        List<SequenceGroup> pinSequences = findSequences(pinTiles);
        List<SequenceGroup> souSequences = findSequences(souTiles);

        List<SequenceGroup> newList = findMaxValidSequencesOfSuit(wanSequences,wanTiles);
        newList.addAll(findMaxValidSequencesOfSuit(pinSequences,pinTiles));
        newList.addAll(findMaxValidSequencesOfSuit(souSequences,souTiles));

        return newList;
    }
    public List<SequenceGroup> findMaxValidSequencesOfSuit(List<SequenceGroup> possibleSequences, List<Tile> tiles){
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

        List<SequenceGroup> additionalGroups = findSequences(tiles);
        List<SequenceGroup> additionalValidSequences = new ArrayList<>();

        if( additionalGroups.size() != 0){
            additionalValidSequences = findMaxValidSequences(additionalGroups,tiles);
        }
        validSequences.addAll(additionalValidSequences);
        return validSequences;
    }


    }

