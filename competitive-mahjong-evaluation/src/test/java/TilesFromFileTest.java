import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class TilesFromFileTest {

    @BeforeTest
    public void setup(){

    }

    @Test
    public void testReadFile() throws IOException{
        TilesFromFile tilesFromFile = new TilesFromFile();
        tilesFromFile.getFromFile();
    }
}
