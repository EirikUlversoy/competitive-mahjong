import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class TileSet {
    private List<Tile> unusedTiles = new ArrayList<Tile>();
    private List<Tile> usedTiles = new ArrayList<Tile>();

    public Integer getAmount(){
        return unusedTiles.size();
    }
    public List<Tile> getRandomTiles(Integer amount){
        int randomNumber = 0;
        Random rnd = new Random();

        List<Tile> returnedTiles = new ArrayList<>();

        for(int i=0;i<amount;i++)
        {
            randomNumber = rnd.ints(1, 0, unusedTiles.size()).findFirst().getAsInt();
            returnedTiles.add(this.unusedTiles.get(randomNumber));
            usedTiles.add(this.unusedTiles.get(randomNumber));
            unusedTiles.remove(randomNumber);

        }

        return returnedTiles;

    };
}
