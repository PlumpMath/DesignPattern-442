/**
 * Created by DrownFish on 2017/3/17.
 */
public class ShapeFactory {
    public static Shape getShape(String name) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        return (Shape) Class.forName(name).newInstance();
    }
}
