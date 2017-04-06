package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import cardDesign.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * 飞机牌的牌型
 * 
 * @author Administrator
 *
 */
public class PlaneCardType implements utils.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		// TODO Auto-generated method stub
		Integer[] arr1 = CardUtil.sortCardList(a);
		Integer[] arr2 = CardUtil.sortCardList(b);
		if (arr1.length != arr2.length) {
			return -1;
		} else {
			if (arr1[arr1.length - 1] > arr2[arr2.length - 1]) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return CardTypeString.PLANE_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			// 333444或333444555
			if (list.size() >= 6 && list.size() % 3 == 0) {
				Object[] cards = list.toArray();
				Arrays.sort(cards);
				for (Object c : cards) {
					Card card = (Card) c;
				}
				boolean b = true;
				Card card = (Card) cards[0];
				int minValue = card.getValue();
				for (int i = 0; i < cards.length; i++) {
					Card c = (Card) cards[i];
					if (c.getValue() != (minValue + i / 3)) {
						b = false;
					}
				}

				return b;
			}
		}
		return false;
	}

	@Override
	public int getMinLength() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		List<Card> preList = preOneSendCard.getOneSendCardList();
		Integer[] intArr = CardUtil.sortCardList(preList);
		// 获取连队的最大的那个值
		int preMaxValue = intArr[intArr.length - 1];
		int preMinValue = intArr[0];
		int preCount = intArr.length / 3;
		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		if (preMaxValue >= 14) {
			return null;
		} else {
			// 连续牌值的个数
			int count = 0;
			int start = 0;
			// 14是A
			for (int i = preMinValue + 1; i <= 14; i++) {
				// int
				Integer o = map.get(String.valueOf(i));
				if (o != null && o == 3) {
					// 如果有这张牌且个数大于等于3
					if (start == 0) {
						// 如果start被置零，给start赋值
						start = i;
					}
					count++;
				} else {
					// 断掉一张牌要重新开始
					count = 0;
					start = 0;
				}
				if (count == preCount) {
					// 数量已经够了，就返回
					break;
				}
			}
			List<Card> needList = new ArrayList<Card>();
			if (count >= preCount) {
				// 找到连字
				for (int i = start; i < start + preCount; i++) {
					List<Card> list = CardUtil.getCardListByValueAndCount(playerList, i, 3);
					needList.addAll(list);
				}
				OneSendCard osc = new OneSendCard(needList, CardTypeString.PLANE_CARDTYPE);
				return osc;
			} else {
				// 没有找到合适的牌
				return null;
			}
		}
	}
}
