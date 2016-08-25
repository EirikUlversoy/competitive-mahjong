import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scorefinder {
    private ValuationFu valuationFu;
    private ValuationHan valuationHan;
    private boolean dealer;
    private boolean closed;
    private boolean tsumo;
    private Hand hand;
    private Integer fu;
    private Integer han;
    public Scorefinder(Hand hand,boolean dealer, boolean tsumo){
        this.valuationFu = new ValuationFu();
        this.valuationHan = new ValuationHan();
        this.hand = hand;
        Integer fu = valuationFu.calculateFu(hand);
        Integer han = valuationHan.calculateHan(hand.getTiles(),hand.closed);

        this.closed = hand.closed;
        this.dealer = dealer;
        this.tsumo = tsumo;

    }

    public Double findBasicPoints(){
        Double baseValue = (this.fu * Math.pow(2, 2 + this.han));
        Double modulus = baseValue % 100;
        Double difference = 100-modulus;
        baseValue += difference;
        return baseValue;
    }
    public Map<Integer,Integer> getPayables(){
        Map<Integer,Integer> playerIdToMultiplier = new HashMap<>();
        String method = this.delegateMethod(this.dealer,this.tsumo);
        if(method == "ndTsumo"){
            playerIdToMultiplier.put(0,2);
            playerIdToMultiplier.put(1,1);
            playerIdToMultiplier.put(2,1);
            playerIdToMultiplier.put(3,0);
        } else if (method == "ndDiscard") {
            playerIdToMultiplier.put(0,4);
            playerIdToMultiplier.put(1,0);
            playerIdToMultiplier.put(2,0);
            playerIdToMultiplier.put(3,0);
        } else if (method == "dTsumo") {
            playerIdToMultiplier.put(0,2);
            playerIdToMultiplier.put(1,2);
            playerIdToMultiplier.put(2,2);
            playerIdToMultiplier.put(3,0);
        } else if (method == "dDiscard"){
            playerIdToMultiplier.put(0,6);
            playerIdToMultiplier.put(1,0);
            playerIdToMultiplier.put(2,0);
            playerIdToMultiplier.put(3,0);
        }
        return playerIdToMultiplier;
    }

    public String delegateMethod(boolean dealer, boolean tsumo){
        if(dealer){
            if(tsumo){
                return "dTsumo";
            } else {
                return "dDiscard";
            }
        } else {
            if(tsumo){
                return "ndTsumo";
            } else {
                return "ndDiscard";
            }
        }
    }
}
