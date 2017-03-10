package com.must.game.utils.devided;

import java.util.List;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.cardTypeImpl.CardTypeString;

/**
 * 拆牌方案类
 * 
 * @author Administrator
 *
 */
public class DevidedCardSolution {
	/**
	 * 拆牌的每一手牌
	 */
	private List<OneSendCard> oneSendCards = null;
	/**
	 * 剩余的对牌或单牌
	 */
	private List<OneSendCard> singleOrDouble = null;

	/**
	 * 获取单牌手数，用于选择最优方案 单牌手数， 炸弹可将单牌手数-2 飞机（x连续个）可将单牌手数-x（x>2） 三张可将单牌手数减1
	 * 除此之外，对所有手牌 进行判断被压牌比值，如果为0，-0.5，如果singleOrDouble -1.5
	 * 
	 * @return
	 */
	public double getSingleCount() {
		// 初始值为singleOrDouble
		double initCount = singleOrDouble.size();
		if (oneSendCards != null) {
			for (OneSendCard osc : oneSendCards) {
				if (osc.getCardType().equals(CardTypeString.FOUR_CARDTYPE)) {
					if (osc.getOneSendCardList().size() == 2) {
						// 是王炸不能带单牌，但是可以作为被压牌比值为0的牌，所以
						// 可以抵消1.5张牌
					}
					initCount = initCount - 2;
				} else if (osc.getCardType().equals(CardTypeString.PLANE_CARDTYPE)) {
					initCount = initCount - osc.getOneSendCardList().size() / 3;
				} else if (osc.getCardType().equals(CardTypeString.THREE_CARDTYPE)) {
					initCount = initCount - 1;
				}
			}
			// 对于单牌和双牌，如果被压牌比值=0，则本身不视为单牌手数，并且可以将
		}
		// 除此之外，对所有手牌 进行判断被压牌比值，如果为0，-0.5，如果singleOrDouble -1.5
		if (oneSendCards != null) {
			for (OneSendCard osc : oneSendCards) {
				if (osc.getLittleRate() == 0) {
					// 别压牌比值为0，-0.5
					initCount = initCount - 0.5;
				}
			}
		}
		if (this.singleOrDouble != null) {
			for (OneSendCard osc : this.singleOrDouble) {
				if (osc.getLittleRate() == 0) {
					// 别压牌比值为0，-0.5
					initCount = initCount - 1.5;
				}
			}
		}
		return initCount;
	}

	public List<OneSendCard> getOneSendCards() {
		return oneSendCards;
	}

	public void setOneSendCards(List<OneSendCard> oneSendCards) {
		this.oneSendCards = oneSendCards;
	}

	public List<OneSendCard> getSingleOrDouble() {
		return singleOrDouble;
	}

	public void setSingleOrDouble(List<OneSendCard> singleOrDouble) {
		this.singleOrDouble = singleOrDouble;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String total = "";
		if (oneSendCards != null) {
			for (OneSendCard osc : oneSendCards) {
				total += osc.getCardType() + ":" + osc.toString();
			}
		}
		if (singleOrDouble != null) {
			for (OneSendCard osc : singleOrDouble) {
				total += osc.getCardType() + ":" + osc.toString();
			}
		}
		return total;
	}

	/**
	 * 获取总的出牌手数=oneSendCards的个数+单牌手数
	 * 
	 * @return
	 */
	public double getSendCount() {
		// TODO Auto-generated method stub
		int count = 0;
		if (this.oneSendCards != null) {
			count = this.oneSendCards.size();
		}
		return count + this.getSingleCount();
	}
}
