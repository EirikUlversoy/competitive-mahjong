import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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

        List<Tile> ColorTiles = generateColorTiles(ruleset.getColors(),ruleset.getPerTile());
        List<Tile> WindTiles = generateWindTiles(ruleset.getWinds(),ruleset.getPerTile());

        Suit wanSuit = new Suit("Wan");
        Suit souSuit = new Suit("Sou");
        Suit pinSuit = new Suit("Pin");

        List<Tile> WanTiles = generateSuitTiles(ruleset.getSuits(),ruleset.getPerTile(), ruleset.getPerSuit(),
                wanSuit);
        List<Tile> SouTiles = generateSuitTiles(ruleset.getSuits(),ruleset.getPerTile(), ruleset.getPerSuit(),
                souSuit);
        List<Tile> PinTiles = generateSuitTiles(ruleset.getSuits(),ruleset.getPerTile(), ruleset.getPerSuit(),
                pinSuit);

        unusedTiles.addAll(ColorTiles);
        unusedTiles.addAll(WindTiles);
        unusedTiles.addAll(WanTiles);
        unusedTiles.addAll(SouTiles);
        unusedTiles.addAll(PinTiles);




    }


    public void initializeTiles(String ruletype){

    }

    public List<Tile> generateSuitTiles(Integer amountSuits, Integer amountPerTile, Integer amountPerSuit, Suit suit){
        List<Tile> tiles = new ArrayList<>();
        Integer tilenumber = 0;
            for (int x = 1; x <= amountPerSuit; x++) {
                tilenumber = x;

                for (int i = 0; i < amountPerTile; i++) {
                    Position position = new Position();
                    Tile tile = new Tile();
                    if(suit.getIdentifier() == "Wan"){
                        tile = new WanTile();
                    } else if(suit.getIdentifier() == "Sou") {
                        tile = new SouTile();
                    } else if(suit.getIdentifier() == "Pin"){
                        tile = new PinTile();
                    }
                    tile.setIdentifier(suit.getIdentifier() + "--" + tilenumber + "--" + i);
                    tile.setTileNumber(i);
                    tile.setPosition(position);
                    tile.setTileId(UUID.randomUUID().toString());
                    tiles.add(tile);
                }
            }


        if(suit.getIdentifier() == "Pin")
        {

        }

        if(suit.getIdentifier() == "Sou"){

        }
        return tiles;

    }
    public List<Tile> generateWindTiles (Integer amountWinds, Integer amountPerTile){

        List<Tile> windTiles = new ArrayList<>();

        for(int i = 0; i<amountWinds;i++)
        {
            for(int x = 0; x<amountPerTile; x++){
                WindTile tile = new WindTile();
                Position position = new Position();
                tile.setTileId(UUID.randomUUID().toString());
                switch (i){
                    case 0:
                        tile.setIdentifier("West");
                        tile.setTileNumber(x+1);
                        tile.setPosition(position);
                        windTiles.add(tile);
                    case 1:
                        tile.setIdentifier("East");
                        tile.setTileNumber(x+1);
                        tile.setPosition(position);
                        windTiles.add(tile);
                    case 2:
                        tile.setIdentifier("North");
                        tile.setTileNumber(x+1);
                        tile.setPosition(position);
                        windTiles.add(tile);
                    case 3:
                        tile.setIdentifier("South");
                        tile.setTileNumber(x+1);
                        tile.setPosition(position);
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
                Position position = new Position();
                tile.setPosition(position);
                tile.setTileNumber(x+1);
                switch (i){
                    case 0:
                        tile.setIdentifier("Chun");
                        colorTiles.add(tile);
                    case 1:
                        tile.setIdentifier("Haku");
                        colorTiles.add(tile);
                    case 2:
                        tile.setIdentifier("Hatsu");
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
