
public class WanTile extends Tile {
    private String classIdentifier = "Wan";

    public WanTile() {
        super();
        this.setSuit(new Suit("Wan"));

    }

    public WanTile(Integer tileNumber, Integer tile_id) {
        super(tileNumber,tile_id, new Suit("Wan"));

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
