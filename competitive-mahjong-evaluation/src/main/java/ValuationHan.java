import java.util.List;

public class ValuationHan {

    public static void calculateHan(Hand hand, List<Group> groupList){

    }

    public static boolean checkStraight(List<Group> groupList){
        boolean wan = groupList.stream().filter().allMatch(group -> group.getSuit().getIdentifier() == "Wan");
        boolean pin = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin");
        boolean sou = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou");

        return (wan || pin || sou);
        }
    }



