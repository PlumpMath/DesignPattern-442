package DesignPattern.cardType.cardTypeImpl;

import DesignPattern.cardDesign.Card;
import utils.CardUtil;
import utils.OneSendCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * 单张牌的牌型
 * 
 * @author Administrator
 *
 */
public class ThreeAndOneCardType implements DesignPattern.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		Map<String, Integer> mapA = CardUtil.asValueStaticCount(a);
		Map<String, Integer> mapB = CardUtil.asValueStaticCount(b);
		int keyA = 0;
		int keyB = 0;
		for (Map.Entry<String, Integer> entry : mapA.entrySet()) {
			if (entry.getValue() == 3) {
				keyA = Integer.parseInt(entry.getKey());
			}
		}
		for (Map.Entry<String, Integer> entry : mapB.entrySet()) {
			if (entry.getValue() == 3) {
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
		return CardTypeString.THREEANDONE_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			if (list.size() == 4) {
				Map<String, Integer> map = CardUtil.asValueStaticCount(list);
				if (map.entrySet().size() == 2) {
					if (map.containsValue(1) && map.containsValue(3)) {
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
		return 4;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		OneSendCard osc = null;
		// 三带一中，三个的那个值
		int preValue = 0;
		Map<String, Integer> mapPre = CardUtil.asValueStaticCount(preOneSendCard.getOneSendCardList());
		for (Map.Entry<String, Integer> entry : mapPre.entrySet()) {
			if (entry.getValue() == 3) {
				preValue = Integer.parseInt(entry.getKey());
				break;
			}
		}
		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		List threeList = new ArrayList<Integer>();
		List oneList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 3) {
				threeList.add(Integer.parseInt(entry.getKey().toString()));
			}
			if (entry.getValue() == 1) {
				oneList.add(Integer.parseInt(entry.getKey().toString()));
			}
		}
		if (threeList.size() == 0) {
			// 如果没有3张相同牌
			return null;
		} else {
			// 如果有3张相同牌，找出牌中最小一个，但是比b的值大的那个
			int biggerButLeast = CardUtil.getBiggerButLeastFromList(threeList, preValue);
			if (biggerButLeast > 0) {
				// 找到且大于牌值
				List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 3);
				if (oneList.size() > 0) {
					// 如果有单牌就带一张最小的单牌
					int least = CardUtil.getLeastFromList(oneList);
					List<Card> oneCardList = CardUtil.getCardListByValueAndCount(playerList, least, 1);
					needList.add(oneCardList.get(0));
				} else {
					// 如果没有单牌，就从剩余牌中找一张最小的牌
					List<Card> restList = CardUtil.getRestListByRemove(playerList, needList);
					if (restList.size() == 0) {
						return null;
					}
					Card bc = CardUtil.getLeastCardFromCardList(restList);
					needList.add(bc);
				}
				osc = new OneSendCard(needList, CardTypeString.THREEANDONE_CARDTYPE);
				return osc;
			} else {
				// 找到但是没有找到牌值大的
				return null;
			}
		}
	}
}
