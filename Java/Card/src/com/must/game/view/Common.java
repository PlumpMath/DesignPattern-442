package com.must.game.view;

import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.must.game.vo.Card;

public class Common {
	// 移动效果的函数,用于发牌
	public static void move(Card card, Point from, Point to) {
		if (to.x != from.x) {
			double k = (1.0) * (to.y - from.y) / (to.x - from.x);
			double b = to.y - to.x * k;
			int flag = 0;// 判断向左还是向右移动步幅
			if (from.x < to.x)
				flag = 20;
			else {
				flag = -20;
			}
			for (int i = from.x; Math.abs(i - to.x) > 20; i += flag) {
				double y = k * i + b;// 这里主要用的数学中的线性函数
				card.setLocation(i, (int) y);
				try {
					Thread.sleep(5); // 延迟，可自己设置
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 位置校准
		card.setLocation(to);
	}

	public static void order(List<Card> list) {
		Collections.sort(list, new Comparator<Card>() {
			@Override
			public int compare(Card o1, Card o2) { 
				if (o1.getValue() < o2.getValue()) {
					return 1;
				} else if (o1.getValue() > o2.getValue()) {
					return -1;
				} else {
					if (o1.getColor() < o2.getColor()) {
						return 1;
					} else if (o1.getColor() == o2.getColor()) {
						return 0;
					} else {
						return -1;
					}
				}
			}
		});
	}

	// 重新定位 flag代表电脑1 ,2 或者是我
	public static void rePosition(Home m, List<Card> list, int flag) {
		Point p = new Point();
		if (flag == 0) {
			p.x = 50;
			p.y = (450 / 2) - (list.size() + 1) * 15 / 2;
		}
		if (flag == 1) {
			p.x = (800 / 2) - (list.size() + 1) * 21 / 2;
			p.y = 450;
		}
		if (flag == 2) {
			p.x = 700;
			p.y = (450 / 2) - (list.size() + 1) * 15 / 2;
		}
		int len = list.size();
		for (int i = 0; i < len; i++) {
			Card card = list.get(i);
			Common.move(card, card.getLocation(), p);
			m.container.setComponentZOrder(card, 0);
			if (flag == 1)
				p.x += 21;
			else
				p.y += 15;
		}
	}

	/**
	 * 计算电脑谁抢地主
	 * 
	 * @param playerList
	 * @param playerList2
	 * @return
	 */
	public static int computeLord(List<Card> playerList, List<Card> playerList2) {
		// TODO Auto-generated method stub
		int score0 = getScore(playerList);
		int score2 = getScore(playerList2);
		if (score0 > score2) {
			return 0;
		} else {
			return 2;
		}
	}

	// 地主牌权值，看是否抢地主
	public static int getScore(List<Card> list) {
		int count = 0;
		for (int i = 0, len = list.size(); i < len; i++) {
			Card card = list.get(i);
			if (card.getValue() == 16) {
				// 小王
				count += 4;
			}
			if (card.getValue() == 17) {
				// 小王
				count += 5;
			}
			if (card.getValue() == 15) {
				count += 2;
			}
		}
		return count;
	}

	// 隐藏之前出过的牌
	public static void hideCards(List<Card> list) {
		for (int i = 0, len = list.size(); i < len; i++) {
			list.get(i).setVisible(false);
		}
	}              
}
