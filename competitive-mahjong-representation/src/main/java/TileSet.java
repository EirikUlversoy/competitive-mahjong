import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TileSet {
    private List<Tile> unusedTiles = new ArrayList<Tile>();
    private List<Tile> usedTiles = new ArrayList<Tile>();
    private Ruleset ruleset;
    private enum Colors {
        HAKU, HATSU, CHUN
    };
    public Integer getAmount(){
        return unusedTiles.size();
    }

    public void initializeTiles(){
        this.ruleset = new Ruleset();

        for(int i = 0;i<this.ruleset.getColors();i++)
        {
            List<Tile> ColorTiles = generateColorTiles(this.ruleset.getColors(),this.ruleset.getPerTile());
            List<Tile> WindTiles = generateWindTiles(this.ruleset.getWinds(),this.ruleset.getPerTile());


        }
    }
    public void initializeTiles(String ruletype){

    }
    public List<Tile> generateWindTiles (Integer amountWinds, Integer amountPerTile){

        List<Tile> windTiles = new ArrayList<>();

        for(int i = 0; i<amountWinds;i++)
        {
            for(int x = 0; x<amountPerTile; x++){
                WindTile tile = new WindTile();
                switch (i){
                    case 0:
                        tile.setIdentifier("West");
                        tile.setTileNumber(x+1);
                        windTiles.add(tile);
                    case 1:
                        tile.setIdentifier("East");
                        tile.setTileNumber(x+1);
                        windTiles.add(tile);
                    case 2:
                        tile.setIdentifier("North");
                        tile.setTileNumber(x+1);
                        windTiles.add(tile);
                    case 3:
                        tile.setIdentifier("South");
                        tile.setTileNumber(x+1);
                        windTiles.add(tile);
                }
            }
        }
        return windTiles;
    }
    public List<Tile> generateColorTiles (Integer amountColors, Integer amountPerTile)
    {
        List<Tile> colorTiles = new ArrayList<>();

        for(int i = 0; i<amountColors;i++)
        {
            for(int x = 0; x<amountPerTile; x++){
                ColorTile tile = new ColorTile();
                switch (i){
                    case 0:
                        tile.setIdentifier("Chun");
                        tile.setTileNumber(x+1);
                        colorTiles.add(tile);
                    case 1:
                        tile.setIdentifier("Haku");
                        tile.setTileNumber(x+1);
                        colorTiles.add(tile);
                    case 2:
                        tile.setIdentifier("Hatsu");
                        tile.setTileNumber(x+1);
                        colorTiles.add(tile);
                }
            }
        }
        return colorTiles;
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
