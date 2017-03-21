import javafx.scene.image.Image;

import java.net.URL;
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
        String basePath = "file:competitive-mahjong-representation/src/main/resources/images/";
            for (int x = 1; x <= amountPerSuit; x++) {
                tilenumber = x;

                for (int tile_id = 1; tile_id <= amountPerTile; tile_id++) {
                    Position position = new Position();
                    Tile tile = new Tile();
                    if(suit.getIdentifier().equals("Wan")){
                        tile = new WanTile();
                    } else if(suit.getIdentifier().equals("Sou")) {
                        tile = new SouTile();
                    } else if(suit.getIdentifier().equals("Pin")){
                        tile = new PinTile();
                    }
                    tile.setSuit(suit);
                    tile.setIdentifier(suit.getIdentifier() + tilenumber + tile_id);
                    tile.setTileNumber(tilenumber);
                    tile.setTileId(tile_id);
                    URL url = TileSet.class.getClassLoader().getResource(x+suit.getIdentifier()+".png");
                    //String filesPathAndName = url.getPath();
                    //Image image = new Image("file:competitive-mahjong-representation/src/main/resources/images/"+x+suit.getIdentifier()+".png");
                    tile.setImagePath(basePath.concat(x+suit.getIdentifier()+".png"));
                    //tile.setImage(image);
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
        String windPath = "file:competitive-mahjong-representation/src/main/resources/images/";
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
            for(int tile_id = 0; tile_id<amountPerTile; tile_id++){
                WindTile tile = new WindTile();
                Position position = new Position();
                tile.setTileId(tile_id);

                //Image image = new Image(windPath);
                tile.setImagePath(windPath);
                switch (i){
                    case 0:
                        tile = new WindTile("West",tile_id);
                        tile.setIdentifier("West");
                        //tile.setImage(image);
                        tile.setTileNumber(tile_id+1);
                        windTiles.add(tile);
                        break;
                    case 1:
                        tile = new WindTile("East",tile_id);
                        tile.setIdentifier("East");
                        //tile.setImage(image);
                        tile.setTileNumber(tile_id+1);
                        windTiles.add(tile);
                        break;
                    case 2:
                        tile = new WindTile("North",tile_id);
                        tile.setIdentifier("North");
                        //tile.setImage(image);
                        tile.setTileNumber(tile_id+1);
                        windTiles.add(tile);
                        break;
                    case 3:
                        tile = new WindTile("South",tile_id);
                        tile.setIdentifier("South");
                        //tile.setImage(image);
                        tile.setTileNumber(tile_id+1);
                        windTiles.add(tile);
                        break;
                }
            }
        }
        return windTiles;
    }
    public List<Tile> generateColorTiles (Integer amountColors, Integer amountPerTile)
    {
        List<Tile> colorTiles = new ArrayList<>();
        String colorPath = "file:competitive-mahjong-representation/src/main/resources/images/";
        for(int i = 0; i<amountColors;i++)
        {
            if(i == 0){
                colorPath = colorPath.concat("Red.png");
            } else if(i == 1) {
                colorPath = colorPath.concat("White.png");
            } else if(i == 2) {
                colorPath = colorPath.concat("Green.png");
            }
            for(int tile_id = 0; tile_id<amountPerTile; tile_id++){
                ColorTile tile = new ColorTile();
                Position position = new Position();
                tile.setTileNumber(tile_id+1);
                //Image image = new Image(colorPath);
                tile.setImagePath(colorPath);

                switch (i){
                    case 0:
                        tile = new ColorTile("Red",0,tile_id);
                        tile.setIdentifier("Red");
                        //tile.setImage(image);
                        colorTiles.add(tile);
                        break;
                    case 1:
                        tile = new ColorTile("White",1,tile_id);
                        tile.setIdentifier("White");
                        //tile.setImage(image);
                        colorTiles.add(tile);
                        break;
                    case 2:
                        tile = new ColorTile("Green",2,tile_id);
                        tile.setIdentifier("Green");
                        //tile.setImage(image);
                        colorTiles.add(tile);
                        break;
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
        System.out.println(returnedTiles.stream().distinct().count());
        System.out.println("Drew and removed " + amount + " tiles");
        System.out.println("There are " + unusedTiles.size() + "tiles left in the tileset");
        return returnedTiles;

    };
}
