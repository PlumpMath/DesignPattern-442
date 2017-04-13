package DesignPattern.iterator;

import java.util.List;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class CardList extends AbstractObjectList {
    public CardList(List objects) {
        super(objects);
    }

    @Override
    public AbstractIterator createrIterator() {
        return new CardIterator(this);
    }
}
