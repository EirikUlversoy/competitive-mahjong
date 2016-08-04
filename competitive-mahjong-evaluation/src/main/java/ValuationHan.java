import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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

    public boolean hasThreeQuads(List<SetGroup> groupList){
        return groupList.stream()
                .filter(SetGroup::isKAN)
                .collect(Collectors.toList()).size() >= 3 ;
    }

    public boolean isAllTriplets(List<SetGroup> groupList){
        return groupList.size() == 4;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    public List<SetGroup> removeDuplicateSuit(List<SetGroup> setGroups){
        return setGroups.stream().filter(distinctByKey(o -> o.getSuit().getIdentifier())).collect(Collectors.toList());
    }

    public boolean hasTripletColors(List<SetGroup> groupList){
        List<SetGroup> newGroupList = removeDuplicateSuit(groupList);
        
        boolean threeSuits = groupList.stream()
                .map(z -> z.getSuit().getIdentifier())
                .distinct()
                .collect(Collectors.toList()).size() == 3;

        boolean sameNumbers = newGroupList.stream()
                .map(z -> z.getFirstMember().getTileNumber())
                .allMatch(z -> newGroupList.get(0).getFirstMember().getTileNumber().equals(z));

        return threeSuits && sameNumbers;

    }

    public boolean sameSequenceInThreeSuits( List<SequenceGroup> sequenceGroups){
        if(sequenceGroups.size() == 3){
            return (sequenceGroups.stream()
                    .map(z -> z.getFirstMember().getTileNumber())
                    .distinct()
                    .count() == 1 )
                    && (sequenceGroups.stream()
                    .map(z -> z.getSuit().getIdentifier())
                    .distinct()
                    .count() == 3);

        } else if (sequenceGroups.size() == 4) {
            return (sequenceGroups.stream()
                    .map(z -> z.getFirstMember().getTileNumber())
                    .distinct().count() <= 2 ) &&
                    (sequenceGroups.stream()
                    .map(z -> z.getSuit().getIdentifier())
                    .distinct()
                    .count() == 3);
        }
        return false;
    }



    public boolean oneSetOfIdenticalSequencesSameSuit(List<SequenceGroup> sequenceGroups, Suit suit){
        List<SequenceGroup> filteredSequenceGroups = sequenceGroups.stream()
                .filter(z -> z.getSuit().getIdentifier() == suit.getIdentifier())
                .collect(Collectors.toList());

        return filteredSequenceGroups.size() >= 2 && filteredSequenceGroups.stream()
                    .map(SequenceGroup::getFirstMember)
                    .map(Tile::getTileNumber)
                    .distinct()
                    .count() == filteredSequenceGroups.size() - 1;
        }



    public boolean hasStraight(List<SequenceGroup> sequenceGroups){

        List<Integer> disqualifyingNumbers = new ArrayList<>();
        disqualifyingNumbers.add(2);
        disqualifyingNumbers.add(3);
        disqualifyingNumbers.add(5);
        disqualifyingNumbers.add(6);

        List<SequenceGroup> newSequenceGroups = filterLargestSuit(sequenceGroups);

        return newSequenceGroups.stream()
                .map(Group::getFirstMember)
                .filter(z -> !disqualifyingNumbers.contains(z.getTileNumber()))
                .distinct()
                .count() >= 3;
    }

    public List<SequenceGroup> filterLargestSuit(List<SequenceGroup> sequenceGroups){
        Map<String, List<SequenceGroup>> stringListMap =
                sequenceGroups.stream()
                        .collect(Collectors.groupingBy(z -> z.getSuit().getIdentifier()));

        return stringListMap.get(stringListMap.keySet().stream().max((z,x) -> stringListMap.get(z).size()).get());



    }

    //public Integer tripletHands(List<Group> groupList){

    //}


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



