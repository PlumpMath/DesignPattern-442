package com.must.game.utils.cardType;

import java.util.List;
import com.must.game.utils.OneSendCard;
import com.must.game.vo.Card;

/**
 * ���ͽӿ�
 * 
 * @author Administrator
 *
 */
public interface ICardType {
	/**
	 * ��������
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * �����Ƿ�ƥ��
	 * 
	 * @return
	 */
	public boolean matches(List<Card> list);

	/**
	 * �Ƚϴ�С
	 * 
	 * @param a
	 * @param b
	 * @return��1Ϊ��0��ȣ�-1ΪС��
	 */
	public int compare(List<Card> a, List<Card> b);

	/**
	 * �������͵ĳ��ȣ�0��ʾ��ȷ�����ȣ������ӣ�����
	 * 
	 * @return
	 */
	public int getLength();

	/**
	 * �������͵���С����
	 * 
	 * @return
	 */
	public int getMinLength();

	/**
	 * �����������ҵ������͵Ĳ��Ҵ�����һ�γ��Ƶ�һ�γ���,�Ҳ�������null
	 * 
	 * @param playerList
	 * @param preOneSendCard
	 * @return
	 */
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard);

}
