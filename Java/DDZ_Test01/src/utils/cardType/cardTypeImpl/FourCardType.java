package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import vo.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * 三张牌的牌型
 * 
 * @author Administrator
 *
 */
public class FourCardType implements utils.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		Card c1 = a.get(0);
		Card c2 = b.get(0);
		if (c1.getValue() == 16 || c1.getValue() == 17) {
			return 1;
		}
		if (c2.getValue() == 16 || c2.getValue() == 17) {
			return 0;
		}
		if (c1.getValue() > c2.getValue()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String getName() {
		return CardTypeString.FOUR_CARDTYPE;
	}

	@Override
	public int getLength() {
		return 4;
	}

	@Override
	public boolean matches(List<Card> list) {
		if (list != null) {
			if (list.size() == 4) {
				int value1 = list.get(0).getValue();
				int value2 = list.get(1).getValue();
				int value3 = list.get(2).getValue();
				int value4 = list.get(3).getValue();
				if (value1 == value2 && value2 == value3 && value3 == value4) {
					return true;
				}
			} else if (list.size() == 2) {
				int value1 = list.get(0).getValue();
				int value2 = list.get(1).getValue();
				if ((value1 == 16 || value1 == 17) && (value2 == 16 || value2 == 17)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getMinLength() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		OneSendCard osc = null;
		Card b = preOneSendCard.getOneSendCardList().get(0);

		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		List fourList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 4) {
				fourList.add(Integer.parseInt(entry.getKey().toString()));
			}
		}
		if (fourList.size() == 0) {
			// 如果没有4张相同牌
			return getKingBomb(playerList);
		} else {
			// 如果有4张相同牌，找出牌中最小一个，但是比b的值大的那个
			int biggerButLeast = CardUtil.getBiggerButLeastFromList(fourList, b.getValue());
			if (biggerButLeast > 0) {
				// 找到且大于牌值
				List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 4);
				osc = new OneSendCard(needList, CardTypeString.FOUR_CARDTYPE);
				return osc;
			} else {
				// 找到但是没有找到牌值大的
				return getKingBomb(playerList);
			}

		}
	}

	/**
	 * 找王炸弹，如果没有返回null
	 * 
	 * @param playerList
	 * @return
	 */
	private OneSendCard getKingBomb(List<Card> playerList) {
		// TODO Auto-generated method stub
		OneSendCard oneSendCard = null;
		Card c1 = null;
		Card c2 = null;
		for (Card bc : playerList) {
			if (bc.getValue() == 16) {
				c1 = bc;
			}
			if (bc.getValue() == 17) {
				c2 = bc;
			}
		}
		if (c1 != null && c2 != null) {
			List<Card> cardList = new ArrayList<Card>();
			cardList.add(c1);
			cardList.add(c2);
			oneSendCard = new OneSendCard(cardList, CardTypeString.FOUR_CARDTYPE);
			return oneSendCard;
		} else {
			return null;
		}
	}
}
