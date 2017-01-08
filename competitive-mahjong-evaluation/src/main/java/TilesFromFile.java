import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Reading the file is not really used, using analyzeString directly in the tests
 * is better.
 */
public class TilesFromFile {
    List<Tile> tiles = new ArrayList<>();
    boolean WAN, SOU, PIN, COLOR, WIND = false;
    private Map<Integer,String> colorIntToString = new HashMap<>();
    private Map<Integer,String> windIntToString = new HashMap<>();

    TilesFromFile(){
        colorIntToString.put(1,"Green");
        colorIntToString.put(2,"Red");
        colorIntToString.put(3,"White");

        windIntToString.put(1,"East");
        windIntToString.put(2,"South");
        windIntToString.put(3,"West");
        windIntToString.put(4,"North");
    }
    public List<List<Tile>> getFromFile() throws IOException{



        List<String> strings= new ArrayList<>();
        List<List<Tile>> tileLists = new ArrayList<>();
        //System.out.println(System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\samplehands");
        Files.lines(Paths.get(System.getProperty("user.dir")+"\\src\\test\\resources\\samplehands"))
                .filter(z -> !z.contains("#"))
                .forEach(z -> strings.add(z));

        for(String string : strings){
            tileLists.add(analyzeString(string));
        }

       //System.out.println(tileLists.toString());


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
        Map<String, Integer> mapthing = new HashMap<>();

        for(Tile tile: tiles){
            String key = tile.getSuit().getIdentifier()+tile.getTileNumber();
            if(mapthing.containsKey(key)){
                System.out.println(tile.getTileId());
                tile.setTileId(mapthing.get(key) + 1);
                tile.setIdentifier(tile.getSuit().getIdentifier()+tile.getTileNumber()+tile.getTileId());
                System.out.println(tile.getTileId());
                mapthing.replace(key,tile.getTileId());
            } else {
                mapthing.put(key,1);
            }

            }
        System.out.println(tiles);
        return tiles;
    }
    public List<Tile> analyzeString(String string){
        this.tiles.clear();
        clearBooleans();
        string.chars()
                .mapToObj(i -> (char)i)
                .forEach(this::analyzeCharacters);
        System.out.println("before dupl");
        System.out.println(this.tiles);
        this.tiles = dealWithDuplicates(this.tiles);
        System.out.println("dupl");
        System.out.println(this.tiles);
        List<Tile> tileOutput = new ArrayList<>();
        tileOutput.addAll(this.tiles);
        return tileOutput;
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
                    this.tiles.add(new SouTile(numericValue,1));
                } else if (this.WAN) {
                    this.tiles.add(new WanTile(numericValue,1));
                } else if (this.PIN) {
                    this.tiles.add(new PinTile(numericValue,1));
                } else if (this.COLOR) {
                    this.tiles.add(new ColorTile(colorIntToString.get(numericValue),numericValue,1));
                } else if (this.WIND) {
                    this.tiles.add(new WindTile(windIntToString.get(numericValue),numericValue,1));
    }
            }
        }
    }
}
