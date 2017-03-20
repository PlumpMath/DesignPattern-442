/**
 * Created by DrownFish on 2017/3/17.
 */
public class FactoryPattern {
    public static void main(String args[]) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ShapeFactory shapeFactory_Circle = new CircleFactory();
        Shape shape_circle = shapeFactory_Circle.getShape();
        shape_circle.draw();

        ShapeFactory shapeFactory_Square = new SquareFactory();
        Shape shape_square = shapeFactory_Square.getShape();
        shape_square.draw();

        ShapeFactory shapeFactory_Rectangle = new RectangleFactory();
        Shape shape_rectangle = shapeFactory_Rectangle.getShape();
        shape_rectangle.draw();
    }
}
