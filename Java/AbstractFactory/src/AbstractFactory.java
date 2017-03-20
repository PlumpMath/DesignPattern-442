/**
 * Created by DrownFish on 2017/3/17.
 */
public abstract class AbstractFactory {
    abstract Color getColor(String colorType);
    abstract Shape getShape(String shapeType);
}
