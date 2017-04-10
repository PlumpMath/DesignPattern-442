package utils.strategy;

import cardDesign.Card;
import utils.OneSendCard;
import utils.cardType.CardTypeFactory;

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
