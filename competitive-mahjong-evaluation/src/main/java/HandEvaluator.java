import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Should assess value of final hand
 */
public class HandEvaluator {
    private Hand hand;
    private int[] tileNumbers = {1,2,3,4,5,6,7,8,9};

    HandEvaluator(Hand hand){
        this.hand = hand;
    }

    public List<Tile> filterSou(){
        List<Tile> souTiles = hand.getTiles().stream().filter(z -> z.getClass() == SouTile.class).collect(Collectors.toList());
        return souTiles;
    }

    public List<Tile> filterPin(){
        List<Tile> pinTiles = hand.getTiles().stream().filter(z -> z.getClass() == PinTile.class).collect(Collectors.toList());
        return pinTiles;
    }

    public List<Tile> filterWan(){
        List<Tile> wanTiles = hand.getTiles().stream().filter(z -> z.getClass() == WanTile.class).collect(Collectors.toList());
        return wanTiles;
    }

    public List<Tile> reduceTileSet(List<Tile> tiles){
        tiles.sort((z,x)-> z.getTileNumber());
        //List<Tile> newTiles = tiles.stream().sorted((z,x) -> z.getTileNumber()
        //        .compareTo(x.getTileNumber()))
         //       .collect(Collectors.toList());
        //newTiles.stream().forEach(z -> System.out.println(z.toString()));
        return tiles;
    }
    public List<SequenceGroup> findValidSequenceGroups(int[] numbers){
        List<SequenceGroup> validSequenceGroups = new ArrayList<>();
        for (int number: numbers){
            if(number <= 7){
                Tile tile = new Tile();
                tile.setTileNumber(number);
                Tile secondTile = new Tile();
                secondTile.setTileNumber(number+1);
                Tile thirdTile = new Tile();
                thirdTile.setTileNumber(number+2);
                validSequenceGroups.add(new SequenceGroup(tile,secondTile,thirdTile));

            }


        }
        return validSequenceGroups;
    }
    public List<SequenceGroup> findSequences(List<Tile> tiles, List<SequenceGroup> validSequenceGroups) {

        List<SequenceGroup> validSequenceGroupsA = findValidSequenceGroups(this.tileNumbers);
        List<Integer> candidate = new ArrayList<>();
        List<SequenceGroup> possibleGroups = new ArrayList<>();
        List<Tile> tileHolder = new ArrayList<>();

        for(Tile tile: tiles){
            candidate.add(tile.getTileNumber());
            Boolean firstHit = false;
            Boolean secondHit = false;
            Boolean thirdHit = false;
            if(candidate.size() == 3){

                for(Integer candidatePart : candidate){
                    if(validSequenceGroupsA.stream().anyMatch(z -> z.getFirstMember().getTileNumber() == candidatePart)){
                        firstHit = true;
                        tileHolder.add(tile);
                    }
                    if(validSequenceGroupsA.stream().anyMatch(z -> z.getSecondMember().getTileNumber() == candidatePart)){
                        secondHit = true;
                        tileHolder.add(tile);
                    }
                    if(validSequenceGroupsA.stream().anyMatch(z -> z.getThirdMember().getTileNumber() == candidatePart)){
                        thirdHit = true;
                        tileHolder.add(tile);
                    }


                }
                if(firstHit && secondHit && thirdHit){
                    possibleGroups.add(new SequenceGroup(tileHolder.get(0),
                            tileHolder.get(1),tileHolder.get(2)));
                } else {

                }

                for (SequenceGroup sequenceGroup : validSequenceGroupsA){

                }
            }

            if(candidate.size() == 2){
                if(Math.abs(candidate.get(0)) - Math.abs(candidate.get(1)) > 2){
                    candidate.clear();
                }
            }
        }
    }
}
