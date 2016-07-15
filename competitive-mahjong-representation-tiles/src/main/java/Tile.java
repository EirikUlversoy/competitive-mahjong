import javafx.scene.image.Image;

public class Tile {

    private String tileId;
    private Position position;
    private Integer tileNumber;
    private String identifier;
    private Image image;
    private String imagePath;

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
        returnString = returnString.concat(identifier).concat("@"+position.toString()).concat(tileNumber.toString());

        return returnString;
    }
}
