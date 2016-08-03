import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandEvaluatorTest {

    private TileSet tileSet;
    private Hand hand;

    @BeforeTest
    public void setupTestEnvironment(){
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1,120);
        hand.initializeHand(tileSet);
    }

    @Test
    public void findSets() {
        List<Tile> testList = new ArrayList<>();
        testList.add(new SouTile(1));
        testList.add(new SouTile(1));
        testList.add(new SouTile(1));
        testList.add(new SouTile(2));
        testList.add(new SouTile(2));
        testList.add(new SouTile(2));
        testList.add(new SouTile(2));
        testList.add(new SouTile(3));
        testList.add(new SouTile(3));

        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
        List<SetGroup> sets = handEvaluator.findSets(testList);
        Assert.assertEquals(sets.size(),2);


    }
    @Test
    public void testName() throws Exception {
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
        //hand.getTiles().stream()
        //        .filter(z-> z.getIdentifier() == "Chun" || z.getIdentifier() == "Hatsu" || z.getIdentifier() == "Haku")
        //        .forEach(z -> System.out.println(z.toString()));
        //hand.getTiles().stream()
        //        .forEach(z -> System.out.println("The hands tiles: "+ z.getIdentifier().concat("@"+z.getPosition().toString()).concat(z.getTileNumber().toString())));
        List<Tile> filteredTiles = handEvaluator.filterPin();

        List<Tile> testList = new ArrayList<>();
        testList.add(new SouTile(1,2));
        testList.add(new SouTile(2,2));
        testList.add(new SouTile(5,2));
        testList.add(new SouTile(6,2));


        testList.add(new SouTile(1,1));
        testList.add(new SouTile(2,1));
        testList.add(new SouTile(3,1));

        testList.add(new SouTile(4,1));

        testList.add(new SouTile(5,1));
        testList.add(new SouTile(6,1));
        testList.add(new SouTile(7,1));
        testList.add(new SouTile(8,1));

        testList.add(new SouTile(9,1));
        List<Tile> testListSequenceAndSetTogether = new ArrayList<>();
        testListSequenceAndSetTogether.add(new SouTile(1,1));
        testListSequenceAndSetTogether.add(new SouTile(2,1));
        testListSequenceAndSetTogether.add(new SouTile(3,1));
        testListSequenceAndSetTogether.add(new SouTile(1,2));
        testListSequenceAndSetTogether.add(new SouTile(2,2));
        testListSequenceAndSetTogether.add(new SouTile(3,2));
        testListSequenceAndSetTogether.add(new SouTile(1,1));
        testListSequenceAndSetTogether.add(new SouTile(2,1));
        testListSequenceAndSetTogether.add(new SouTile(3,1));

        Collections.shuffle(testList);

        handEvaluator.findTileCount(testList);




        List<SequenceGroup> sequenceGroupList = handEvaluator.findSequences(testList);
        List<SequenceGroup> validSequenceGroupList = handEvaluator.findMaxValidSequences(sequenceGroupList,testList);
        Assert.assertEquals(validSequenceGroupList.size(),3);

        List<SequenceGroup> sequenceGroupList1 = handEvaluator.findSequences(testList);
        List<SequenceGroup> validSequenceGroupList1 = handEvaluator.findMaxValidSequences(sequenceGroupList1,testListSequenceAndSetTogether);

        Assert.assertEquals(validSequenceGroupList1.size(),3);

    }
}
