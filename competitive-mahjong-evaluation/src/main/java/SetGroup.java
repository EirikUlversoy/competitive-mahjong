import java.util.List;
import java.util.Optional;

public class SetGroup extends Group {
    private Suit suit;
    private Integer tilenumber;

    private boolean KAN = false;

    private Tile firstTile;
    private Tile secondTile;
    private Tile thirdTile;
    private Optional<Tile> fourthTile;

    public SetGroup(List<Tile> tiles){
        super(tiles);
        this.firstTile = tiles.get(0);
        this.secondTile = tiles.get(1);
        this.thirdTile = tiles.get(2);
        try{
            this.fourthTile = Optional.ofNullable(tiles.get(3));

        } catch (IndexOutOfBoundsException ex) {
            this.fourthTile = Optional.empty();
        }

    }

    public String toString(){
        String returnString = "";
        returnString = returnString.concat(firstTile.toString()).concat(" + "+secondTile.toString())
                .concat(" + " + thirdTile.toString() + fourthTile.map(z -> z.toString()));

        return returnString;
    }
}
