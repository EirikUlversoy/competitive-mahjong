import javafx.scene.image.Image;

public class Tile {

    private Integer tileId;
    private Integer tileNumber;
    private Suit suit;

    private Position position;
    private String identifier;

    private Image image;
    private String imagePath;



    public Tile (){

    }

    public Tile(Integer tileNumber){
        this.tileNumber = tileNumber;
        this.identifier = "not set";
        this.position = new Position();
        this.tileId = 0;
    }

    public Tile(Integer tileNumber, Integer tileId){
        this.tileNumber = tileNumber;
        this.identifier = "not set";
        this.position = new Position();
        this.tileId = tileId;
    }


    public Suit getSuit() {
        return suit;
    }
    public void setSuit(Suit suit) {
        this.suit = suit;
    }
    public void findImage(){
        this.image = new Image(imagePath);
    }
    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
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
    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public Integer getTileId() {
        return tileId;
    }
    public void setTileId(Integer tileId) {
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
        returnString = returnString.concat(tileNumber.toString()+"-"+tileId.toString()).concat("@"+position.toString()).concat(tileNumber.toString());

        return returnString;
    }
}
