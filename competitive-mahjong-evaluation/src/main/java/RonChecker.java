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
    RonChecker(Gamerules gamerules, Hand hand){
        this.gamerules = gamerules;
        this.hanEvaluator = new ValuationHan();
        this.handEvaluator = new HandEvaluator();
    }

    public boolean handIsValid(List<Tile> tiles){
        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);
        Optional<Pair> pair = handEvaluator.findPair(tiles);
        return setGroups.size() + sequenceGroups.size() == 4 && pair.isPresent();

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
