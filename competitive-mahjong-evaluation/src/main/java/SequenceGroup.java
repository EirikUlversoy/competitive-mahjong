import java.util.NoSuchElementException;
import java.util.Optional;

public class SequenceGroup extends Group {
    private Optional<Integer> realMember;
    SequenceGroup(Tile firstMember, Tile secondMember, Tile thirdMember){
        super(firstMember,secondMember,thirdMember);


    }
    SequenceGroup(Tile firstMember, Tile secondMember, Tile thirdMember, Integer realMember){
        super(firstMember,secondMember,thirdMember);
        this.realMember = Optional.ofNullable(realMember);

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
