/**
 * Created by DrownFish on 2017/3/31.
 */
public abstract class Burger implements Item{

    @Override
    public Packing packing() {
        return new Wrapper();
    }

    @Override
    public abstract  float price();

}
