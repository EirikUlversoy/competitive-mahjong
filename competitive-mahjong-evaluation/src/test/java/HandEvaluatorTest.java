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
        hand = new Hand(1,50);
        hand.initializeHand(tileSet);
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
        testList.add(new SouTile(5));
        testList.add(new SouTile(6));
        testList.add(new SouTile(7));
        testList.add(new SouTile(8));
        testList.add(new SouTile(9));
        testList.add(new SouTile(4));
        testList.add(new SouTile(3));
        testList.add(new SouTile(2));
        testList.add(new SouTile(1));


        List<SequenceGroup> sortedTiles = handEvaluator.reduceTileSet(testList);
        sortedTiles = handEvaluator.reduceTileSet(filteredTiles);
        List<SetGroup> sets = handEvaluator.checkForSets(filteredTiles);
        List<Group> totalGroups = new ArrayList<>();
        totalGroups.addAll(sets);
        totalGroups.addAll(sortedTiles);

        Integer validGroups = handEvaluator.checkForOverlap(totalGroups);
        System.out.println(validGroups);
        totalGroups.clear();
        totalGroups.addAll(handEvaluator.reduceTileSet(testList));

        validGroups = handEvaluator.checkForOverlap(totalGroups);

        System.out.println(validGroups);

        //sets.stream().map(SetGroup::toString).forEach(System.out::println);
        //sortedTiles.stream()
        //        .forEach(z -> System.out.println(z.toString()));
    }
}
