
public class Position {
    private String location = "NOT-PLACED";
    private int number = -1;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String toString(){
        return "".concat("Number:" + number + "@Location:" +location);
    }

}
