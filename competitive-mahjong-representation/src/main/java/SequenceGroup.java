import java.util.*;

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

    public boolean isStrictMember(Tile tile){
     return false;
    }
    public Map<Integer, Integer> intInOrder(Object b){
        Map<Integer, Integer> intsInOrder = new HashMap<>();

        SequenceGroup sequenceGroup = (SequenceGroup)b;
        intsInOrder.put(sequenceGroup.getFirstMember().getTileNumber(),sequenceGroup.getFirstMember().getTileId());
        intsInOrder.put(sequenceGroup.getSecondMember().getTileNumber(),sequenceGroup.getSecondMember().getTileId());
        intsInOrder.put(sequenceGroup.getThirdMember().getTileNumber(),sequenceGroup.getThirdMember().getTileId());

        return intsInOrder;


    }

    @Override
    public int hashCode(){
        return 5;
    }
    @Override
    public boolean equals(Object b) {
        Map<Integer, Integer> oldInts = new HashMap<>();
        oldInts.put(this.getFirstMember().getTileNumber(), this.getFirstMember().getTileId());
        oldInts.put(this.getSecondMember().getTileNumber(), this.getSecondMember().getTileId());
        oldInts.put(this.getThirdMember().getTileNumber(), this.getThirdMember().getTileId());

        Map<Integer, Integer> newInts = this.intInOrder(b);
        Boolean equal = oldInts.keySet().stream().allMatch(z -> {
            if (newInts.containsKey(z)) {
                if (newInts.get(z) == oldInts.get(z)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        });
        return equal;
    }

    public boolean isSuitlessMember(Tile tile){
        String targetString = tile.getTileNumber()+tile.getTileId().toString();
        String firstMemberString = this.getFirstMember().getTileNumber() + tile.getTileId().toString();
        String secondMemberString = this.getSecondMember().getTileNumber() + tile.getTileId().toString();
        String thirdMemberString = this.getThirdMember().getTileNumber() + tile.getTileId().toString();
        return targetString.equals(firstMemberString) || targetString.equals(secondMemberString) || targetString.equals(thirdMemberString);

    }
    public boolean isMember(Tile tile){
        String targetString = tile.getSuit().getIdentifier()+tile.getTileNumber()+tile.getTileId();
        String firstMemberString = this.getFirstMember().getSuit().getIdentifier() + this.getFirstMember().getTileNumber() + tile.getTileId();
        String secondMemberString = this.getSecondMember().getSuit().getIdentifier() + this.getSecondMember().getTileNumber() + tile.getTileId();
        String thirdMemberString = this.getThirdMember().getSuit().getIdentifier() + this.getThirdMember().getTileNumber() + tile.getTileId();
        return targetString.equals(firstMemberString) || targetString.equals(secondMemberString) || targetString.equals(thirdMemberString);

    }
    public String toString(){
        String returnString = "";
        returnString = returnString.concat(FirstMember.toString()).concat(" + "+SecondMember.toString())
                .concat(" + " + ThirdMember.toString() );

        return returnString;
    }
}
