/**
 * Created by DrownFish on 2017/3/17.
 */
public class CircleFactory implements ShapeFactory {
    @Override
    public Shape getShape() {
        Shape shape = new Circle();
        return  shape;
    }
}
