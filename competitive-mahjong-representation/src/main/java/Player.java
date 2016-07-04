public class Player {
    private Hand hand;
    private Pond pond;
    private String name;
    private Integer playerId;
    private Ruleset ruleset;

    Player(String name, Integer playerId,Pond pond){
        this.name = name;
        this.playerId = playerId;
        this.pond = pond;
        this.ruleset = new Ruleset();
    }

    public void initializeHand(TileSet tileSet){
        this.hand = new Hand(playerId,ruleset.getHandsize());
        hand.initializeHand(tileSet);
    }






}
