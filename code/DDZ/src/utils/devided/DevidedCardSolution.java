package utils.devided;

import utils.OneSendCard;

import java.util.Iterator;
import java.util.List;

public class DevidedCardSolution {
    private List<OneSendCard> oneSendCards = null;
    private List<OneSendCard> singleOrDouble = null;

    public DevidedCardSolution() {
    }

    public double getSingleCount() {
        double initCount = (double)this.singleOrDouble.size();
        OneSendCard osc;
        Iterator var4;
        if(this.oneSendCards != null) {
            var4 = this.oneSendCards.iterator();

            while(var4.hasNext()) {
                osc = (OneSendCard)var4.next();
                if(osc.getCardType().equals("c4")) {
                    osc.getOneSendCardList().size();
                    initCount -= 2.0D;
                } else if(osc.getCardType().equals("c111222")) {
                    initCount -= (double)(osc.getOneSendCardList().size() / 3);
                } else if(osc.getCardType().equals("c3")) {
                    --initCount;
                }
            }
        }

        if(this.oneSendCards != null) {
            var4 = this.oneSendCards.iterator();

            while(var4.hasNext()) {
                osc = (OneSendCard)var4.next();
                if(osc.getLittleRate() == 0.0D) {
                    initCount -= 0.5D;
                }
            }
        }

        if(this.singleOrDouble != null) {
            var4 = this.singleOrDouble.iterator();

            while(var4.hasNext()) {
                osc = (OneSendCard)var4.next();
                if(osc.getLittleRate() == 0.0D) {
                    --initCount;
                }
            }
        }

        return initCount;
    }

    public List<OneSendCard> getOneSendCards() {
        return this.oneSendCards;
    }

    public void setOneSendCards(List<OneSendCard> oneSendCards) {
        this.oneSendCards = oneSendCards;
    }

    public List<OneSendCard> getSingleOrDouble() {
        return this.singleOrDouble;
    }

    public void setSingleOrDouble(List<OneSendCard> singleOrDouble) {
        this.singleOrDouble = singleOrDouble;
    }

    public String toString() {
        String total = "";
        OneSendCard osc;
        Iterator var3;
        if(this.oneSendCards != null) {
            for(var3 = this.oneSendCards.iterator(); var3.hasNext(); total = total + osc.getCardType() + ":" + osc.toString()) {
                osc = (OneSendCard)var3.next();
            }
        }

        if(this.singleOrDouble != null) {
            for(var3 = this.singleOrDouble.iterator(); var3.hasNext(); total = total + osc.getCardType() + ":" + osc.toString()) {
                osc = (OneSendCard)var3.next();
            }
        }

        return total;
    }

    public double getSendCount() {
        int count = 0;
        if(this.oneSendCards != null) {
            count = this.oneSendCards.size();
        }

        return (double)count + this.getSingleCount();
    }
}
