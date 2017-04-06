package cardDesign;

import java.util.Hashtable;

/**
 * Created by DrownFish on 2017/4/6.
 */
public class CardShare {
    //使用享元模式和单例模式，避免相同的牌重复的创建，能够节省内存资源
    private static CardShare cardShare = new CardShare();
    private static Hashtable<String,Card> hashtable;
    public CardShare(){
        hashtable = new Hashtable();

        //初始化黑红梅花片四种牌的每种13张牌
        for(int t = 1; t <= 4; ++t) {
            for(int i = 3; i <= 15; ++i) {
                if(i >= 14 && i <= 15){
                    hashtable.put(t + " " + (i-13),new Card(i, t, false));
                }else{
                    hashtable.put(t + " " + i,new Card(i,t,false));
                }
            }
        }
        //初始化大王和小王
        hashtable.put("smallKing",new Card(16, 5, false));
        hashtable.put("bigKing",new Card(17, 5, false));

    }
    public static CardShare getCardShare(){
        return cardShare;
    }
    public Card getCard(String value){
        return (Card)hashtable.get(value);
    }

}
