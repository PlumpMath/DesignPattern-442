package DesignPattern.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import DesignPattern.cardDesign.Card;
import java.util.ArrayList;
import java.util.Arrays;
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
public class SingleCardType implements DesignPattern.cardType.ICardType {

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
		return CardTypeString.SINGLE_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			if (list.size() > 0 && list.size() == 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		OneSendCard osc = null;
		Card b = preOneSendCard.getOneSendCardList().get(0);
		Map<String, Integer> map = CardUtil.asValueStaticCount(playerList);
		List singleList = new ArrayList<Integer>();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() == 1) {
				singleList.add(Integer.parseInt(entry.getKey().toString()));
			}
		}
		if (singleList.size() == 0) {
			// 如果没有单牌,就找一个比张牌大的牌就行了
			for (Card bc : playerList) {
				if (bc.getValue() > b.getValue()) {
					List<Card> findList = new ArrayList<Card>();
					findList.add(bc);
					osc = new OneSendCard(findList, CardTypeString.SINGLE_CARDTYPE);
					return osc;
				}
			}
		} else {
			// 如果有单牌，找出单牌中最小一个，但是别b的值大的那个
			Object[] objs = singleList.toArray();
			Arrays.sort(objs);
			int findValue = 0;
			for (Object o : objs) {
				Integer i = Integer.parseInt(String.valueOf(o.toString()));
				if (i > b.getValue()) {
					findValue = i;
					break;
				}
			}
			if (findValue > 0) {
				// 如果找到
				for (Card bc : playerList) {
					if (bc.getValue() == findValue) {
						List<Card> findList = new ArrayList<Card>();
						findList.add(bc);
						osc = new OneSendCard(findList, CardTypeString.SINGLE_CARDTYPE);
						return osc;
					}
				}
			} else {
				// 如果没有找到
				for (Card bc : playerList) {
					if (bc.getValue() > b.getValue()) {
						List<Card> findList = new ArrayList<Card>();
						findList.add(bc);
						osc = new OneSendCard(findList, CardTypeString.SINGLE_CARDTYPE);
						return osc;
					}
				}
			}
		}
		return null;
	}

	@Override
	public int getMinLength() {
		// TODO Auto-generated method stub
		return 1;
	}
}
