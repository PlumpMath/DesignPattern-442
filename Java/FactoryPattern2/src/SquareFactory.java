/**
 * Created by DrownFish on 2017/3/17.
 */
public class SquareFactory implements ShapeFactory {
    @Override
    public Shape getShape() {
        Shape shape = new Square();
        return shape;
    }
}
