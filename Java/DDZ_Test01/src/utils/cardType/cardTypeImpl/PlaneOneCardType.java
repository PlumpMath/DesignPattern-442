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
 * �ɻ��ƴ�һ�ŵ��Ƶ�����
 * 
 * @author Administrator
 *
 */
public class PlaneOneCardType implements utils.cardType.ICardType {

	@Override
	public int compare(List<Card> a, List<Card> b) {
		// TODO Auto-generated method stub
		// ����ȥ�������ƣ�����plane�ıȽ��㷨
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
			// 333444 56��333444555 789
			// ����4�õ����̣������������һ��map<value,count>countΪͬһ��value�ĸ�����count>3�ĸ���Ҫ������
			if (list.size() >= 8 && list.size() % 4 == 0) {
				int shang = list.size() / 4;
				Map<String, Integer> map = CardUtil.asValueStaticCount(list);
				int count = 0;
				// ������3����ֵ�ŵ�list�У��������3����ֵ��3��4��5��������󲻳���14
				List<Integer> count3 = new ArrayList<Integer>();
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					if (entry.getValue() >= 3) {
						count++;
						count3.add(Integer.parseInt(entry.getKey()));
					}
				}
				if (shang == count) {
					// ����ȣ��������shang��keyΪ3��4��5��������󲻳���14
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
			// �����������ֱ�ӷ���
			return null;
		}

		// ȥ�����ƣ����÷ɻ���ѹ���㷨���ٴ�ʣ��������ҳ�3�ŵ��ƾ�ok��
		List<Card> aRest = CardUtil.removeByCount(preOneSendCard.getOneSendCardList(), 1);
		OneSendCard newPreOneSendCard = new OneSendCard(aRest, CardTypeString.PLANE_CARDTYPE);
		PlaneCardType pCardType = new PlaneCardType();
		OneSendCard retOsc = null;
		OneSendCard osc = pCardType.getOneSendCardBiggerButleast(playerList, newPreOneSendCard);

		if (osc == null) {
			// ����ɻ���ѹ���ˣ���ô���ϵ��ƻ���ѹ����
			return null;
		} else {
			// ��ѹס���ٴ�ʣ���������3�ŵ���
			// �Ȼ�ȡʣ����
			List<Card> restList = CardUtil.getRestListByRemove(playerList, osc.getOneSendCardList());
			// �ж��ٸ�3��һ������������
			int count = preOneSendCard.getOneSendCardList().size() / 4;
			List<Card> needList = CardUtil.getSingleCardListBy(restList, count);
			// �ɻ�����ѹס���Ƶ�list
			List<Card> newList = osc.getOneSendCardList();
			newList.addAll(needList);
			retOsc = new OneSendCard(newList, CardTypeString.PLANEONE_CARDTYPE);
			return retOsc;
		}
	}
}
