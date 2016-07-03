public class Tile {

    private String tileId;
    private Position position;
    private Integer tileNumber;
    private String identifier;

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

    public String getTileId() {
        return tileId;
    }

    public void setTileId(String tileId) {
        this.tileId = tileId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String toString(){
        String returnString = "";
        returnString.concat(tileId).concat("@"+position.toString());

        return returnString;
    }
}
