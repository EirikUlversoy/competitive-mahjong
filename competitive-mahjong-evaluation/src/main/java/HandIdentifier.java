import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.security.acl.*;
import java.util.*;
import java.util.stream.Collectors;

public class HandIdentifier {
    private HandEvaluator handEvaluator;
    private YakumanChecker yakumanChecker;
    public HandIdentifier(){
        handEvaluator = new HandEvaluator();
        yakumanChecker = new YakumanChecker(this);
    }

    public List<String> identifyMatchingHands(List<Tile> tiles, boolean singleWait, boolean closed, boolean nineWait){
        List<SetGroup> setGroups = handEvaluator.findSets(tiles);

        List<Tile> wanTiles = handEvaluator.filterWan(tiles);
        List<Tile> souTiles = handEvaluator.filterPin(tiles);
        List<Tile> pinTiles = handEvaluator.filterSou(tiles);

        List<SequenceGroup> sequenceGroups = handEvaluator.findSequences(wanTiles);
        sequenceGroups.addAll(handEvaluator.findSequences(souTiles));
        sequenceGroups.addAll(handEvaluator.findSequences(pinTiles));

        sequenceGroups = handEvaluator.findMaxValidSequences(sequenceGroups,tiles);

        List<String> matchingHands = new ArrayList<>();
        Pair pair = handEvaluator.findPair(tiles).get();

        sequenceGroups = handEvaluator.findSequences(wanTiles);
        sequenceGroups.addAll(handEvaluator.findSequences(souTiles));
        sequenceGroups.addAll(handEvaluator.findSequences(pinTiles));


        if(hasSevenPairs(tiles)){
            matchingHands.add("Seven Pairs");
        }


        List<String> matchingYakumanHands = new ArrayList<>();

        matchingYakumanHands = yakumanChecker.findYakumanIfAny(tiles,singleWait,closed,nineWait);

        System.out.println(setGroups);
        System.out.println(sequenceGroups);
        if(!matchingYakumanHands.contains("No Yakuman")){
            matchingHands.addAll(matchingYakumanHands);
            //return matchingHands;
        }

        if(hasChanta(setGroups, sequenceGroups, pair)){
            matchingHands.add("Chanta");
        }

        if(hasThreeQuads(setGroups)){
            matchingHands.add("Three Quads");
        }

        if(isAllTriplets(setGroups)){
            matchingHands.add("All Triplets");
        }

        if(hasTripletColors(setGroups)){
            matchingHands.add("Triplet In Each Color");
        }

        if(sameSequenceInThreeSuits(sequenceGroups)){
            matchingHands.add("Sequence In Each Suit");
        }

        if(twoSetsOfIdenticalSequences(sequenceGroups)){
            matchingHands.add("Two Sets Of Identical Sequences");
        }

        if(hasAllSimples(setGroups, sequenceGroups, pair)){
            matchingHands.add("All Simples");
        }

        if(hasTerminalInEachSet(setGroups, sequenceGroups, pair)){
            matchingHands.add("Terminal In Each Set");
        }

        if(allTerminalsAndHonors(setGroups, sequenceGroups, pair)){
            matchingHands.add("All Terminals And Honors");
        }

        if(hasStraight(sequenceGroups)){
            matchingHands.add("Straight");
        }

        if(hasSevenPairs(tiles)){
            if(hasHalfFlushWithSevenPairs(tiles, setGroups, sequenceGroups, pair)) {
                matchingHands.add("Half Flush");
            }

        } else {
            if(hasHalfFlush(setGroups,sequenceGroups,pair)){
                matchingHands.add("Half Flush");
            }
        }
        if (hasSevenPairs(tiles)) {
            if(hasFullFlushWithSevenPairs(tiles)){
                if(matchingHands.contains("Half Flush")){
                    matchingHands.remove("Half Flush");
                }
                matchingHands.add("Full Flush");
            }
        } else {
            if(hasFullFlush(setGroups, sequenceGroups, pair)){
                if(matchingHands.contains("Half Flush")){
                    matchingHands.remove("Half Flush");
                }
                matchingHands.add("Full Flush");
            }
        }


        if(hasThreeLittleDragons(setGroups,sequenceGroups,pair)){
            matchingHands.add("Three Little Dragons");
        }

        return matchingHands;
    }

    public boolean hasSevenPairs(List<Tile> tiles){
        Map<Integer, List<Tile>> integerListMap= handEvaluator.findTileCount(tiles);
        List<Optional<Pair>> listOfPairs = handEvaluator.findPairs(tiles);
        return listOfPairs.size() == 7;
    }
    /**
     * Chanta is a hand where you have a terminal in each set. Honors can be used. Example: S123W123P123789C11
     * @return
     */
    public boolean hasChanta(List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair){
        //List<SetGroup> groups = HandEvaluator.filterOutHonors(setGroups);


        return handEvaluator.pairIsTerminalOrHonor(pair)
                && handEvaluator.allSetGroupsHaveATerminal(setGroups)
                && handEvaluator.allSequenceGroupsHaveATerminal(sequenceGroups)
                && sequenceGroups.size() >= 1;

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

        newGroupList = handEvaluator.filterOutHonors(newGroupList);
        List<SetGroup> otherList = newGroupList;
        boolean threeSuits = newGroupList.stream()
                .map(z -> z.getSuit().getIdentifier())
                .distinct()
                .collect(Collectors.toList()).size() == 3;

        boolean sameNumbers = newGroupList.stream()
                .map(z -> z.getFirstMember().getTileNumber())
                .allMatch(z -> otherList.get(0).getFirstMember().getTileNumber().equals(z));

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
        boolean differentSuits = sequenceGroups.stream().map(z -> z.getSuit().getIdentifier()).distinct().count() == 3;
        SequenceGroup firstSequenceGroup = sequenceGroups.get(0);
        Tile firstTile = firstSequenceGroup.getFirstMember();
        Tile secondTile = firstSequenceGroup.getSecondMember();
        Tile thirdTile = firstSequenceGroup.getThirdMember();
        boolean sameNumberAndIdInThreeSequences = sequenceGroups.stream().filter( z -> {
            return z.isSuitlessMember(firstTile) && z.isSuitlessMember(secondTile) && z.isSuitlessMember(thirdTile);
        }).count() == 3;

        return differentSuits && sameNumberAndIdInThreeSequences;
    }

    /**
     * Returns true if we have two sets of identical sequences. Example: S112233W112233
     * @param sequenceGroups
     * @return
     */
    public boolean twoSetsOfIdenticalSequences(List<SequenceGroup> sequenceGroups){
        boolean WAN = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Wan"));
        boolean PIN = oneSetOfIdenticalSequencesSameSuit(sequenceGroups,new Suit("Pin"));
        boolean SOU = oneSetOfIdenticalSequencesSameSuit(sequenceGroups, new Suit("Sou"));

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
                .filter(z -> z.getSuit().getIdentifier().equals(suit.getIdentifier()))
                .collect(Collectors.toList());

        long distinctSequencesAmount = getDistinctSequencesAmount(filteredSequenceGroups);
        boolean lessThanMaxDistinctSequences = distinctSequencesAmount <= filteredSequenceGroups.size() - 1;
        boolean twoOrMoreFilteredSequenceGroups = filteredSequenceGroups.size() >= 2;
        
        return twoOrMoreFilteredSequenceGroups && lessThanMaxDistinctSequences;
    }

    public long getDistinctSequencesAmount(List<SequenceGroup> sequenceGroups){
        return sequenceGroups.stream()
                .map(SequenceGroup::getFirstMember)
                .map(Tile::getTileNumber)
                .distinct()
                .count();
    }

    /**
     * No terminals, no honors. Example: S234567W234567P22
     * @return
     */
    public boolean hasAllSimples(List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair){




        if( pairIsASimplePair(pair) && noHonorsInSetGroups(setGroups) && allGroupsAreAllSimples(sequenceGroups, setGroups)){
            return true;
        } else {
            return false;
        }
    }

    private boolean pairIsASimplePair(Pair pair){
        List<Integer> nonSimpleNumbers = new ArrayList<>();
        nonSimpleNumbers.add(1);
        nonSimpleNumbers.add(9);

        Integer numberOfFirstTileInPair = pair.getFirstMember().getTileNumber();

        return !nonSimpleNumbers.contains(numberOfFirstTileInPair);
    }

    private boolean noHonorsInSetGroups(List<SetGroup> setGroups)
    {
        List<String> honorNames = handEvaluator.getHonorNames();

        return setGroups.stream()
                .map(z -> z.getSuit().getIdentifier())
                .noneMatch( z -> honorNames.contains(z));
    }

    private boolean allGroupsAreAllSimples(List<SequenceGroup> sequenceGroups, List<SetGroup> setGroups){
        return sequencesAreAllSimples(sequenceGroups) && setsGroupsAreAllSimples(setGroups);
    }
    private boolean sequencesAreAllSimples(List<SequenceGroup> sequenceGroups){
        List<Integer> nonSimpleNumbers = new ArrayList<>();
        nonSimpleNumbers.add(1);
        nonSimpleNumbers.add(9);
        
        return sequenceGroups.stream()
                .map(z -> z.getThirdMember().getTileNumber())
                .noneMatch( z -> nonSimpleNumbers.contains(z));
    }
    private boolean setsGroupsAreAllSimples(List<SetGroup> setGroups){
        List<Integer> nonSimpleNumbers = new ArrayList<>();
        nonSimpleNumbers.add(1);
        nonSimpleNumbers.add(9);
        
        return setGroups.stream()
                .map(z -> z.getThirdMember().getTileNumber())
                .noneMatch( z -> nonSimpleNumbers.contains(z));
    }
    


    /**
     * Same as chanta but has the additional condition of no honors.
     * @return
     */
    public boolean hasTerminalInEachSet(List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair){

        if( noHonorsInSetGroups(setGroups) && hasChanta(setGroups, sequenceGroups, pair)){
            if(!handEvaluator.pairIsTerminalOrHonor(pair)){
                return true;
            }
        }

        return false;
    }

    /**
     * All terminals hand. Example: S111W111P999C222V22
     * @return
     */
    public boolean allTerminalsAndHonors(List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair){
        setGroups = HandEvaluator.filterOutHonors(setGroups);

        boolean hasFourSetGroups = setGroups.size() == 4;
        boolean allGroupsHaveATerminal = handEvaluator.allSetGroupsHaveATerminal(setGroups);
        boolean pairIsTerminalOrHonor = handEvaluator.pairIsTerminalOrHonor(pair);

        return hasFourSetGroups && allGroupsHaveATerminal && pairIsTerminalOrHonor;
    }

    /**
     * Checks for a straight in the sequence groups. Example: S123456789
     * @param sequenceGroups
     * @return
     */
    public boolean hasStraight(List<SequenceGroup> sequenceGroups){

        List<Integer> nonSimpleNumbers = new ArrayList<>();
        nonSimpleNumbers.add(2);
        nonSimpleNumbers.add(3);
        nonSimpleNumbers.add(5);
        nonSimpleNumbers.add(6);
        List<SetGroup> setGroups = new ArrayList<>();

        List<Tile> tiles = handEvaluator.decomposeGroupsIntoTiles(sequenceGroups, setGroups);
        List<SequenceGroup> wanSequences = handEvaluator.findSequences(handEvaluator.filterWan(tiles));
        List<SequenceGroup> pinSequences = handEvaluator.findSequences(handEvaluator.filterPin(tiles));
        List<SequenceGroup> souSequences = handEvaluator.findSequences(handEvaluator.filterSou(tiles));

        //List<SequenceGroup> newSequenceGroups = handEvaluator.filterLargestSuit(sequenceGroups);

        boolean wanHasStraight = wanSequences.stream()
                .map(SequenceGroup::getThirdMember)
                .filter(z -> !nonSimpleNumbers.contains(z.getTileNumber()))
                .distinct()
                .count() >= 3;
        boolean pinHasStraight = pinSequences.stream()
                .map(SequenceGroup::getThirdMember)
                .filter(z -> !nonSimpleNumbers.contains(z.getTileNumber()))
                .distinct()
                .count() >= 3;
        boolean souHasStraight = souSequences.stream()
                .map(SequenceGroup::getThirdMember)
                .filter(z -> !nonSimpleNumbers.contains(z.getTileNumber()))
                .distinct()
                .count() >= 3;
        return wanHasStraight || pinHasStraight || souHasStraight;
    }

    /**
     * Checks for half flush.
     *
     * @return
     */
    public boolean hasHalfFlush(List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair){
        List<SetGroup> groupListWithoutHonors = HandEvaluator.filterOutHonors(setGroups);

        boolean wanHalfFlushSets = setGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Wan")) || handEvaluator.pairIsHonorPair(pair));
        boolean pinHalfFlushSets = setGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Pin")) || handEvaluator.pairIsHonorPair(pair));
        boolean souHalfFlushSets = setGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Sou")) || handEvaluator.pairIsHonorPair(pair));

        boolean wanHalfFlushSequences = sequenceGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Wan")) || handEvaluator.pairIsHonorPair(pair));
        boolean pinHalfFlushSequences = sequenceGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Pin")) || handEvaluator.pairIsHonorPair(pair));
        boolean souHalfFlushSequences = sequenceGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && (handEvaluator.pairIsGivenSuit(pair,new Suit("Sou")) || handEvaluator.pairIsHonorPair(pair));

        return (wanHalfFlushSets && wanHalfFlushSequences) ||( pinHalfFlushSets && pinHalfFlushSequences)
                || (souHalfFlushSets && souHalfFlushSequences);


    }

    /**
     * Three little dragons. Example: C111C222C33*
     * @param pair
     * @return
     */
    public boolean hasThreeLittleDragons(List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair){
        return (handEvaluator.findColorSetAmount(setGroups)== 2) && handEvaluator.pairIsDragonPair(pair);
    }


    public boolean hasFullFlushWithSevenPairs(List<Tile> tiles){

        return tiles.stream().map(z -> z.getSuit().getIdentifier()).distinct().count() == 1;
    }

    public boolean hasHalfFlushWithSevenPairs(List<Tile> tiles, List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair ){
        boolean honors = this.allTerminalsAndHonors(setGroups,sequenceGroups,pair);
        if(honors){
            return false;
        }

        List<Tile> newTiles = tiles.stream()
                .filter(z -> z.getSuit().getIdentifier().equals("Wan")
                        || z.getSuit().getIdentifier().equals("Pin") || z.getSuit().getIdentifier().equals("Sou")).collect(Collectors.toList());
        return newTiles.stream().map(z -> z.getSuit().getIdentifier()).distinct().count() == 1;
    }
    /**
     * Checks for full flush.
     * @return
     */
    public boolean hasFullFlush(List<SetGroup> setGroups, List<SequenceGroup> sequenceGroups, Pair pair){
        boolean WANSets = setGroups.stream().allMatch(group -> group.getFirstMember().getSuit().getIdentifier() == "Wan")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Wan"));
        boolean PINSets = setGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Pin"));
        boolean SOUSets = setGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Sou"));

        boolean WANSeq = sequenceGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Wan")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Wan"));
        boolean PINSeq = sequenceGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Pin")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Pin"));
        boolean SOUSeq = sequenceGroups.stream().allMatch(group -> group.getSuit().getIdentifier() == "Sou")
                && handEvaluator.pairIsGivenSuit(pair,new Suit("Sou"));

        System.out.println(WANSets);
        System.out.println(WANSeq);
        System.out.println(PINSeq);
        System.out.println(PINSets);

        return (WANSets && WANSeq) || (PINSets && PINSeq) || (SOUSets && SOUSeq);
    }
}
