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
                .forEach(z->strings.add(z));

        for(String string : strings){
            tiles.clear();
            string.chars()
                    .mapToObj(i -> (char)i)
                    .forEach(this::analyzeCharacters);
        }
        return tileLists;
    }
    public void clearBooleans(){
        WAN = false;
        SOU = false;
        PIN = false;
        COLOR = false;
        WIND = false;
    }
    public void dealWithDuplicates(){
        Hand hand = new Hand(1);
        HandEvaluator handEvaluator = new HandEvaluator(hand);
        handEvaluator.
        List<Tile> newTiles = this.tiles.stream()
                .sorted()
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
                if(this.SOU){
                    this.tiles.add(new SouTile((int)i));
                } else if (this.WAN) {
                    this.tiles.add(new WanTile((int)i));
                } else if (this.PIN) {
                    this.tiles.add(new PinTile((int)i));
                } else if (this.COLOR) {
                    this.tiles.add(new ColorTile(colorIntToString.get((int)i)));
                } else if (this.WIND) {
                    this.tiles.add(new WindTile(windIntToString.get((int)i)));
    }
            }
        }
    }
}
