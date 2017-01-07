import java.util.HashMap;
import java.util.Map;

public class ColorTile extends Tile {

    public ColorTile(){
        super();
        this.setSuit(new Suit("A color"));
    }

    public ColorTile(String color, Integer colorId){
        super(colorId,colorId,new Suit(color));
        //this.setSuit(new Suit(color));
    }
    public ColorTile(String color, Integer colorId, Integer tile_id){
        super(colorId,tile_id, new Suit(color));
        //this.setSuit(new Suit(color));
    }
}
