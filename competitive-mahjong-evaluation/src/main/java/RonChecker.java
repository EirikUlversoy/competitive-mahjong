import java.util.List;

/**
 * Supposed to check if the given hand is in a state ready to go out.
 */
public class RonChecker {
    private Gamerules gamerules;
    private ValuationHan hanEvaluator;

    RonChecker(Gamerules gamerules){
        this.gamerules = gamerules;
        this.hanEvaluator = new ValuationHan();
    }

    public boolean checkIfValid(Hand hand, List<Group> groups){
        if(groups.size() == 4){
            /*
            if(hanEvaluator(hand, groups) >= 1){
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
        */
    }
    return false;
}}
