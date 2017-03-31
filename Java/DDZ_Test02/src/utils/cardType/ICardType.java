package utils.cardType;

import utils.OneSendCard;
import vo.Card;
import java.util.List;

/**
 * Created by DrownFish on 2017/3/10.
 */

public interface ICardType {
    String getName();

    boolean matches(List<Card> var1);

    int compare(List<Card> var1, List<Card> var2);

    int getLength();

    int getMinLength();

    OneSendCard getOneSendCardBiggerButleast(List<Card> var1, OneSendCard var2);
}
