public class Tile {

    private String tileId;
    private Position position;


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
