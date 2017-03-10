package com.must.game.utils.devided;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.must.game.utils.CardUtil;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.cardTypeImpl.CardTypeString;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * ���ƹ�����ͨ��ָ���Ĳ��ƹ��򣬷��ز��Ʒ���
 * 
 * @author Administrator ��ȡ���Ʒ��� ->�������ų��Ʒ�������������ԽСԽ�ã�-> �����ƺ�˫�Ʒ�����ɻ��ȣ��� -���������ȳ��Ʒ���
 * 
 *
 */
public class DevidedCardSolutionFactory {
	private static DevidedCardSolutionFactory dcsf = null;

	private DevidedCardSolutionFactory() {
	}

	public static DevidedCardSolutionFactory getInstance() {
		if (dcsf == null) {
			dcsf = new DevidedCardSolutionFactory();
		}
		return dcsf;
	}

	/**
	 * ��ȡ���ƽ������
	 * 
	 * @param cards
	 *            ������ solutions ���н������������ currSolution ��ǰsolution
	 * @return false��ʾ�����ٲ�� ����ը�����ɻ������ӣ�3��
	 */
	public void getDevidedCardSolution(List<Card> cards, List<DevidedCardSolution> solutions,
			DevidedCardSolution currSolution) {
		// ������п��ܵ� ����ը�����ɻ������ӣ�3��
		List<OneSendCard> oneSendCardList = getAllPossibleOneSendCard(cards);
		// ����У����������κ�һ�����뵽currSolution��
		if (oneSendCardList != null && oneSendCardList.size() > 0) {
			for (OneSendCard osc : oneSendCardList) {
				// ����һ���µģ�������һ��
				DevidedCardSolution nextSolution = new DevidedCardSolution();
				List<OneSendCard> oneSendCards = new ArrayList<OneSendCard>();
				List<OneSendCard> singleOrDouble = new ArrayList<OneSendCard>();
				if (currSolution != null) {
					if (currSolution.getOneSendCards() != null && currSolution.getOneSendCards().size() > 0) {
						// ��֮ǰ��OneSendCard�Ž���
						oneSendCards.addAll(currSolution.getOneSendCards());
					}
				}

				// �����ε�OneSendCard�Ž���
				oneSendCards.add(osc);
				nextSolution.setOneSendCards(oneSendCards);
				// ��ȡʣ����
				List<Card> restCards = CardUtil.getRestListByRemove(cards, osc.getOneSendCardList());
				// ���еݹ����
				getDevidedCardSolution(restCards, solutions, nextSolution);
			}
		} else {
			// ���û�У�˵���������ٲ���ˣ�
			// 1��cards���뵽solution��rabisish��
			// 2��currSolution���뵽solutions�У�����currSolution
			// 1
			List<OneSendCard> singleOrDouble = getOneSendCardOfSingleOrDouble(cards);
			if (currSolution == null) {
				currSolution = new DevidedCardSolution();
			}
			currSolution.setSingleOrDouble(singleOrDouble);
			// 2
			solutions.add(currSolution);
		}
	}

	/**
	 * ��ȡ���Ž������
	 * 
	 * @param solutions
	 * @return
	 */
	public DevidedCardSolution getBestDevidedCardSolution(List<DevidedCardSolution> solutions) {
		// ���ݵ��������Ƚϣ���������ԽС���������Խ��,
		DevidedCardSolution best = null;
		// ���ҳ������������ٵ����н������
		List<DevidedCardSolution> allBestSolutions = getAllBestDevidedCardSolution(solutions);
		// ���ҳ����ƴ������ٵķ���
		double shouShu = 100;
		for (DevidedCardSolution temp : allBestSolutions) {
			if (shouShu > temp.getSendCount()) {
				best = temp;
			}
		}
		return best;
	}

	/**
	 * ��ȡ�������Ž������,��Ϊ�����кܶ�õĽ������
	 * 
	 * @param solutions
	 * @return
	 */
	public List<DevidedCardSolution> getAllBestDevidedCardSolution(List<DevidedCardSolution> solutions) {
		// ���ݵ��������Ƚϣ���������ԽС���������Խ��
		double initValue = 100;
		DevidedCardSolution best = null;
		List<DevidedCardSolution> tempList = new ArrayList<DevidedCardSolution>();
		for (DevidedCardSolution solution : solutions) {
			if (solution.getSingleCount() < initValue) {
				// ���С��
				best = solution;
				initValue = solution.getSingleCount();
				tempList.clear();
				tempList.add(solution);
			} else if (solution.getSingleCount() == initValue) {
				tempList.add(solution);
			}
		}
		return tempList;
	}

	/**
	 * �ҳ����ȳ��ƣ�ͨ���Ƚ�ѹ�Ʊ�ֵ��ѹ�Ʊ�ֵ��С�����ȳ�
	 * 
	 * @param dcs
	 * @return
	 */
	public OneSendCard getFirstOneSendCard(DevidedCardSolution dcs) {
		List<OneSendCard> oldSingleOrDouble = dcs.getSingleOrDouble();
		// �Ƚ�δ ����֮ǰ�Ľ����������
		List<OneSendCard> saveSingleOrDouble = new ArrayList<OneSendCard>();
		for (OneSendCard osc : oldSingleOrDouble) {
			OneSendCard newOsc = new OneSendCard(osc.getOneSendCardList(), osc.getCardType());
			saveSingleOrDouble.add(newOsc);
		}
		// �Ƿ��б�Ҫ�ȳ�����
		boolean hasNessary = false;
		// ����ж��Ƿ�Ҫ�ȳ�����
		boolean isSingleFirst = false;

		// ���ж���û�б�Ҫ������
		if (dcs.getSingleCount() > 0) {
			// ��������>0
			hasNessary = true;
		} else {
			hasNessary = false;
		}

		OneSendCard osc = null;
		if (hasNessary == false) {
			// û�б�Ҫ�����ƣ������е��ƶ��ܴ���ȥ
			// ��������OneSendCard ��ѹ�Ʊ�ֵ�ͱ�ѹ�Ʊ�ֵ��ѹ�Ʊ�ֵԽСԽ�ȳ���ѹ�Ʊ�ֵ�����ѹ�Ʊ�ֵԽСԽ�ȳ�
			dispatchSingleOrDouble(dcs);
			// Ŀǰֻ��ѹ�Ʊ�ֵ�Ƚ�
			double minRate = 1;
			if (dcs.getOneSendCards() != null) {
				for (OneSendCard temp : dcs.getOneSendCards()) {
					if (temp.getBiggerRate() < minRate) {
						osc = temp;
						minRate = temp.getBiggerRate();
					}
				}
			}
		} else {
			// ����б�Ҫ������
			// ��Ҫ�Ȱѵ��ƴ��� 3�Ż�4����
			dispatchSingleOrDouble(dcs);
			double minRate = 2;
			if (dcs.getOneSendCards() != null) {
				for (OneSendCard temp : dcs.getOneSendCards()) {
					if (temp.getBiggerRate() < minRate) {
						osc = temp;
						minRate = temp.getBiggerRate();
					}
				}
			}
			if (dcs.getSingleOrDouble() != null) {
				for (OneSendCard temp : dcs.getSingleOrDouble()) {
					if (temp.getBiggerRate() < minRate) {
						osc = temp;
						minRate = temp.getBiggerRate();
					}
				}
			}

		}
		// ������ȳ����ǵ��ƣ�����ƣ���Ҫ����֮ǰ��ƥ����г��ƣ����Ǵӵ��ƺ�˫�����ҳ���С�Ƴ�
		if (osc == null || osc.getCardType().equals(CardTypeString.SINGLE_CARDTYPE)
				|| osc.getCardType().equals(CardTypeString.DOUBLE_CARDTYPE)) {
			osc = getMinSingleOrDouble(saveSingleOrDouble);
		}
		return osc;
	}

	/**
	 * �����е��ƺ�˫�����ҳ���С��
	 * 
	 * @param saveSingleOrDouble
	 * @return
	 */
	private OneSendCard getMinSingleOrDouble(List<OneSendCard> saveSingleOrDouble) {
		// TODO Auto-generated method stub
		Map<Integer, OneSendCard> map = CardUtil.asValueStaticOneSendCard(saveSingleOrDouble);
		List<Integer> intList = new ArrayList<Integer>();
		for (Integer i : map.keySet()) {
			intList.add(i);
		}
		intList = CardUtil.sortIntegerList(intList);
		if (intList == null || intList.size() == 0) {
			return null;
		}
		OneSendCard tempOsc = map.get(intList.get(0));
		return tempOsc;
	}

	/**
	 * �ַ����ƺ�˫�Ʒַ�
	 * 
	 * @param dcs
	 */
	public void dispatchSingleOrDouble(DevidedCardSolution dcs) {
		Map<Integer, OneSendCard> map = CardUtil.asValueStaticOneSendCard(dcs.getSingleOrDouble());
		int singleCount = 0;
		int doubleCount = 0;
		List<Integer> singleList = new ArrayList<Integer>();
		List<Integer> doubleList = new ArrayList<Integer>();
		for (OneSendCard temp : dcs.getSingleOrDouble()) {
			if (temp.getCardType().equals(CardTypeString.SINGLE_CARDTYPE)) {
				singleCount++;
			}
			if (temp.getCardType().equals(CardTypeString.DOUBLE_CARDTYPE)) {
				doubleCount++;
			}
		}

		// Ϊ�ɻ�����
		// �ɻ���Ҫ�����ŵ���;
		int needCount = 0;
		if (dcs.getOneSendCards() != null) {
			for (OneSendCard temp : dcs.getOneSendCards()) {
				if (temp.getCardType().equals(CardTypeString.PLANE_CARDTYPE)) {
					map = CardUtil.asValueStaticOneSendCard(dcs.getSingleOrDouble());
					singleCount = 0;
					doubleCount = 0;
					singleList = new ArrayList<Integer>();
					doubleList = new ArrayList<Integer>();
					for (Map.Entry<Integer, OneSendCard> entry : map.entrySet()) {
						if (entry.getValue().getCardType().equals(CardTypeString.SINGLE_CARDTYPE)) {
							singleList.add(entry.getKey());
							singleCount++;
						}
						if (entry.getValue().getCardType().equals(CardTypeString.DOUBLE_CARDTYPE)) {
							doubleList.add(entry.getKey());
							doubleCount++;
						}
					}
					singleList = CardUtil.sortIntegerList(singleList);
					doubleList = CardUtil.sortIntegerList(doubleList);
					needCount = temp.getOneSendCardList().size() / 3;
					List<Card> addCardList = new ArrayList<Card>();
					// ������ƹ���������
					if (singleCount >= needCount) {
						for (int i = 0; i < needCount; i++) {
							int tempInt = singleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// �ӵ�����ɾ��
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						temp.setCardType(CardTypeString.PLANEONE_CARDTYPE);
					} else if (doubleCount >= needCount) {
						// ������Ʋ�����˫�ƹ���˫��
						for (int i = 0; i < needCount; i++) {
							int tempInt = doubleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// �ӵ�����ɾ��
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						temp.setCardType(CardTypeString.PLANETWO_CARDTYPE);
					} else {
						// �жϵ��ƺ�˫�Ƶ��ܸ���
						int totalSingleCount = 0;
						totalSingleCount = singleCount + doubleCount * 2;
						if (totalSingleCount < needCount) {
							// �ܵ���������
							// ֻ�ܼ�������
							continue;
						}
						// ������Ʋ�����˫�Ʋ�������˫���в������
						// �����
						int shotCount = needCount - singleCount;
						for (int i = 0; i < needCount; i++) {
							int tempInt = singleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// �ӵ�����ɾ��
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						// ��Ķ���
						int shotDoubleCount = shotCount / 2;
						for (int i = 0; i < shotDoubleCount; i++) {
							int tempInt = doubleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// �ӵ�����ɾ��
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						// ��ĵ���
						int shotSingleCount = shotCount % 2;
						for (int i = 0; i < shotSingleCount; i++) {
							int tempInt = doubleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// �ӵ�����ɾ��
							// �Ƴ�˫���еĵ�0��
							Card bc = tempOsc.getOneSendCardList().remove(0);
							// ��˫�Ƹĳɵ���
							tempOsc.setCardType(CardTypeString.SINGLE_CARDTYPE);
							addCardList.add(bc);
						}
						temp.getOneSendCardList().addAll(addCardList);
						temp.setCardType(CardTypeString.PLANEONE_CARDTYPE);
					}
				}
			}

			// Ϊ3�ŷ���
			// Ϊ3�ŷ���ֻ��Ҫ��С������Ƽ���
			for (OneSendCard temp : dcs.getOneSendCards()) {
				if (temp.getCardType().equals(CardTypeString.THREE_CARDTYPE)) {
					if (dcs.getSingleOrDouble().size() > 0) {
						map = CardUtil.asValueStaticOneSendCard(dcs.getSingleOrDouble());
						List<Integer> intList = new ArrayList<Integer>();
						for (Integer i : map.keySet()) {
							intList.add(i);
						}
						intList = CardUtil.sortIntegerList(intList);
						OneSendCard tempOsc = map.get(intList.get(0));
						List<Card> addCardList = tempOsc.getOneSendCardList();
						dcs.getSingleOrDouble().remove(tempOsc);
						temp.getOneSendCardList().addAll(addCardList);
						if (tempOsc.getCardType().equals(CardTypeString.SINGLE_CARDTYPE)) {
							temp.setCardType(CardTypeString.THREEANDONE_CARDTYPE);
						} else {
							temp.setCardType(CardTypeString.THREEANTWO_CARDTYPE);
						}
					}
				}
			}
		}
	}

	/**
	 * �����ƺ�˫�Ʋ��oneSendCard����
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getOneSendCardOfSingleOrDouble(List<Card> cards) {
		// TODO Auto-generated method stub
		List<OneSendCard> retList = new ArrayList<OneSendCard>();
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				// ����
				List<Card> list1 = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt(entry.getKey()), 1);
				OneSendCard o1 = new OneSendCard(list1, CardTypeString.SINGLE_CARDTYPE);
				retList.add(o1);
			} else if (entry.getValue() == 2) {
				// ����
				List<Card> list1 = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt(entry.getKey()), 2);
				OneSendCard o1 = new OneSendCard(list1, CardTypeString.DOUBLE_CARDTYPE);
				retList.add(o1);
			}
		}
		return retList;
	}

	/**
	 * //������п��ܵ� ����ը�����ɻ������ӣ�3��
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAllPossibleOneSendCard(List<Card> cards) {
		// TODO Auto-generated method stub
		List<OneSendCard> retList = new ArrayList<OneSendCard>();

		// 1�ҳ����е�ը��
		List<OneSendCard> bombList = getAllBombByCardList(cards);
		// 2�ҳ����е�3����ͬ
		List<OneSendCard> threeList = getAllThreeByCardList(cards);
		// 3�ҳ����еķɻ�
		List<OneSendCard> planeList = getAllPlaneByCardList(cards);
		// 4�ҳ����е�����
		List<OneSendCard> lianDuiList = getAlllianDuiByCardList(cards);
		// 5�ҳ����еĵ�����
		List<OneSendCard> lianZiList = getAlllianZiByCardList(cards);
		List<OneSendCard> doubleList = getAlllianZiByCardList(cards);
		// �����еļ��뵽retList��
		retList.addAll(bombList);
		retList.addAll(threeList);
		retList.addAll(planeList);
		retList.addAll(lianDuiList);
		retList.addAll(lianZiList);
		retList.addAll(doubleList);
		return retList;
	}

	/**
	 * �ҳ����еĵ�����
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAlllianZiByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		// ������С����Ϊ5����������Ϊ3-AΪ12��,A=14
		int minCount = 5;
		int maxCount = 12;
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		OneSendCard osc = null;
		List<OneSendCard> lianziList = new ArrayList<OneSendCard>();
		for (int i = minCount; i <= maxCount; i++) {
			// ��ѭ���������ָ���
			for (int j = 3; j <= 14 - i + 1; j++) {
				// ��ѭ�����Ƴ�ʼֵ
				// �Ƿ���Գ����ӣ����ֻҪ��һ���Ƶ�����Ϊ0��������Ӳ���
				boolean bHas = true;
				for (int h = j; h < j + i; h++) {
					// ����ѭ�����Ʋ��ҵ�ǰ��ֵ
					if (map.get(String.valueOf(h)) == null || map.get(String.valueOf(h)) == 0) {
						bHas = false;
						break;
					}
				}
				if (bHas == true) {
					// ��j��ʼ��
					List<Card> totalCards = new ArrayList<Card>();
					for (int h = j; h < j + i; h++) {
						// ����ѭ�����Ʋ��ҵ�ǰ��ֵ
						List<Card> tempCards = null;

						tempCards = CardUtil.getCardListByValueAndCount(cards, h, 1);
						totalCards.addAll(tempCards);
					}
					osc = new OneSendCard(totalCards, CardTypeString.ONETWOTHREE_CARDTYPE);
					lianziList.add(osc);
				}
			}
		}
		return lianziList;
	}

	/**
	 * �ҳ����е�����
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAlllianDuiByCardList(List<Card> cards) {
		// ������С����Ϊ3����������Ϊ3-AΪ12��,A=14
		int minCount = 3;
		int maxCount = 10;
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		OneSendCard osc = null;
		List<OneSendCard> lianziList = new ArrayList<OneSendCard>();
		for (int i = minCount; i <= maxCount; i++) {
			// ��ѭ���������ָ���
			for (int j = 3; j <= 14 - i + 1; j++) {
				// ��ѭ�����Ƴ�ʼֵ
				// �Ƿ���Գ����ӣ����ֻҪ��һ���Ƶ�����Ϊ0��������Ӳ���
				boolean bHas = true;
				for (int h = j; h < j + i; h++) {
					// ����ѭ�����Ʋ��ҵ�ǰ��ֵ
					if (map.get(String.valueOf(h)) == null || map.get(String.valueOf(h)) < 2) {
						bHas = false;
						break;
					}
				}
				if (bHas == true) {
					// ��j��ʼ��
					List<Card> totalCards = new ArrayList<Card>();
					for (int h = j; h < j + i; h++) {
						// ����ѭ�����Ʋ��ҵ�ǰ��ֵ
						List<Card> tempCards = null;
						tempCards = CardUtil.getCardListByValueAndCount(cards, h, 2);
						totalCards.addAll(tempCards);
					}
					osc = new OneSendCard(totalCards, CardTypeString.ONEONETWOTWO_CARDTYPE);
					lianziList.add(osc);
				}
			}
		}
		return lianziList;
	}

	/**
	 * 3�ҳ����еķɻ�
	 * 
	 * @param cards
	 * @return
	 */
	public List<OneSendCard> getAllPlaneByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		List<OneSendCard> planeList = new ArrayList<OneSendCard>();
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		// ��3��k,�ҳ������Ĵ���3������ֵ
		ArrayList<ArrayList<Integer>> intListList = new ArrayList<ArrayList<Integer>>();
		List<Integer> threeList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() >= 3) {
				threeList.add(Integer.parseInt(entry.getKey()));
			}
		}
		Object objs[] = threeList.toArray();
		Arrays.sort(objs);
		int preNumber = 0;
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for (int i = 0; i < objs.length; i++) {
			int curNumber = Integer.parseInt(objs[i].toString());
			if (preNumber == 0) {
				tempList.add(curNumber);
			} else {
				if (preNumber == curNumber - 1) {
					// ����
					tempList.add(curNumber);
				} else {
					// ������
					// ���֮ǰ�������ڵ���2�ͼ��뵽intListList��
					if (tempList.size() >= 2) {
						intListList.add(tempList);
					}
					// ����һ����List
					tempList = new ArrayList<Integer>();
				}
			}
			// ��ǰֵ��ֵ��preNumber;
			preNumber = curNumber;
		}
		// �����ѭ�������һ���������һ��������������ҪҲ�ӽ�ȥ
		if (tempList.size() >= 2) {
			intListList.add(tempList);
		}
		// �ҳ����еķɻ�������ɻ������ĸ���>2,��333444555���򵱳�һ���ɻ���������333444��444555
		for (ArrayList<Integer> temList : intListList) {
			OneSendCard osc = null;
			List<Card> totalCards = new ArrayList<Card>();
			List<Card> tempCards = null;
			for (Integer temInt : temList) {
				tempCards = CardUtil.getCardListByValueAndCount(cards, temInt, 3);
				totalCards.addAll(tempCards);
			}
			osc = new OneSendCard(totalCards, CardTypeString.PLANE_CARDTYPE);
			planeList.add(osc);
		}
		return planeList;
	}

	/**
	 * �ҳ����е�3����ͬ
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAllThreeByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		List<OneSendCard> bombList = new ArrayList<OneSendCard>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() >= 3) {
				List<Card> bombcards = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt(entry.getKey()), 3);
				OneSendCard osc = new OneSendCard(bombcards, CardTypeString.THREE_CARDTYPE);
				bombList.add(osc);
			}
		}
		return bombList;
	}

	/**
	 * �ҳ����е�2����ͬ
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAllDoubleByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		List<OneSendCard> bombList = new ArrayList<OneSendCard>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() >= 2) {
				List<Card> bombcards = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt(entry.getKey()), 2);
				OneSendCard osc = new OneSendCard(bombcards, CardTypeString.DOUBLE_CARDTYPE);
				bombList.add(osc);
			}
		}
		return bombList;
	}

	/**
	 * �ҳ����е�ը��
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAllBombByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);//����ֵ��ͬ��ͳ�Ƹ��� ����ʲôֵ�ƣ��м�����ͬ��ֵ
		List<OneSendCard> bombList = new ArrayList<OneSendCard>();
		if (map.get("16") != null && map.get("17") != null) {
			List<Card> bombcards = new ArrayList<Card>();
			List<Card> bombcards1 = CardUtil.getCardListByValueAndCount(cards, 16, 1);// ���Ƶ�list����ֵΪvalue���ƣ�ȡcount��
			List<Card> bombcards2 = CardUtil.getCardListByValueAndCount(cards, 17, 1);
			bombcards.addAll(bombcards1);
			bombcards.addAll(bombcards2);
			OneSendCard osc = new OneSendCard(bombcards, CardTypeString.FOUR_CARDTYPE);
			bombList.add(osc);
		}
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 4) {
				List<Card> bombcards = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt(entry.getKey()), 4);
				OneSendCard osc = new OneSendCard(bombcards, CardTypeString.FOUR_CARDTYPE);
				bombList.add(osc);
			}
		}
		return bombList;
	}
}