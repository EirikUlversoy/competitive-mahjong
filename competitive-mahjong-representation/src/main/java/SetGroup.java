import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SetGroup extends Group {


    protected boolean KAN = false;

    public SetGroup(List<Tile> tiles){
        super(tiles);
        this.setSuit(tiles.get(0).getSuit());
        try {
            if(FourthMember.isPresent()){
                this.KAN = true;
            }
        } catch (NoSuchElementException nos) {
            this.KAN = false;
        }
    }

    public boolean isKAN(){
        return KAN;
    }

    public String toString(){
        String returnString = "";
        returnString = returnString.concat(FirstMember.toString()).concat(" + "+SecondMember.toString())
                .concat(" + " + ThirdMember.toString() + FourthMember.map(z -> z.toString()));

        return returnString;
    }
}
