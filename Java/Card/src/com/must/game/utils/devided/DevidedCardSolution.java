package com.must.game.utils.devided;

import java.util.List;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.cardTypeImpl.CardTypeString;

/**
 * ���Ʒ�����
 * 
 * @author Administrator
 *
 */
public class DevidedCardSolution {
	/**
	 * ���Ƶ�ÿһ����
	 */
	private List<OneSendCard> oneSendCards = null;
	/**
	 * ʣ��Ķ��ƻ���
	 */
	private List<OneSendCard> singleOrDouble = null;

	/**
	 * ��ȡ��������������ѡ�����ŷ��� ���������� ը���ɽ���������-2 �ɻ���x���������ɽ���������-x��x>2�� ���ſɽ�����������1
	 * ����֮�⣬���������� �����жϱ�ѹ�Ʊ�ֵ�����Ϊ0��-0.5�����singleOrDouble -1.5
	 * 
	 * @return
	 */
	public double getSingleCount() {
		// ��ʼֵΪsingleOrDouble
		double initCount = singleOrDouble.size();
		if (oneSendCards != null) {
			for (OneSendCard osc : oneSendCards) {
				if (osc.getCardType().equals(CardTypeString.FOUR_CARDTYPE)) {
					if (osc.getOneSendCardList().size() == 2) {
						// ����ը���ܴ����ƣ����ǿ�����Ϊ��ѹ�Ʊ�ֵΪ0���ƣ�����
						// ���Ե���1.5����
					}
					initCount = initCount - 2;
				} else if (osc.getCardType().equals(CardTypeString.PLANE_CARDTYPE)) {
					initCount = initCount - osc.getOneSendCardList().size() / 3;
				} else if (osc.getCardType().equals(CardTypeString.THREE_CARDTYPE)) {
					initCount = initCount - 1;
				}
			}
			// ���ڵ��ƺ�˫�ƣ������ѹ�Ʊ�ֵ=0��������Ϊ�������������ҿ��Խ�
		}
		// ����֮�⣬���������� �����жϱ�ѹ�Ʊ�ֵ�����Ϊ0��-0.5�����singleOrDouble -1.5
		if (oneSendCards != null) {
			for (OneSendCard osc : oneSendCards) {
				if (osc.getLittleRate() == 0) {
					// ��ѹ�Ʊ�ֵΪ0��-0.5
					initCount = initCount - 0.5;
				}
			}
		}
		if (this.singleOrDouble != null) {
			for (OneSendCard osc : this.singleOrDouble) {
				if (osc.getLittleRate() == 0) {
					// ��ѹ�Ʊ�ֵΪ0��-0.5
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
	 * ��ȡ�ܵĳ�������=oneSendCards�ĸ���+��������
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
