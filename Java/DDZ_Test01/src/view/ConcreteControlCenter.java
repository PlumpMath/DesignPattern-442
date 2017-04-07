package view;

/**
 * Created by DrownFish on 2017/4/7.
 */
public class ConcreteControlCenter extends ControlCenter {
    @Override
    public void notifyObserver(String name) {
        for (Observer observer:players){
            if(!observer.getName().equalsIgnoreCase(name)){
                observer.getNotify();
            }
        }
    }
}
