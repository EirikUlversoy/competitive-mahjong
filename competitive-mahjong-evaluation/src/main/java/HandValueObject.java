import com.typesafe.config.Config;

public class HandValueObject {

    private String name;
    private String japanese_name;

    private Integer value_open;
    private Integer value_closed;

    public HandValueObject(Config handConfig){
        this.name = handConfig.getString("name");
        this.japanese_name = handConfig.getString("japanesename");
        this.value_open = Integer.parseInt(handConfig.getString("valueopen"));
        this.value_closed = Integer.parseInt(handConfig.getString("valueclosed"));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJapanese_name() {
        return japanese_name;
    }

    public void setJapanese_name(String japanese_name) {
        this.japanese_name = japanese_name;
    }

    public Integer getValue_open() {
        return value_open;
    }

    public void setValue_open(Integer value_open) {
        this.value_open = value_open;
    }

    public Integer getValue_closed() {
        return value_closed;
    }

    public void setValue_closed(Integer value_closed) {
        this.value_closed = value_closed;
    }
}
