package com.must.game.utils.cardType.cardTypeImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.must.game.utils.CardUtil;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.ICardType;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * ը�������ŵ����Ƶ�����
 * 
 * @author Administrator
 *
 */
public class FourOneOneCardType implements ICardType {

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
		return CardTypeString.FOURONEONE_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			if (list.size() == 6) {
				Map<String, Integer> map = CardUtil.asValueStaticCount(list);
				if (map.entrySet().size() == 2 || map.entrySet().size() == 3) {
					if (map.containsValue(4)) {
						return true;
					}
				}
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
		List oneList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 4) {
				fourList.add(Integer.parseInt(entry.getKey().toString()));
			}
			if (entry.getValue() == 1) {
				oneList.add(Integer.parseInt(entry.getKey().toString()));
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
				if (oneList.size() > 1) {
					// ��������ŵ��ƾʹ�������С�ĵ���
					List<Integer> leastList = CardUtil.getLeastFromList(oneList, 2);
					for (int i : leastList) {
						List<Card> oneCardList = CardUtil.getCardListByValueAndCount(playerList, i, 1);
						needList.add(oneCardList.get(0));
					}
				} else {
					// ���û�����ŵ��ƣ��ʹ�ʣ����������������С����
					List<Card> restList = CardUtil.getRestListByRemove(playerList, needList);
					if (restList.size() == 0) {
						return null;
					}
					// �ҵ���һ��
					Card bc = CardUtil.getLeastCardFromCardList(restList);
					needList.add(bc);
					// �ҵ���2��
					List<Card> aList = new ArrayList<Card>();
					aList.add(bc);
					List<Card> restList2 = CardUtil.getRestListByRemove(restList, aList);
					Card bc2 = CardUtil.getLeastCardFromCardList(restList2);
					needList.add(bc2);
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
