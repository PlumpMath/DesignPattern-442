package DesignPattern.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrownFish on 2017/4/10.
 */
public abstract class AbstractObjectList {
    protected List<Object> objects = new ArrayList<Object>();
    public AbstractObjectList(List objects){
        this.objects = objects;
    }
    public void addObject(Object object){
        this.objects.add(object);
    }
    public void removeObject(Object object){
        this.objects.remove(object);
    }

    public List<Object> getObjects() {
        return objects;
    }

    public abstract AbstractIterator createrIterator();
}
