import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ValuationHanTest {
    private HandEvaluator handEvaluator;
    private TileSet tileSet;
    private Hand hand;
    private ValuationHan valuationHan;
    private List<SequenceGroup> straightSequences;
    private List<Group> straightCombinations = new ArrayList<>();
    private List<Tile> straight = new ArrayList<>();
    private TilesFromFile tilesFromFile;

    @BeforeTest
    public void setup(){
        tilesFromFile = new TilesFromFile();
        List<Tile> straight = new ArrayList<>();
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1,120);
        hand.initializeHand(tileSet);
        valuationHan = new ValuationHan();
        this.handEvaluator = new HandEvaluator(hand);
        straight = tilesFromFile.analyzeString("S123456778899C2C2");
        straight.stream().map(Tile::toString).forEach(System.out::println);
        this.straight = straight;

    }


    @Test
    public void checkFlushFunction(){
        straightSequences = handEvaluator.findSequences(straight);
        System.out.println("printing sequences???");
        straightSequences.stream().map(SequenceGroup::toString).forEach(System.out::println);

        straightSequences = handEvaluator.findMaxValidSequences(straightSequences,straight);
        straightCombinations.addAll(this.straightSequences);
        Assert.assertEquals(valuationHan.checkFlush(this.straightCombinations),"Flush");

    }

    @Test
    public void testGroupAmountCounterReturnsCorrectAnswer(){
        straightSequences = handEvaluator.findSequences(straight);
        straightSequences = handEvaluator.findMaxValidSequences(straightSequences,straight);
        Assert.assertEquals(valuationHan.groupAmountCounter(straightCombinations,SouTile.class).intValue(),4);
    }

    @Test
    public void testHasThreeQuads() {
        TilesFromFile tilesFromFile = new TilesFromFile();
        tilesFromFile.analyzeString("S123123");
        List<Tile> threeQuadsHand = new ArrayList<>();
        threeQuadsHand = tilesFromFile.analyzeString("S1111W2222P3333");
        List<Tile> noQuadsHand = tilesFromFile.analyzeString("S123456789");
        List<Tile> twoQuadsHand = tilesFromFile.analyzeString("S1111W1111C2C2C2");
        List<SetGroup> threeQuadsGroup = handEvaluator.findSets(threeQuadsHand);
        List<SetGroup> noQuadsGroup = handEvaluator.findSets(noQuadsHand);
        List<SetGroup> twoQuadsGroup = handEvaluator.findSets(twoQuadsHand);

        Assert.assertTrue(valuationHan.hasThreeQuads(threeQuadsGroup));
        Assert.assertFalse(valuationHan.hasThreeQuads(noQuadsGroup));
        Assert.assertFalse(valuationHan.hasThreeQuads(twoQuadsGroup));

    }

    @Test
    public void testAllTriplets(){
        List<Tile> triplets = tilesFromFile.analyzeString("S111222333444");
        List<SetGroup> tripletSets = handEvaluator.findSets(triplets);

        Assert.assertTrue(valuationHan.isAllTriplets(tripletSets));
    }


    @Test
    public void testFilterOutHonors(){
        List<Tile> listWithHonors = tilesFromFile.analyzeString("S123456C111C222");
        List<Tile> listWithHonorsForSets = tilesFromFile.analyzeString("S123456C111C222");

        List<SequenceGroup> honorGroup = handEvaluator.findSequences(listWithHonors);
        honorGroup = handEvaluator.findMaxValidSequences(honorGroup,listWithHonors);
        List<SetGroup> honorGroupSets = handEvaluator.findSets(listWithHonorsForSets);

        List<Group> groups = new ArrayList<>();
        groups.addAll(honorGroup);
        groups.addAll(honorGroupSets);

        List<Group> filteredGroups = ValuationHan.filterOutHonors(groups);
        Assert.assertEquals(filteredGroups.size(),2);
    }
}
