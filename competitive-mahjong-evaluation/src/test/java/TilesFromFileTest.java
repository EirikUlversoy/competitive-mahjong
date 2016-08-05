import com.google.common.collect.Iterables;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TilesFromFileTest {
    private TilesFromFile tilesFromFile;
    @BeforeTest
    public void setup(){
        tilesFromFile = new TilesFromFile();

    }

    @Test
    public void testReadFile() throws IOException{
        List<List<Tile>> tileList = tilesFromFile.getFromFile();
        Assert.assertNotNull(tileList);
    }

    @Test
    public void testReadString(){
        List<Tile> tiles = new ArrayList<>();
        tiles.add(new ColorTile("Green",1,1));
        List<Tile> testTiles = tilesFromFile.analyzeString("C1");
        Assert.assertEquals(testTiles.toString(),tiles.toString());

        List<Tile> varietyTest = new ArrayList<>();
        varietyTest.add(new SouTile(1,1));
        varietyTest.add(new PinTile(2,1));
        varietyTest.add(new WanTile(3,1));
        varietyTest.add(new ColorTile("Green",1,1));
        varietyTest.add(new WindTile("West",1,1));

        List<Tile> varietyResults = tilesFromFile.analyzeString("S1P2W3C1V1");
        Collections.sort(varietyTest,(x,z)-> z.getTileNumber().compareTo(x.getTileNumber()));
        Collections.sort(varietyResults,(x,z) -> z.getTileNumber().compareTo(x.getTileNumber()));
        //Assert.assertTrue(Iterables.elementsEqual(varietyTest,varietyResults));
        Assert.assertEquals(varietyResults.toString(),varietyTest.toString());

        List<Tile> duplicatesAndVarietyTest = new ArrayList<>();
        duplicatesAndVarietyTest.add(new PinTile(1,1));
        duplicatesAndVarietyTest.add(new PinTile(1,2));
        duplicatesAndVarietyTest.add(new PinTile(1,3));
        duplicatesAndVarietyTest.add(new WanTile(1,1));
        duplicatesAndVarietyTest.add(new WanTile(1,2));
        duplicatesAndVarietyTest.add(new WanTile(1,3));
        duplicatesAndVarietyTest.add(new SouTile(1,1));
        duplicatesAndVarietyTest.add(new SouTile(1,2));
        duplicatesAndVarietyTest.add(new SouTile(1,3));
        duplicatesAndVarietyTest.add(new WindTile("North",4,1));
        duplicatesAndVarietyTest.add(new WindTile("South",2,1));
        
        List<Tile> duplicatesAndVarietyResults = tilesFromFile.analyzeString("P111W111S111V42");

        for(Tile tile : duplicatesAndVarietyTest){
            Assert.assertTrue(duplicatesAndVarietyResults.stream().map(Tile::toString).anyMatch(z -> z.equals(tile.toString())));
        }
    }

    @Test
    public void dealWithDuplicatesTest(){
        List<Tile> test = new ArrayList<>();
        test.add(new PinTile(1,0));
        test.add(new PinTile(1,0));
        test.add(new PinTile(1,0));
        test.add(new PinTile(1,0));

        List<Tile> result = new ArrayList<>();
        result.add(new PinTile(1,1));
        result.add(new PinTile(1,2));
        result.add(new PinTile(1,3));
        result.add(new PinTile(1,4));

        List<Tile> testResult = tilesFromFile.dealWithDuplicates(test);

        Collections.sort(testResult,(x,z)-> z.getTileId().compareTo(x.getTileId()));
        Collections.sort(result,(x,z) -> z.getTileId().compareTo(x.getTileId()));

        Assert.assertEquals(testResult.toString(),result.toString());

    }
}
