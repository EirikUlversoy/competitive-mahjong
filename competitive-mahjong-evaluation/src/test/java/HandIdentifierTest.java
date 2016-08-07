import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class HandIdentifierTest {
    private HandEvaluator handEvaluator;
    private TileSet tileSet;
    private Hand hand;
    private ValuationHan valuationHan;
    private List<SequenceGroup> straightSequences;
    private List<Group> straightCombinations = new ArrayList<>();
    private List<Tile> straight = new ArrayList<>();
    private TilesFromFile tilesFromFile;
    private HandIdentifier handIdentifier;

    @BeforeTest
    public void setup(){
        tilesFromFile = new TilesFromFile();
        List<Tile> straight = new ArrayList<>();
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1,120);
        hand.initializeHand(tileSet);
        valuationHan = new ValuationHan();
        this.handEvaluator = new HandEvaluator();
        straight = tilesFromFile.analyzeString("S123456778899C2C2");
        this.straight = straight;
        handIdentifier = new HandIdentifier();

    }



    @Test
    public void checkFlushFunction(){
        straightSequences = handEvaluator.findSequences(straight);
        List<SetGroup> setGroups = handEvaluator.findSets(straight);
        straightSequences = handEvaluator.findMaxValidSequences(straightSequences,straight);
        straightCombinations.addAll(this.straightSequences);
        straightCombinations.addAll(setGroups);
        Pair pair = handEvaluator.findPair(straight).get();
        Assert.assertEquals(handIdentifier.checkFullFlush(this.straightCombinations,pair),true);

        List<Tile> tiles = tilesFromFile.analyzeString("S123456789123W456P111");
        List<SequenceGroup> wrongSequences = handEvaluator.findSequences(tiles);
        wrongSequences = handEvaluator.findMaxValidSequences(wrongSequences,tiles);
        List<SetGroup> wrongSetGroups = handEvaluator.findSets(tiles);
        List<Group> groups = new ArrayList<>();
        groups.addAll(wrongSequences);
        groups.addAll(wrongSetGroups);

        Assert.assertEquals(handIdentifier.checkFullFlush(groups,pair),false);


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

        Assert.assertTrue(handIdentifier.hasThreeQuads(threeQuadsGroup));
        Assert.assertFalse(handIdentifier.hasThreeQuads(noQuadsGroup));
        Assert.assertFalse(handIdentifier.hasThreeQuads(twoQuadsGroup));

    }

    @Test
    public void testAllTriplets(){
        List<Tile> triplets = tilesFromFile.analyzeString("S111222333444");
        List<SetGroup> tripletSets = handEvaluator.findSets(triplets);

        Assert.assertTrue(handIdentifier.isAllTriplets(tripletSets));
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

        List<Group> filteredGroups = HandEvaluator.filterOutHonors(groups);
        Assert.assertEquals(filteredGroups.size(),2);
    }

    @Test
    public void tripletsInAllColors(){
        List<Tile> tileList = tilesFromFile.analyzeString("S111W111P111");
        List<Tile> falseTileList = tilesFromFile.analyzeString("S111W111P11C1C1C1");

        List<SetGroup> setGroups = handEvaluator.findSets(tileList);

        List<SetGroup> falseSetGroups = handEvaluator.findSets(falseTileList);

        Assert.assertEquals(handIdentifier.hasTripletColors(setGroups),true);
        Assert.assertEquals(handIdentifier.hasTripletColors(falseSetGroups),false);

    }

    @Test
    public void sameSequenceInThreeSuitsTest(){
        List<Tile> tiles = tilesFromFile.analyzeString("S123W123P123");
        List<Tile> incorrectTiles = tilesFromFile.analyzeString("S123W123C1C2C3");
        List<Tile> correctTiles = tilesFromFile.analyzeString("S1234W123P1234V1V2V3");

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        Assert.assertEquals(sequenceGroups.size(),3);
        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(sequenceGroups.size(),3);
        Assert.assertTrue(handIdentifier.sameSequenceInThreeSuits(sequenceGroups));

        List<SequenceGroup> sequenceGroupsIncorrect = handEvaluator.findSequences(incorrectTiles);
        Assert.assertEquals(sequenceGroupsIncorrect.size(),2);
        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroupsIncorrect,incorrectTiles);
        Assert.assertEquals(sequenceGroupsIncorrect.size(),2);
        Assert.assertFalse(handIdentifier.sameSequenceInThreeSuits(sequenceGroupsIncorrect));

        List<SequenceGroup> sequenceGroupsCorrect = handEvaluator.findMaxValidSequences(handEvaluator.findSequences(correctTiles),correctTiles);

        Assert.assertFalse(handIdentifier.sameSequenceInThreeSuits(sequenceGroupsIncorrect));
        Assert.assertTrue(handIdentifier.sameSequenceInThreeSuits(sequenceGroupsCorrect));

    }

    @Test
    public void testOneSetOfIdenticalSequencesSameSuit(){
        List<Tile> tiles = tilesFromFile.analyzeString("S123123W12P1245C1C2");
        List<Tile> incorrectTiles = tilesFromFile.analyzeString("S12312W123P123C1C2");

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(sequenceGroups.size(),2);
        List<SequenceGroup> incorrectGroups = handEvaluator.findSequences(tiles);
        incorrectGroups = handEvaluator.findMaxValidSequences(incorrectGroups,incorrectTiles);
        Assert.assertEquals(incorrectGroups.size(),3);

        Assert.assertTrue(handIdentifier.oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Sou")));
        Assert.assertFalse(handIdentifier.oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Wan")));

        Assert.assertFalse(handIdentifier.oneSetOfIdenticalSequencesSameSuit(incorrectGroups,new Suit("Sou")));
        Assert.assertFalse(handIdentifier.oneSetOfIdenticalSequencesSameSuit(incorrectGroups,new Suit("Pin")));

    }

    @Test
    public void testHasStraight(){
        List<Tile> tiles = tilesFromFile.analyzeString("S123456789C1C2C3V1V2V3");
        List<Tile> incorrectTiles = tilesFromFile.analyzeString("S12345678W98765432P123");

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(sequenceGroups.size(),3);
        Assert.assertTrue(handIdentifier.hasStraight(sequenceGroups));

        List<SequenceGroup> sequenceGroupsIncorrect = handEvaluator.findSequences(incorrectTiles);
        sequenceGroupsIncorrect = handEvaluator.findMaxValidSequences(sequenceGroupsIncorrect,incorrectTiles);
        Assert.assertEquals(sequenceGroupsIncorrect.size(),5);
        Assert.assertFalse(handIdentifier.hasStraight(sequenceGroupsIncorrect));

    }
}
