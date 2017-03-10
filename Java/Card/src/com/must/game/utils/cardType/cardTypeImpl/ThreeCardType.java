package com.must.game.utils.cardType.cardTypeImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.must.game.utils.CardUtil;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.ICardType;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * 三张牌的牌型
 * 
 * @author Administrator
 *
 */
public class ThreeCardType implements ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		// TODO Auto-generated method stub
		Card c1 = a.get(0);
		Card c2 = b.get(0);
		if (c1.getValue() > c2.getValue()) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return CardTypeString.THREE_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			if (list.size() == 3) {
				int value1 = list.get(0).getValue();
				int value2 = list.get(1).getValue();
				int value3 = list.get(2).getValue();
				if (value1 == value2 && value2 == value3) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int getMinLength() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		OneSendCard osc = null;
		Card b = preOneSendCard.getOneSendCardList().get(0);
		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		List threeList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 3) {
				threeList.add(Integer.parseInt(entry.getKey().toString()));
			}
		}
		if (threeList.size() == 0) {
			// 如果没有3张相同牌
			return null;
		} else {
			// 如果有3张相同牌，找出牌中最小一个，但是比b的值大的那个
			int biggerButLeast = CardUtil.getBiggerButLeastFromList(threeList, b.getValue());
			if (biggerButLeast > 0) {
				// 找到且大于牌值
				List<Card> needList = CardUtil.getCardListByValueAndCount(playerList, biggerButLeast, 3);
				osc = new OneSendCard(needList, CardTypeString.THREE_CARDTYPE);
				return osc;
			} else {
				// 找到但是没有找到牌值大的
				return null;
			}
		}
	}
}
