import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Should check for yakuman hands
 */
public class YakumanChecker {
    private ValuationHan valuationHan;
    private HandEvaluator handEvaluator;
    private TilesFromFile tilesFromFile;
    public YakumanChecker(Hand hand){
        this.valuationHan = new ValuationHan();
        this.handEvaluator = new HandEvaluator(hand);
    }
    public boolean thirtheenOrphans(List<Tile> tiles){
        Pair pair = handEvaluator.findPair(tiles).get();

        List<Tile> thirtheenOrphansExample = tilesFromFile.analyzeString("V1234C1234S19W19P19");

        boolean thirtheenOrphans = thirtheenOrphansExample.stream()
                .map(z -> z.getTileNumber() + z.getSuit().getIdentifier())
                .allMatch(z -> tiles.stream().map(x -> x.getTileNumber() + x.getSuit().getIdentifier())
        .collect(Collectors.toList()).contains(z));


        return thirtheenOrphans && valuationHan.pairIsTerminalOrHonor(pair);

    }




}
