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

    private HandEvaluator handEvaluator;
    public ValuationFu(){
        this.handEvaluator = new HandEvaluator();
    }
    public void calculateFu(Hand hand, List<Group> groupList){
    }

    public Integer fuFromMelds(Pair pair, List<Group> groupList, Integer prevailingWind, Integer seatWind){
        Integer fuValue = groupList.stream()
                .filter(z -> z.getClass() == SetGroup.class)
                .map(z -> z.getSetGroup())
                .map(z -> {
                    if(z.isKAN()){
                        if(z.getFirstMember().getClass() == ColorTile.class
                                || z.getFirstMember().getClass() == WindTile.class
                                || z.getFirstMember().getTileNumber() == 9
                                || z.getFirstMember().getTileNumber() == 1){
                            return z.getStatus() ? 32 : 16;
                        } else {
                            return z.getStatus() ? 16 : 8;

                        }

                    } else {
                        if(z.getFirstMember().getClass() == ColorTile.class
                                || z.getFirstMember().getClass() == WindTile.class
                                || z.getFirstMember().getTileNumber() == 9
                                || z.getFirstMember().getTileNumber() == 1){
                            return z.getStatus() ? 8 : 4;
                        } else {
                            return z.getStatus() ? 4 : 2;
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
