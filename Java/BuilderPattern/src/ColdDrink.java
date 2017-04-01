/**
 * Created by DrownFish on 2017/3/31.
 */
public class ColdDrink implements Item {
    @Override
    public String name() {
        return null;
    }

    @Override
    public Packing packing() {
        return new Bottle();
    }

    @Override
    public float price() {
        return 0;
    }
}
