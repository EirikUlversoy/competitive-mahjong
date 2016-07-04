import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Gameboard {
    private TileSet tileSet;
    private Wall southWall,eastWall,northWall,westWall;
    private Pond southPond,eastPond,northPond,westPond;

    private List<Player> playerList = new ArrayList<>();
    private List<Pond> ponds = new ArrayList<>();

    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Ruleset ruleset = new Ruleset();

    public void startGame(){
        this.tileSet = new TileSet();
        tileSet.initializeTiles();
        initializePonds();
        initializeWall("South");
        initializeWall("East");
        initializeWall("West");
        initializeWall("North");
        initializePlayers(ruleset.getPlayerAmount());
        //System.out.println(ponds.get(0).toString());
        System.out.println(eastWall.toString());


    }
    public void setPlayerName(String name, Integer playerId){


    }
    public void initializePlayers(Integer amountOfPlayers){
        if(!ponds.isEmpty()) {
            for (int i = 1; i <= amountOfPlayers; i++) {
                Player player = new Player(UUID.randomUUID().toString(), i, ponds.get(i-1));
                player.initializeHand(tileSet);
            }
        }
    }

    public void initializeWall(String direction){
        if(direction == "South"){
            southWall = new Wall(direction);
            southWall.generateWall(tileSet);
        } else if(direction =="North"){
            northWall = new Wall(direction);
            northWall.generateWall(tileSet);
        } else if(direction =="East"){
            eastWall = new Wall(direction);
            eastWall.generateWall(tileSet);
        } else if(direction =="West"){
            westWall = new Wall(direction);
            westWall.generateWall(tileSet);
        }
    }

    public void initializePonds(){
        southPond = new Pond("South");
        eastPond = new Pond("East");
        northPond = new Pond("North");
        westPond = new Pond("West");

        ponds.add(southPond);
        ponds.add(eastPond);
        ponds.add(northPond);
        ponds.add(westPond);

    }


}
