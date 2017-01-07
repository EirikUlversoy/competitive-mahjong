public class WindTile extends Tile{

    public WindTile(){
        super();

    }

    public WindTile(String wind, Integer windId){
        super(windId);
        this.setSuit(new Suit(wind));
        this.setIdentifier(this.getSuit().getIdentifier()+getTileNumber()+getTileId());

    }
    public WindTile(String wind, Integer windId,Integer tile_id){
        super(windId,tile_id, new Suit(wind));
        //this.setSuit(new Suit(wind));
    }

}
