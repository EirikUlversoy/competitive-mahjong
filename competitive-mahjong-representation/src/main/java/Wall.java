import java.util.ArrayList;
import java.util.List;

public class Wall {
    private String direction;
    private List<Tile> tiles = new ArrayList<Tile>();
    private Integer slotAmount = 20;

    public void generateWall(TileSet tileSet){
        System.out.println("there are " + tileSet.getAmount() + "tiles left in the tileset");
        this.tiles = tileSet.getRandomTiles(this.slotAmount);
        System.out.println("there are " + tileSet.getAmount() + "tiles left in the tileset");
    }

    public String toString(){
        String returnString = "";
        for (Tile tile : tiles)
        {
            returnString.concat(tile.toString());

        };
        return returnString;

    }

}
