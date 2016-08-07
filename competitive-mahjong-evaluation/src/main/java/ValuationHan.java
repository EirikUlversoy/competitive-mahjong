import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ValuationHan {
    private HandEvaluator handEvaluator;
    /**
     * Constructor sets up a list of the honor suits. Todo: Switch for class? Why else do I have different tile classes?
     */
    public ValuationHan(){


        handEvaluator = new HandEvaluator();
    }

    /**
     * Should output the max han number for a hand.
     * @param hand
     * @param groupList
     */
    public void calculateHan(Hand hand, List<Group> groupList){

    }

    /**
     * Conditions upon winning. Just a template.
     * @param RICHII Having declared richii
     * @param doraAmount The amount of Dora tiles.
     * @param hand Needed for assessing 7 pairs
     * @param NAGASHI Special hand based on discards.
     * @return
     */
    public Integer assessConditions(boolean RICHII, Integer doraAmount, Hand hand, boolean NAGASHI){
        Integer amountOfHan = 0;
        if(RICHII){
            amountOfHan += 1;
        }

        amountOfHan += doraAmount;

        HandEvaluator handEvaluator = new HandEvaluator();

        Map<Integer, List<Tile>> integerListMap= handEvaluator.findTileCount(hand.getTiles());

        boolean sevenPairs = false;
        List<Integer> integers =  integerListMap.keySet().stream()
                .filter(z -> integerListMap.get(z).size() == 2)
                .collect(Collectors.toList());

        if(integers.size() == 7){
            sevenPairs = true;
            amountOfHan += 2;
        }

        if(NAGASHI){
            amountOfHan += 5;
        }

        return amountOfHan;
    }



    }



