import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class HandEvaluatorTest {

    private TileSet tileSet;
    private Hand hand;

    @BeforeTest
    public void setupTestEnvironment(){
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1);
        hand.initializeHand(tileSet);
    }

    @Test
    public void testName() throws Exception {
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
        //hand.getTiles().stream()
        //        .filter(z-> z.getIdentifier() == "Chun" || z.getIdentifier() == "Hatsu" || z.getIdentifier() == "Haku")
        //        .forEach(z -> System.out.println(z.toString()));
        hand.getTiles().stream()
                .forEach(z -> System.out.println(z.getIdentifier().concat("@"+z.getPosition().toString()).concat(z.getTileNumber().toString())));
        List<Tile> filteredTiles = handEvaluator.filterPin();

        List<Tile> sortedTiles = handEvaluator.reduceTileSet(filteredTiles);
        filteredTiles.stream()
                .forEach(z -> System.out.println(z.toString()));
        //sortedTiles.stream()
        //        .forEach(z -> System.out.println(z.toString()));
    }
}
