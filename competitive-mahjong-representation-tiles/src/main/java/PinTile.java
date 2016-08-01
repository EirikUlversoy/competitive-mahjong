
public class PinTile extends Tile {
    private String classIdentifier = "Pin";

    public PinTile() {
    }

    public PinTile(Integer tileNumber) {
        super(tileNumber);
        this.setPosition(new Position());
        this.setIdentifier("???");
    }
}
