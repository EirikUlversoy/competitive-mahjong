import java.util.List;

public class Pond {
    private List<Tile> tiles;
    private String direction;

    Pond(String direction){
        this.direction = direction;
    }
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void discardTile(Tile tile){
        this.tiles.add(tile);

    }
    public void clearList(){
        tiles.clear();
    }
    public String toString(){
        String returnString = "";
        for(Tile tile : tiles){
            returnString.concat(tile.toString());
            returnString.concat(";");

        }
        return returnString;
    }
}
