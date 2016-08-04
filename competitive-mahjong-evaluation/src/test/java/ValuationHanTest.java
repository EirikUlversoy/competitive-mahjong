import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ValuationHanTest {
    private HandEvaluator handEvaluator;
    private TileSet tileSet;
    private Hand hand;
    private ValuationHan valuationHan;
    private List<SequenceGroup> straightSequences;
    private List<Group> straightCombinations = new ArrayList<>();
    private List<Tile> straight = new ArrayList<>();

    @BeforeTest
    public void setup(){
        List<Tile> straight = new ArrayList<>();
        tileSet = new TileSet();
        tileSet.initializeTiles();
        hand = new Hand(1,120);
        hand.initializeHand(tileSet);
        valuationHan = new ValuationHan();
        this.handEvaluator = new HandEvaluator(hand);
        straight.add(new SouTile(1,1));
        straight.add(new SouTile(2,1));
        straight.add(new SouTile(3,1));
        straight.add(new SouTile(4,1));
        straight.add(new SouTile(5,1));
        straight.add(new SouTile(6,1));
        straight.add(new SouTile(7,1));
        straight.add(new SouTile(8,1));
        straight.add(new SouTile(9,1));

        straight.add(new SouTile(9,2));
        straight.add(new SouTile(8,2));
        straight.add(new SouTile(7,2));
        Tile colorTile1 = new ColorTile("Chun",1);
        Tile colorTile2 = new ColorTile("Chun",2);
        straight.add(colorTile1);
        straight.add(colorTile2);
        straight.stream().map(Tile::toString).forEach(System.out::println);
        this.straight = straight;

    }


    @Test
    public void checkFlushFunction(){
        straightSequences = handEvaluator.findSequences(straight);
        System.out.println("printing sequences???");
        straightSequences.stream().map(SequenceGroup::toString).forEach(System.out::println);

        straightSequences = handEvaluator.findMaxValidSequences(straightSequences,straight);
        straightCombinations.addAll(this.straightSequences);
        Assert.assertEquals(valuationHan.checkFlush(this.straightCombinations),"Flush");

    }

    @Test
    public void testGroupAmountCounterReturnsCorrectAnswer(){
        straightSequences = handEvaluator.findSequences(straight);
        straightSequences = handEvaluator.findMaxValidSequences(straightSequences,straight);
        Assert.assertEquals(valuationHan.groupAmountCounter(straightCombinations,SouTile.class).intValue(),4);
    }

    @Test
    public void testHasThreeQuads() {


    }
}
