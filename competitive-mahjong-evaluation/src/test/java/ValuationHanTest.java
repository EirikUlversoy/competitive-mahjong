import com.typesafe.config.Config;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ValuationHanTest {
    private ValuationHan valuationHan;
    @BeforeTest
    public void setup(){
        this.valuationHan = new ValuationHan();
        this.valuationHan.readHandValuesConfig();
        this.valuationHan.readYakumanHandValuesConfig();
        this.valuationHan.setupYakumanHandValueObjectMap();
        this.valuationHan.setupHandValueObjectMap();
    }
    @Test
    public void testLoadingConfigs(){
        Assert.assertNotNull(valuationHan.readHandValuesConfig());
        Assert.assertNotNull(valuationHan.readYakumanHandValuesConfig());

    }

    @Test
    public void testConfigContent(){
        Config normalConfig = valuationHan.readHandValuesConfig();
        valuationHan.setupHandValueObjectMap();
    }

    @Test
    public void testYakuConfigContent(){
        Config yakumanConfig = valuationHan.readYakumanHandValuesConfig();
        valuationHan.setupYakumanHandValueObjectMap();
    }
    @Test
    public void testCalculateHan(){
        TilesFromFile tilesFromFile = new TilesFromFile();
        List<Tile> tiles = tilesFromFile.analyzeString("S123456789123C2C2");
        Assert.assertEquals(valuationHan.calculateHan(tiles,false).intValue(),1);
        Assert.assertEquals(valuationHan.calculateHan(tiles,true).intValue(),3);

    }
}
