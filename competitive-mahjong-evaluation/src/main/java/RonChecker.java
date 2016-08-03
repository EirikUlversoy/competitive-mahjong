import java.util.List;

/**
 * Supposed to check if the given hand is in a state ready to go out.
 */
public class RonChecker {
    private Gamerules gamerules;
    RonChecker(Gamerules gamerules){
        this.gamerules = gamerules;
    }

    public static boolean checkIfValid(Hand hand, List<Group> groups){
        if(groups.size() == 4){
            if(ValuationHan.calculateHan(hand, groups); >= 1){
                return true;
            } else if(ValuationFu.calculateFu(hand, groups) >= 40) {
                    return true;
                } else {
                return false;
            }
        }

        if (ValuationHan.sevenPairs(hand)) {
            return true;
        } else {
            return false;
        }
        return false;
    }

}
