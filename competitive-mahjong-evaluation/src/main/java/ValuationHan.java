import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ValuationHan {
    private List<String> honors;

    /**
     * Constructor sets up a list of the honor suits. Todo: Switch for class? Why else do I have different tile classes?
     */
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

    /**
     * Should output the max han number for a hand.
     * @param hand
     * @param groupList
     */
    public void calculateHan(Hand hand, List<Group> groupList){

    }

    /**
     * Returns all non-honor groups.
     * @param groupList
     * @return
     */
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

    /**
     * Counts the amount of groups of a given class
     * @param groupList
     * @param aClass
     * @return
     */
    public Integer groupAmountCounter(List<Group> groupList, Class aClass){
        System.out.println(groupList.get(0).getSecondMember().getClass().toString());
        System.out.println(aClass.toString());
        List<Group> setGroups = groupList.stream().filter(z -> z.getSecondMember().getClass().toString() != aClass.toString()).collect(Collectors.toList());
        return setGroups.size();
    }

    /**
     * checks if the given list contains three quad groups
     * @param groupList
     * @return
     */
    public boolean hasThreeQuads(List<SetGroup> groupList){
        return groupList.stream()
                .filter(SetGroup::isKAN)
                .collect(Collectors.toList()).size() >= 3 ;
    }

    /**
     * If there are 4 SetGroups, alltriplets is true..
     * @param groupList
     * @return
     */
    public boolean isAllTriplets(List<SetGroup> groupList){
        return groupList.size() == 4;
    }

    /**
     * Helper function for removing duplicate suitsets in some specific circumstances
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T,Object> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Removing duplicate suitsets and sequences, respectively.
     * @param setGroups
     * @return
     */
    public List<SetGroup> removeDuplicateSuitSets(List<SetGroup> setGroups){
        return setGroups.stream().filter(distinctByKey(o -> o.getSuit().getIdentifier())).collect(Collectors.toList());
    }
    public List<SequenceGroup> removeDuplicateSuitSequences(List<SequenceGroup> sequenceGroups){
        return sequenceGroups.stream().filter(distinctByKey(o -> o.getSuit().getIdentifier())).collect(Collectors.toList());
    }


    /**
     * Returns true if we have one triplet of every color, example S111W111P111
     * @param groupList
     * @return
     */
    public boolean hasTripletColors(List<SetGroup> groupList){
        List<SetGroup> newGroupList = removeDuplicateSuitSets(groupList);
        newGroupList.stream().map(Group::toString).forEach(System.out::println);

        List<Group> groups = new ArrayList<>();
        groups.addAll(newGroupList);
        groups = filterOutHonors(groups);
        boolean threeSuits = groups.stream()
                .map(z -> z.getSuit().getIdentifier())
                .distinct()
                .collect(Collectors.toList()).size() == 3;

        boolean sameNumbers = newGroupList.stream()
                .map(z -> z.getFirstMember().getTileNumber())
                .allMatch(z -> newGroupList.get(0).getFirstMember().getTileNumber().equals(z));

        return threeSuits && sameNumbers;

    }

    /**
     * Returns true if there are three sequencegroups with the same tiles, example: S123W123P123
     * @param sequenceGroups
     * @return
     */
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

    /**
     * Returns true if we have two sets of identical sequences. Example: S112233W112233
     * @param sequenceGroups
     * @return
     */
    public boolean twoSetsOfIdenticalSequences(List<SequenceGroup> sequenceGroups){
        boolean WAN = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Wan"));
        boolean PIN = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Pin"));
        boolean SOU = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Sou"));

        return WAN && (PIN || SOU) || (PIN && SOU);

    }

    /**
     * Returns true if we have one set of identical sequences of the same suit. Example: S112233
     * @param sequenceGroups
     * @param suit
     * @return
     */
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

    /**
     * No terminals, no honors. Example: S234567W234567P22
     * @param groups
     * @return
     */
    public boolean hasAllSimples(List<Group> groups, Pair pair){
        List<Integer> disqualifyingNumbers = new ArrayList<>();
        disqualifyingNumbers.add(1);
        disqualifyingNumbers.add(9);
        boolean noHonors = groups.stream()
                .noneMatch( z -> honors.contains(z.getSuit().getIdentifier()) );

        boolean isAllSimples = groups.stream()
                .noneMatch( z -> disqualifyingNumbers.contains(z.getThirdMember().getTileNumber()));

        boolean pairIsSimplePair = !disqualifyingNumbers.contains(pair.getFirstMember().getTileNumber());

        return noHonors && isAllSimples && pairIsSimplePair;

    }

    /**
     * Amount of sets of colors. Distinctions do not matter for them.
     * @param groups
     * @return
     */
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

    /**
     * Finds amount of winds. They have their own functions because their value is different depending on
     * the given table wind and player wind.
     * @param groups
     * @return
     */
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

    /**
     * Finds a set of given honor suit. Used by the color finding function
     * @param groups
     * @param suit
     * @return
     */
    public boolean hasHonorSet(List<Group> groups, Suit suit){
        return groups.stream()
                .filter(z -> z.getFirstMember().getSuit().getIdentifier().equals(suit.getIdentifier()))
                .count() == 1;
    }

    /**
     * Chanta is a hand where you have a terminal in each set. Honors can be used. Example: S123W123P123789C11
     * @param groups
     * @return
     */
    public boolean hasChanta(List<Group> groups, Pair pair){
        groups = ValuationHan.filterOutHonors(groups);

        return pairIsTerminalOrHonor(pair)
                || allGroupsHaveATerminal(groups)
                && (groups.stream().anyMatch(z -> z.getClass() == SequenceGroup.class));

    }

    /**
     * Helper function. checks if the pair is a terminal or an honor tile. Only checks one of the tiles because
     * a pair must have two equal(except for id of course) tiles in it.
     * @param pair
     * @return
     */
    public boolean pairIsTerminalOrHonor(Pair pair){
        return honors.contains(pair.getFirstMember().getSuit().getIdentifier())
                || pair.getFirstMember().getTileNumber() == 1
                || pair.getFirstMember().getTileNumber() == 9;

    }
    /**
     * Helper function for chanta. checks that all given groups have terminal entries within them.
     * @param groups
     * @return
     */
    public boolean allGroupsHaveATerminal(List<Group> groups){
        return groups.stream().allMatch(z -> z.getFirstMember().getTileNumber() == 9
                || z.getThirdMember().getTileNumber() == 1);
    }

    /**
     * Same as chanta but has the additional condition of no honors.
     * @param groups
     * @return
     */
    public boolean hasTerminalInEachSet(List<Group> groups, Pair pair){
        boolean noHonors = groups.stream()
                .noneMatch( z -> honors.contains(z.getSuit().getIdentifier()) );
        boolean chanta = hasChanta(groups, pair);

        return noHonors && chanta && !pairIsTerminalOrHonor(pair);
    }

    /**
     * All terminals hand. Example: S111W111P999C222V22
     * @param groups
     * @return
     */
    public boolean allTerminalsAndHonors(List<Group> groups, Pair pair){
        groups = ValuationHan.filterOutHonors(groups);
        boolean hasFour = groups.size() == 4;
        boolean allGroupsAreSets = groups.stream().allMatch(z -> z.getClass() == SetGroup.class);
        boolean allGroupsHaveATerminal = allGroupsHaveATerminal(groups);

        return hasFour && allGroupsAreSets && allGroupsHaveATerminal && pairIsTerminalOrHonor(pair);
    }

    /**
     * Checks for a straight in the sequence groups. Example: S123456789
     * @param sequenceGroups
     * @return
     */
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

    /**
     * Checks if the pair is a dragon pair
     * @param pair
     * @return
     */
    public boolean pairIsDragonPair(Pair pair){
        List<String> dragons = new ArrayList<>();
        dragons.add("Red");
        dragons.add("White");
        dragons.add("Green");

        return dragons.contains(pair.getFirstMember().getSuit().getIdentifier());
    }

    /**
     * Three little dragons. Example: C111C222C33*
     * @param groups
     * @param pair
     * @return
     */
    public boolean hasThreeLittleDragons(List<Group> groups, Pair pair){
        return (findColorSetAmount(groups)== 2) && pairIsDragonPair(pair);
    }


    /**
     * Filters out the largest suit. Helper function.
     * @param sequenceGroups
     * @return
     */
    public List<SequenceGroup> filterLargestSuit(List<SequenceGroup> sequenceGroups){
        Map<String, List<SequenceGroup>> stringListMap =
                sequenceGroups.stream()
                        .collect(Collectors.groupingBy(z -> z.getSuit().getIdentifier()));

        return stringListMap.get(stringListMap.keySet().stream().max((z,x) -> stringListMap.get(z).size()).get());



    }


    //public Integer tripletHands(List<Group> groupList){

    //}

    /**
     * Checks for full flush.
     * @param groupList
     * @return
     */
    public boolean checkFullFlush(List<Group> groupList, Pair pair){
        boolean WAN = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan")
                && pairIsGivenSuit(pair,new Suit("Wan"));
        boolean PIN = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && pairIsGivenSuit(pair,new Suit("Pin"));
        boolean SOU = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && pairIsGivenSuit(pair,new Suit("Sou"));

        return WAN || PIN || SOU;
    }

    /**
     * Checks if pair is of the given suit.
     */
    public boolean pairIsGivenSuit(Pair pair, Suit suit){
        return pair.getFirstMember().getSuit().getIdentifier().equals(suit.getIdentifier());
    }

    public boolean pairIsGivenClass(Pair pair, Class aClass){
        return pair.getFirstMember().getClass() == aClass;
    }

    public boolean pairIsHonorPair(Pair pair){
        return honors.contains(pair.getFirstMember().getSuit().getIdentifier());
    }
    /**
     * Checks for half flush.
     * @param groupList
     * @return
     */
    public boolean checkHalfFlush(List<Group> groupList, Pair pair){
        List<Group> filteredGroupList = filterOutHonors(groupList);
        boolean wanHalfFlush = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan")
                && (pairIsGivenSuit(pair,new Suit("Wan")) || pairIsHonorPair(pair));
        boolean pinHalfFlush = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && (pairIsGivenSuit(pair,new Suit("Pin")) || pairIsHonorPair(pair));
        boolean souHalfFlush = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && (pairIsGivenSuit(pair,new Suit("Sou")) || pairIsHonorPair(pair));

        return wanHalfFlush || pinHalfFlush || souHalfFlush;


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

    public List<String> getHonors(){
        return this.honors;
    }

    }



