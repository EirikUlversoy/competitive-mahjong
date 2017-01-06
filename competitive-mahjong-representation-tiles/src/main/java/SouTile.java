
public class SouTile extends Tile {
    public SouTile() {
        super();
        this.setSuit(new Suit("Sou"));

    }

    public SouTile(Integer tileNumber){
        super(tileNumber);
        this.setSuit(new Suit("Sou"));

    }

    public SouTile(Integer tileNumber, Integer tileId){
        super(tileNumber,tileId, new Suit("Sou"));
        //this.setSuit(new Suit("Sou"));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}