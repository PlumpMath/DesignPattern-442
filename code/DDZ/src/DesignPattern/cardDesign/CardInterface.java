package DesignPattern.cardDesign;

/**
 * Created by DrownFish on 2017/4/17.
 */
public interface CardInterface {
    static String rearPNG = "images/rear.png";
    public void turnFront();
    public void turnRear();
    public void move();
}
