import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

public class RonCheckerTest {
    HandEvaluator handEvaluator;
    HandIdentifier handIdentifier;
    TilesFromFile tilesFromFile;
    RonChecker ronChecker;
    @BeforeTest
    public void setup(){
        tilesFromFile = new TilesFromFile();
        ronChecker = new RonChecker(new Gamerules());

    }

    @Test
    public void testHasSevenPairsReturnsTrue(){
        List<Tile> tiles = tilesFromFile.analyzeString("S113355P3355W1199");
        Assert.assertEquals(true,ronChecker.hasSevenPairs(tiles));
        tiles = tilesFromFile.analyzeString("C112233V11223344");
        Assert.assertEquals(true,ronChecker.hasSevenPairs(tiles));

    }

    @Test
    public void testHasSevenPairsReturnsFalse(){
        List<Tile> tiles = tilesFromFile.analyzeString("S113355P3333W1119");
        Assert.assertEquals(false,ronChecker.hasSevenPairs(tiles));
        tiles = tilesFromFile.analyzeString("S1111W1111P1111W222P99");
        Assert.assertEquals(false,ronChecker.hasSevenPairs(tiles));
        tiles = tilesFromFile.analyzeString("C111122223333E111X11");
        Assert.assertEquals(false,ronChecker.hasSevenPairs(tiles));

    }

    @Test
    public void testMeetsMinimumHanValueTrue(){
        List<Tile> tiles = tilesFromFile.analyzeString("S113355P3355W1199");
        Assert.assertEquals(true,ronChecker.handMeetsMinimumValue(tiles,true));

        tiles = tilesFromFile.analyzeString("S123123P123123V22");
        Assert.assertEquals(true,ronChecker.handMeetsMinimumValue(tiles,true));
    }

    @Test
    public void testMeetsMinimumHanValueFalse(){
        List<Tile> tiles = tilesFromFile.analyzeString("S123W987P345P678V11");
        boolean closed = true;
        Assert.assertFalse(ronChecker.handMeetsMinimumValue(tiles, false));

        tiles = tilesFromFile.analyzeString("S123W987P345P678W11");
        closed = true;
        Assert.assertEquals(false,ronChecker.handMeetsMinimumValue(tiles, closed));

        //A valid hand that has no Han
        tiles = tilesFromFile.analyzeString("S123W987P345P678V11");
        closed = false;
        Assert.assertEquals(false,ronChecker.handMeetsMinimumValue(tiles, closed));
    }


}
