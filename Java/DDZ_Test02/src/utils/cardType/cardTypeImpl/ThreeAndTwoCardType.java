package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import vo.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * 三带2的牌型
 * 
 * @author Administrator
 *
 */
public class ThreeAndTwoCardType implements utils.cardType.ICardType {

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
		return CardTypeString.THREEANTWO_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			if (list.size() == 5) {
				Map<String, Integer> map = CardUtil.asValueStaticCount(list);
				if (map.entrySet().size() == 2) {
					if (map.containsValue(2) && map.containsValue(3)) {
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
		return 5;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
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
		List twoList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 3) {
				threeList.add(Integer.parseInt(entry.getKey().toString()));
			}
			if (entry.getValue() == 2) {
				twoList.add(Integer.parseInt(entry.getKey().toString()));
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
				if (twoList.size() > 0) {
					List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 3);
					// 如果有就找最小的一对双牌
					int least = CardUtil.getLeastFromList(twoList);
					List<Card> twoCardList = CardUtil.getCardListByValueAndCount(playerList, least, 2);
					needList.addAll(twoCardList);
					osc = new OneSendCard(needList, CardTypeString.THREEANTWO_CARDTYPE);
					return osc;
				} else {
					// 如果没有双牌，就从剩余三张牌的类型中找
					if (threeList.size() == 1) {
						return null;
					} else {
						// 有除要出的这个值之外的三张牌
						int moreBiggerButLeast = CardUtil.getBiggerButLeastFromList(threeList, biggerButLeast);
						List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, moreBiggerButLeast, 3);
						List<Card> needList2 = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 2);
						needList.addAll(needList2);
						osc = new OneSendCard(needList, CardTypeString.THREEANTWO_CARDTYPE);
						return osc;
					}
				}
			} else {
				// 找到但是没有找到牌值大的
				return null;
			}
		}
	}
}
