import javafx.scene.image.Image;

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
            for (int x = 1; x < amountPerSuit; x++) {
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
                    Image image = new Image("file:/resources/images/"+x+suit.getIdentifier() + ".png");
                    tile.setImage(image);
                    tiles.add(tile);
                }
            }


        return tiles;

    }

    public List<Tile> getUnusedTiles() {
        return unusedTiles;
    }

    public List<Tile> getUsedTiles() {
        return usedTiles;
    }

    public Ruleset getRuleset() {
        return ruleset;
    }

    public List<Tile> generateWindTiles (Integer amountWinds, Integer amountPerTile){

        List<Tile> windTiles = new ArrayList<>();
        String windPath = "file:resources/images/";
        for(int i = 0; i<amountWinds;i++)
        {
            if(i == 0){
                windPath = windPath.concat("West.png");
            } else if(i == 1) {
                windPath = windPath.concat("East.png");
            } else if(i == 2) {

                windPath = windPath.concat("North.png");
            } else if(i == 3) {
                windPath = windPath.concat("South.png");
            }
            for(int x = 0; x<amountPerTile; x++){
                WindTile tile = new WindTile();
                Position position = new Position();
                tile.setTileId(UUID.randomUUID().toString());

                Image image = new Image(windPath);
                switch (i){
                    case 0:
                        tile.setIdentifier("West");
                        tile.setImage(image);
                        tile.setTileNumber(x+1);
                        tile.setPosition(position);
                        windTiles.add(tile);
                    case 1:
                        tile.setIdentifier("East");
                        tile.setImage(image);
                        tile.setTileNumber(x+1);
                        tile.setPosition(position);
                        windTiles.add(tile);
                    case 2:
                        tile.setIdentifier("North");
                        tile.setImage(image);
                        tile.setTileNumber(x+1);
                        tile.setPosition(position);
                        windTiles.add(tile);
                    case 3:
                        tile.setIdentifier("South");
                        tile.setImage(image);
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
        String colorPath = "file:resources/images/";
        for(int i = 0; i<amountColors;i++)
        {
            if(i == 0){
                colorPath = colorPath.concat("Red.png");
            } else if(i == 1) {
                colorPath = colorPath.concat("White.png");
            } else if(i == 2) {
                colorPath = colorPath.concat("Green.png");
            }
            for(int x = 0; x<amountPerTile; x++){
                ColorTile tile = new ColorTile();
                Position position = new Position();
                tile.setPosition(position);
                tile.setTileNumber(x+1);
                System.out.println(colorPath);
                Image image = new Image(colorPath);
                switch (i){
                    case 0:
                        tile.setIdentifier("Chun");
                        tile.setImage(image);
                        colorTiles.add(tile);
                    case 1:
                        tile.setIdentifier("Haku");
                        tile.setImage(image);
                        colorTiles.add(tile);
                    case 2:
                        tile.setIdentifier("Hatsu");
                        tile.setImage(image);
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
        System.out.println("Drew and removed " + amount + " tiles");
        System.out.println("There are " + unusedTiles.size() + "tiles left in the tileset");
        return returnedTiles;

    };
}
