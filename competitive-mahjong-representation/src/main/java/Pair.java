
public class Pair {
    private Tile firstMember;
    private Tile secondMember;
    private Suit suit;

    public Tile getFirstMember() {
        return firstMember;
    }

    public void setFirstMember(Tile firstMember) {
        this.firstMember = firstMember;
    }

    public Tile getSecondMember() {
        return secondMember;
    }

    public void setSecondMember(Tile secondMember) {
        this.secondMember = secondMember;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public Pair(Tile firstMember, Tile secondMember){
        this.firstMember = firstMember;
        this.secondMember = secondMember;
        this.suit = firstMember.getSuit();

    }

    @Override
    public String toString() {
        return "Suit:" + suit.getIdentifier()
                + " First member:" +
                firstMember.toString() +
                " Second member:" + secondMember.toString();
    }
}
