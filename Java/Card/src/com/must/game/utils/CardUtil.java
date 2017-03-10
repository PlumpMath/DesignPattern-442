package com.must.game.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * 
 * @author Administrator
 *
 */
public class CardUtil {
	/**
	 * 按照值相同，统计个数 返回什么值牌，有几种相同的值
	 */
	public static Map<String, Integer> asValueStaticCount(List<Card> list) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Card c : list) {
			String key = String.valueOf(c.getValue());
			if (map.containsKey(String.valueOf(c.getValue()))) {
				map.put(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}
		return map;
	}

	/**
	 * 
	 * 返回什么值牌，有count个
	 */
	public static Map<String, Integer> asValueStaticCountByValue(List<Card> list, int count) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (Card c : list) {
			String key = String.valueOf(c.getValue());
			if (map.containsKey(String.valueOf(c.getValue()))) {
				map.put(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}
		Map<String, Integer> newMap = new HashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == count) {
				newMap.put(entry.getKey(), entry.getValue());
			}
		}
		return newMap;
	}

	/**
	 * 从list中找出大于value的最小值
	 * 
	 * @param list
	 * @param value
	 * @return 找不到返回0
	 */
	public static int getBiggerButLeastFromList(List<Integer> list, int value) {
		// TODO Auto-generated method stub
		Object[] objs = list.toArray();
		Arrays.sort(objs);
		for (Object o : objs) {
			int i = Integer.parseInt(o.toString());
			if (i > value) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * 从牌的list中找值为value的牌，取count个
	 * 
	 * @param playerList
	 * @param value
	 * @param i
	 * @return
	 */
	public static List<Card> getCardListByValueAndCount(List<Card> playerList, int value, int count) {
		// TODO Auto-generated method stub
		List<Card> retList = new ArrayList<Card>();
		int has = 0;
		for (Card bc : playerList) {
			if (bc.getValue() == value) {
				retList.add(bc);
				has++;
				if (has >= count) {
					break;
				}
			}
		}
		return retList;
	}

	/**
	 * 
	 * @param 从oneList找出最小的值
	 * @return
	 */
	public static int getLeastFromList(List<Integer> oneList) {
		// TODO Auto-generated method stub
		Object[] objs = oneList.toArray();
		Arrays.sort(objs);
		int ret = Integer.parseInt(objs[0].toString());
		return ret;
	}

	/**
	 * 从playerList中去掉needList的牌，返回一个新的List
	 * 
	 * @param playerList
	 * @param needList
	 * @return
	 */
	public static List<Card> getRestListByRemove(List<Card> playerList, List<Card> needList) {
		// TODO Auto-generated method stub
		List<Card> retList = new ArrayList<Card>();
		for (Card bc : playerList) {
			boolean has = false;
			for (Card bc2 : needList) {
				if (bc.isEqual(bc2)) {
					has = true;
					break;
				}
			}
			if (!has) {
				retList.add(bc);
			}
		}
		return retList;
	}

	/**
	 * 从restList中找出最小的一张牌
	 * 
	 * @param restList
	 * @return
	 */
	public static Card getLeastCardFromCardList(List<Card> restList) {
		// TODO Auto-generated method stub
		int initValue = restList.get(0).getValue();
		Card bc = null;
		for (Card b : restList) {
			if (b.getValue() < initValue) {
				initValue = b.getValue();
				bc = b;
			}
		}
		return bc;
	}

	/**
	 * 从oneList中取出count个最小的值
	 * 
	 * @param oneList
	 * @param i
	 * @return
	 */
	public static List<Integer> getLeastFromList(List oneList, int count) {
		Object[] objs = oneList.toArray();
		Arrays.sort(objs);
		List<Integer> iList = new ArrayList<Integer>();
		for (int i = 0; i < count; i++) {
			iList.add(Integer.parseInt(objs[i].toString()));
		}
		return iList;
	}

	/**
	 * 从oneList中取出count个最小的值
	 * 
	 * @param oneList
	 * @param i
	 * @return
	 */
	public static List<Integer> getLeastFromCardList(List<Card> cardList, int count) {
		List oneList = new ArrayList<Integer>();
		for (Card bc : cardList) {
			oneList.add(bc.getValue());
		}
		return CardUtil.getLeastFromList(oneList, count);
	}

	/**
	 * 对list进行排序，返回整数值
	 * 
	 * @param preList
	 * @return
	 */
	public static Integer[] sortCardList(List<Card> preList) {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		for (Card bc : preList) {
			list.add(bc.getValue());
		}
		Object objs[] = list.toArray();
		Arrays.sort(objs);
		Integer[] arr = new Integer[objs.length];
		for (int i = 0; i < objs.length; i++) {
			arr[i] = Integer.parseInt(objs[i].toString());
		}
		return arr;
	}

	/**
	 * 从list中把单牌去掉，返回一个新list
	 * 
	 * @param list
	 * @param count
	 * @return
	 */
	public static List<Card> removeByCount(List<Card> list, int count) {
		// TODO Auto-generated method stub
		List<Card> retList = new ArrayList<Card>();
		Map<String, Integer> map = CardUtil.asValueStaticCount(list);
		List<String> removeList = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == count) {
				removeList.add(entry.getKey());
			}
		}
		for (Card b : list) {
			String str = String.valueOf(b.getValue());
			if (!removeList.contains(str)) {
				retList.add(b);
			}
		}
		return retList;
	}

	/**
	 * 从剩余的牌中找i张最小的单牌
	 * 
	 * @param restList
	 * @param i
	 * @return
	 */
	public static List<Card> getSingleCardListBy(List<Card> restList, int count) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = CardUtil.asValueStaticCount(restList);
		List<Integer> singleList = new ArrayList<Integer>();
		// 最后确定的单牌
		List<Integer> needList = new ArrayList<Integer>();
		List<Card> retList = new ArrayList<Card>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1 && !entry.getKey().equals("16") && !entry.getKey().equals("17")) {
				singleList.add(Integer.parseInt(entry.getKey()));
			}
		}
		if (singleList.size() > count) {
			// 如果单牌数量大于要找的数量，就直接从单牌中找i张单牌
			Object[] objs = singleList.toArray();
			Arrays.sort(objs);
			for (int i = 0; i < count; i++) {
				int temp = Integer.parseInt(objs[i].toString());
				List<Card> tempList = CardUtil.getCardListByValueAndCount(restList,
						Integer.parseInt(objs[i].toString()), 1);
				retList.addAll(tempList);
			}

		} else {
			// 如果单牌数量不够，差几张，就从剩余的牌中再找几张，补齐
			Object[] objs = singleList.toArray();
			Arrays.sort(objs);
			for (int i = 0; i < singleList.size(); i++) {
				int temp = Integer.parseInt(objs[i].toString());
				List<Card> tempList = CardUtil.getCardListByValueAndCount(restList, singleList.get(i), 1);
				retList.addAll(tempList);
			}
			// 还差几张牌
			int needCount = count - singleList.size();
			// 新的剩余牌
			List<Card> newRestList = CardUtil.getRestListByRemove(restList, retList);
			// 从新的剩余的牌中找出needCount张单牌
			Integer[] sortCardList = CardUtil.sortCardList(newRestList);
			for (int temp : sortCardList) {
				if (needCount == 0) {
					break;
				} else {
					retList.addAll(CardUtil.getCardListByValueAndCount(newRestList, temp, 1));
					needCount--;
				}

			}

		}
		return retList;
	}

	/**
	 * 从剩余的牌中找几对最小的对牌
	 * 
	 * @param restList
	 * @param i
	 * @return
	 */
	public static List<Card> getDoubleCardListBy(List<Card> restList, int count) {
		// TODO Auto-generated method stub
		Map<String, Integer> map = CardUtil.asValueStaticCount(restList);
		List<Integer> doubleList = new ArrayList<Integer>();
		List<Integer> threeList = new ArrayList<Integer>();
		// 最后确定的对牌
		List<Integer> needList = new ArrayList<Integer>();
		List<Card> retList = new ArrayList<Card>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 2) {

				doubleList.add(Integer.parseInt(entry.getKey()));
			}
			if (entry.getValue() == 3) {

				threeList.add(Integer.parseInt(entry.getKey()));
			}
		}
		if (doubleList.size() > count) {
			// 如果单牌数量大于要找的数量，就直接从单牌中找i张单牌
			Object[] objs = doubleList.toArray();
			Arrays.sort(objs);
			for (int i = 0; i < count; i++) {
				int temp = Integer.parseInt(objs[i].toString());
				List<Card> tempList = CardUtil.getCardListByValueAndCount(restList,
						Integer.parseInt(objs[i].toString()), 2);
				retList.addAll(tempList);
			}

		} else {
			// 如果单牌数量不够，差几张，就从剩余的牌中再找几张，补齐
			Object[] objs = doubleList.toArray();
			Arrays.sort(objs);
			for (int i = 0; i < doubleList.size(); i++) {
				int temp = Integer.parseInt(objs[i].toString());
				List<Card> tempList = CardUtil.getCardListByValueAndCount(restList, doubleList.get(i), 2);
				retList.addAll(tempList);
			}
			// 还差几对牌
			int needCount = count - doubleList.size();
			if (threeList.size() >= needCount) {
				// 三牌的数量够用
				Object threeObjs[] = threeList.toArray();
				Arrays.sort(threeObjs);
				for (int i = 0; i < needCount; i++) {
					Integer key = Integer.parseInt(threeObjs[i].toString());
					List<Card> tempList = CardUtil.getCardListByValueAndCount(restList, key, 2);
					retList.addAll(tempList);
				}
			} else {
				// 三牌的数量不够用
				return null;
			}
		}
		return retList;

	}

	/**
	 * 安值统计oneSendCard(单牌和双牌)
	 * 
	 * @param singleOrDouble
	 * @return
	 */
	public static Map<Integer, OneSendCard> asValueStaticOneSendCard(List<OneSendCard> singleOrDouble) {
		// TODO Auto-generated method stub
		Map<Integer, OneSendCard> map = new HashMap<Integer, OneSendCard>();
		for (OneSendCard osc : singleOrDouble) {
			Card bc = osc.getOneSendCardList().get(0);
			map.put(bc.getValue(), osc);
		}
		return map;
	}

	/**
	 * 排序整数数组，从小到大
	 * 
	 * @param singleList
	 * @return
	 */
	public static List<Integer> sortIntegerList(List<Integer> singleList) {
		// TODO Auto-generated method stub
		List<Integer> retList = new ArrayList<Integer>();
		Object[] objs = singleList.toArray();
		Arrays.sort(objs);
		for (Object o : objs) {
			retList.add(Integer.parseInt(o.toString()));
		}
		return retList;
	}
}
