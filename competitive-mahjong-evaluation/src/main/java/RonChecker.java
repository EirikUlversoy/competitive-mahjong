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
    public boolean handIsValidWithSetPriority(List<Tile> tiles ){
        List<SetGroup> setGroupList = handEvaluator.findSets(tiles);
        List<Tile> setTilesUsed = handEvaluator.decomposeSetGroups(setGroupList);
        List<Tile> remainingTiles = tiles.stream().filter(z -> !setTilesUsed.contains(z)).collect(Collectors.toList());


        List<Tile> wanTiles = handEvaluator.filterWan(remainingTiles);
        List<Tile> souTiles = handEvaluator.filterPin(remainingTiles);
        List<Tile> pinTiles = handEvaluator.filterSou(remainingTiles);

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(wanTiles);
        sequenceGroups.addAll(handEvaluator.findSequences(souTiles));
        sequenceGroups.addAll(handEvaluator.findSequences(pinTiles));
        System.out.println("Set priority gave: ");
        List<Tile> newTilesUsed = handEvaluator.decomposeSequenceGroups(sequenceGroups);
        remainingTiles = tiles.stream().filter(z -> !newTilesUsed.contains(z)).collect(Collectors.toList());

        Optional<Pair> pair = handEvaluator.findPair(remainingTiles);
        if(setGroupList.size()+sequenceGroups.size() == 4 && pair.isPresent()){
            System.out.println("A valid hand!");
            return true;
        } else {
            System.out.println("An invalid hand!");
            return false;
        }
    }

    public boolean handIsValidWithSeqPriority(List<Tile> tiles){
        List<Tile> wanTiles = handEvaluator.filterWan(tiles);
        List<Tile> souTiles = handEvaluator.filterPin(tiles);
        List<Tile> pinTiles = handEvaluator.filterSou(tiles);

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(wanTiles);
        sequenceGroups.addAll(handEvaluator.findSequences(souTiles));
        sequenceGroups.addAll(handEvaluator.findSequences(pinTiles));

        List<Tile> seqTilesUsed = handEvaluator.decomposeSequenceGroups(sequenceGroups);
        List<Tile> remainingTiles = tiles.stream().filter(z -> !seqTilesUsed.contains(z)).collect(Collectors.toList());

        List<SetGroup> setGroups = handEvaluator.findSets(remainingTiles);
        List<Tile> setTilesUsed = handEvaluator.decomposeSetGroups(setGroups);
        remainingTiles = tiles.stream().filter(z -> !setTilesUsed.contains(z)).collect(Collectors.toList());

        Optional<Pair> pair = handEvaluator.findPair(remainingTiles);


        System.out.println("Sequence priority gave: ");

        if(setGroups.size()+sequenceGroups.size() == 4 && pair.isPresent()){
            System.out.println("A valid hand!");
            return true;
        } else {
            System.out.println("An invalid hand!");
            return false;
        }
    }
    public boolean handIsValid(List<Tile> tiles){
        return handIsValidWithSeqPriority(tiles) || handIsValidWithSetPriority(tiles);
    }

    public boolean handMeetsMinimumValue(List<Tile> tiles, boolean closed){
        HandIdentifier handIdentifier = new HandIdentifier();
        //Hand hand = new Hand(4);
        //hand.setTiles(tiles);
        if(hasSevenPairs(tiles)){
            return true;
        }
        List<String> matchingHands = handIdentifier.identifyMatchingHands(tiles,true,closed,false);
        System.out.println(matchingHands);
        System.out.println("...");
        return matchingHands.size() > 1;

    }
    public boolean hasSevenPairs(List<Tile> tiles){
        Map<Integer, List<Tile>> integerListMap= handEvaluator.findTileCount(tiles);
        List<Optional<Pair>> listOfPairs = handEvaluator.findPairs(tiles);
        return listOfPairs.size() == 7;
    }



}
