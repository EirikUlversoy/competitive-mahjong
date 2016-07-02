
public class Ruleset {
    private Integer suits;
    private Integer colors;
    private Integer winds;
    private Integer flowers;

    Ruleset(){
        this.suits = 3;
        this.colors = 3;
        this.winds = 4;
        this.flowers = 0;
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
