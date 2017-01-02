import javafx.scene.image.Image;

public class Tile {

    private Integer tileId;
    private Integer tileNumber;
    private Suit suit = new Suit("No suit set yet");

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

    public Tile(Integer tileNumber, Integer tileId, Suit suit){
        this.tileNumber = tileNumber;
        this.identifier = "not set";
        this.position = new Position();
        this.tileId = tileId;
        this.suit = suit;
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
        returnString = returnString.concat(this.getClass().toString() +tileNumber.toString()+"-"+tileId.toString()).concat("@"+position.toString()).concat(tileNumber.toString());

        return returnString;
    }

    public void fixImage(){
        if(this.getClass() != ColorTile.class && this.getClass() != WindTile.class){
            if(this.getClass().equals(WanTile.class) || this.getSuit().getIdentifier().equals("Wan")){
                this.setImagePath("/images/"+this.getTileNumber()+"Wan.png");
                System.out.println(this.getSuit().getIdentifier());
            } else {
                this.setImagePath("/images/"+this.getTileNumber()+this.getSuit().getIdentifier()+".png");

            }
            //System.out.println(this.toString());

            System.out.println(this.imagePath);
            this.findImage();
            System.out.println(this.image);
        } else {
            this.setImagePath("/images/"+this.getIdentifier()+".png");
            this.findImage();
        }

    }
}
