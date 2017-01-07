import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.function.Function;

public class YakumanCheckerTest {
    private ValuationHan valuationHan;
    private HandEvaluator handEvaluator;
    private TilesFromFile tilesFromFile;
    private Hand hand;
    private TileSet tileSet;
    private YakumanChecker yakumanChecker;
    @BeforeTest
    private void setup(){
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1,120);
        hand.initializeHand(tileSet);
        valuationHan = new ValuationHan();
        handEvaluator = new HandEvaluator();
        tilesFromFile = new TilesFromFile();
        yakumanChecker = new YakumanChecker(new HandIdentifier());
    }

    @Test
    public void thirtheenOrphansTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("V11V2S1S9W1W9V3V4P1P9C1C2C3");
        List<Tile> falseTiles = tilesFromFile.analyzeString("S123V1V2V3V4C1C2C3W9133");
        Assert.assertTrue(yakumanChecker.thirtheenOrphans(trueTiles));
        System.out.println("true tiles");
        System.out.println(trueTiles);
        Assert.assertFalse(yakumanChecker.thirtheenOrphans(falseTiles));
        List<Tile> tiles = tilesFromFile.analyzeString("S123W987P345P678V11");
        Assert.assertFalse(yakumanChecker.thirtheenOrphans(tiles));

    }

    @Test
    public void fourConcealedTripletsTest() {
        List<Tile> trueTiles = tilesFromFile.analyzeString("V111W111P444S222C11");
        List<Tile> falseTiles = tilesFromFile.analyzeString("V11V2S1S9W1W9V3V4P1P9C1C2C3");
        boolean closed = true;
        Assert.assertTrue(yakumanChecker.fourConcealedTriplets(trueTiles, closed));
        Assert.assertFalse(yakumanChecker.fourConcealedTriplets(trueTiles, !closed));
        Assert.assertFalse(yakumanChecker.fourConcealedTriplets(falseTiles,closed));
        Assert.assertFalse(yakumanChecker.fourConcealedTriplets(falseTiles,!closed));
    }
    @Test
    public void fourConcealedTripletsSingleWaitTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("V111W111P444S222C11");
        List<Tile> falseTiles = tilesFromFile.analyzeString("V11V2S1S9W1W9V3V4P1P9C1C2C3");
        boolean closed = true;
        boolean singleWait = true;

        Assert.assertTrue(yakumanChecker.fourConcealedTripletsSingleWait(trueTiles, closed, singleWait));
        Assert.assertFalse(yakumanChecker.fourConcealedTripletsSingleWait(trueTiles, closed, !singleWait));
        Assert.assertFalse(yakumanChecker.fourConcealedTripletsSingleWait(falseTiles,closed,singleWait));
        Assert.assertFalse(yakumanChecker.fourConcealedTripletsSingleWait(falseTiles,closed,!singleWait));
    }

    @Test
    public void threeBigDragonsTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("C111C222C333S123V11");
        List<Tile> falseTiles = tilesFromFile.analyzeString("S123456C111C222V11");
        Assert.assertTrue(yakumanChecker.threeBigDragons(trueTiles));
        Assert.assertFalse(yakumanChecker.threeBigDragons(falseTiles));
    }

    @Test
    public void littleFourWindsTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("V11V2222V3333V444S123");
        List<Tile> falseTiles = tilesFromFile.analyzeString("V11V222V333S123456");
        Assert.assertTrue(yakumanChecker.littleFourWinds(trueTiles));
        Assert.assertFalse(yakumanChecker.littleFourWinds(falseTiles));
    }

    @Test
    public void bigFourWinds(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("V1111V2222V3333V444C11");
        List<Tile> falseTiles = tilesFromFile.analyzeString("V11V222V333V444S123");
        Assert.assertTrue(yakumanChecker.bigFourWinds(trueTiles));
        Assert.assertFalse(yakumanChecker.bigFourWinds(falseTiles));


    }

    @Test
    public void allHonorsTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("V1111V2222V3333V444C11");
        List<Tile> falseTiles = tilesFromFile.analyzeString("P123V22V333V444S123");
        Assert.assertTrue(yakumanChecker.allHonors(trueTiles));
        Assert.assertFalse(yakumanChecker.allHonors(falseTiles));

    }

    @Test
    public void allTerminalsTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("S111P111999W999S99");
        List<Tile> falseTiles = tilesFromFile.analyzeString("S123P123789W999S99");
        Assert.assertTrue(yakumanChecker.allTerminals(trueTiles));
        Assert.assertFalse(yakumanChecker.allTerminals(falseTiles));

    }

    @Test
    public void allGreenTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("S222333C111S88866");
        List<Tile> falseTiles = tilesFromFile.analyzeString("S777333C111S88866");
        List<Tile> clearlyFalseTiles = tilesFromFile.analyzeString("P123456789W111C11");

        Assert.assertTrue(yakumanChecker.allGreen(trueTiles));
        Assert.assertFalse(yakumanChecker.allGreen(falseTiles));
        Assert.assertFalse(yakumanChecker.allGreen(clearlyFalseTiles));

    }

    @Test
    public void doubleNineGatesTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("S11122345678999");
        List<Tile> falseTiles = tilesFromFile.analyzeString("S11122345678W999");
        List<Tile> clearlyFalseTiles = tilesFromFile.analyzeString("C111C222C333V111V22");
        boolean nineWait = true;
        Assert.assertTrue(yakumanChecker.doubleNineGates(trueTiles,nineWait));
        Assert.assertFalse(yakumanChecker.doubleNineGates(trueTiles,!nineWait));
        Assert.assertFalse(yakumanChecker.doubleNineGates(falseTiles,nineWait));
        Assert.assertFalse(yakumanChecker.doubleNineGates(clearlyFalseTiles,nineWait));

    }

    @Test
    public void nineGatesTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("S11122345678999");
        List<Tile> falseTiles = tilesFromFile.analyzeString("S11122345678W999");
        List<Tile> clearlyFalseTiles = tilesFromFile.analyzeString("C111C222C333V111V22");
        boolean nineWait = true;
        Assert.assertTrue(yakumanChecker.nineGates(trueTiles));
        Assert.assertFalse(yakumanChecker.nineGates(falseTiles));
        Assert.assertFalse(yakumanChecker.nineGates(clearlyFalseTiles));
    }

    @Test
    public void fourKansTest(){
        List<Tile> trueTiles = tilesFromFile.analyzeString("S1111W2222C2222V1111C33");
        List<Tile> falseTiles = tilesFromFile.analyzeString("S1111W2222C2222P123W99");
        List<Tile> clearlyFalseTiles = tilesFromFile.analyzeString("S123456789C111V11");

        Assert.assertTrue(yakumanChecker.fourKans(trueTiles));
        Assert.assertFalse(yakumanChecker.fourKans(falseTiles));
        Assert.assertFalse(yakumanChecker.fourKans(clearlyFalseTiles));


    }


}
