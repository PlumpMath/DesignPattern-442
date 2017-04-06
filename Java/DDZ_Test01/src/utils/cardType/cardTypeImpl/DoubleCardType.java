package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import cardDesign.Card;
import java.util.*;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * 两张牌的牌型
 * 
 * @author Administrator
 *
 */
public class DoubleCardType implements utils.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		Card c1 = a.get(0);
		Card c2 = b.get(0);
		if (c1.getValue() > c2.getValue()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String getName() {
		return CardTypeString.DOUBLE_CARDTYPE;
	}

	@Override
	public int getLength() {
		return 2;
	}

	@Override
	public boolean matches(List<Card> list) {
		if (list == null || list.size() != 2) {
			return false;
		} else {
			if (list.get(0).getValue() != list.get(1).getValue()) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public int getMinLength() {
		return 2;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		OneSendCard osc = null;
		Card b = preOneSendCard.getOneSendCardList().get(0);

		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		List doubleList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 2) {
				doubleList.add(Integer.parseInt(entry.getKey().toString()));
			}
		}
		if (doubleList.size() == 0) {
			// 如果没有对牌,就找3张牌
			osc = findTwoInThree(playerList, b.getValue());
		} else {
			// 如果有对牌，找出对牌中最小一个，但是比b的值大的那个
			Object[] objs = doubleList.toArray();
			Arrays.sort(objs);
			int findValue = 0;
			for (Object o : objs) {
				Integer i = Integer.parseInt(String.valueOf(o.toString()));
				if (i > b.getValue()) {
					findValue = i;
					break;
				}
			}
			if (findValue > 0) {
				// 如果找到大于出牌的牌
				List<Card> findList = new ArrayList<Card>();
				int count = 0;
				for (Card bc : playerList) {
					if (bc.getValue() == findValue) {
						findList.add(bc);
						count++;
						if (count == 2) {
							osc = new OneSendCard(findList, CardTypeString.DOUBLE_CARDTYPE);
							return osc;
						}
					}
				}
			} else {
				// 如果没有找到大于出牌的牌
				osc = findTwoInThree(playerList, b.getValue());
			}
		}
		return osc;
	}

	/**
	 * 从数量为3的牌中找一个大于value值的最小两张牌
	 * 
	 * @param playerList
	 * @param value
	 * @return
	 */
	private OneSendCard findTwoInThree(List<Card> playerList, int value) {
		OneSendCard osc = null;
		Map<String, Integer> map3 = CardUtil.asValueStaticCountByValue(playerList, 3);
		Set<String> set = map3.keySet();
		List<Integer> list = new ArrayList<Integer>();
		if (set.size() > 0) {
			// 3张牌中找到
			for (String s : set) {
				list.add(Integer.parseInt(s));
			}
			int biggerButLeast = CardUtil.getBiggerButLeastFromList(list, value);
			if (biggerButLeast > 0) {
				// 找到且大于牌值
				List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 2);
				osc = new OneSendCard(needList, CardTypeString.DOUBLE_CARDTYPE);
				return osc;
			} else {
				// 找到但是没有找到牌值大的
				return null;
			}

		} else {
			// 3张牌中没有找到
			return null;
		}
	}
}
