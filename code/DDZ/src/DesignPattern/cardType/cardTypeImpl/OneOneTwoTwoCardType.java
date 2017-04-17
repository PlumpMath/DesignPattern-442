package DesignPattern.cardType.cardTypeImpl;

import DesignPattern.cardDesign.Card;
import utils.CardUtil;
import utils.OneSendCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * �����Ƶ�����
 * 
 * @author Administrator
 *
 */
public class OneOneTwoTwoCardType implements DesignPattern.cardType.ICardType {

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
		return CardTypeString.ONEONETWOTWO_CARDTYPE;
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
			if (list.size() >= 6 && list.size() % 2 == 0) {
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
					if (c.getValue() != (minValue + i / 2)) {
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
		// ��ȡ���ӵ������Ǹ�ֵ
		int preMaxValue = intArr[intArr.length - 1];
		int preMinValue = intArr[0];
		int preCount = intArr.length / 2;
		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		if (preMaxValue >= 14) {
			return null;
		} else {
			// ������ֵ�ĸ���
			int count = 0, start = 0;
			// 14��A
			for (int i = preMinValue + 1; i <= 14; i++) {
				Integer o = map.get(String.valueOf(i));
				if (o != null && o > 1) {
					// ������������Ҹ������ڵ���2
					if (start == 0) {
						// ���start�����㣬��start��ֵ
						start = i;
					}
					count++;
				} else {
					// �ϵ�һ����Ҫ���¿�ʼ
					count = 0;
					start = 0;
				}
				if (count == preCount) {
					// �����Ѿ����ˣ��ͷ���
					break;
				}
			}
			List<Card> needList = new ArrayList<Card>();
			if (count >= preCount) {
				// �ҵ�����
				for (int i = start; i < start + preCount; i++) {
					List<Card> list = CardUtil.getCardListByValueAndCount(playerList, i, 2);
					needList.addAll(list);
				}
				OneSendCard osc = new OneSendCard(needList, CardTypeString.ONEONETWOTWO_CARDTYPE);
				return osc;
			} else {
				// û���ҵ����ʵ���
				return null;
			}
		}
	}
}
