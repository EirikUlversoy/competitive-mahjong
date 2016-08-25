import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class SetGroup{


    protected boolean KAN = false;
    private Tile FirstMember;
    private Tile SecondMember;
    private Tile ThirdMember;
    private Optional<Tile> FourthMember = Optional.empty();
    private boolean closed = true;
    private Suit suit;

    public Suit getSuit(){
        return suit;
    }

    public boolean isClosed(){
        return closed;
    }
    public Tile getFirstMember() {
        return FirstMember;
    }

    public void setFirstMember(Tile firstMember) {
        FirstMember = firstMember;
    }

    public Tile getSecondMember() {
        return SecondMember;
    }

    public void setSecondMember(Tile secondMember) {
        SecondMember = secondMember;
    }

    public Tile getThirdMember() {
        return ThirdMember;
    }

    public void setThirdMember(Tile thirdMember) {
        ThirdMember = thirdMember;
    }

    public void setFourthMember(Optional<Tile> fourthMember) {
        FourthMember = fourthMember;
    }

    public SetGroup(List<Tile> tiles){
        this.FirstMember = tiles.get(0);
        this.SecondMember = tiles.get(1);
        this.ThirdMember = tiles.get(2);
        this.suit = FirstMember.getSuit();
        if(tiles.size() == 4){
            this.FourthMember = Optional.ofNullable(tiles.get(3));
        }
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

    public Tile getFourthMember(){
        return FourthMember.orElse(new Tile());
    }
    public String toString(){
        String returnString = "";
        returnString = returnString.concat(FirstMember.toString()).concat(" + "+SecondMember.toString())
                .concat(" + " + ThirdMember.toString() + FourthMember.map(z -> z.toString()));

        return returnString;
    }
}
