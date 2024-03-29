import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.*;

public class HandEvaluatorTest {

    private TileSet tileSet;
    private Hand hand;
    private TilesFromFile tilesFromFile;
    private HandEvaluator handEvaluator;
    @BeforeTest
    public void setupTestEnvironment(){
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1,120);
        hand.initializeHand(tileSet);
        tilesFromFile = new TilesFromFile();
        handEvaluator = new HandEvaluator();
    }

    @Test
    public void findSets() {
        List<Tile> testList = new ArrayList<>();
        testList = tilesFromFile.analyzeString("S111222233");
        HandEvaluator handEvaluator = new HandEvaluator();
        List<SetGroup> sets = handEvaluator.findSets(testList);
        Assert.assertEquals(sets.size(),2);
        Assert.assertTrue(sets.stream().filter(z -> z.getFirstMember().getTileNumber() == 2)
        .anyMatch(z -> z.isKAN()));



    }

    @Test
    public void numberToTileMapTest(){
        HandEvaluator handEvaluator = new HandEvaluator();
        List<Tile> tiles = tilesFromFile.analyzeString("S11222333344");
        Map<Integer, List<Tile>> integerListMap = handEvaluator.findTileCount(tiles);
        Assert.assertEquals(integerListMap.get(1).size(),2);
        Assert.assertEquals(integerListMap.get(2).size(),3);
        Assert.assertEquals(integerListMap.get(3).size(),4);
        Assert.assertEquals(integerListMap.get(4).size(),2);

    }

    @Test
    public void findPairsTest(){
        HandEvaluator handEvaluator = new HandEvaluator();
        List<Tile> tiles = tilesFromFile.analyzeString("S11W123456789C111");
        List<Tile> fakeTiles = tilesFromFile.analyzeString("S123W123C111V111S45");
        Optional<Pair> pair = handEvaluator.findPair(tiles);
        Optional<Pair> incorrectPair = handEvaluator.findPair(fakeTiles);
        Assert.assertEquals(pair.isPresent(),true);
        Assert.assertEquals(incorrectPair.isPresent(),false);


    }

    @Test
    public void findPairInSuit(){
        HandEvaluator handEvaluator = new HandEvaluator();
        List<Tile> tiles = tilesFromFile.analyzeString("S11");
        List<Optional<Pair>> pairList = handEvaluator.findPairInSuit(tiles,SouTile.class);

        Assert.assertEquals(pairList.size(),1);
        Assert.assertEquals(pairList.get(0).isPresent(),true);
    }

    @Test
    public void fSeq(){
        List<Tile> testList = tilesFromFile.analyzeString("S111222333");
        List<SequenceGroup> sequenceGroups = handEvaluator.fSeq(testList);
        Assert.assertEquals(sequenceGroups.size(),27);

        List<Tile> tList = tilesFromFile.analyzeString("S123456789");
        sequenceGroups = handEvaluator.fSeq(tList);
        Assert.assertEquals(sequenceGroups.size(),7);

        tList = tilesFromFile.analyzeString("S123456778899");
        sequenceGroups = handEvaluator.fSeq(tList);
        Assert.assertEquals(sequenceGroups.size(),18);

        tList = tilesFromFile.analyzeString("S122345567899");
        sequenceGroups = handEvaluator.fSeq(tList);
        Assert.assertEquals(sequenceGroups.size(),13);

        tList = tilesFromFile.analyzeString("S112233445566778899");
        sequenceGroups = handEvaluator.fSeq(tList);
        Assert.assertEquals(sequenceGroups.size(),56);
    }
    @Test
    public void findSequencesTest(){
        HandEvaluator handEvaluator = new HandEvaluator();
        List<Tile> testList = new ArrayList<>();
        testList = tilesFromFile.analyzeString("S111222333");

        List<SequenceGroup> sequenceGroupList = handEvaluator.findSequences(testList);
        Assert.assertEquals(sequenceGroupList.size(), 3);

        testList.clear();
        testList.add(new SouTile(1,1));
        sequenceGroupList.clear();
        sequenceGroupList = handEvaluator.findSequences(testList);
        Assert.assertEquals(sequenceGroupList.size(),0);

        testList.clear();
        testList = tilesFromFile.analyzeString("S111P111W111");

        sequenceGroupList.clear();
        sequenceGroupList = handEvaluator.findSequences(testList);
        Assert.assertEquals(sequenceGroupList.size(),0);

        sequenceGroupList.clear();
        sequenceGroupList = handEvaluator.findSequences(tilesFromFile.analyzeString("S123W123P123"));
        Assert.assertEquals(sequenceGroupList.size(),3);



        testList.clear();
        sequenceGroupList.clear();

        testList = tilesFromFile.analyzeString("S234P123W876");
        List<Tile> wanList = handEvaluator.filterWan(testList);
        List<Tile> pinList = handEvaluator.filterPin(testList);
        List<Tile> souList = handEvaluator.filterSou(testList);

        sequenceGroupList = handEvaluator.findSequences(wanList);
        sequenceGroupList.addAll(handEvaluator.findSequences(pinList));
        sequenceGroupList.addAll(handEvaluator.findSequences(souList));

        sequenceGroupList.stream().forEach(z -> System.out.println(z.toString()));
        Assert.assertEquals(sequenceGroupList.size(),3);
        testList = tilesFromFile.analyzeString("S123456789");
        sequenceGroupList = handEvaluator.findSequences(testList);
        Assert.assertEquals(sequenceGroupList.size(),7);


    }

    @Test
    public void testFindValidSequencesFromSequences(){
        HandEvaluator handEvaluator = new HandEvaluator();
        List<Tile> tiles = tilesFromFile.analyzeString("S123456789");
        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        //As the other test shows, this gives 7 possible groups

        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(sequenceGroups.size(),3);
    }
    @Test
    public void testName() throws Exception {
        HandEvaluator handEvaluator = new HandEvaluator();

        List<Tile> testList = new ArrayList<>();
        testList = tilesFromFile.analyzeString("S1234567891256");
        List<Tile> testListSequenceAndSetTogether = new ArrayList<>();
        testListSequenceAndSetTogether = tilesFromFile.analyzeString("S111222333");

        handEvaluator.findTileCount(testList);

        List<SequenceGroup> sequenceGroupList = handEvaluator.findSequences(testList);
        List<SequenceGroup> validSequenceGroupList = handEvaluator.findMaxValidSequences(sequenceGroupList,testList);
        Assert.assertEquals(validSequenceGroupList.size(),3);

        List<SequenceGroup> sequenceGroupList1 = handEvaluator.findSequences(testList);
        List<SequenceGroup> validSequenceGroupList1 = handEvaluator.findMaxValidSequences(sequenceGroupList1,testListSequenceAndSetTogether);

        Assert.assertEquals(validSequenceGroupList1.size(),3);

    }

    @Test
    public void testSetsAndSequencesTogether(){
        HandEvaluator handEvaluator = new HandEvaluator();

        List<Tile> tiles = tilesFromFile.analyzeString("S123456C1C1C1C2C2C2");
        List<Tile> tiles2 = tilesFromFile.analyzeString("S123456C1C1C1C2C2C2");

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        List<SequenceGroup> validSequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(validSequenceGroups.size(),2);
        List<SetGroup> setGroups = handEvaluator.findSets(tiles2);

        Assert.assertEquals(setGroups.size()+validSequenceGroups.size(),4);

    }

    @Test
    public void testGroupAmountCounterReturnsCorrectAnswer(){
        List<Tile> tiles = tilesFromFile.analyzeString("S123456789123");
        List<SequenceGroup> straightSequences = handEvaluator.findSequences(tiles);
        straightSequences = handEvaluator.findMaxValidSequences(straightSequences,tiles);
        Assert.assertEquals(handEvaluator.groupAmountCounterSequences(straightSequences,SouTile.class).intValue(),4);
    }
}
