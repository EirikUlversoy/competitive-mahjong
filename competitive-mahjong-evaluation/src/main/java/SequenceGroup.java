
public class SequenceGroup {
    private Suit suit;
    private Tile FirstMember;
    private Tile SecondMember;
    private Tile ThirdMember;

    SequenceGroup(Tile firstMember, Tile secondMember, Tile thirdMember){
        this.FirstMember = firstMember;
        this.SecondMember = secondMember;
        this.ThirdMember = thirdMember;
    }
}
