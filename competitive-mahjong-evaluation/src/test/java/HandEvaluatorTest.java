import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HandEvaluatorTest {

    private TileSet tileSet;
    private Hand hand;
    private TilesFromFile tilesFromFile;
    @BeforeTest
    public void setupTestEnvironment(){
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1,120);
        hand.initializeHand(tileSet);
        tilesFromFile = new TilesFromFile();

    }

    @Test
    public void findSets() {
        List<Tile> testList = new ArrayList<>();
        testList = tilesFromFile.analyzeString("S111222233");
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
        List<SetGroup> sets = handEvaluator.findSets(testList);
        Assert.assertEquals(sets.size(),2);
        Assert.assertTrue(sets.stream().filter(z -> z.getFirstMember().getTileNumber() == 2)
        .anyMatch(z -> z.isKAN()));



    }

    @Test
    public void numberToTileMapTest(){
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
        List<Tile> tiles = tilesFromFile.analyzeString("S11222333344");
        Map<Integer, List<Tile>> integerListMap = handEvaluator.findTileCount(tiles);
        Assert.assertEquals(integerListMap.get(1).size(),2);
        Assert.assertEquals(integerListMap.get(2).size(),3);
        Assert.assertEquals(integerListMap.get(3).size(),4);
        Assert.assertEquals(integerListMap.get(4).size(),2);

    }
    @Test
    public void findSequencesTest(){
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
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

        testList.clear();
        sequenceGroupList.clear();
        testList = tilesFromFile.analyzeString("S123456789");
        sequenceGroupList = handEvaluator.findSequences(testList);
        Assert.assertEquals(sequenceGroupList.size(),7);


    }

    @Test
    public void testFindValidSequencesFromSequences(){
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
        List<Tile> tiles = tilesFromFile.analyzeString("S123456789");
        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        //As the other test shows, this gives 7 possible groups

        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(sequenceGroups.size(),3);
    }
    @Test
    public void testName() throws Exception {
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);

        List<Tile> testList = new ArrayList<>();
        testList = tilesFromFile.analyzeString("S1234567891256");
        List<Tile> testListSequenceAndSetTogether = new ArrayList<>();
        testListSequenceAndSetTogether = tilesFromFile.analyzeString("S111222333");
        //Collections.shuffle(testList);

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
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);

        List<Tile> tiles = tilesFromFile.analyzeString("S123456C1C1C1C2C2C2");
        List<Tile> tiles2 = tilesFromFile.analyzeString("S123456C1C1C1C2C2C2");

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(tiles);
        List<SequenceGroup> validSequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);
        Assert.assertEquals(validSequenceGroups.size(),2);
        tiles.stream().map(Tile::toString).forEach(System.out::println);
        List<SetGroup> setGroups = handEvaluator.findSets(tiles2);
        List<Group> groups = new ArrayList<>();
        groups.addAll(validSequenceGroups);
        groups.addAll(setGroups);

        Assert.assertEquals(groups.size(),4);

    }
}
