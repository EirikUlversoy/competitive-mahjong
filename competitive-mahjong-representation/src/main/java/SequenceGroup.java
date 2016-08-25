import java.util.NoSuchElementException;
import java.util.Optional;

public class SequenceGroup {
    private Optional<Integer> realMember;
    private Tile FirstMember;
    private Tile SecondMember;
    private Tile ThirdMember;
    private Suit suit;

    SequenceGroup(Tile firstMember, Tile secondMember, Tile thirdMember){
        this.FirstMember = firstMember;
        this.SecondMember = secondMember;
        this.ThirdMember = thirdMember;
        this.suit = firstMember.getSuit();
    }
    SequenceGroup(Tile firstMember, Tile secondMember, Tile thirdMember, Integer realMember){
        this.FirstMember = firstMember;
        this.SecondMember = secondMember;
        this.ThirdMember = thirdMember;
        this.suit = firstMember.getSuit();
    }


    public Optional<Tile> getRealMember(){
        try{

        if(getFirstMember().getTileNumber() == realMember.get() ){
            return Optional.ofNullable(getFirstMember());
        }
        if(getSecondMember().getTileNumber() == realMember.get() ){
            return Optional.ofNullable(getSecondMember());
        }
        if(getThirdMember().getTileNumber() == realMember.get() ){
            return Optional.ofNullable(getThirdMember());
        }
        return Optional.empty();
        } catch (NoSuchElementException NSEE) {
            return Optional.empty();
        }
    }
    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Tile getFirstMember() {
        return this.FirstMember;
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
