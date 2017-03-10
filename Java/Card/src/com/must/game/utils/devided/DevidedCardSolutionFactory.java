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
 * 拆牌工厂，通过指定的拆牌规则，返回拆牌方案
 * 
 * @author Administrator 获取拆牌方案 ->计算最优出牌方案（单牌手数越小越好）-> 将单牌和双牌分配给飞机等（） -》计算最先出牌方案
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
	 * 获取拆牌解决方案
	 * 
	 * @param cards
	 *            所有牌 solutions 所有解决方案的引用 currSolution 当前solution
	 * @return false表示不可再拆出 龙，炸弹，飞机，连队，3张
	 */
	public void getDevidedCardSolution(List<Card> cards, List<DevidedCardSolution> solutions,
			DevidedCardSolution currSolution) {
		// 拆出所有可能的 龙，炸弹，飞机，连队，3张
		List<OneSendCard> oneSendCardList = getAllPossibleOneSendCard(cards);
		// 如果有，遍历，将任何一个加入到currSolution中
		if (oneSendCardList != null && oneSendCardList.size() > 0) {
			for (OneSendCard osc : oneSendCardList) {
				// 创建一个新的，进入下一层
				DevidedCardSolution nextSolution = new DevidedCardSolution();
				List<OneSendCard> oneSendCards = new ArrayList<OneSendCard>();
				List<OneSendCard> singleOrDouble = new ArrayList<OneSendCard>();
				if (currSolution != null) {
					if (currSolution.getOneSendCards() != null && currSolution.getOneSendCards().size() > 0) {
						// 将之前的OneSendCard放进来
						oneSendCards.addAll(currSolution.getOneSendCards());
					}
				}

				// 将本次的OneSendCard放进来
				oneSendCards.add(osc);
				nextSolution.setOneSendCards(oneSendCards);
				// 获取剩余牌
				List<Card> restCards = CardUtil.getRestListByRemove(cards, osc.getOneSendCardList());
				// 进行递归调用
				getDevidedCardSolution(restCards, solutions, nextSolution);
			}
		} else {
			// 如果没有，说明不可以再拆解了，
			// 1将cards加入到solution的rabisish中
			// 2将currSolution加入到solutions中，并将currSolution
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
	 * 获取最优解决方案
	 * 
	 * @param solutions
	 * @return
	 */
	public DevidedCardSolution getBestDevidedCardSolution(List<DevidedCardSolution> solutions) {
		// 根据单牌手数比较，单牌手数越小，解决方案越好,
		DevidedCardSolution best = null;
		// 先找出单牌手数最少的所有解决方案
		List<DevidedCardSolution> allBestSolutions = getAllBestDevidedCardSolution(solutions);
		// 再找出出牌次数最少的方案
		double shouShu = 100;
		for (DevidedCardSolution temp : allBestSolutions) {
			if (shouShu > temp.getSendCount()) {
				best = temp;
			}
		}
		return best;
	}

	/**
	 * 获取所有最优解决方案,因为可能有很多好的解决方案
	 * 
	 * @param solutions
	 * @return
	 */
	public List<DevidedCardSolution> getAllBestDevidedCardSolution(List<DevidedCardSolution> solutions) {
		// 根据单牌手数比较，单牌手数越小，解决方案越好
		double initValue = 100;
		DevidedCardSolution best = null;
		List<DevidedCardSolution> tempList = new ArrayList<DevidedCardSolution>();
		for (DevidedCardSolution solution : solutions) {
			if (solution.getSingleCount() < initValue) {
				// 如果小于
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
	 * 找出最先出牌，通过比较压牌比值，压牌比值最小的最先出
	 * 
	 * @param dcs
	 * @return
	 */
	public OneSendCard getFirstOneSendCard(DevidedCardSolution dcs) {
		List<OneSendCard> oldSingleOrDouble = dcs.getSingleOrDouble();
		// 先将未 分配之前的解决方案保存
		List<OneSendCard> saveSingleOrDouble = new ArrayList<OneSendCard>();
		for (OneSendCard osc : oldSingleOrDouble) {
			OneSendCard newOsc = new OneSendCard(osc.getOneSendCardList(), osc.getCardType());
			saveSingleOrDouble.add(newOsc);
		}
		// 是否有必要先出单牌
		boolean hasNessary = false;
		// 最后判断是否要先出单牌
		boolean isSingleFirst = false;

		// 先判断有没有必要出单牌
		if (dcs.getSingleCount() > 0) {
			// 单牌手数>0
			hasNessary = true;
		} else {
			hasNessary = false;
		}

		OneSendCard osc = null;
		if (hasNessary == false) {
			// 没有必要出单牌，即所有单牌都能带出去
			// 计算所有OneSendCard 的压牌比值和被压牌比值，压牌笔值越小越先出，压牌比值相等则被压牌比值越小越先出
			dispatchSingleOrDouble(dcs);
			// 目前只用压牌比值比较
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
			// 如果有必要出单牌
			// 需要先把单牌带到 3张或4张上
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
		// 如果最先出牌是单牌，或对牌，则不要根据之前的匹配进行出牌，而是从单牌和双牌中找出最小牌出
		if (osc == null || osc.getCardType().equals(CardTypeString.SINGLE_CARDTYPE)
				|| osc.getCardType().equals(CardTypeString.DOUBLE_CARDTYPE)) {
			osc = getMinSingleOrDouble(saveSingleOrDouble);
		}
		return osc;
	}

	/**
	 * 在所有单牌和双牌中找出最小的
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
	 * 分发单牌和双牌分发
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

		// 为飞机分牌
		// 飞机需要带几张单牌;
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
					// 如果单牌够，带单牌
					if (singleCount >= needCount) {
						for (int i = 0; i < needCount; i++) {
							int tempInt = singleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// 从单牌中删除
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						temp.setCardType(CardTypeString.PLANEONE_CARDTYPE);
					} else if (doubleCount >= needCount) {
						// 如果单牌不够，双牌够带双牌
						for (int i = 0; i < needCount; i++) {
							int tempInt = doubleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// 从单牌中删除
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						temp.setCardType(CardTypeString.PLANETWO_CARDTYPE);
					} else {
						// 判断单牌和双牌的总个数
						int totalSingleCount = 0;
						totalSingleCount = singleCount + doubleCount * 2;
						if (totalSingleCount < needCount) {
							// 总单牌数不够
							// 只能继续尝试
							continue;
						}
						// 如果单牌不够，双牌不够，从双牌中拆除单牌
						// 还差几个
						int shotCount = needCount - singleCount;
						for (int i = 0; i < needCount; i++) {
							int tempInt = singleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// 从单牌中删除
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						// 差的对数
						int shotDoubleCount = shotCount / 2;
						for (int i = 0; i < shotDoubleCount; i++) {
							int tempInt = doubleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// 从单牌中删除
							dcs.getSingleOrDouble().remove(tempOsc);
							addCardList.addAll(tempOsc.getOneSendCardList());
						}
						temp.getOneSendCardList().addAll(addCardList);
						// 差的单数
						int shotSingleCount = shotCount % 2;
						for (int i = 0; i < shotSingleCount; i++) {
							int tempInt = doubleList.get(i);
							OneSendCard tempOsc = map.get(tempInt);
							// 从单牌中删除
							// 移除双牌中的第0张
							Card bc = tempOsc.getOneSendCardList().remove(0);
							// 将双牌改成单牌
							tempOsc.setCardType(CardTypeString.SINGLE_CARDTYPE);
							addCardList.add(bc);
						}
						temp.getOneSendCardList().addAll(addCardList);
						temp.setCardType(CardTypeString.PLANEONE_CARDTYPE);
					}
				}
			}

			// 为3张分牌
			// 为3张分配只需要从小到大分牌即可
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
	 * 将单牌和双牌拆成oneSendCard返回
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
				// 单牌
				List<Card> list1 = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt(entry.getKey()), 1);
				OneSendCard o1 = new OneSendCard(list1, CardTypeString.SINGLE_CARDTYPE);
				retList.add(o1);
			} else if (entry.getValue() == 2) {
				// 对牌
				List<Card> list1 = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt(entry.getKey()), 2);
				OneSendCard o1 = new OneSendCard(list1, CardTypeString.DOUBLE_CARDTYPE);
				retList.add(o1);
			}
		}
		return retList;
	}

	/**
	 * //拆出所有可能的 龙，炸弹，飞机，连队，3张
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAllPossibleOneSendCard(List<Card> cards) {
		// TODO Auto-generated method stub
		List<OneSendCard> retList = new ArrayList<OneSendCard>();

		// 1找出所有的炸弹
		List<OneSendCard> bombList = getAllBombByCardList(cards);
		// 2找出所有的3张相同
		List<OneSendCard> threeList = getAllThreeByCardList(cards);
		// 3找出所有的飞机
		List<OneSendCard> planeList = getAllPlaneByCardList(cards);
		// 4找出所有的连对
		List<OneSendCard> lianDuiList = getAlllianDuiByCardList(cards);
		// 5找出所有的的连子
		List<OneSendCard> lianZiList = getAlllianZiByCardList(cards);
		List<OneSendCard> doubleList = getAlllianZiByCardList(cards);
		// 将所有的加入到retList中
		retList.addAll(bombList);
		retList.addAll(threeList);
		retList.addAll(planeList);
		retList.addAll(lianDuiList);
		retList.addAll(lianZiList);
		retList.addAll(doubleList);
		return retList;
	}

	/**
	 * 找出所有的的连子
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAlllianZiByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		// 连子最小个数为5个，最大个数为3-A为12个,A=14
		int minCount = 5;
		int maxCount = 12;
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		OneSendCard osc = null;
		List<OneSendCard> lianziList = new ArrayList<OneSendCard>();
		for (int i = minCount; i <= maxCount; i++) {
			// 外循环控制连字个数
			for (int j = 3; j <= 14 - i + 1; j++) {
				// 内循环控制初始值
				// 是否可以成连子，其间只要有一张牌的数量为0，则该连子不成
				boolean bHas = true;
				for (int h = j; h < j + i; h++) {
					// 最内循环控制查找当前牌值
					if (map.get(String.valueOf(h)) == null || map.get(String.valueOf(h)) == 0) {
						bHas = false;
						break;
					}
				}
				if (bHas == true) {
					// 从j开始的
					List<Card> totalCards = new ArrayList<Card>();
					for (int h = j; h < j + i; h++) {
						// 最内循环控制查找当前牌值
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
	 * 找出所有的连对
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAlllianDuiByCardList(List<Card> cards) {
		// 连子最小个数为3个，最大个数为3-A为12个,A=14
		int minCount = 3;
		int maxCount = 10;
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		OneSendCard osc = null;
		List<OneSendCard> lianziList = new ArrayList<OneSendCard>();
		for (int i = minCount; i <= maxCount; i++) {
			// 外循环控制连字个数
			for (int j = 3; j <= 14 - i + 1; j++) {
				// 内循环控制初始值
				// 是否可以成连子，其间只要有一张牌的数量为0，则该连子不成
				boolean bHas = true;
				for (int h = j; h < j + i; h++) {
					// 最内循环控制查找当前牌值
					if (map.get(String.valueOf(h)) == null || map.get(String.valueOf(h)) < 2) {
						bHas = false;
						break;
					}
				}
				if (bHas == true) {
					// 从j开始的
					List<Card> totalCards = new ArrayList<Card>();
					for (int h = j; h < j + i; h++) {
						// 最内循环控制查找当前牌值
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
	 * 3找出所有的飞机
	 * 
	 * @param cards
	 * @return
	 */
	public List<OneSendCard> getAllPlaneByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		List<OneSendCard> planeList = new ArrayList<OneSendCard>();
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);
		// 从3到k,找出连续的大于3个的牌值
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
					// 连续
					tempList.add(curNumber);
				} else {
					// 不连续
					// 如果之前数量大于等于2就加入到intListList中
					if (tempList.size() >= 2) {
						intListList.add(tempList);
					}
					// 创建一个新List
					tempList = new ArrayList<Integer>();
				}
			}
			// 当前值赋值给preNumber;
			preNumber = curNumber;
		}
		// 如果是循环的最后一个，且最后一个是连续，则需要也加进去
		if (tempList.size() >= 2) {
			intListList.add(tempList);
		}
		// 找出所有的飞机，如果飞机连续的个数>2,如333444555，则当成一个飞机，不考虑333444和444555
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
	 * 找出所有的3张相同
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
	 * 找出所有的2张相同
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
	 * 找出所有的炸弹
	 * 
	 * @param cards
	 * @return
	 */
	private List<OneSendCard> getAllBombByCardList(List<Card> cards) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = CardUtil.asValueStaticCount(cards);//按照值相同，统计个数 返回什么值牌，有几种相同的值
		List<OneSendCard> bombList = new ArrayList<OneSendCard>();
		if (map.get("16") != null && map.get("17") != null) {
			List<Card> bombcards = new ArrayList<Card>();
			List<Card> bombcards1 = CardUtil.getCardListByValueAndCount(cards, 16, 1);// 从牌的list中找值为value的牌，取count个
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