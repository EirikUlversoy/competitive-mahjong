
public class PinTile extends Tile {
    private String classIdentifier = "Pin";

    public PinTile() {
        super();
        this.setSuit(new Suit("Pin"));

    }

    public PinTile(Integer tileNumber, Integer tileId){
        super(tileNumber,tileId, new Suit("Pin"));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
