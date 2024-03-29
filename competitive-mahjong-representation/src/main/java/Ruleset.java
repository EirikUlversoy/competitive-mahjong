
public class Ruleset {
    private Integer suits;
    private Integer colors;
    private Integer winds;
    private Integer flowers;

    private Integer perTile;
    private Integer perSuit;
    private Integer handsize;
    private Integer playerAmount;
    public Integer getPerSuit() {
        return perSuit;
    }

    public void setPerSuit(Integer perSuit) {
        this.perSuit = perSuit;
    }

    public Integer getHandsize() {
        return handsize;
    }

    public void setHandsize(Integer handsize) {
        this.handsize = handsize;
    }

    public Integer getPlayerAmount() {
        return playerAmount;
    }

    public void setPlayerAmount(Integer playerAmount) {
        this.playerAmount = playerAmount;
    }

    Ruleset(){
        this.suits = 3;
        this.colors = 3;
        this.winds = 4;
        this.flowers = 0;
        this.perTile = 4;
        this.perSuit = 9;
        this.handsize = 13;
        this.playerAmount = 4;


    }

    public Integer getPerTile() {
        return perTile;
    }

    public void setPerTile(Integer perTile) {
        this.perTile = perTile;
    }

    public Integer getSuits() {
        return suits;
    }

    public void setSuits(Integer suits) {
        this.suits = suits;
    }

    public Integer getColors() {
        return colors;
    }

    public void setColors(Integer colors) {
        this.colors = colors;
    }

    public Integer getWinds() {
        return winds;
    }

    public void setWinds(Integer winds) {
        this.winds = winds;
    }

    public Integer getFlowers() {
        return flowers;
    }

    public void setFlowers(Integer flowers) {
        this.flowers = flowers;
    }
}
