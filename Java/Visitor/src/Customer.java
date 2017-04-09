/**
 * Created by DrownFish on 2017/4/7.
 */
public abstract class Customer {
    private String customerID;
    private String name;
    public abstract void accept(Visitor visitor);
}
