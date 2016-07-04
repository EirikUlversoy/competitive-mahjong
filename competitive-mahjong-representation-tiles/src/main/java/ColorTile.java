
public class ColorTile extends Tile {
    private Position position;
    private String tileId;
    private String identifier = "color";


    public Integer getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(Integer tileNumber) {
        this.tileNumber = tileNumber;
    }

    private Integer tileNumber;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
