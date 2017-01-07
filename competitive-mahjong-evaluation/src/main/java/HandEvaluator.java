import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Should assess value of final hand
 */
public class HandEvaluator {
    private Hand hand;
    private List<String> honors;
    private int[] tileNumbers = {1,2,3,4,5,6,7,8,9};

    HandEvaluator(){
        honors = new ArrayList<>();
        honors.add("Red");
        honors.add("White");
        honors.add("Green");
        honors.add("Wind");
        honors.add("Color");
        honors.add("North");
        honors.add("South");
        honors.add("West");
        honors.add("East");
    }
    public List<String> getHonors(){
        return this.honors;
    }
    /**
     * These functions filter out various tiles.
     * @param tiles
     * @return
     */
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
    /**
     * Returns all non-honor groups.
     * @return
     */
    public static List<SetGroup> filterOutHonors(List<SetGroup> setGroups){

        List<String> honors = new ArrayList<>();
        honors.add("Red");
        honors.add("White");
        honors.add("Green");
        honors.add("Wind");
        honors.add("Color");
        honors.add("North");
        honors.add("South");
        honors.add("West");
        honors.add("East");

        return setGroups.stream()
                .filter(z -> !honors.contains(z.getSuit().getIdentifier()))
                .collect(Collectors.toList());
    }

    /**
     * Filters out the largest suit. Helper function.
     * @param sequenceGroups
     * @return
     */
    public List<SequenceGroup> filterLargestSuit(List<SequenceGroup> sequenceGroups){
        Map<String, List<SequenceGroup>> stringListMap =
                sequenceGroups.stream()
                        .collect(Collectors.groupingBy(z -> z.getSuit().getIdentifier()));

        return stringListMap.get(stringListMap.keySet().stream().max((z, x) -> stringListMap.get(z).size()).get());



    }
    /**
     * Finds all sets from a given tile list input. This one takes all suits.
     * @param input
     * @return
     */
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

    /**
     * Find all sets for the given suit
     * @param input
     * @return
     */
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

    /**
     * Returns a map where integers point to tilelists. Returns one map only
     * , meaning that it only takes one suit as input. Todo: Could be made explicit for clarity.
     * @param tiles
     * @return
     */
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
     * Finds the pair in the given 14 tile set. If the tileset has less than 14 tiles
     * it should return an empty optional. There should only be one pair.
     * Todo: Where to enforce one pair restriction
     * @param tiles
     * @return
     */
    public Optional<Pair> findPair(List<Tile> tiles){
        List<Optional<Pair>> potentialPairs = new ArrayList<>();
        if(tiles.size() <= 13 || tiles.size() >=19 ){
            return Optional.empty();
        }

        List<SequenceGroup> sequenceGroups = this.findMaxValidSequences(this.findSequences(tiles),tiles);
        List<SetGroup> setGroups = this.findSets(tiles);

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

        if(potentialPairs.size() == 0){
            potentialPairs.add(Optional.empty());
        }
        List<Optional<Pair>> newOptionalPairs = new ArrayList<>();


        if(potentialPairs.size() >= 1){
            List<Tile> usedTiles = this.decomposeGroups(sequenceGroups,setGroups);
            for (Optional<Pair> pair : potentialPairs){
                if(pair.isPresent()) {
                    if (!usedTiles.contains(pair.get().getFirstMember()) && !usedTiles.contains(pair.get().getSecondMember())) {
                        newOptionalPairs.add(pair);
                    }
                }
            }
        }
        if(newOptionalPairs.size() == 0){
            newOptionalPairs.add(Optional.empty());
        }
        return newOptionalPairs.get(0);
    }

    public List<Optional<Pair>> findPairs(List<Tile> tiles){
        List<Optional<Pair>> potentialPairs = new ArrayList<>();
        if(tiles.size() <= 13 || tiles.size() >=19 ){
            potentialPairs.add(Optional.empty());
            return potentialPairs;
        }

        List<SequenceGroup> sequenceGroups = this.findMaxValidSequences(this.findSequences(tiles),tiles);
        List<SetGroup> setGroups = this.findSets(tiles);

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

        if(potentialPairs.size() == 0){
            potentialPairs.add(Optional.empty());
        }
        List<Optional<Pair>> newOptionalPairs = new ArrayList<>();


        if(potentialPairs.size() >= 1){
            List<Tile> usedTiles = this.decomposeGroups(sequenceGroups,setGroups);
            for (Optional<Pair> pair : potentialPairs){
                if(pair.isPresent()) {
                    if (!usedTiles.contains(pair.get().getFirstMember()) && !usedTiles.contains(pair.get().getSecondMember())) {
                        newOptionalPairs.add(pair);
                    }
                }
            }
        }
        if(newOptionalPairs.size() == 0){
            newOptionalPairs.add(Optional.empty());
        }
        return newOptionalPairs;
    }
    public List<Tile> decomposeSetGroups(List<SetGroup> setGroups){
        List<Tile> tiles = new ArrayList<>();
        setGroups.stream()
                .forEach( z -> {
                    tiles.add(z.getFirstMember());
                    tiles.add(z.getSecondMember());
                    tiles.add(z.getThirdMember());
                    tiles.add(z.getFourthMember());
                });

        return tiles;
    }

    public List<Tile> decomposeSequenceGroups(List<SequenceGroup> seqGroups){
        List<Tile> tiles = new ArrayList<>();
        seqGroups.stream()
                .forEach( z -> {
                    tiles.add(z.getFirstMember());
                    tiles.add(z.getSecondMember());
                    tiles.add(z.getThirdMember());
                });

        return tiles;
    }

    public List<Tile> decomposeGroups(List<SequenceGroup> sequenceGroups, List<SetGroup> setGroups){
        List<Tile> tiles = new ArrayList<>();
        sequenceGroups.stream()
                .forEach( z -> {
                    tiles.add(z.getFirstMember());
                    tiles.add(z.getSecondMember());
                    tiles.add(z.getThirdMember());
                });

        setGroups.stream()
                .forEach( z -> {
                    tiles.add(z.getFirstMember());
                    tiles.add(z.getSecondMember());
                    tiles.add(z.getThirdMember());
                    tiles.add(z.getFourthMember());
                });

        return tiles;
    }
    /**
     * Finds the pair in the given tile list of the given class/suit
     * @param tiles
     * @param aClass
     * @return
     */
    public List<Optional<Pair>> findPairInSuit(List<Tile> tiles, Class aClass){
        List<Optional<Pair>> potentialPairs = new ArrayList<>();
        


        Map<Integer, List<Tile>> integerTileMap= tiles.stream()
                .filter(z -> z.getClass() == aClass)
                .collect(Collectors.groupingBy(Tile::getTileNumber));

        integerTileMap.keySet().stream().map(integerTileMap::get).forEach( z -> {
            if(z.size() == 2){
                potentialPairs.add(Optional.ofNullable(new Pair(z.get(0),z.get(1))));
            }
        });


        return potentialPairs;



    }

    public List<SequenceGroup> fSeq(List<Tile> tiles){
        List<Tile> copy = tiles;
        List<List<SequenceGroup>> allPermutations = new ArrayList<>();
        tiles.stream().forEach(z -> {
            List<SequenceGroup> sequenceGroups= this.findSequencesFromTile(tiles,z);

            allPermutations.add(sequenceGroups);
        });
         List<SequenceGroup> allFlatPermutations = allPermutations.stream().flatMap(z -> z.stream()).collect(Collectors.toList());

        allFlatPermutations = allFlatPermutations.stream().distinct().collect(Collectors.toList());
        return allFlatPermutations;
    }

    public List<SequenceGroup> findSequencesFromTile(List<Tile> tiles, Tile tile){
        List<Tile> downs = new ArrayList<>();
        List<Tile> ups = new ArrayList<>();
        List<Tile> middles = new ArrayList<>();

        Map<Tile, Tile> downUpPair = new HashMap<>();

        tiles.stream().forEach(z -> {
            if(z.getTileNumber() == tile.getTileNumber()-1){
                downs.add(z);
            } else if(z.getTileNumber() == tile.getTileNumber()+1) {
                ups.add(z);
            } else if(z.getTileNumber() == tile.getTileNumber()) {
                middles.add(z);
            }
        });
        List<SequenceGroup> allSequenceGroups = new ArrayList<>();

        for (Tile tileD : downs){
            for(Tile tileU : ups){
                for(Tile tileM : middles){
                    allSequenceGroups.add(new SequenceGroup(tileD,tileM,tileU));
                }
            }
        }

        return allSequenceGroups;
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
        if(tiles.size() > 2) {


            Map<String, Tile> stringTileMap = new HashMap<>();

            List<SequenceGroup> possibleSeqGroups = new ArrayList<>();

            List<Tile> newTiles = tiles.stream()
                    .peek(z -> stringTileMap.put(z.getTileNumber() + "", z))
                    .filter(z -> previousTiles.contains(z.getTileNumber() + 1) && previousTiles.contains(z.getTileNumber() - 1)
                            || previousTiles.contains(z.getTileNumber() + 1) && previousTiles.contains(z.getTileNumber() + 2)
                            || previousTiles.contains(z.getTileNumber() - 1) && previousTiles.contains(z.getTileNumber() - 2))
                    .collect(Collectors.toList());


            List<Tile> middleSequenceTiles = tiles.stream()
                    .filter(z -> previousTiles.contains(z.getTileNumber() + 1) && previousTiles.contains(z.getTileNumber() - 1))
                    //.peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber()+1),z, new Tile(z.getTileNumber()-1))))
                    .collect(Collectors.toList());

            List<Tile> risingSequenceTiles = tiles.stream()
                    .filter(z -> previousTiles.contains(z.getTileNumber() + 1) && previousTiles.contains(z.getTileNumber() + 2))
                    //.peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber()+1),z, new Tile(z.getTileNumber()+2))))
                    .collect(Collectors.toList());

            List<Tile> sinkingSequenceTiles = tiles.stream()
                    .filter(z -> previousTiles.contains(z.getTileNumber() - 1) && previousTiles.contains(z.getTileNumber() - 2))
                    .peek(z -> possibleSeqGroups.add(new SequenceGroup(new Tile(z.getTileNumber() - 1, 4, z.getSuit()), z, new Tile(z.getTileNumber() - 2, 4, z.getSuit()))))
                    .collect(Collectors.toList());

            List<Tile> mrsTiles = new ArrayList<>();
            mrsTiles.addAll(middleSequenceTiles);
            mrsTiles.addAll(sinkingSequenceTiles);
            mrsTiles.addAll(risingSequenceTiles);

            Map<Integer, List<Tile>> tilemap = this.findTileCount(mrsTiles);

            List<SequenceGroup> newPossibleSeqGroups = possibleSeqGroups;
            Integer i = 0;
            possibleSeqGroups.stream().forEach(z -> {
                //Integer index = possibleSeqGroups.indexOf(z);
                try {
                    z.setFirstMember(tilemap.get(z.getSecondMember().getTileNumber() - 1).get(0));
                    z.setThirdMember(tilemap.get(z.getSecondMember().getTileNumber() - 2).get(0));
                } catch (IndexOutOfBoundsException IOOBE) {
                }

            });
            possibleSeqGroups.stream().map(SequenceGroup::getSecondMember).forEach(z ->
            {
                try {
                    z = tilemap.get(z.getTileNumber()).get(0);
                } catch (IndexOutOfBoundsException IOOBE) {
                }
            });
            possibleSeqGroups.stream().map(SequenceGroup::getThirdMember).forEach(z -> {
                try {
                    z = tilemap.get(z.getTileNumber()).get(0);
                } catch (IndexOutOfBoundsException IOOBE) {
                }
            });


            //groups.stream().forEach(z -> {
            //    possibleSeqGroups.add(new SequenceGroup(z.getFirstMember(),z.getSecondMember(),z.getThirdMember()));
            //});
            return possibleSeqGroups;
        } else {
             return new ArrayList<>();
            }
    }

    /**
     * While the other function finds all *possible* sequences, this one restricts it to a possible number.
     * It goes from bottom up, which is probably optimal and should give no errors. (for ready hands)
     * @param possibleSequences
     * @param tiles
     * @return
     */
    public List<SequenceGroup> findMaxValidSequences(List<SequenceGroup> possibleSequences, List<Tile> tiles){
        List<Tile> wanTiles = filterWan(tiles);
        List<Tile> pinTiles = filterPin(tiles);
        List<Tile> souTiles = filterSou(tiles);

        List<SequenceGroup> wanSequences = findSequences(wanTiles);
        List<SequenceGroup> pinSequences = findSequences(pinTiles);
        List<SequenceGroup> souSequences = findSequences(souTiles);

        List<SequenceGroup> newList = new ArrayList<>();
        if(wanSequences.size() != 0){
            newList.addAll(findMaxValidSequencesOfSuit(wanSequences,wanTiles));
        }
        if(pinSequences.size() != 0){
            newList.addAll(findMaxValidSequencesOfSuit(pinSequences,pinTiles));
        }
        if(souSequences.size() != 0){
            newList.addAll(findMaxValidSequencesOfSuit(souSequences,souTiles));
        }

        return newList;
    }

    /**
     * Helper function for above function, returns max valid sequences from one suit
     * @param possibleSequences
     * @param tiles
     * @return
     */
    public List<SequenceGroup> findMaxValidSequencesOfSuit(List<SequenceGroup> possibleSequences, List<Tile> tiles){
        List<SequenceGroup> validSequences = new ArrayList<>();
        List<Tile> usedTiles = new ArrayList<>();

        Collections.sort(possibleSequences, (SequenceGroup s1, SequenceGroup s2) -> s1.getThirdMember().getTileNumber().compareTo(s2.getThirdMember().getTileNumber()));

        possibleSequences.stream().forEach(z -> {
            if(usedTiles.contains(z.getFirstMember()) ||
                    usedTiles.contains(z.getSecondMember()) ||
                    usedTiles.contains(z.getThirdMember())) {
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

        List<SequenceGroup> additionalGroups = findSequences(tiles);
        List<SequenceGroup> additionalValidSequences = new ArrayList<>();

        if( additionalGroups.size() != 0){
            additionalValidSequences = findMaxValidSequences(additionalGroups,tiles);
        }
        validSequences.addAll(additionalValidSequences);
        return validSequences;
    }

    /**
     * Removing duplicate suitsets and sequences, respectively.
     * @param setGroups
     * @return
     */
    public List<SetGroup> removeDuplicateSuitSets(List<SetGroup> setGroups){
        return setGroups.stream().filter(distinctByKey(o -> o.getSuit().getIdentifier())).collect(Collectors.toList());
    }
    public List<SequenceGroup> removeDuplicateSuitSequences(List<SequenceGroup> sequenceGroups){
        return sequenceGroups.stream().filter(distinctByKey(o -> o.getSuit().getIdentifier())).collect(Collectors.toList());
    }

    /**
     * Helper function for removing duplicate suitsets in some specific circumstances
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Counts the amount of groups of a given class
     * @param aClass
     * @return
     */
    public Integer groupAmountCounterSets(List<SetGroup> setGroups, Class aClass){
        List<SetGroup> newSetGroups = setGroups.stream().filter(z -> z.getSecondMember().getClass().toString() != aClass.toString()).collect(Collectors.toList());
        return newSetGroups.size();
    }

    public Integer groupAmountCounterSequences(List<SequenceGroup> sequenceGroups, Class aClass){
        List<SequenceGroup> newSetGroups = sequenceGroups.stream().filter(z -> z.getSecondMember().getClass().toString() != aClass.toString()).collect(Collectors.toList());
        return newSetGroups.size();
    }

    /**
     * Amount of sets of colors. Distinctions do not matter for them.
     * @return
     */
    public Integer findColorSetAmount(List<SetGroup> setGroups){
        Integer colorCount = 0;
        if(hasHonorSet(setGroups,new Suit("Green"))){
            colorCount += 1;
        }
        if(hasHonorSet(setGroups,new Suit("White"))){
            colorCount += 1;
        }
        if(hasHonorSet(setGroups,new Suit("Red"))){
            colorCount += 1;
        }
        return colorCount;
    }
    public boolean hasSouthWind(List<SetGroup> groups){
        return hasHonorSet(groups,new Suit("South"));
    }
    public boolean hasNorthWind(List<SetGroup> groups){
        return hasHonorSet(groups,new Suit("North"));
    }
    public boolean hasWestWind(List<SetGroup> groups){
        return hasHonorSet(groups,new Suit("West"));
    }
    public boolean hasEastWind(List<SetGroup> groups){
        return hasHonorSet(groups,new Suit("East"));
    }

    /**
     * Finds amount of winds. They have their own functions because their value is different depending on
     * the given table wind and player wind.
     * @param groups
     * @return
     */
    public Integer findWindSetAmount(List<SetGroup> groups){
        Integer windCount = 0;
        if(hasSouthWind(groups)){
            windCount+=1;
        }
        if(hasEastWind(groups)){
            windCount+=1;
        }
        if(hasNorthWind(groups)){
            windCount+=1;
        }
        if(hasWestWind(groups)){
            windCount+=1;
        }
        return windCount;
    }

    /**
     * Finds a set of given honor suit. Used by the color finding function
     * @param groups
     * @param suit
     * @return
     */
    public boolean hasHonorSet(List<SetGroup> groups, Suit suit){
        return groups.stream()
                .filter(z -> z.getFirstMember().getSuit().getIdentifier().equals(suit.getIdentifier()))
                .count() == 1;
    }



    /**
     * Helper function. checks if the pair is a terminal or an honor tile. Only checks one of the tiles because
     * a pair must have two equal(except for id of course) tiles in it.
     * @param pair
     * @return
     */
    public boolean pairIsTerminalOrHonor(Pair pair){
        return honors.contains(pair.getFirstMember().getSuit().getIdentifier())
                || pair.getFirstMember().getTileNumber() == 1
                || pair.getFirstMember().getTileNumber() == 9;

    }
    /**
     * Helper function for chanta. checks that all given groups have terminal entries within them.
     * @param groups
     * @return
     */
    public boolean allSetGroupsHaveATerminal(List<SetGroup> groups){
        return groups.stream().allMatch(z -> z.getFirstMember().getClass() == ColorTile.class
                || z.getThirdMember().getTileNumber() == 1
                || z.getThirdMember().getTileNumber() == 9
                || z.getFirstMember().getClass() == WindTile.class) ;
    }

    public boolean allSequenceGroupsHaveATerminal(List<SequenceGroup> groups){
        return groups.stream().allMatch(z -> z.getFirstMember().getTileNumber() == 9
                || z.getThirdMember().getTileNumber() == 1);
    }


    /**
     * Checks if the pair is a dragon pair
     * @param pair
     * @return
     */
    public boolean pairIsDragonPair(Pair pair){
        List<String> dragons = new ArrayList<>();
        dragons.add("Red");
        dragons.add("White");
        dragons.add("Green");

        return dragons.contains(pair.getFirstMember().getSuit().getIdentifier());
    }

    /**
     * Checks if pair is of the given suit.
     */
    public boolean pairIsGivenSuit(Pair pair, Suit suit){
        return pair.getFirstMember().getSuit().getIdentifier().equals(suit.getIdentifier());
    }

    public boolean pairIsGivenClass(Pair pair, Class aClass){
        return pair.getFirstMember().getClass() == aClass;
    }

    public boolean pairIsHonorPair(Pair pair){
        return honors.contains(pair.getFirstMember().getSuit().getIdentifier());
    }
    }

