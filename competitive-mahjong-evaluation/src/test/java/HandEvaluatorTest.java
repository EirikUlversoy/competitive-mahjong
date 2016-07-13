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

        hand.initializeHand(tileSet);
    }

    @Test
    public void testName() throws Exception {
        HandEvaluator handEvaluator = new HandEvaluator(this.hand);
        List<Tile> filteredTiles = handEvaluator.filterPin();
        List<Tile> sortedTiles = handEvaluator.reduceTileSet(filteredTiles);

    }
}
