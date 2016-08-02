import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
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
        testList.add(new SouTile(1));
        testList.add(new SouTile(2));
        testList.add(new SouTile(3));
        testList.add(new SouTile(4));
        testList.add(new SouTile(5));
        testList.add(new SouTile(6));
        testList.add(new SouTile(7));
        testList.add(new SouTile(8));
        testList.add(new SouTile(9));



        /*
        List<SequenceGroup> sortedTiles = handEvaluator.findSequences(testList);
        sortedTiles = handEvaluator.findSequences(filteredTiles);
        List<SetGroup> sets = handEvaluator.findSets(filteredTiles);
        List<Group> totalGroups = new ArrayList<>();
        totalGroups.addAll(sets);
        totalGroups.addAll(sortedTiles);
        totalGroups.addAll(handEvaluator.findSets(handEvaluator.filterSou()));
        totalGroups.addAll(handEvaluator.findSequences(handEvaluator.filterSou()));
        totalGroups.addAll(handEvaluator.findSets(handEvaluator.filterWan()));
        totalGroups.addAll(handEvaluator.findSequences(handEvaluator.filterWan()));


        Integer validGroups = handEvaluator.checkForOverlap(totalGroups);
        System.out.println(validGroups);
        */

        List<SequenceGroup> sequenceGroupList = handEvaluator.findSequences(testList);
        List<Group> normalizedGroupList = new ArrayList<>();
        normalizedGroupList.addAll(sequenceGroupList);
        int validGroups = handEvaluator.checkForOverlap(normalizedGroupList);
        Assert.assertEquals(normalizedGroupList.size(),7);
        Assert.assertEquals(validGroups,7);
        //totalGroups.clear();
        //totalGroups.addAll(handEvaluator.findSequences(testList));

        //validGroups = handEvaluator.checkForOverlap(totalGroups);

        //System.out.println(validGroups);

        //sets.stream().map(SetGroup::toString).forEach(System.out::println);
        //sortedTiles.stream()
        //        .forEach(z -> System.out.println(z.toString()));
    }
}
