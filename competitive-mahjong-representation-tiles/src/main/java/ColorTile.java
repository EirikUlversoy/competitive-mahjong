
public class ColorTile extends Tile {
    private String classIdentifier = "Color";
    private Position position = new Position();
    private String tileId = "hmm";
    private String identifier = "color";
    private Integer tileNumber = 0;


    public Integer getTileNumber() {
        return tileNumber;
    }

    public void setTileNumber(Integer tileNumber) {
        this.tileNumber = tileNumber;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
