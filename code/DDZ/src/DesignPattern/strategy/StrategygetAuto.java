package DesignPattern.strategy;

import DesignPattern.cardDesign.Card;
import utils.OneSendCard;
import DesignPattern.cardType.CardTypeFactory;

import java.util.List;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class StrategygetAuto implements Strategy {
    @Override
    public OneSendCard getPrompt(List<Card> list, OneSendCard preOneSendCard) {
        return CardTypeFactory.getAutoPrompt(list);
    }
}
