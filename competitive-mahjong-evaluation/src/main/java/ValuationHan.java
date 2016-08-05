import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ValuationHan {
    private List<String> honors;
    public ValuationHan(){
        honors = new ArrayList<>();
        honors.add("Red");
        honors.add("White");
        honors.add("Green");
        honors.add("Wind");
        honors.add("Color");
        honors.add("North");
        honors.add("South");
        honors.add("West");
        honors.add("East");
    }
    public void calculateHan(Hand hand, List<Group> groupList){

    }
    public static List<Group> filterOutHonors(List<Group> groupList){

        List<String> honors = new ArrayList<>();
        honors.add("Red");
        honors.add("White");
        honors.add("Green");
        honors.add("Wind");
        honors.add("Color");
        honors.add("North");
        honors.add("South");
        honors.add("West");
        honors.add("East");

        return groupList.stream()
                .peek(z -> System.out.println(z.getSecondMember().getSuit().getIdentifier()))
                .filter(z -> !honors.contains(z.getSuit().getIdentifier()))
                .collect(Collectors.toList());
    }

    public Integer groupAmountCounter(List<Group> groupList, Class aClass){
        System.out.println(groupList.get(0).getSecondMember().getClass().toString());
        System.out.println(aClass.toString());
        List<Group> setGroups = groupList.stream().filter(z -> z.getSecondMember().getClass().toString() != aClass.toString()).collect(Collectors.toList());
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
    public List<SetGroup> removeDuplicateSuitSets(List<SetGroup> setGroups){
        return setGroups.stream().filter(distinctByKey(o -> o.getSuit().getIdentifier())).collect(Collectors.toList());
    }

    public List<SequenceGroup> removeDuplicateSuitSequences(List<SequenceGroup> sequenceGroups){
        return sequenceGroups.stream().filter(distinctByKey(o -> o.getSuit().getIdentifier())).collect(Collectors.toList());
    }



    public boolean hasTripletColors(List<SetGroup> groupList){
        List<SetGroup> newGroupList = removeDuplicateSuitSets(groupList);
        newGroupList.stream().map(Group::toString).forEach(System.out::println);

        List<Group> groups = new ArrayList<>();
        groups.addAll(newGroupList);
        groups = filterOutHonors(groups);
        groups.stream().map(Group::toString).forEach(System.out::println);
        boolean threeSuits = groups.stream()
                .map(z -> z.getSuit().getIdentifier())
                .distinct()
                .collect(Collectors.toList()).size() == 3;

        boolean sameNumbers = newGroupList.stream()
                .map(z -> z.getFirstMember().getTileNumber())
                .allMatch(z -> newGroupList.get(0).getFirstMember().getTileNumber().equals(z));

        return threeSuits && sameNumbers;

    }

    public boolean sameSequenceInThreeSuits( List<SequenceGroup> sequenceGroups){
        if(sequenceGroups.size() <= 2){
            return false;
        }
        List<SequenceGroup> newSequenceGroups = removeDuplicateSuitSequences(sequenceGroups);
        List<Group> newGroups = new ArrayList<>();
        newGroups.addAll(newSequenceGroups);
        newGroups = ValuationHan.filterOutHonors(newGroups);
        return newGroups.stream()
                .map(z -> z.getFirstMember().getTileNumber())
                .distinct()
                .count() == 1;

    }

    public boolean twoSetsOfIdenticalSequences(List<SequenceGroup> sequenceGroups){
        boolean WAN = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Wan"));
        boolean PIN = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Pin"));
        boolean SOU = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Sou"));

        return WAN && (PIN || SOU) || (PIN && SOU);

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


    public boolean hasAllSimples(List<Group> groups){
        List<Integer> disqualifyingNumbers = new ArrayList<>();
        disqualifyingNumbers.add(1);
        disqualifyingNumbers.add(9);
        boolean noHonors = groups.stream()
                .noneMatch( z -> honors.contains(z.getSuit().getIdentifier()) );

        boolean isAllSimples = groups.stream()
                .noneMatch( z -> disqualifyingNumbers.contains(z.getThirdMember().getTileNumber()));

        return noHonors && isAllSimples;

    }

    public Integer findColorSetAmount(List<Group> groups){
        Integer colorCount = 0;
        if(hasHonorSet(groups,new Suit("Green"))){
            colorCount += 1;
        }
        if(hasHonorSet(groups,new Suit("White"))){
            colorCount += 1;
        }
        if(hasHonorSet(groups,new Suit("Red"))){
            colorCount += 1;
        }
        return colorCount;
    }
    public boolean hasSouthWind(List<Group> groups){
        return hasHonorSet(groups,new Suit("South"));
    }
    public boolean hasNorthWind(List<Group> groups){
        return hasHonorSet(groups,new Suit("North"));
    }
    public boolean hasWestWind(List<Group> groups){
        return hasHonorSet(groups,new Suit("West"));
    }
    public boolean hasEastWind(List<Group> groups){
        return hasHonorSet(groups,new Suit("East"));
    }
    public Integer findWindSetAmount(List<Group> groups){
        Integer windCount = 0;
        if(hasSouthWind(groups)){
            windCount+=1;
        }
        if(hasEastWind(groups)){
            windCount+=1;
        }
        if(hasNorthWind(groups)){
            windCount+=1;
        }
        if(hasWestWind(groups)){
            windCount+=1;
        }
        return windCount;
    }
    public boolean hasHonorSet(List<Group> groups, Suit suit){
        return groups.stream()
                .filter(z -> z.getSuit() == suit)
                .count() == 1;
    }

    public boolean hasChanta(List<Group> groups){
        groups = ValuationHan.filterOutHonors(groups);
        return allGroupsHaveATerminal(groups) && (groups.stream().anyMatch(z -> z.getClass() == SequenceGroup.class));
    }

    public boolean allGroupsHaveATerminal(List<Group> groups){
        return groups.stream().allMatch(z -> z.getFirstMember().getTileNumber() == 9
                || z.getThirdMember().getTileNumber() == 1);
    }

    public boolean hasTerminalInEachSet(List<Group> groups){
        boolean noHonors = groups.stream()
                .noneMatch( z -> honors.contains(z.getSuit().getIdentifier()) );
        boolean chanta = hasChanta(groups);

        return noHonors && chanta;
    }

    public boolean allTerminalsAndHonors(List<Group> groups){
        groups = ValuationHan.filterOutHonors(groups);
        boolean hasFour = groups.size() == 4;
        boolean allGroupsAreSets = groups.stream().allMatch(z -> z.getClass() == SetGroup.class);
        boolean allGroupsHaveATerminal = allGroupsHaveATerminal(groups);

        return hasFour && allGroupsAreSets && allGroupsHaveATerminal;
    }

    public boolean hasStraight(List<SequenceGroup> sequenceGroups){

        List<Integer> disqualifyingNumbers = new ArrayList<>();
        disqualifyingNumbers.add(2);
        disqualifyingNumbers.add(3);
        disqualifyingNumbers.add(5);
        disqualifyingNumbers.add(6);

        List<SequenceGroup> newSequenceGroups = filterLargestSuit(sequenceGroups);

        return newSequenceGroups.stream()
                .map(Group::getThirdMember)
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



