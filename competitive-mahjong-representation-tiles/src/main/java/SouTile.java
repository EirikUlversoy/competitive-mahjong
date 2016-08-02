
public class SouTile extends Tile {
    private String classIdentifier = "Sou";
    public SouTile() {
        super();
    }

    public SouTile(Integer tileNumber){
        super(tileNumber);
        this.setPosition(new Position());
        this.setIdentifier("???");
    }

    public SouTile(Integer tileNumber, Integer tileId){
        super(tileNumber,tileId);
    }

}