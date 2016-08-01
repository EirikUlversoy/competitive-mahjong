import java.util.List;
import java.util.Optional;

public class Group {
    private Suit suit;
    private Tile FirstMember;
    private Tile SecondMember;
    private Tile ThirdMember;
    private Optional<Tile> FourthMember;
    Group(Tile firstMember, Tile secondMember, Tile thirdMember){
        this.FirstMember = firstMember;
        this.SecondMember = secondMember;
        this.ThirdMember = thirdMember;
    }

    public Group(List<Tile> tiles){
        this.FirstMember = tiles.get(0);
        this.SecondMember = tiles.get(1);
        this.ThirdMember = tiles.get(2);
        try{
            this.FourthMember = Optional.ofNullable(tiles.get(3));

        } catch (IndexOutOfBoundsException ex) {
            this.FourthMember = Optional.empty();
        }


    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
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

    public String toString(){
        String returnString = "";
        returnString = returnString.concat(FirstMember.toString()).concat(" + "+SecondMember.toString())
                .concat(" + " + ThirdMember.toString() );

        return returnString;
    }
}