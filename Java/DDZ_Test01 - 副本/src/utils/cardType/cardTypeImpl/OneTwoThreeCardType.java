package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import vo.Card;
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
public class OneTwoThreeCardType implements utils.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		// TODO Auto-generated method stub
		if (a.size() != b.size()) {
			// ���ӵ�������һ��
			return -1;
		} else {
			// ���a������ƴ���b������ƾͷ���1
			int[] arr1 = new int[a.size()];
			int[] arr2 = new int[b.size()];
			int i = 0;
			for (Card bc : a) {
				arr1[i] = bc.getValue();
				i++;
			}
			int j = 0;
			for (Card bc : b) {
				arr2[j] = bc.getValue();
				j++;
			}
			Arrays.sort(arr1);
			Arrays.sort(arr2);
			int max1 = arr1[arr1.length - 1];
			int max2 = arr2[arr2.length - 1];
			if (max1 > max2) {
				return 1;
			} else {
				return -1;
			}
		}

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return CardTypeString.ONETWOTHREE_CARDTYPE;
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
			if (list.size() >= 5) {
				Object[] cards = list.toArray();
				Arrays.sort(cards);

				for (Object c : cards) {
					Card card = (Card) c;
				}
				boolean b = true;
				for (int i = 1; i < cards.length; i++) {
					Card c1 = (Card) cards[i - 1];
					Card c2 = (Card) cards[i];
					if (c2.getValue() != c1.getValue() + 1) {
						// ��һ��������ǰ���ֵ+1;
						b = false;
					}
				}
				// ������һ������>=2,Ҳ��������
				Card card = (Card) cards[cards.length - 1];
				if (card.getValue() >= 15) {
					b = false;
				}
				return b;
			}
		}
		return false;
	}

	@Override
	public int getMinLength() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		List<Card> preList = preOneSendCard.getOneSendCardList();
		Integer[] intArr = CardUtil.sortCardList(preList);
		// ��ȡ���������Ǹ�ֵ
		int preMaxValue = intArr[intArr.length - 1];
		int preMinValue = intArr[0];
		int preCount = intArr.length;
		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		if (preMaxValue >= 14) {
			return null;
		} else {
			// ������ֵ�ĸ���
			int count = 0;
			int start = 0;
			// 14�� A
			for (int i = preMinValue + 1; i <= 14; i++) {
				// int
				Integer o = map.get(String.valueOf(i));
				if (o != null && o > 0) {
					// �����������
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
					List<Card> list = CardUtil.getCardListByValueAndCount(playerList, i, 1);
					needList.add(list.get(0));
				}
				OneSendCard osc = new OneSendCard(needList, CardTypeString.ONETWOTHREE_CARDTYPE);
				return osc;
			} else {
				// û���ҵ����ʵ���
				return null;
			}
		}
	}
}
