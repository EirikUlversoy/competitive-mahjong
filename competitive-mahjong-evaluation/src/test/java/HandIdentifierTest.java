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
    private List<SequenceGroup> straightCombinations = new ArrayList<>();
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
    public void checkChantaFunction(){
        List<Tile> correctTiles = tilesFromFile.analyzeString("S123W123P123S123C2C2");
        List<Tile> incorrectTiles = tilesFromFile.analyzeString("S123456P123W123C2C2");

        List<SequenceGroup> cSequenceGroups = handEvaluator.findSequences(correctTiles);
        List<SequenceGroup> iSequenceGroups = handEvaluator.findSequences(incorrectTiles);
        cSequenceGroups = handEvaluator.findMaxValidSequences(cSequenceGroups,correctTiles);
        iSequenceGroups = handEvaluator.findMaxValidSequences(iSequenceGroups,incorrectTiles);
        List<SetGroup> cSetGroups = handEvaluator.findSets(correctTiles);
        List<SetGroup> iSetGroups = handEvaluator.findSets(incorrectTiles);

        Pair cPair = handEvaluator.findPair(correctTiles).get();

        Assert.assertTrue(handIdentifier.hasChanta(cSetGroups,cSequenceGroups,cPair));
        Assert.assertFalse(handIdentifier.hasChanta(iSetGroups,iSequenceGroups,cPair));
        Assert.assertFalse(handIdentifier.hasChanta(iSetGroups,iSequenceGroups,cPair));
    }
    @Test
    public void checkFlushFunction(){
        straightSequences = handEvaluator.findSequences(straight);
        List<SetGroup> setGroups = handEvaluator.findSets(straight);
        straightSequences = handEvaluator.findMaxValidSequences(straightSequences,straight);
        straightCombinations.addAll(this.straightSequences);
        Pair pair = handEvaluator.findPair(straight).get();

        List<Tile> tiles = tilesFromFile.analyzeString("S123456789123W456P111");
        List<SequenceGroup> wrongSequences = handEvaluator.findSequences(tiles);
        wrongSequences = handEvaluator.findMaxValidSequences(wrongSequences,tiles);
        List<SetGroup> wrongSetGroups = handEvaluator.findSets(tiles);
        System.out.println(pair.toString());
        Assert.assertEquals(handIdentifier.hasFullFlush(setGroups,straightSequences,pair),false);

        List<Tile> testTiles = tilesFromFile.analyzeString("S123456789123C1C1");
        List<SequenceGroup> testSequences = handEvaluator.findSequences(testTiles);
        testSequences = handEvaluator.findMaxValidSequences(testSequences,testTiles);

        List<SetGroup> setGroupsList = handEvaluator.findSets(testTiles);

        Pair icPair = handEvaluator.findPair(testTiles).get();
        Assert.assertFalse(handIdentifier.hasFullFlush(wrongSetGroups,testSequences,icPair));


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
        List<Tile> listWithHonors = tilesFromFile.analyzeString("S123332C111C222");
        List<Tile> listWithHonorsForSets = tilesFromFile.analyzeString("S123332C111C222");

        List<SequenceGroup> honorGroup = handEvaluator.findSequences(listWithHonors);
        honorGroup = handEvaluator.findMaxValidSequences(honorGroup,listWithHonors);
        List<SetGroup> honorGroupSets = handEvaluator.findSets(listWithHonorsForSets);


        List<SetGroup> filteredGroups = HandEvaluator.filterOutHonors(honorGroupSets);
        Assert.assertEquals(filteredGroups.size(),1);
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
        List<Tile> incorrectTiles = tilesFromFile.analyzeString("S123456789123C2C2");
        //List<Tile> incorrectTiles = tilesFromFile.analyzeString("S123W123C1C2C3");
        List<Tile> correctTiles = tilesFromFile.analyzeString("S1234W123P1234V1V2V3");

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequencesFromAllSuits(tiles);
        System.out.println(sequenceGroups);
        Assert.assertEquals(sequenceGroups.size(),3);
        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(sequenceGroups.size(),3);
        System.out.println(sequenceGroups);

        Assert.assertTrue(handIdentifier.sameSequenceInThreeSuits(sequenceGroups));

        List<SequenceGroup> sequenceGroupsIncorrect = handEvaluator.findSequencesFromAllSuits(incorrectTiles);
        //Assert.assertEquals(sequenceGroupsIncorrect.size(),2);
        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroupsIncorrect,incorrectTiles);
        //Assert.assertEquals(sequenceGroupsIncorrect.size(),2);
        Assert.assertFalse(handIdentifier.sameSequenceInThreeSuits(sequenceGroupsIncorrect));

        List<SequenceGroup> sequenceGroupsCorrect = handEvaluator.findMaxValidSequences(handEvaluator.findSequences(correctTiles),correctTiles);

        Assert.assertFalse(handIdentifier.sameSequenceInThreeSuits(sequenceGroupsIncorrect));
        Assert.assertTrue(handIdentifier.sameSequenceInThreeSuits(sequenceGroupsCorrect));

        List<Tile> tiles2 = tilesFromFile.analyzeString("S123W123P123V22S789");
        sequenceGroups = handEvaluator.findSequencesFromAllSuits(tiles2);
        System.out.println(sequenceGroups);

        Assert.assertTrue(handIdentifier.sameSequenceInThreeSuits(sequenceGroups));
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
