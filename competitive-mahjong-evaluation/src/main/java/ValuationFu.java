import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ValuationFu {

    private Integer winningHandFu = 20;
    private Integer menzen_kafu = 10;
    private Integer single_wait = 2;
    private Integer tsumo_value = 2;
    private Integer seven_pairs = 25;
    private Integer open_pinfu = 30;
    private HandIdentifier handIdentifier;
    private HandEvaluator handEvaluator;
    public ValuationFu(){
        this.handEvaluator = new HandEvaluator();
        this.handIdentifier = new HandIdentifier();
    }
    public Integer calculateFu(Hand hand){

        Integer fuValue = 0;

        fuValue += winningHandFu + (hand.tsumo ? tsumo_value : 0) +
                (hand.openPinfu ? open_pinfu : 0 ) + (hand.singleWait ? single_wait : 0)
         + (hand.menzenkafu ? menzen_kafu : 0) + (hand.sevenPairs ? seven_pairs : 0);

        fuValue += fuFromMelds(hand,0,0);

        return fuValue;
    }

    public Integer fuFromMelds(Hand hand, Integer prevailingWind, Integer seatWind){
        Pair pair = hand.getPair();

        Integer fuValue = hand.getSetGroups().stream()
                .map(z -> {
                    if(z.isKAN()){
                        if(z.getFirstMember().getClass() == ColorTile.class
                                || z.getFirstMember().getClass() == WindTile.class
                                || z.getFirstMember().getTileNumber() == 9
                                || z.getFirstMember().getTileNumber() == 1){
                            return z.isClosed() ? 32 : 16;
                        } else {
                            return z.isClosed() ? 16 : 8;

                        }

                    } else {
                        if(z.getFirstMember().getClass() == ColorTile.class
                                || z.getFirstMember().getClass() == WindTile.class
                                || z.getFirstMember().getTileNumber() == 9
                                || z.getFirstMember().getTileNumber() == 1){
                            return z.isClosed() ? 8 : 4;
                        } else {
                            return z.isClosed() ? 4 : 2;
                    }
                }})
                .mapToInt(i -> i).sum();

        if(pair.getFirstMember().getClass() == ColorTile.class){
            fuValue = fuValue + 2;
        }

        if(pair.getFirstMember().getClass() == WindTile.class){
            if(pair.getFirstMember().getTileNumber() == prevailingWind
                    || pair.getFirstMember().getTileNumber() == seatWind){
                fuValue = fuValue + 2;
            }

        }

        return fuValue;


    }
}
