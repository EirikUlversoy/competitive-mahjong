import java.util.HashMap;
import java.util.Map;

public class ColorTile extends Tile {

    ColorTile(){
        super();
        this.setSuit(new Suit("A color"));
    }

    ColorTile(String color, Integer colorId){
        super(colorId);
        this.setSuit(new Suit(color));
    }
    ColorTile(String color, Integer colorId, Integer tile_id){
        super(colorId,tile_id);
        this.setSuit(new Suit(color));
    }
}
