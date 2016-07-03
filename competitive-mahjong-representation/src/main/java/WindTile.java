public class WindTile extends Tile{
    private String identifier = "wind";
    private String tileId;
    private Integer tileNumber;
    private Position position;

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
