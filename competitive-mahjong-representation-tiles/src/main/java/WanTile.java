
public class WanTile extends Tile {
    private String classIdentifier = "Wan";

    public WanTile() {
    }

    public WanTile(Integer tileNumber) {
        super(tileNumber);
        this.setPosition(new Position());
        this.setIdentifier("???");
    }
}
