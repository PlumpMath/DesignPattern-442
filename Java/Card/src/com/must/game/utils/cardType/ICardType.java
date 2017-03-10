package com.must.game.utils.cardType;

import java.util.List;
import com.must.game.utils.OneSendCard;
import com.must.game.vo.Card;

/**
 * 牌型接口
 * 
 * @author Administrator
 *
 */
public interface ICardType {
	/**
	 * 牌型名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 牌型是否匹配
	 * 
	 * @return
	 */
	public boolean matches(List<Card> list);

	/**
	 * 比较大小
	 * 
	 * @param a
	 * @param b
	 * @return，1为大，0相等，-1为小于
	 */
	public int compare(List<Card> a, List<Card> b);

	/**
	 * 返回牌型的长度，0表示不确定长度，如连子，连队
	 * 
	 * @return
	 */
	public int getLength();

	/**
	 * 返回牌型的最小长度
	 * 
	 * @return
	 */
	public int getMinLength();

	/**
	 * 从所有牌中找到该牌型的并且大于上一次出牌的一次出牌,找不到返回null
	 * 
	 * @param playerList
	 * @param preOneSendCard
	 * @return
	 */
	public OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard);

}
