
public class ColorTile extends Tile {
    private Position position;
    private String tileId;
    private String identifier = "color";
    private Integer tileNumber;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
