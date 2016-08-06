import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Supposed to check if the given hand is in a state ready to go out.
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

    public boolean basicValidityCheck(List<Tile> tiles){
        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);
        List<Group> groups = new ArrayList<>();
        groups.addAll(sequenceGroups);
        groups.addAll(setGroups);
        Optional<Pair> pair = handEvaluator.findPair(tiles);
        return groups.size() == 4 && pair.isPresent();

    }

    public boolean sevenPairsCheck(List<Tile> tiles){
        Map<Integer, List<Tile>> integerListMap= handEvaluator.findTileCount(tiles);

        List<Integer> integers =  integerListMap.keySet().stream()
                .filter(z -> integerListMap.get(z).size() == 2)
                .collect(Collectors.toList());

        return integers.size() == 7;
    }

}
