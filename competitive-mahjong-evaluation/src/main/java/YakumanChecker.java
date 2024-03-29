import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Should check for yakuman hands
 */
public class YakumanChecker {
    private ValuationHan valuationHan;
    private HandEvaluator handEvaluator;
    private TilesFromFile tilesFromFile;
    private HandIdentifier handIdentifier;

    public YakumanChecker(HandIdentifier handIdentifier){
        this.valuationHan = new ValuationHan();
        this.handEvaluator = new HandEvaluator();
        this.tilesFromFile = new TilesFromFile();
        this.handIdentifier = handIdentifier;
    }

    public List<String> findYakumanIfAny(List<Tile> tiles, boolean singleWait, boolean closed, boolean nineWait){
        List<String> yakumanMatches = new ArrayList<>();
        if(thirtheenOrphans(tiles)){
            yakumanMatches.add("Thirtheen Orphans");
        }

        if(fourKans(tiles)){
            yakumanMatches.add("Four Kans");

        }

        if(fourConcealedTripletsSingleWait(tiles,singleWait,closed)){
            yakumanMatches.add("Four Concealed Triplets With Single Wait");

        }

        if(fourConcealedTriplets(tiles,closed)){
            yakumanMatches.add("Four Concealed Triplets");

        }

        if(threeBigDragons(tiles)){
            yakumanMatches.add("Three Big Dragons");

        }

        if(bigFourWinds(tiles)){
            yakumanMatches.add("Big Four Winds");
        }

        if(littleFourWinds(tiles)){
            yakumanMatches.add("Little Four Winds");
        }

        if(doubleNineGates(tiles,nineWait)){
            yakumanMatches.add("Double Nine Gates");
        }

        if(nineGates(tiles)){
            yakumanMatches.add("Nine Gates");
        }

        if(allHonors(tiles)){
            yakumanMatches.add("All Honors");
        }

        if(allTerminals(tiles)){
            yakumanMatches.add("All Terminals");
        }

        if(allGreen(tiles)){
            yakumanMatches.add("All Green");
        }
        yakumanMatches.add("Not A Yakuman");
        return yakumanMatches;
    }

    public boolean thirtheenOrphans(List<Tile> tiles){

        Pair pair = handEvaluator.findPair(tiles).get();

        List<Tile> thirtheenOrphansExample = tilesFromFile.analyzeString("V1234C123S19W19P19");
        boolean didContain = false;
        didContain = thirtheenOrphansExample.stream().distinct().allMatch(z -> tiles.contains(z));
        //didContain = tiles.stream().distinct().allMatch(z -> thirtheenOrphansExample.contains(z));
        System.out.println(tiles);
        System.out.println(thirtheenOrphansExample);
        boolean onlyTwoOfOne = tiles.stream()
                .map(z -> z.getTileNumber() + z.getSuit().getIdentifier())
                .distinct()
                .count() == 13;

        System.out.println(didContain);
        System.out.println(onlyTwoOfOne);
        System.out.println(handEvaluator.pairIsTerminalOrHonor(pair));
        return didContain && onlyTwoOfOne && handEvaluator.pairIsTerminalOrHonor(pair);

    }

    public boolean fourConcealedTriplets(List<Tile> tiles, boolean closed){
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);
        Optional<Pair> pair = handEvaluator.findPair(tiles);
        return setGroups.size() == 4 && pair.isPresent() && closed;
    }

    public boolean fourConcealedTripletsSingleWait(List<Tile> tiles, boolean singleWait, boolean closed){
        return fourConcealedTriplets(tiles,closed) && singleWait;
    }

    public boolean threeBigDragons(List<Tile> tiles){
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);
        return handEvaluator.findColorSetAmount(setGroups) == 3;
    }

    public boolean littleFourWinds(List<Tile> tiles){
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);
        Pair pair = handEvaluator.findPair(tiles).get();
        return handEvaluator.findWindSetAmount(setGroups) == 3 && handEvaluator.pairIsGivenClass(pair,WindTile.class);
    }

    public boolean bigFourWinds(List<Tile> tiles){
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);
        return handEvaluator.findWindSetAmount(setGroups) == 4;
    }

    public boolean allHonors(List<Tile> tiles){
        List<Tile> honorTiles = handEvaluator.filterSuit(WindTile.class,tiles);
        List<Tile> colorTiles = handEvaluator.filterSuit(ColorTile.class,tiles);
        honorTiles.addAll(colorTiles);
        return tiles.size() == honorTiles.size();
    }

    public boolean allTerminals(List<Tile> tiles){
        return tiles.stream().allMatch(z -> z.getTileNumber() == 1 || z.getTileNumber() == 9);
    }

    public boolean allGreen(List<Tile> tiles){

        List<String> greenTileIDs = tilesFromFile.analyzeString("S23468C1").stream()
                .map(z -> z.getTileNumber() + z.getSuit().getIdentifier())
                .collect(Collectors.toList());

        return tiles.stream()
                .allMatch(z -> greenTileIDs.contains(z.getTileNumber() + z.getSuit().getIdentifier()));
    }

    public boolean doubleNineGates(List<Tile> tiles, boolean nineWait){
        return nineGates(tiles) && nineWait;
    }

    public boolean nineGates(List<Tile> tiles){
        List<Tile> nineGatesExample = tilesFromFile.analyzeString("S1112345678999");
        Map<Integer, List<Tile>> nineGatesExampleMap = handEvaluator.findTileCount(nineGatesExample);
        Map<Integer, List<Tile>> tilesCount = handEvaluator.findTileCount(tiles);
        boolean hasAllRequiredTiles = nineGatesExampleMap.keySet().stream()
                .allMatch(z -> {
                    return tilesCount.get(z).size() >= nineGatesExampleMap.get(z).size();
                });
        boolean thereIsAPair = tilesCount.keySet().stream()
                .map(z -> tilesCount.get(z))
                .anyMatch(z -> z.size() == 2);

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);

        Optional<Pair> pair = handEvaluator.findPair(tiles);
        boolean isFlush = false;
        isFlush =  handIdentifier.hasFullFlush(setGroups,sequenceGroups,pair.get());

        return hasAllRequiredTiles && isFlush && thereIsAPair;
    }

    public boolean fourKans(List<Tile> tiles) {
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);
        boolean allSetsAreQuads = setGroups.stream().allMatch(z -> z.isKAN());
        boolean fourSets = setGroups.size() == 4;

        return allSetsAreQuads && fourSets;
    }
}
