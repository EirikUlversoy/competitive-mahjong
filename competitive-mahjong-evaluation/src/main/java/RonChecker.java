import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Supposed to check if the given hand is in a state ready to go out.
 * In a way it does the same as ReadyChecker but for one more tile so that class,
 * this one, and RonProximityChecker can probably be combined.
 */
public class RonChecker {
    private Gamerules gamerules;
    private ValuationHan hanEvaluator;
    private HandEvaluator handEvaluator;
    RonChecker(Gamerules gamerules){
        this.gamerules = gamerules;
        this.hanEvaluator = new ValuationHan();
        this.handEvaluator = new HandEvaluator();
    }

    public boolean handIsValid(List<Tile> tiles){
        Boolean setPrioValid = false;
        Boolean seqPrioValid = false;
        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        List<Tile> seqTilesUsed = handEvaluator.decomposeSequenceGroups(sequenceGroups);
        List<Tile> newTiles = tiles.stream().filter(z -> !seqTilesUsed.contains(z)).collect(Collectors.toList());

        List<SetGroup> setGroups = handEvaluator.findSets(newTiles);
        Optional<Pair> pair = handEvaluator.findPair(newTiles);


        System.out.println("Sequence priority gave: ");

        if(setGroups.size()+sequenceGroups.size() == 4 && pair.isPresent()){
            System.out.println("A valid hand!");
            seqPrioValid = true;
        } else {
            System.out.println("An invalid hand!");
        }

        List<SetGroup> setGroupList = handEvaluator.findSets(tiles);
        List<Tile> setTilesUsed = handEvaluator.decomposeSetGroups(setGroupList);
        newTiles = tiles.stream().filter(z -> !setTilesUsed.contains(z)).collect(Collectors.toList());


        List<SequenceGroup> sequenceGroups1 = handEvaluator.findSequences(newTiles);
        System.out.println("Set priority gave: ");
        List<Tile> newTilesUsed = handEvaluator.decomposeSequenceGroups(sequenceGroups1);
        newTiles = tiles.stream().filter(z -> !newTilesUsed.contains(z)).collect(Collectors.toList());

        pair = handEvaluator.findPair(newTiles);
        if(setGroupList.size()+sequenceGroups1.size() == 4 && pair.isPresent()){
            System.out.println("A valid hand!");
            setPrioValid = true;
        } else {
            System.out.println("An invalid hand!");
        }


        return setPrioValid || seqPrioValid;

    }

    public boolean handMeetsMinimumValue(List<Tile> tiles, Pair pair, Integer minimumHandValue, boolean singleWait, boolean nineWait, boolean closed ){
        HandIdentifier handIdentifier = new HandIdentifier();
        //Hand hand = new Hand(4);
        //hand.setTiles(tiles);
        List<String> matchingHands = handIdentifier.identifyMatchingHands(tiles,singleWait,closed,nineWait);

        return !matchingHands.isEmpty();

    }
    public boolean hasSevenPairs(List<Tile> tiles){
        Map<Integer, List<Tile>> integerListMap= handEvaluator.findTileCount(tiles);

        List<Integer> integers =  integerListMap.keySet().stream()
                .filter(z -> integerListMap.get(z).size() == 2)
                .collect(Collectors.toList());

        return integers.size() == 7;
    }



}
