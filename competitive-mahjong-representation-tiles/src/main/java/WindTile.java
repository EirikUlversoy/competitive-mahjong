public class WindTile extends Tile{

    WindTile(){
        super();

    }

    WindTile(String wind, Integer windId){
        super(windId);
        this.setSuit(new Suit(wind));
    }
    WindTile(String wind, Integer windId,Integer tile_id){
        super(windId,tile_id);
        this.setSuit(new Suit(wind));
    }

}
