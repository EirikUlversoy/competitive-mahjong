
public class ColorTile extends Tile {

    ColorTile(){
        super();

    }

    ColorTile(String color){
        super(1);
        this.setSuit(new Suit(color));
    }
    ColorTile(String color, Integer tile_id){
        super(1,tile_id);
        this.setSuit(new Suit(color));
    }
}
