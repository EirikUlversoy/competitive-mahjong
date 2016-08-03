import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValuationHan {


    public void calculateHan(Hand hand, List<Group> groupList){

    }
    public static List<Group> filterOutHonors(List<Group> groupList){
        return groupList.stream().filter(z -> {
            if(z.getSuit().getIdentifier() == "Color" || z.getSuit().getIdentifier() == "Wind"){
                return false;
            }
            return true;
        }).collect(Collectors.toList());

    }

    public Integer groupAmountCounter(List<Group> groupList, Class aClass){
        List<Group> setGroups = groupList.stream().filter(z -> z.getClass() == aClass).collect(Collectors.toList());
        return setGroups.size();
    }

    public boolean threeQuads(List<SetGroup> groupList){
        return groupList.stream()
                .filter(SetGroup::isKAN)
                .collect(Collectors.toList()).size() >= 3 ;
    }

    public boolean allTriplets(List<SetGroup> groupList){
        return groupList.size() == 4;
    }

    public boolean tripletColors(List<SetGroup> groupList){
        boolean threeSuits = groupList.stream()
                .map(z -> z.getSuit().getIdentifier())
                .distinct()
                .collect(Collectors.toList()).size() == 3;

        boolean sameNumbers = groupList.stream()
                .map(z -> z.getFirstMember().getTileNumber())
                .allMatch(z -> groupList.get(0).getFirstMember().getTileNumber() == z);

        return threeSuits && sameNumbers;

    }
    public Integer tripletHands(List<Group> groupList){

    }


    public String checkFlush(List<Group> groupList){

        List<Group> filteredGroupList = filterOutHonors(groupList);
        boolean FULL_FLUSH = false;
        if(filteredGroupList.equals(groupList)){
            FULL_FLUSH = true;
        }
        boolean WAN = filteredGroupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan");
        boolean PIN = filteredGroupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin");
        boolean SOU = filteredGroupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou");

        if(WAN || PIN || SOU){
            if(FULL_FLUSH){
                return "Flush";
            } else {
                return "Half Flush";
            }
        }
        return "No Flush";
        }

    public Integer assessConditions(boolean RICHII, Integer doraAmount, Hand hand, boolean NAGASHI){
        Integer amountOfHan = 0;
        if(RICHII){
            amountOfHan += 1;
        }

        amountOfHan += doraAmount;

        HandEvaluator handEvaluator = new HandEvaluator(hand);

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



