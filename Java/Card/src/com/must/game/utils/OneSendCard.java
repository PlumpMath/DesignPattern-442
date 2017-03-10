package com.must.game.utils;

import java.util.List;
import java.util.Map;
import com.must.game.utils.cardType.CardTypeFactory;
import com.must.game.utils.cardType.cardTypeImpl.CardTypeString;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * һ�γ���
 * 
 * @author Administrator
 *
 */
public class OneSendCard {
	
	/**
	 * һ�γ��Ƶ��б�
	 */
	private List<Card> oneSendCardList;
	/**
	 * ����
	 */
	private String cardType;
	/**
	 * ˭������
	 */
	private int whoSend;

	/**
	 * ����ѹ�Ʊ�ֵ
	 * 
	 * @return
	 */
	public double getBiggerRate() {
		if (cardType.equals(CardTypeString.FOUR_CARDTYPE)) {
			return 1;
		} else if (cardType.equals(CardTypeString.FOURONEONE_CARDTYPE)
				|| cardType.equals(CardTypeString.FOURTWOTWO_CARDTYPE)) {
			return 1;
		} else if (cardType.equals(CardTypeString.PLANE_CARDTYPE) || cardType.equals(CardTypeString.PLANEONE_CARDTYPE)
				|| cardType.equals(CardTypeString.PLANETWO_CARDTYPE)) {
			// �ɻ���ѹ�Ʊ�ֵΪ�������ŵ�ѹ�Ʊ�ֵ
			Map<String, Integer> map = CardUtil.asValueStaticCount(oneSendCardList);
			int maxThree = 0;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() == 3) {
					int temp = Integer.parseInt(entry.getKey());
					if (temp > maxThree) {
						maxThree = temp;
					}
				}
			}
			List<Card> cardList = CardUtil.getCardListByValueAndCount(oneSendCardList, maxThree, 3);
			OneSendCard osc = new OneSendCard(cardList, CardTypeString.THREE_CARDTYPE);
			return osc.getBiggerRate();
		} else if (cardType.equals(CardTypeString.ONEONETWOTWO_CARDTYPE)
				|| cardType.equals(CardTypeString.ONETWOTHREE_CARDTYPE)) {
			int count = 0;
			if (cardType.equals(CardTypeString.ONEONETWOTWO_CARDTYPE)) {
				count = oneSendCardList.size() / 2;
			} else {
				count = oneSendCardList.size();
			}
			// ���ж�����
			double total = 13 - count;
			// ��ѹ��������
			List<Integer> minList = CardUtil.getLeastFromCardList(oneSendCardList, 1);
			double minValue = minList.get(0);
			double bigger = minValue - 3;
			return bigger / total;
		} else if (cardType.equals(CardTypeString.THREE_CARDTYPE)) {
			double total = 13;
			List<Integer> minList = CardUtil.getLeastFromCardList(oneSendCardList, 1);
			double minValue = minList.get(0);
			double bigger = minValue - 3;
			return bigger / total;
		} else if (cardType.equals(CardTypeString.THREEANDONE_CARDTYPE)
				|| cardType.equals(CardTypeString.THREEANTWO_CARDTYPE)) {
			Map<String, Integer> map = CardUtil.asValueStaticCount(oneSendCardList);
			int maxThree = 0;
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				if (entry.getValue() == 3) {
					int temp = Integer.parseInt(entry.getKey());
					maxThree = temp;
				}
			}
			List<Card> cardList = CardUtil.getCardListByValueAndCount(oneSendCardList, maxThree, 3);
			OneSendCard osc = new OneSendCard(cardList, CardTypeString.THREE_CARDTYPE);
			return osc.getBiggerRate();
		} else if (cardType.equals(CardTypeString.DOUBLE_CARDTYPE) || cardType.equals(CardTypeString.SINGLE_CARDTYPE)) {
			double total = 15;
			List<Integer> minList = CardUtil.getLeastFromCardList(oneSendCardList, 1);
			double minValue = minList.get(0);
			if (cardType.equals(CardTypeString.SINGLE_CARDTYPE)) {
				if (minValue == 17) {
					// ����
					return 1;
				}
			}
			if (cardType.equals(CardTypeString.DOUBLE_CARDTYPE)) {
				if (minValue == 15) {
					// ��2
					return 1;
				}
			}
			double bigger = minValue - 3;
			return bigger / total;
		}
		return 0;
	}

	/**
	 * ���㱻ѹ�Ʊ�ֵ��Ҫ�����Լ����ƣ��͵������Ѿ������ƣ����㵱ǰ�Ƶ�ѹ�Ʊ�ֵ
	 * 
	 * @param myCardList
	 *            ��Ŀǰ����
	 * @param hasSendCardList
	 *            Ŀǰ�Ѿ������� *@param otherTwoCardList ʣ�������˵��Ƶĺ�
	 * @return
	 */
	public double getLittleRate() {
		if (Home.main.turn == 0) {
			OneSendCard osc1 = null;
			OneSendCard osc2 = null;
			if (!Home.main.isFriend(0, 1)) {
				osc1 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[1], this);
			} else {
				osc1 = null;
			}
			if (!Home.main.isFriend(0, 2)) {
				osc2 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[2], this);
			} else {
				osc2 = null;
			}
			if (osc1 == null && osc2 == null) {
				// ���������Ҳ��������
				return 0;
			} else {
				return 1;
			}
		}
		if (Home.main.turn == 1) {
			OneSendCard osc1 = null;
			OneSendCard osc2 = null;
			if (!Home.main.isFriend(1, 0)) {
				osc1 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[0], this);
			} else {
				osc1 = null;
			}
			if (!Home.main.isFriend(1, 2)) {
				osc2 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[2], this);
			} else {
				osc2 = null;
			}
			if (osc1 == null && osc2 == null) {
				// ���������Ҳ��������
				return 0;
			} else {
				return 1;
			}
		}
		if (Home.main.turn == 2) {
			OneSendCard osc1 = null;
			OneSendCard osc2 = null;
			if (!Home.main.isFriend(2, 0)) {
				osc1 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[0], this);
			} else {
				osc1 = null;
			}
			if (!Home.main.isFriend(2, 1)) {
				osc2 = CardTypeFactory.getOneSendCardBiggerButleast(Home.main.playerList[1], this);
			} else {
				osc2 = null;
			}
			if (osc1 == null && osc2 == null) {
				// ���������Ҳ��������
				return 0;
			} else {
				return 1;
			}
		}
		return 1;
	}

	public OneSendCard(List<Card> list, String cardType) {
		this.oneSendCardList = list;
		this.cardType = cardType;
	}

	public List<Card> getOneSendCardList() {
		return oneSendCardList;
	}

	public void setOneSendCardList(List<Card> oneSendCardList) {
		this.oneSendCardList = oneSendCardList;

	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public int getWhoSend() {
		return whoSend;
	}

	public void setWhoSend(int whoSend) {
		this.whoSend = whoSend;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String total = "";
		for (Card bc : oneSendCardList) {
			total += String.valueOf(bc.getValue());
		}
		return total;
	}
}
