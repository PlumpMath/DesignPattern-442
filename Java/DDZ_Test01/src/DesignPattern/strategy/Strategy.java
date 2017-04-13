package DesignPattern.strategy;

import DesignPattern.cardDesign.Card;
import utils.OneSendCard;

import java.util.List;

/**
 * Created by DrownFish on 2017/4/10.
 */
public interface Strategy {
    public OneSendCard getPrompt(List<Card> list, OneSendCard preOneSendCard);
}
