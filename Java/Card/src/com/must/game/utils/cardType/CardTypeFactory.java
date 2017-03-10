package com.must.game.utils.cardType;

import java.util.ArrayList;
import java.util.List;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.cardTypeImpl.CardTypeString;
import com.must.game.utils.cardType.cardTypeImpl.DoubleCardType;
import com.must.game.utils.cardType.cardTypeImpl.FourCardType;
import com.must.game.utils.cardType.cardTypeImpl.FourOneOneCardType;
import com.must.game.utils.cardType.cardTypeImpl.FourTwoTwoCardType;
import com.must.game.utils.cardType.cardTypeImpl.OneOneTwoTwoCardType;
import com.must.game.utils.cardType.cardTypeImpl.OneTwoThreeCardType;
import com.must.game.utils.cardType.cardTypeImpl.PlaneCardType;
import com.must.game.utils.cardType.cardTypeImpl.PlaneOneCardType;
import com.must.game.utils.cardType.cardTypeImpl.PlaneTwoCardType;
import com.must.game.utils.cardType.cardTypeImpl.SingleCardType;
import com.must.game.utils.cardType.cardTypeImpl.ThreeAndOneCardType;
import com.must.game.utils.cardType.cardTypeImpl.ThreeAndTwoCardType;
import com.must.game.utils.cardType.cardTypeImpl.ThreeCardType;
import com.must.game.utils.devided.DevidedCardSolution;
import com.must.game.utils.devided.DevidedCardSolutionFactory;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * ���͹�������
 * 
 * @author Administrator
 *
 */
public class CardTypeFactory {
	public static String[] cardTypeStrings = { CardTypeString.SINGLE_CARDTYPE };
	/**
	 * ׼����������
	 */
	public static ICardType[] cardTypes = { new SingleCardType(), new DoubleCardType(), new ThreeCardType(),
			new FourCardType(), new ThreeAndOneCardType(), new ThreeAndTwoCardType(), new FourOneOneCardType(),
			new FourTwoTwoCardType(), new OneTwoThreeCardType(), new OneOneTwoTwoCardType(), new PlaneCardType(),
			new PlaneOneCardType(), new PlaneTwoCardType() };

	/**
	 * ���ݴ�����ַ�����������
	 * 
	 * @param cardTypeString
	 *            ���͵��ַ���
	 * @return ���͵�ʵ��
	 */
	public static ICardType getCardTypeByCardTypeString(String cardTypeString) {

		if (cardTypeString.equals(CardTypeString.SINGLE_CARDTYPE)) {
			return new SingleCardType();
		} else if (cardTypeString.equals(CardTypeString.DOUBLE_CARDTYPE)) {
			return new DoubleCardType();
		} else if (cardTypeString.equals(CardTypeString.THREE_CARDTYPE)) {
			return new ThreeCardType();
		} else if (cardTypeString.equals(CardTypeString.FOUR_CARDTYPE)) {
			return new FourCardType();
		} else if (cardTypeString.equals(CardTypeString.THREEANDONE_CARDTYPE)) {
			return new ThreeAndOneCardType();
		} else if (cardTypeString.equals(CardTypeString.THREEANTWO_CARDTYPE)) {
			return new ThreeAndTwoCardType();
		} else if (cardTypeString.equals(CardTypeString.FOURONEONE_CARDTYPE)) {
			return new FourOneOneCardType();
		} else if (cardTypeString.equals(CardTypeString.FOURTWOTWO_CARDTYPE)) {
			return new FourTwoTwoCardType();
		} else if (cardTypeString.equals(CardTypeString.ONETWOTHREE_CARDTYPE)) {
			return new OneTwoThreeCardType();
		} else if (cardTypeString.equals(CardTypeString.ONEONETWOTWO_CARDTYPE)) {
			return new OneOneTwoTwoCardType();
		} else if (cardTypeString.equals(CardTypeString.PLANE_CARDTYPE)) {
			return new PlaneCardType();
		} else if (cardTypeString.equals(CardTypeString.PLANEONE_CARDTYPE)) {
			return new PlaneOneCardType();
		} else if (cardTypeString.equals(CardTypeString.PLANEONE_CARDTYPE)) {
			return new PlaneOneCardType();
		} else if (cardTypeString.equals(CardTypeString.PLANETWO_CARDTYPE)) {
			return new PlaneTwoCardType();
		}
		return null;
	}

	/**
	 * ͨ���������ͣ����ظ������ַ���
	 * 
	 * @param list
	 * @return
	 */
	public static String getCardType(List<Card> list) {
		for (ICardType cardType : cardTypes) {
			if (cardType.matches(list)) {
				return cardType.getName();
			}
		}
		return CardTypeString.NONE_CARDTYPE;
	}

	/**
	 * ��������ѳ��ƣ������˳�ƣ���˳�ƣ����絥�ƣ�Ҫ˳��
	 * 
	 * @param playerList
	 *            ���е���
	 * @param preOneSendCard
	 *            ��һ�γ�����
	 * @return
	 */
	public static OneSendCard getFriendBiggerOneSendCard(List<Card> playerList, OneSendCard preOneSendCard) {
		return null;
	}

	/**
	 * �ҳ�ѹ�Ƶĳ��ƣ����ȴ����ŷ������ܹ�ѹ�Ƶ� OneSendCard����������ڣ����ٵ���
	 * getOneSendCardBiggerButleast�ҵ�һ������ѹ�Ƶ�OneSendCard
	 * 
	 * @param playerList
	 *            ���е���
	 * @param preOneSendCard
	 *            ��һ�γ�����
	 * @return
	 */
	public static OneSendCard getBiggerOneSendCard(List<Card> playerList, OneSendCard preOneSendCard) {
		DevidedCardSolutionFactory dcsf = DevidedCardSolutionFactory.getInstance();
		List<DevidedCardSolution> solutions = new ArrayList<DevidedCardSolution>();
		DevidedCardSolution currSolution = null;
		dcsf.getDevidedCardSolution(playerList, solutions, currSolution);
		DevidedCardSolution bestSolution = dcsf.getBestDevidedCardSolution(solutions);
		List<DevidedCardSolution> allBestSolution = dcsf.getAllBestDevidedCardSolution(solutions);
		List<OneSendCard> biggerList = new ArrayList<OneSendCard>();

		// ��û��ƥ�䵥��ʱ��
		if (preOneSendCard.getCardType().equals(CardTypeString.SINGLE_CARDTYPE)
				|| preOneSendCard.getCardType().equals(CardTypeString.DOUBLE_CARDTYPE)) {
			// �ڵ��ƺ�˫������
			for (DevidedCardSolution tempSolution : allBestSolution) {
				if (tempSolution.getSingleOrDouble() != null) {
					for (OneSendCard osc : tempSolution.getSingleOrDouble()) {
						if (osc.getCardType().equals(preOneSendCard.getCardType())) {
							// ���������ͬ
							ICardType cardType = CardTypeFactory
									.getCardTypeByCardTypeString(preOneSendCard.getCardType());
							if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
								// ������ڣ�����뵽biggerList��
								biggerList.add(osc);
							}
						}
					}
				}
			}

		} else if (preOneSendCard.getCardType().equals(CardTypeString.THREEANDONE_CARDTYPE)
				|| preOneSendCard.getCardType().equals(CardTypeString.THREEANTWO_CARDTYPE)
				|| preOneSendCard.getCardType().equals(CardTypeString.PLANEONE_CARDTYPE)
				|| preOneSendCard.getCardType().equals(CardTypeString.PLANETWO_CARDTYPE)) {
			// ������⼸�����ͣ���Ҫ��ƥ���˵��ƺ���
			dcsf.dispatchSingleOrDouble(bestSolution);
			// �ڷǵ��ƺͷ�˫������
			for (DevidedCardSolution tempSolution : allBestSolution) {
				if (tempSolution.getOneSendCards() != null) {
					for (OneSendCard osc : tempSolution.getOneSendCards()) {
						if (osc.getCardType().equals(preOneSendCard.getCardType())) {
							// ���������ͬ
							ICardType cardType = CardTypeFactory
									.getCardTypeByCardTypeString(preOneSendCard.getCardType());
							if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
								// ������ڣ�����뵽biggerList��
								biggerList.add(osc);
							}
						}
					}
				}
			}
		} else {
			// �ڷǵ��ƺͷ�˫������
			for (DevidedCardSolution tempSolution : allBestSolution) {
				if (tempSolution.getOneSendCards() != null) {
					for (OneSendCard osc : tempSolution.getOneSendCards()) {
						if (osc.getCardType().equals(preOneSendCard.getCardType())) {
							// ���������ͬ
							ICardType cardType = CardTypeFactory
									.getCardTypeByCardTypeString(preOneSendCard.getCardType());
							if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
								// ������ڣ�����뵽biggerList��
								biggerList.add(osc);
							}
						}
					}
				}
			}
		}
		// ������ŷ�������ͨ�����Ҳ��������������ŷ����е�ը������
		if (bestSolution.getOneSendCards() != null) {
			for (OneSendCard osc : bestSolution.getOneSendCards()) {
				if (osc.getCardType().equals(CardTypeString.FOUR_CARDTYPE)) {
					// �����ը��
					ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(preOneSendCard.getCardType());
					if (!preOneSendCard.getCardType().equals(CardTypeString.FOUR_CARDTYPE)) {
						// ���֮ǰ����ը��������뵽biggerList��
						biggerList.add(osc);
					} else {
						// ���֮ǰҲ��ը��
						if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
							biggerList.add(osc);
						}
					}
				}
			}
		}
		if (biggerList.size() == 0) {
			// ���ŷ������Ҳ�������pre OneSendCard�����ͣ����������getOneSendCardBiggerButleast
			return getOneSendCardBiggerButleast(playerList, preOneSendCard);
		} else {

			// �ҵ��˴���
			if (biggerList.size() == 1) {
				return biggerList.get(0);
			} else {
				// ����ҵ�����������1
				// ͨ���Ƚϴ�С�ķ�ʽ���ҵ�����������С���Ǹ�
				ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(preOneSendCard.getCardType());
				OneSendCard osc = biggerList.get(0);
				for (OneSendCard temp : biggerList) {
					if (cardType.compare(osc.getOneSendCardList(), temp.getOneSendCardList()) > 0) {
						osc = temp;
					}
				}
				return osc;
			}
		}
	}

	/**
	 * �����е������ҵ�����һ�γ��ƴ������С���Ǹ�һ�γ���
	 * 
	 * @param playerList
	 *            ���е���
	 * @param preOneSendCard
	 *            ��һ�γ�����
	 * @return
	 */
	public static OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(preOneSendCard.getCardType());
		OneSendCard ret = null;
		if (playerList.size() < cardType.getMinLength()) {
			ret = null;
		} else {
			ret = cardType.getOneSendCardBiggerButleast(playerList, preOneSendCard);
		}
		// ������Ͳ���ը�����Ұ������Ҳ�������ѹ�Ƶ��ƣ�����ը��
		if ((!cardType.getName().equals(CardTypeString.FOUR_CARDTYPE)) && ret == null) {
			ICardType bomb = getCardTypeByCardTypeString(CardTypeString.FOUR_CARDTYPE);
			// ����һ�������ڵ�ը����2222����ը�����ͼ�������������
			List<Card> cardList = new ArrayList<Card>();
			for (int i = 0; i < 4; i++) {
				Card bc = new Card(null, 2, 1, false);
				cardList.add(bc);
			}
			OneSendCard virtual = new OneSendCard(cardList, CardTypeString.FOUR_CARDTYPE);
			ret = bomb.getOneSendCardBiggerButleast(playerList, virtual);
		}
		return ret;
	}

	/**
	 * �Ƚ����γ��ƵĴ�С
	 * 
	 * @param oneSendCard
	 * @param preOneSendCard
	 * @return 1������ 0��С�ڻ���� -1�����Ͳ�ƥ��
	 */
	public static int compareOneSendCard(OneSendCard oneSendCard, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		String cardType1 = oneSendCard.getCardType();
		String cardType2 = preOneSendCard.getCardType();
		if (!cardType1.equals(cardType2) && !cardType1.equals(CardTypeString.FOUR_CARDTYPE)) {
			// ����������Ͳ�һ������cardType1����ը��
			return -1;
		} else if (!cardType1.equals(cardType2) && cardType1.equals(CardTypeString.FOUR_CARDTYPE)) {
			// ����������Ͳ�һ������cardType1��ը��
			return 1;
		} else {
			ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(cardType1);
			return cardType.compare(oneSendCard.getOneSendCardList(), preOneSendCard.getOneSendCardList());
		}
	}

	/**
	 * ������������
	 * 
	 * @param list
	 * @return
	 */
	public static OneSendCard getFirstBestOneSendCard(List<Card> playerList) {
		DevidedCardSolutionFactory dcsf = DevidedCardSolutionFactory.getInstance();
		List<DevidedCardSolution> solutions = new ArrayList<DevidedCardSolution>();
		DevidedCardSolution currSolution = null;
		dcsf.getDevidedCardSolution(playerList, solutions, currSolution);
		DevidedCardSolution bestSolution = dcsf.getBestDevidedCardSolution(solutions);
		OneSendCard osc = dcsf.getFirstOneSendCard(bestSolution);
		return osc;
	}

	/**
	 * ��ȡ����������ʾ�ĳ���
	 * 
	 * @param list
	 * @return
	 */
	public static OneSendCard getAutoPrompt(List<Card> list) {
		DevidedCardSolutionFactory dcsf = DevidedCardSolutionFactory.getInstance();
		List<DevidedCardSolution> solutions = new ArrayList<DevidedCardSolution>();
		DevidedCardSolution currSolution = null;
		dcsf.getDevidedCardSolution(list, solutions, currSolution);
		DevidedCardSolution bestSolution = dcsf.getBestDevidedCardSolution(solutions);
		OneSendCard osc = dcsf.getFirstOneSendCard(bestSolution);
		return osc;
	}

	/**
	 * ��ȡѹ�Ƶ���ʾ��
	 * 
	 * @param list
	 * @return
	 */
	public static OneSendCard getBiggerPrompt(List<Card> list, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		return getBiggerOneSendCard(list, preOneSendCard);
	}
}
