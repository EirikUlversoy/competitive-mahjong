
public class WanTile extends Tile {
    private String classIdentifier = "Wan";

    public WanTile() {
        this.setSuit(new Suit("Wan"));

    }

    public WanTile(Integer tileNumber) {
        super(tileNumber);
        this.setPosition(new Position());
        this.setIdentifier("???");

        this.setSuit(new Suit("Wan"));

    }
}
