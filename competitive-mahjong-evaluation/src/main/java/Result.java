import java.util.Map;

public class Result {
    private Map<Integer, Integer> playerIdToMultiplier;
    private Double basicPoints;

    public Result(Double basicPoints, Map<Integer, Integer> playerIdToMultiplier){
        this.playerIdToMultiplier = playerIdToMultiplier;
        this.basicPoints = basicPoints;

    }

    public double getScorePayable(Integer playerId){
        return playerIdToMultiplier.get(playerId).doubleValue()*basicPoints;
    }

    public double getBasicPoints(){
        return basicPoints;
    }

}
