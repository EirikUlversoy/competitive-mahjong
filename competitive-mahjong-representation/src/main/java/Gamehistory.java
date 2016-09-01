import java.sql.Timestamp;
import java.util.List;

public class Gamehistory {
    private List<Move> moveList;
    private Integer amountOfPlayers;

    public Gamehistory(Integer amountOfPlayers){
        this.amountOfPlayers = amountOfPlayers;
    }

    public void addMove(Integer direction,Move move){
        moveList.add(move);
    }


}
