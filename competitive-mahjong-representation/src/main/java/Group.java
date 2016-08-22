import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Group {
    protected Suit suit;
    protected Tile FirstMember;
    protected Tile SecondMember;
    protected Tile ThirdMember;
    protected Optional<Tile> FourthMember;
    protected boolean closed;

    public Group(Tile firstMember, Tile secondMember, Tile thirdMember){
        this.FirstMember = firstMember;
        this.SecondMember = secondMember;
        this.ThirdMember = thirdMember;
        this.suit = secondMember.getSuit();
        this.closed = true;
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
        this.suit = SecondMember.getSuit();

        this.closed = true;
    }

    public SetGroup getSetGroup (){
        List<Tile> tiles = new ArrayList<>();
        tiles.add(FirstMember);
        tiles.add(SecondMember);
        tiles.add(ThirdMember);
        tiles.add(FourthMember.orElse(new Tile()));
        return new SetGroup(tiles);
    }
    public void setStatus(boolean status){
        this.closed = status;
    }

    public boolean getStatus(){
        return this.closed;
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