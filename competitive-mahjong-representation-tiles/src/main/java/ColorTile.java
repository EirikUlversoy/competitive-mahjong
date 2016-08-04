
public class ColorTile extends Tile {
    private String classIdentifier = "Color";
    private Position position = new Position();
    private String tileId = "hmm";
    private String identifier = "color";
    private Integer tileNumber = 1;
    private Suit suit = new Suit("Color");

    ColorTile(){

    }

    ColorTile(String identifier){
        super(1);

        this.identifier = identifier;
        this.setSuit(new Suit("Color"));
    }
    ColorTile(String identifier, Integer tileNumber){
        super(1,tileNumber);
        this.identifier = identifier;
        this.tileNumber = tileNumber;
        this.setSuit(new Suit("Color"));
    }
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
