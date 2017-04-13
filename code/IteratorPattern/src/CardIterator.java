import java.util.List;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class CardIterator implements AbstractIterator {
    private CardList cardList;
    private List products;

    private int cursor1;
    private int cursor2;

    public CardIterator(CardList cardList) {
        this.cardList = cardList;
        this.products = cardList.getObjects();
        cursor1 = 0;
        cursor2 = products.size() - 1;
    }

    @Override
    public void next() {
        if(cursor1 < products.size()){
            cursor1++;
        }
    }

    @Override
    public boolean isLast() {
        return cursor1 == products.size();
    }

    @Override
    public void previous() {
        if(cursor2 > -1){
            cursor2--;
        }
    }

    @Override
    public boolean isFirst() {
        return cursor2 == -1;
    }

    @Override
    public Object getNextItem() {
        return products.get(cursor1);
    }

    @Override
    public Object getPreviousItem() {
        return products.get(cursor2);
    }

    public CardList getCardList() {
        return cardList;
    }

    public void setCardList(CardList cardList) {
        this.cardList = cardList;
    }
}
