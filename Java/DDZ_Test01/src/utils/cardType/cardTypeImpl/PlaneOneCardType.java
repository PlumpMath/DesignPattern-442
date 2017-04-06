package utils.cardType.cardTypeImpl;

import utils.CardUtil;
import utils.OneSendCard;
import cardDesign.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * 飞机牌带一张单牌的牌型
 * 
 * @author Administrator
 *
 */
public class PlaneOneCardType implements utils.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		// TODO Auto-generated method stub
		// 把牌去除掉单牌，调用plane的比较算法
		List<Card> aRest = CardUtil.removeByCount(a, 1);
		List<Card> bRest = CardUtil.removeByCount(b, 1);
		int ret = new PlaneCardType().compare(aRest, bRest);
		return ret;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return CardTypeString.PLANEONE_CARDTYPE;
	}

	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean matches(List<Card> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			// 333444 56或333444555 789
			// 除以4得到的商，将所有牌求得一个map<value,count>count为同一个value的个数，count>3的个数要大于商
			if (list.size() >= 8 && list.size() % 4 == 0) {
				int shang = list.size() / 4;
				Map<String, Integer> map = CardUtil.asValueStaticCount(list);
				int count = 0;
				// 将大于3个的值放到list中，如果大于3个的值是3，4，5类型且最大不超过14
				List<Integer> count3 = new ArrayList<Integer>();
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					if (entry.getValue() >= 3) {
						count++;
						count3.add(Integer.parseInt(entry.getKey()));
					}
				}
				if (shang == count) {
					// 商相等，并且这个shang个key为3，4，5类型且最大不超过14
					Object[] intArr = count3.toArray();
					Arrays.sort(intArr);
					for (int i = 1; i < intArr.length; i++) {
						int pre = Integer.parseInt(intArr[i - 1].toString());
						int curr = Integer.parseInt(intArr[i].toString());
						if (curr != pre + 1) {
							return false;
						}
					}
					if (Integer.parseInt(intArr[intArr.length - 1].toString()) <= 14) {
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
		return 8;
	}

	@Override
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
		// TODO Auto-generated method stub
		if (playerList.size() < preOneSendCard.getOneSendCardList().size()) {
			// 如果牌数不够直接返回
			return null;
		}

		// 去除单牌，调用飞机的压牌算法，再从剩余的牌中找出3张单牌就ok了
		List<Card> aRest = CardUtil.removeByCount(preOneSendCard.getOneSendCardList(), 1);
		OneSendCard newPreOneSendCard = new OneSendCard(aRest, CardTypeString.PLANE_CARDTYPE);
		PlaneCardType pCardType = new PlaneCardType();
		OneSendCard retOsc = null;
		OneSendCard osc = pCardType.getOneSendCardBiggerButleast(playerList, newPreOneSendCard);

		if (osc == null) {
			// 如果飞机都压不了，那么加上单牌还是压不了
			return null;
		} else {
			// 能压住，再从剩余的牌中找3张单牌
			// 先获取剩余牌
			List<Card> restList = CardUtil.getRestListByRemove(playerList, osc.getOneSendCardList());
			// 有多少个3张一样并且相连的
			int count = preOneSendCard.getOneSendCardList().size() / 4;
			List<Card> needList = CardUtil.getSingleCardListBy(restList, count);
			// 飞机可以压住的牌的list
			List<Card> newList = osc.getOneSendCardList();
			newList.addAll(needList);
			retOsc = new OneSendCard(newList, CardTypeString.PLANEONE_CARDTYPE);
			return retOsc;
		}
	}
}
