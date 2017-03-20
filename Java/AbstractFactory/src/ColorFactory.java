/**
 * Created by DrownFish on 2017/3/17.
 */
public class ColorFactory extends AbstractFactory {

    @Override
    Color getColor(String colorType) {
        if(colorType == null){
            return null;
        }

        if(colorType.equalsIgnoreCase("RED")){
            return new Red();

        }else if(colorType.equalsIgnoreCase("GREEN")){
            return new Green();

        }else if(colorType.equalsIgnoreCase("BLUE")){
            return new Blue();
        }

        return null;
    }

    @Override
    Shape getShape(String shapeType) {
        return null;
    }
}
