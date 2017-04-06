package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import cardDesign.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */


/**
 * ը���������Ƶ�����
 * 
 * @author Administrator
 *
 */
public class FourTwoTwoCardType implements utils.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		Map<String, Integer> mapA = CardUtil.asValueStaticCount(a);
		Map<String, Integer> mapB = CardUtil.asValueStaticCount(b);
		int keyA = 0;
		int keyB = 0;
		for (Map.Entry<String, Integer> entry : mapA.entrySet()) {
			if (entry.getValue() == 4) {
				keyA = Integer.parseInt(entry.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : mapB.entrySet()) {
			if (entry.getValue() == 4) {
				keyB = Integer.parseInt(entry.getKey());
			}
		}
		if (keyA > keyB) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return CardTypeString.FOURTWOTWO_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			if (list.size() == 8) {
				Map<String, Integer> map = CardUtil.asValueStaticCount(list);
				if (map.entrySet().size() == 3) {
					if (map.containsValue(4) && map.containsValue(2)) {
						// ��3�ݣ�8���ƣ�һ��4����ʣ��2��Ϊ4��������һ��Ϊ2����ʣ���Ƿݱض���2��
						return true;
					}
				} else if (map.entrySet().size() == 2) {
					// ���8�������ݣ��϶�������4����
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getMinLength() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		OneSendCard osc = null;
		// 4��2�У�4�����Ǹ���ֵ
		int preValue = 0;
		Map<String, Integer> mapPre = CardUtil.asValueStaticCount(preOneSendCard.getOneSendCardList());
		for (Map.Entry<String, Integer> entry : mapPre.entrySet()) {
			if (entry.getValue() == 4) {
				preValue = Integer.parseInt(entry.getKey());
				break;
			}
		}

		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		List fourList = new ArrayList<Integer>();
		List twoList = new ArrayList<Integer>();
		List threeList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 4) {
				fourList.add(Integer.parseInt(entry.getKey().toString()));
			}
			if (entry.getValue() == 2) {
				twoList.add(Integer.parseInt(entry.getKey().toString()));
			}
			if (entry.getValue() == 3) {
				threeList.add(Integer.parseInt(entry.getKey().toString()));
			}
		}
		if (fourList.size() == 0) {
			// ���û��4����ͬ��
			return null;
		} else {
			// �����4����ͬ�ƣ��ҳ�������Сһ�������Ǳ�b��ֵ����Ǹ�
			int biggerButLeast = CardUtil.getBiggerButLeastFromList(fourList, preValue);
			if (biggerButLeast > 0) {
				// �ҵ��Ҵ�����ֵ
				List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 4);
				if (twoList.size() > 1) {
					// ��������ŵ��ƾʹ�������С�Ķ���
					List<Integer> leastList = CardUtil.getLeastFromList(twoList, 2);
					for (int i : leastList) {
						List<Card> twoCardList = CardUtil.getCardListByValueAndCount(playerList, i, 2);
						needList.addAll(twoCardList);
					}

				} else {
					// ���û�����Ŷ��ƾͷ���null
					return null;
				}
				osc = new OneSendCard(needList, CardTypeString.THREE_CARDTYPE);
				return osc;
			} else {
				// �ҵ�����û���ҵ���ֵ���
				return null;
			}
		}
	}
}
