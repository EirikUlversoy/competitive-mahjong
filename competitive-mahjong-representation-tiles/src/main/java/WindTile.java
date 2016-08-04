public class WindTile extends Tile{

    WindTile(){
        super();

    }

    WindTile(String wind){
        super(1);
        this.setSuit(new Suit(wind));
    }
    WindTile(String wind, Integer tile_id){
        super(1,tile_id);
        this.setSuit(new Suit(wind));
    }

}
