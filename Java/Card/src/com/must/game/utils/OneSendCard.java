package com.must.game.utils;

import java.util.List;
import java.util.Map;
import com.must.game.utils.cardType.CardTypeFactory;
import com.must.game.utils.cardType.cardTypeImpl.CardTypeString;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * 一次出牌
 * 
 * @author Administrator
 *
 */
public class OneSendCard {
	
	/**
	 * 一次出牌的列表
	 */
	private List<Card> oneSendCardList;
	/**
	 * 牌型
	 */
	private String cardType;
	/**
	 * 谁出的牌
	 */
	private int whoSend;

	/**
	 * 计算压牌比值
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
			// 飞机的压牌比值为最大的三张的压牌比值
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
			// 共有多少种
			double total = 13 - count;
			// 能压过多少种
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
					// 大王
					return 1;
				}
			}
			if (cardType.equals(CardTypeString.DOUBLE_CARDTYPE)) {
				if (minValue == 15) {
					// 对2
					return 1;
				}
			}
			double bigger = minValue - 3;
			return bigger / total;
		}
		return 0;
	}

	/**
	 * 计算被压牌比值，要根据自己的牌，和地面上已经出的牌，计算当前牌的压牌比值
	 * 
	 * @param myCardList
	 *            我目前的牌
	 * @param hasSendCardList
	 *            目前已经出的牌 *@param otherTwoCardList 剩余两个人的牌的和
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
				// 敌人那里找不到大的牌
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
				// 敌人那里找不到大的牌
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
				// 敌人那里找不到大的牌
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
