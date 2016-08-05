import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TilesFromFile {
    List<Tile> tiles = new ArrayList<>();
    boolean WAN, SOU, PIN, COLOR, WIND = false;
    private Map<Integer,String> colorIntToString = new HashMap<>();
    private Map<Integer,String> windIntToString = new HashMap<>();

    public List<List<Tile>> getFromFile() throws IOException{

        colorIntToString.put(1,"Green");
        colorIntToString.put(2,"Red");
        colorIntToString.put(3,"White");

        windIntToString.put(1,"East");
        windIntToString.put(2,"South");
        windIntToString.put(3,"West");
        windIntToString.put(4,"North");

        List<String> strings= new ArrayList<>();
        List<List<Tile>> tileLists = new ArrayList<>();
        System.out.println(System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\samplehands");
        Files.lines(Paths.get(System.getProperty("user.dir")+"\\src\\test\\resources\\samplehands"))
                .filter(z -> !z.contains("#"))
                .forEach(z->strings.add(z));

        for(String string : strings){
            tiles.clear();
            string.chars()
                    .mapToObj(i -> (char)i)
                    .forEach(this::analyzeCharacters);
            tiles = dealWithDuplicates(tiles);
            tileLists.add(tiles);
        }

        System.out.println(tileLists.toString());


        return tileLists;
    }
    public void clearBooleans(){
        WAN = false;
        SOU = false;
        PIN = false;
        COLOR = false;
        WIND = false;
    }
    public List<Tile> dealWithDuplicates(List<Tile> tiles){
        Hand hand = new Hand(1);
        HandEvaluator handEvaluator = new HandEvaluator(hand);
        List<Tile> newTiles = new ArrayList<>();
        List<Map<Integer, List<Tile>>> maps = new ArrayList<>();

        List<Tile> Wan = handEvaluator.filterWan();
        List<Tile> Sou = handEvaluator.filterSou();
        List<Tile> Pin = handEvaluator.filterPin();
        List<Tile> Color = handEvaluator.filterSuit(ColorTile.class);
        List<Tile> Wind = handEvaluator.filterSuit(WindTile.class);

        maps.add(handEvaluator.findTileCount(Wan));
        maps.add(handEvaluator.findTileCount(Sou));
        maps.add(handEvaluator.findTileCount(Pin));
        maps.add(handEvaluator.findTileCount(Color));
        maps.add(handEvaluator.findTileCount(Wind));

        //final Map<Integer, List<Tile>> integerToTiles = handEvaluator.findTileCount(tiles);
        System.out.println("Before duplicate fix");
        tiles.stream().map(Tile::toString).forEach(System.out::println);
        for (Map<Integer, List<Tile>> tileMap : maps){
            tileMap.keySet().stream()
                    .map(z -> tileMap.get(z))
                    .forEach(z -> {
                        z.stream()
                                .peek(x ->
                                        x.setTileId(z.indexOf(x)+1))
                                .forEach(x -> newTiles.add(x));
                    });
        }

        System.out.println("After duplicate fix.");
        newTiles.stream().map(Tile::toString).forEach(System.out::println);
        return newTiles;


    }
    public void analyzeCharacters(char i){
        switch(i){
            case 'S' : {
                clearBooleans();
                this.SOU = true;
            }
                break;
            case 'W' : {
                clearBooleans();
                this.WAN = true;
            }
                break;
            case 'P' : {
                clearBooleans();
                this.PIN = true;
            }
                break;
            case 'C' : {
                clearBooleans();
                this.COLOR = true;
            }
                break;
            case 'V' :{
                clearBooleans();
                this.WIND = true;
            }
                break;
            default : {
                Integer numericValue = Character.getNumericValue(i);
                if(this.SOU){
                    this.tiles.add(new SouTile(numericValue));
                } else if (this.WAN) {
                    this.tiles.add(new WanTile(numericValue));
                } else if (this.PIN) {
                    this.tiles.add(new PinTile(numericValue));
                } else if (this.COLOR) {
                    this.tiles.add(new ColorTile(colorIntToString.get(numericValue),numericValue));
                } else if (this.WIND) {
                    this.tiles.add(new WindTile(windIntToString.get(numericValue),numericValue));
    }
            }
        }
    }
}
