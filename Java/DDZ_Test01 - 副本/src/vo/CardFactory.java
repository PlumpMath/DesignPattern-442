package vo;

import java.util.Hashtable;

/**
 * Created by DrownFish on 2017/4/6.
 */
public class CardFactory {
    private static CardFactory cardFactory = new CardFactory();
    private static Hashtable hashtable;
    private CardFactory(){
        hashtable = new Hashtable();

    }
    public static CardFactory getCardFactory(){
        return cardFactory;
    }
    public static CardAbstrct getCard(boolean isOn,int value){
        if(isOn){
            return
        }
        return (CardAbstrct)hashtable.get(isOn);
    }
}
