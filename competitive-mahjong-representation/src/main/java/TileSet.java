import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TileSet {
    Enum HAKU = 0;
    private List<Tile> unusedTiles = new ArrayList<Tile>();
    private List<Tile> usedTiles = new ArrayList<Tile>();
    private Ruleset ruleset;
    private Enum Colors = {
        HAKU=0, HATSU=1, CHUN=2
    };
    public Integer getAmount(){
        return unusedTiles.size();
    }

    public void initializeTiles(){
        this.ruleset = new Ruleset();

        for(int i = 0;i<this.ruleset.getColors();i++)
        {
            List<Tile> ColorTiles = ColorTile.generateColorTiles(this.ruleset.getColors(),this.ruleset.getPerTile());


        }
    }
    public void initializeTiles(String ruletype){

    }

    public List<Tile> generateColorTiles (Integer amountColors, Integer amountPerTile)
    {
        List<Tile> colorTiles = new ArrayList<>();

        for(int i = 0; i<amountColors;i++)
        {
            ColorTile tile = new ColorTile();
            tile.setIdentifier("");

        }
    }
    public List<Tile> getRandomTiles(Integer amount){
        int randomNumber = 0;
        Random rnd = new Random();

        List<Tile> returnedTiles = new ArrayList<>();

        for(int i=0;i<amount;i++)
        {
            randomNumber = rnd.ints(1, 0, unusedTiles.size()).findFirst().getAsInt();
            returnedTiles.add(this.unusedTiles.get(randomNumber));
            usedTiles.add(this.unusedTiles.get(randomNumber));
            unusedTiles.remove(randomNumber);

        }

        return returnedTiles;

    };
}
