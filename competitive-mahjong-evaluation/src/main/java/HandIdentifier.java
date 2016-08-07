import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandIdentifier {
    private HandEvaluator handEvaluator;
    public HandIdentifier(){
        handEvaluator = new HandEvaluator();
    }
    /**
     * Chanta is a hand where you have a terminal in each set. Honors can be used. Example: S123W123P123789C11
     * @param groups
     * @return
     */
    public boolean hasChanta(List<Group> groups, Pair pair){
        groups = HandEvaluator.filterOutHonors(groups);

        return handEvaluator.pairIsTerminalOrHonor(pair)
                || handEvaluator.allGroupsHaveATerminal(groups)
                && (groups.stream().anyMatch(z -> z.getClass() == SequenceGroup.class));

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
     * Returns true if we have one triplet of every color, example S111W111P111
     * @param groupList
     * @return
     */
    public boolean hasTripletColors(List<SetGroup> groupList){
        List<SetGroup> newGroupList = handEvaluator.removeDuplicateSuitSets(groupList);
        List<Group> groups = new ArrayList<>();
        groups.addAll(newGroupList);

        groups = handEvaluator.filterOutHonors(groups);
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
        List<SequenceGroup> newSequenceGroups = handEvaluator.removeDuplicateSuitSequences(sequenceGroups);
        List<Group> newGroups = new ArrayList<>();
        newGroups.addAll(newSequenceGroups);
        newGroups = HandEvaluator.filterOutHonors(newGroups);
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
                .noneMatch( z -> handEvaluator.getHonors().contains(z.getSuit().getIdentifier()) );

        boolean isAllSimples = groups.stream()
                .noneMatch( z -> disqualifyingNumbers.contains(z.getThirdMember().getTileNumber()));

        boolean pairIsSimplePair = !disqualifyingNumbers.contains(pair.getFirstMember().getTileNumber());

        return noHonors && isAllSimples && pairIsSimplePair;

    }

    /**
     * Same as chanta but has the additional condition of no honors.
     * @param groups
     * @return
     */
    public boolean hasTerminalInEachSet(List<Group> groups, Pair pair){
        boolean noHonors = groups.stream()
                .noneMatch( z -> handEvaluator.getHonors().contains(z.getSuit().getIdentifier()) );
        boolean chanta = hasChanta(groups, pair);

        return noHonors && chanta && !handEvaluator.pairIsTerminalOrHonor(pair);
    }

    /**
     * All terminals hand. Example: S111W111P999C222V22
     * @param groups
     * @return
     */
    public boolean allTerminalsAndHonors(List<Group> groups, Pair pair){
        groups = HandEvaluator.filterOutHonors(groups);
        boolean hasFour = groups.size() == 4;
        boolean allGroupsAreSets = groups.stream().allMatch(z -> z.getClass() == SetGroup.class);
        boolean allGroupsHaveATerminal = handEvaluator.allGroupsHaveATerminal(groups);

        return hasFour && allGroupsAreSets && allGroupsHaveATerminal && handEvaluator.pairIsTerminalOrHonor(pair);
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

        List<SequenceGroup> newSequenceGroups = handEvaluator.filterLargestSuit(sequenceGroups);

        return newSequenceGroups.stream()
                .map(Group::getThirdMember)
                .filter(z -> !disqualifyingNumbers.contains(z.getTileNumber()))
                .distinct()
                .count() >= 3;
    }

    /**
     * Checks for half flush.
     * @param groupList
     * @return
     */
    public boolean checkHalfFlush(List<Group> groupList, Pair pair){
        List<Group> filteredGroupList = HandEvaluator.filterOutHonors(groupList);
        boolean wanHalfFlush = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Wan")) || handEvaluator.pairIsHonorPair(pair));
        boolean pinHalfFlush = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Pin")) || handEvaluator.pairIsHonorPair(pair));
        boolean souHalfFlush = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Sou")) || handEvaluator.pairIsHonorPair(pair));

        return wanHalfFlush || pinHalfFlush || souHalfFlush;


    }

    /**
     * Three little dragons. Example: C111C222C33*
     * @param groups
     * @param pair
     * @return
     */
    public boolean hasThreeLittleDragons(List<Group> groups, Pair pair){
        return (handEvaluator.findColorSetAmount(groups)== 2) && handEvaluator.pairIsDragonPair(pair);
    }

    /**
     * Checks for full flush.
     * @param groupList
     * @return
     */
    public boolean checkFullFlush(List<Group> groupList, Pair pair){
        boolean WAN = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Wan"));
        boolean PIN = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Pin"));
        boolean SOU = groupList.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Sou"));

        return WAN || PIN || SOU;
    }
}
