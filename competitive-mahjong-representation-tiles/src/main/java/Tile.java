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
        //this.identifier = "not set";
        this.tileId = 0;
    }

    public Tile(Integer tileNumber, Integer tileId){
        this.tileNumber = tileNumber;
        //this.identifier = "not set";
        this.tileId = tileId;
    }

    public Tile(Integer tileNumber, Integer tileId, Suit suit){
        this.tileNumber = tileNumber;
        this.identifier = suit.getIdentifier()+tileNumber+tileId;
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

    public String toString(){
        return this.getIdentifier();
    }


    @Override
    public boolean equals(Object object){
        Tile otherTile = (Tile)object;
        return this.getIdentifier().equals(otherTile.getIdentifier());
    }

    @Override
    public int hashCode(){
        return 4;
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
            this.setImagePath("/images/"+this.getSuit().getIdentifier()+".png");
           // this.setImagePath("/images/"+this.getTileNumber()+this.getSuit().getIdentifier());
            this.findImage();
        }

    }
}
