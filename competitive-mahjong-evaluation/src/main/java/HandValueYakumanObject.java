import com.typesafe.config.Config;

public class HandValueYakumanObject extends HandValueObject {
    private String specialvaluetype;
    private String specialvalue;
    public HandValueYakumanObject(Config handConfig){
        super(handConfig);
        this.specialvaluetype = handConfig.getString("specialvaluetype");
        this.specialvalue = handConfig.getString("specialvalue");
        

    }
}
