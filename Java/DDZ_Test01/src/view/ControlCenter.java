package view;

import java.util.ArrayList;

/**
 * Created by DrownFish on 2017/4/7.
 */
public abstract class ControlCenter {
    protected ArrayList<Observer> players = new ArrayList<Observer>();
    public void join(Observer observer){
        players.add(observer);
    }
    public void quit(Observer observer){
        players.remove(observer);
    }
    public abstract void notifyObserver(String name);
}
