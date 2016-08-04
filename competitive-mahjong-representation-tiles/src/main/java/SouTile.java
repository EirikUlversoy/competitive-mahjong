
public class SouTile extends Tile {
    private String classIdentifier = "Sou";
    public SouTile() {
        super();
        this.setSuit(new Suit("Sou"));

    }

    public SouTile(Integer tileNumber){
        super(tileNumber);
        this.setPosition(new Position());
        this.setIdentifier("???");
        this.setSuit(new Suit("Sou"));

    }

    public SouTile(Integer tileNumber, Integer tileId){
        super(tileNumber,tileId);
        this.setSuit(new Suit("Sou"));
    }

}