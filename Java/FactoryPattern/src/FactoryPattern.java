/**
 * Created by DrownFish on 2017/3/17.
 */
public class FactoryPattern {
    public static void main(String args[]) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        ShapeFactory.getShape("Circle").draw();
        ShapeFactory.getShape("Square").draw();
        ShapeFactory.getShape("Rectangle").draw();
    }
}
