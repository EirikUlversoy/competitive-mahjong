import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ValuationHan {
    private HandEvaluator handEvaluator;
    private Map<String, HandValueObject> stringHandValueObjectMap;
    private Map<String, HandValueYakumanObject> stringYakumanHandValueObjectMap;
    public Config readHandValuesConfig(){
        Config conf = ConfigFactory.load("handvalues");

        return conf;

    }

    public Config readYakumanHandValuesConfig(){
        Config yakumanConf = ConfigFactory.load("yakumanhandvalues");

        return yakumanConf;
    }

    public void setupYakumanHandValueObjectMap(){
       Config config = readYakumanHandValuesConfig();

        this.stringYakumanHandValueObjectMap = config.getObject("hand").keySet().stream()
                .map(key -> config.getConfig(String.format("hand.%s",key)))
                .map(z -> new HandValueYakumanObject(z))
                .collect(Collectors.toMap(HandValueObject::getName,Function.identity()));
    }

    public void setupHandValueObjectMap(){
        Config config = readHandValuesConfig();

        this.stringHandValueObjectMap = config.getObject("hand").keySet().stream()
                .map(key -> config.getConfig(String.format("hand.%s",key)))
                .map(z -> new HandValueObject(z))
                .collect(Collectors.toMap(HandValueObject::getName,Function.identity()));
    }
    /**
     * Constructor sets up a list of the honor suits. Todo: Switch for class? Why else do I have different tile classes?
     */
    public ValuationHan(){
        handEvaluator = new HandEvaluator();
        this.setupYakumanHandValueObjectMap();
        this.setupHandValueObjectMap();
    }

    /**
     * Should output the max han number for a hand.
     * There should be a map with Strings to Han value somewhere.
     * Probably read in from file.
     */
    public Integer calculateHan(List<Tile> tiles, boolean closed){
        stringHandValueObjectMap.putAll(stringYakumanHandValueObjectMap);
        HandIdentifier handIdentifier = new HandIdentifier();
        //Hand hand = new Hand(4);
        //hand.setTiles(tiles);
        List<String> hands = handIdentifier.identifyMatchingHands(tiles,true,true,true);

        hands.stream().forEach(System.out::println);
        Integer hanValue = hands.stream()
                .map(z -> stringHandValueObjectMap.get(z))
                .map(z -> { if(closed) {
                    return z.getValue_closed();
                } else if(!closed){
                    return z.getValue_open();
                }  else {
                    return 0;
                }

                })
                .peek(z -> z.toString())
                .mapToInt(z -> z)
                .sum();
        YakumanChecker yakumanChecker = new YakumanChecker(handIdentifier);
        List<String> yakumanStrings = yakumanChecker.findYakumanIfAny(tiles,true,true,true);
        if(yakumanChecker.findYakumanIfAny(tiles,true,true,true).size() > 1){
            hanValue = yakumanStrings.stream().map(z -> stringYakumanHandValueObjectMap.get(z)).map(z -> {
                if(closed){
                    return z.getValue_closed();
                } else {
                    return z.getValue_open();
                }
            } ).max((x,z) -> x.compareTo(z) ).orElse(0);
        }
        return hanValue;
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



