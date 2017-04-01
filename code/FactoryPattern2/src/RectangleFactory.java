/**
 * Created by DrownFish on 2017/3/17.
 */
public class RectangleFactory implements ShapeFactory {
    @Override
    public Shape getShape() {
        Shape shape = new Rectangle();
        return shape;
    }
}
