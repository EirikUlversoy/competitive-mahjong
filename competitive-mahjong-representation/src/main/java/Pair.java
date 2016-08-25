
public class Pair {
    private Tile firstMember;
    private Tile secondMember;
    private Suit suit;

    public Tile getFirstMember() {
        return firstMember;
    }

    public Tile getSecondMember() {
        return secondMember;
    }
    public Suit getSuit(){
        return firstMember.getSuit();
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
