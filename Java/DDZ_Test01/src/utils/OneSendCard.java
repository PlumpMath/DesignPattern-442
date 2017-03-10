package utils;

import utils.cardType.CardTypeFactory;
import view.Home;
import vo.Card;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by DrownFish on 2017/3/10.
 */

public class OneSendCard {
    private List<Card> oneSendCardList;
    private String cardType;
    private int whoSend;

    public double getBiggerRate() {
        if(this.cardType.equals("c4")) {
            return 1.0D;
        } else if(!this.cardType.equals("c411") && !this.cardType.equals("c422")) {
            Map total;
            int maxThree;
            Entry minList;
            Iterator minValue;
            int temp;
            List minList1;
            OneSendCard minValue1;
            if(!this.cardType.equals("c111222") && !this.cardType.equals("c11122234") && !this.cardType.equals("c1112223344")) {
                if(!this.cardType.equals("c112233") && !this.cardType.equals("c123")) {
                    double bigger;
                    double total3;
                    double minValue3;
                    if(this.cardType.equals("c3")) {
                        total3 = 13.0D;
                        minList1 = CardUtil.getLeastFromCardList(this.oneSendCardList, 1);
                        minValue3 = (double)((Integer)minList1.get(0)).intValue();
                        bigger = minValue3 - 3.0D;
                        return bigger / total3;
                    } else if(!this.cardType.equals("c31") && !this.cardType.equals("c32")) {
                        if(!this.cardType.equals("c2") && !this.cardType.equals("c1")) {
                            return 0.0D;
                        } else {
                            total3 = 15.0D;
                            minList1 = CardUtil.getLeastFromCardList(this.oneSendCardList, 1);
                            minValue3 = (double)((Integer)minList1.get(0)).intValue();
                            if(this.cardType.equals("c1") && minValue3 == 17.0D) {
                                return 1.0D;
                            } else if(this.cardType.equals("c2") && minValue3 == 15.0D) {
                                return 1.0D;
                            } else {
                                bigger = minValue3 - 3.0D;
                                return bigger / total3;
                            }
                        }
                    } else {
                        total = CardUtil.asValueStaticCount(this.oneSendCardList);
                        maxThree = 0;
                        minValue = total.entrySet().iterator();

                        while(minValue.hasNext()) {
                            minList = (Entry)minValue.next();
                            if(((Integer)minList.getValue()).intValue() == 3) {
                                temp = Integer.parseInt((String)minList.getKey());
                                maxThree = temp;
                            }
                        }

                        minList1 = CardUtil.getCardListByValueAndCount(this.oneSendCardList, maxThree, 3);
                        minValue1 = new OneSendCard(minList1, "c3");
                        return minValue1.getBiggerRate();
                    }
                } else {
                    boolean total1 = false;
                    int total2;
                    if(this.cardType.equals("c112233")) {
                        total2 = this.oneSendCardList.size() / 2;
                    } else {
                        total2 = this.oneSendCardList.size();
                    }

                    double maxThree1 = (double)(13 - total2);
                    List minValue2 = CardUtil.getLeastFromCardList(this.oneSendCardList, 1);
                    double temp1 = (double)((Integer)minValue2.get(0)).intValue();
                    double bigger1 = temp1 - 3.0D;
                    return bigger1 / maxThree1;
                }
            } else {
                total = CardUtil.asValueStaticCount(this.oneSendCardList);
                maxThree = 0;
                minValue = total.entrySet().iterator();

                while(minValue.hasNext()) {
                    minList = (Entry)minValue.next();
                    if(((Integer)minList.getValue()).intValue() == 3) {
                        temp = Integer.parseInt((String)minList.getKey());
                        if(temp > maxThree) {
                            maxThree = temp;
                        }
                    }
                }

                minList1 = CardUtil.getCardListByValueAndCount(this.oneSendCardList, maxThree, 3);
                minValue1 = new OneSendCard(minList1, "c3");
                return minValue1.getBiggerRate();
            }
        } else {
            return 1.0D;
        }
    }

    public double getLittleRate() {
        OneSendCard osc1;
        OneSendCard osc2;
        if(Home.main.turn == 0) {
            osc1 = null;
            osc2 = null;
            if(!Home.main.isFriend(0, 1)) {
                osc1 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[1], this);
            } else {
                osc1 = null;
            }

            if(!Home.main.isFriend(0, 2)) {
                osc2 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[2], this);
            } else {
                osc2 = null;
            }

            return osc1 == null && osc2 == null?0.0D:1.0D;
        } else if(Home.main.turn == 1) {
            osc1 = null;
            osc2 = null;
            if(!Home.main.isFriend(1, 0)) {
                osc1 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[0], this);
            } else {
                osc1 = null;
            }

            if(!Home.main.isFriend(1, 2)) {
                osc2 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[2], this);
            } else {
                osc2 = null;
            }

            return osc1 == null && osc2 == null?0.0D:1.0D;
        } else if(Home.main.turn == 2) {
            osc1 = null;
            osc2 = null;
            if(!Home.main.isFriend(2, 0)) {
                osc1 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[0], this);
            } else {
                osc1 = null;
            }

            if(!Home.main.isFriend(2, 1)) {
                osc2 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[1], this);
            } else {
                osc2 = null;
            }

            return osc1 == null && osc2 == null?0.0D:1.0D;
        } else {
            return 1.0D;
        }
    }

    public OneSendCard(List<Card> list, String cardType) {
        this.oneSendCardList = list;
        this.cardType = cardType;
    }

    public List<Card> getOneSendCardList() {
        return this.oneSendCardList;
    }

    public void setOneSendCardList(List<Card> oneSendCardList) {
        this.oneSendCardList = oneSendCardList;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getWhoSend() {
        return this.whoSend;
    }

    public void setWhoSend(int whoSend) {
        this.whoSend = whoSend;
    }

    public String toString() {
        String total = "";

        Card bc;
        for(Iterator var3 = this.oneSendCardList.iterator(); var3.hasNext(); total = total + String.valueOf(bc.getValue())) {
            bc = (Card)var3.next();
        }

        return total;
    }
}
