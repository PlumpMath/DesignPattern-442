package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import cardDesign.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */


/**
 * 炸弹带两对牌的牌型
 * 
 * @author Administrator
 *
 */
public class FourTwoTwoCardType implements utils.cardType.ICardType {

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
		return CardTypeString.FOURTWOTWO_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			if (list.size() == 8) {
				Map<String, Integer> map = CardUtil.asValueStaticCount(list);
				if (map.entrySet().size() == 3) {
					if (map.containsValue(4) && map.containsValue(2)) {
						// 共3份，8张牌，一份4个，剩余2份为4个，其中一份为2个，剩余那份必定是2个
						return true;
					}
				} else if (map.entrySet().size() == 2) {
					// 如果8张牌两份，肯定是两个4个的
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getMinLength() {
		// TODO Auto-generated method stub
		return 8;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		OneSendCard osc = null;
		// 4带2中，4个的那个牌值
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
		List twoList = new ArrayList<Integer>();
		List threeList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 4) {
				fourList.add(Integer.parseInt(entry.getKey().toString()));
			}
			if (entry.getValue() == 2) {
				twoList.add(Integer.parseInt(entry.getKey().toString()));
			}
			if (entry.getValue() == 3) {
				threeList.add(Integer.parseInt(entry.getKey().toString()));
			}
		}
		if (fourList.size() == 0) {
			// 如果没有4张相同牌
			return null;
		} else {
			// 如果有4张相同牌，找出牌中最小一个，但是比b的值大的那个
			int biggerButLeast = CardUtil.getBiggerButLeastFromList(fourList, preValue);
			if (biggerButLeast > 0) {
				// 找到且大于牌值
				List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 4);
				if (twoList.size() > 1) {
					// 如果有两张单牌就带两对最小的对牌
					List<Integer> leastList = CardUtil.getLeastFromList(twoList, 2);
					for (int i : leastList) {
						List<Card> twoCardList = CardUtil.getCardListByValueAndCount(playerList, i, 2);
						needList.addAll(twoCardList);
					}

				} else {
					// 如果没有两张对牌就返回null
					return null;
				}
				osc = new OneSendCard(needList, CardTypeString.THREE_CARDTYPE);
				return osc;
			} else {
				// 找到但是没有找到牌值大的
				return null;
			}
		}
	}
}
