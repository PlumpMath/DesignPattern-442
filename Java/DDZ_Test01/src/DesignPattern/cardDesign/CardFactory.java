package DesignPattern.cardDesign;

import java.util.Hashtable;

/**
 * Created by DrownFish on 2017/4/6.
 */
public class CardFactory {
    //使用享元模式，避免相同的牌重复的创建，能够节省内存资源
    private static CardFactory cardFactory = new CardFactory();
    private static Hashtable<String,Card> hashtable;
    public CardFactory(){
        cardFactory.hashtable = new Hashtable();

        //初始化黑红梅花片四种牌的每种13张牌
        for(int t = 1; t <= 4; ++t) {
            for(int i = 3; i <= 15; ++i) {
                if(i >= 14 && i <= 15){
                    cardFactory.hashtable.put(t + " " + (i-13),new Card(i, t, false));
                }else{
                    cardFactory.hashtable.put(t + " " + i,new Card(i,t,false));
                }
            }
        }
        //初始化大王和小王
        cardFactory.hashtable.put("smallKing",new Card(16, 5, false));
        cardFactory.hashtable.put("bigKing",new Card(17, 5, false));

    }
    public static CardFactory getCardFactory(){
        return cardFactory;
    }
    public Card getCard(String value){
        return (Card)hashtable.get(value);
    }

}
