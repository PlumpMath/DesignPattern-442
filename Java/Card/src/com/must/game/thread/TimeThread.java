package com.must.game.thread;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.util.List;
import javax.swing.JLabel;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.CardTypeFactory;
import com.must.game.view.Common;
import com.must.game.view.Home;
import com.must.game.vo.Card;

/**
 * �����߳�
 * 
 * @author Administrator
 *
 */
public class TimeThread extends Thread implements Runnable {
	private Home main;

	public TimeThread(Home main) {
		this.main = main;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// ѡ����ʱ���
		for (int i = 10; i >= 0; i--) {
			// �ȴ�ʮ����ѡ����
			if (main.dizhuFlag == -1) {
				// û��ȷ��˭�ǵ���
				main.time[1].setText("����ʱ:" + i);
				sleepSeconds(1);
			} else {
				// ���������
				break;
			}
		}
		// ��������ʱ���
		if (main.dizhuFlag == 1) {
			// �Լ��ǵ���
			main.playerList[1].addAll(main.lordList);
			openlord(true);
			this.sleepSeconds(2);// �ȴ�2��
			Common.order(main.playerList[1]);
			Common.rePosition(main, main.playerList[1], 1);
			main.publishCard[1].setEnabled(false);
			setlord(1);
		} else {
			// ����ѡ����
			if (main.dizhuFlag == 2) {
				main.time[2].setText("������");
				main.time[2].setVisible(true);
				setlord(2);// �趨����
				openlord(true);
				sleepSeconds(3);
				main.playerList[2].addAll(main.lordList);
				Common.order(main.playerList[2]);
				Common.rePosition(main, main.playerList[2], 2);
				openlord(false);
				if (Home.debug) {
					openlord(true);
				}

			} else {
				main.time[0].setText("������");
				main.time[0].setVisible(true);
				setlord(0);// �趨����
				openlord(true);
				sleepSeconds(3);
				main.playerList[0].addAll(main.lordList);
				Common.order(main.playerList[0]);
				Common.rePosition(main, main.playerList[0], 0);
				openlord(false);
				if (Home.debug) {
					openlord(true);
				}
			}
		}
		// ����ʱ���
		turnOn(false);
		for (int i = 0; i < 3; i++) {
			main.time[i].setText("��Ҫ");
			main.time[i].setVisible(false);
		}
		while (true) {

			if (main.turn == 1) // ��
			{
				turnOn(true);// ���ư�ť --�ҳ���
				// ������������ƹرղ�Ҫ��ť
				if (main.preChuPai == 1)
					main.publishCard[1].setEnabled(false);
				else {
					main.publishCard[1].setEnabled(true);
				}
				// ����������ƣ���������
				clearTable(1);
				if (main.preChuPai == 1) {
					clearTable();
				}

				turnOnLord(false);// ����������ť�ڲ�
				this.makeCanClick(main.playerList[1], true);
				timeWait(30, 1);// ���Լ��Ķ�ʱ��
				turnOn(false);// ѡ��ر�
				main.turn = (main.turn + 1) % 3;
				// main.preChuPai=1;

			}
			if (main.turn == 0) {
				// ����һ�����Լ�ʱ�߳�
				main.hasSend[0] = 0;// ��ʾû�г������
				computerTimer(0);
				clearTable(0);
				computer0();
				main.turn = (main.turn + 1) % 3;

			}
			if (main.turn == 2) {
				clearTable(2);
				// ����һ�����Լ�ʱ�߳�
				main.hasSend[2] = 0;// ��ʾû�г������
				computerTimer(2);
				computer2();
				main.turn = (main.turn + 1) % 3;
			}

			if (win()) // �ж���Ӯ
				break;
		}

	}

	/**
	 * �ж���Ӯ
	 * 
	 * @return
	 */
	private boolean win() {
		for (int i = 0; i < 3; i++) {
			if (main.playerList[i].size() == 0) {
				String s;
				if (i == 1) {
					s = "You win!";
					main.winJLabel[0].setVisible(true);
					main.winJLabel[1].setVisible(true);
					main.winOrLose.setForeground(Color.RED);
				} else {
					if (isFriend(i, 1)) {
						s = "You win!";
						main.winJLabel[0].setVisible(true);
						main.winJLabel[1].setVisible(true);
						main.winOrLose.setForeground(Color.RED);
					} else {
						s = "You lose!";
						main.loseJLabel[0].setVisible(true);
						main.loseJLabel[1].setVisible(true);
						main.winOrLose.setForeground(Color.black);
					}

				}
				for (int j = 0; j < main.playerList[(i + 1) % 3].size(); j++)
					main.playerList[(i + 1) % 3].get(j).turnFront();
				for (int j = 0; j < main.playerList[(i + 2) % 3].size(); j++)
					main.playerList[(i + 2) % 3].get(j).turnFront();
				main.winOrLose.setText(s);
				main.winOrLose.setVisible(true);
				return true;
			}
		}
		return false;
	}

	/**
	 * ���Ե���ʱ
	 * 
	 * @param computer
	 */
	public void computerTimer(int computer) {
		int total = 10;
		// ����ʱ��
		main.time[computer].setText("����ʱ:" + String.valueOf(10));
		main.time[computer].setVisible(true);
		for (int i = total; i >= 10; i--) {
			if (main.hasSend[computer] != 0) {
				// �Ѿ������ƻ��߲�Ҫ���˳�
				break;
			}
			main.time[computer].setText("����ʱ:" + String.valueOf(i));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����ĳ�˵�����
	 * 
	 * @param i
	 */
	private void clearTable(int i) {
		// TODO Auto-generated method stub
		for (Card bc : main.currentList[i]) {
			bc.setVisible(false);
		}
		main.currentList[i].clear();
	}

	/**
	 * ��������
	 */
	private void clearTable() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i++) {
			clearTable(i);
		}
	}

	/**
	 * ����2���� ���Ʒ�Ϊ��Ҫ�ƺ��������ƣ� Ҫ�ƹ���1 ֻҪ�ǵ��˾�Ҫ�ƣ�ֻҪ���˾Ͳ�Ҫ
	 * 
	 */
	private void computer2() {
		// TODO Auto-generated method stub
		int role = 2;
		if (!isSelfSendCard(role)) {
			// �������2������������
			if (!isFriend(role, main.preChuPai)) {
				// �������ͬ���ϵ
				OneSendCard oneSendCard = CardTypeFactory.getOneSendCardBiggerButleast(main.playerList[2],
						main.preOneSendCard);

				if (oneSendCard == null) {
					// Ҫ����
					main.hasSend[role] = 2;
					main.time[role].setText("��Ҫ");
				} else {
					showCard(role, oneSendCard);
					// ������һ�γ���ʲô�ƣ�˭�����ƣ��Ƿ��Ѿ�����
					main.preOneSendCard = oneSendCard;
					// ���������ӵ��ѳ��б�
					main.hasSendList.addAll(oneSendCard.getOneSendCardList());
					main.currentList[role] = oneSendCard.getOneSendCardList();
					main.preChuPai = role;
					main.hasSend[role] = 1;
					main.time[role].setText("��Ҫ");
				}
			} else {
				// ��ͬ���ϵ����ʱ��Ҫ
				main.time[role].setText("��Ҫ");
				main.hasSend[role] = 2;
			}
		} else {
			// ����2��������
			OneSendCard oneSendCard = CardTypeFactory.getFirstBestOneSendCard(main.playerList[2]);
			showCard(role, oneSendCard);
			main.preOneSendCard = oneSendCard;
			// ���������ӵ��ѳ��б�
			main.hasSendList.addAll(oneSendCard.getOneSendCardList());
			main.currentList[role] = oneSendCard.getOneSendCardList();
			main.preChuPai = role;
			main.hasSend[role] = 1;
		}
	}

	/**
	 * 
	 * ����Ч��
	 * 
	 * @param role
	 * @param oneSendCard
	 */
	private void showCard(int role, OneSendCard oneSendCard) {
		List<Card> list = oneSendCard.getOneSendCardList();
		main.currentList[role].clear();
		Point point = new Point();
		if (role == 0)
			point.x = 200;
		if (role == 2)
			point.x = 550;
		if (role == 1) {
			point.x = (770 / 2) - (main.currentList[1].size() + 1) * 15 / 2;
			point.y = 300;
		}
		point.y = (400 / 2) - (list.size() + 1) * 15 / 2;// ��Ļ�в�
		for (Card card : list) {
			Common.move(card, card.getLocation(), point);
			point.y += 15;
			main.container.setComponentZOrder(card, 0);
			card.turnFront();
			main.currentList[role].add(card);

			main.playerList[role].remove(card);
		}
		Common.rePosition(main, main.playerList[role], role);
	}

	/**
	 * ����2���� ���Ʒ�Ϊ��Ҫ�ƺ��������ƣ� Ҫ�ƹ���1 ֻҪ�ǵ��˾�Ҫ�ƣ�ֻҪ���˾Ͳ�Ҫ
	 * 
	 */
	private void computer0() {
		// TODO Auto-generated method stub
		int role = 0;
		if (!isSelfSendCard(role)) {
			// �������2������������
			if (!isFriend(role, main.preChuPai)) {
				// �������ͬ���ϵ
				OneSendCard oneSendCard = CardTypeFactory.getBiggerOneSendCard(main.playerList[role],
						main.preOneSendCard);
				if (oneSendCard == null) {
					// Ҫ����
					main.hasSend[role] = 2;
					main.time[role].setText("��Ҫ");
				} else {
					showCard(role, oneSendCard);
					// ������һ�γ���ʲô�ƣ�˭�����ƣ��Ƿ��Ѿ�����
					main.preOneSendCard = oneSendCard;
					// ���������ӵ��ѳ��б�
					main.hasSendList.addAll(oneSendCard.getOneSendCardList());
					main.preChuPai = role;
					main.hasSend[role] = 1;
					main.time[role].setText("��Ҫ");
				}
			} else {
				// ��ͬ���ϵ����ʱ��Ҫ
				main.time[role].setText("��Ҫ");
				main.hasSend[role] = 2;
			}
		} else {
			// �������2����������
			OneSendCard oneSendCard = CardTypeFactory.getFirstBestOneSendCard(main.playerList[role]);
			showCard(role, oneSendCard);
			// ������һ�γ���ʲô�ƣ�˭�����ƣ��Ƿ��Ѿ�����
			main.preOneSendCard = oneSendCard;
			// ���������ӵ��ѳ��б�
			main.hasSendList.addAll(oneSendCard.getOneSendCardList());
			main.preOneSendCard = oneSendCard;
			main.currentList[role] = oneSendCard.getOneSendCardList();
			main.preChuPai = role;
			main.hasSend[role] = 1;
		}
	}

	/**
	 * �ж��Ƿ�����������
	 */
	public boolean isSelfSendCard(int player) {
		if (main.preChuPai == -1 || main.preChuPai == player) {
			// �����û���˳��ƻ�����һ�γ��Ƶ������Լ������ξ����Զ�����
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �ж��������ǲ���һ���
	 */
	public boolean isFriend(int i1, int i2) {
		boolean b = true;
		// ֻҪ����������һ���ǵ��������ǾͲ���һ���
		if (i1 == main.dizhuFlag || i2 == main.dizhuFlag) {
			b = false;
		}
		return b;
	}

	/**
	 * ��������ť��ʾ���ڲش���
	 * 
	 * @param b
	 */
	private void turnOnLord(boolean b) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2; i++) {
			main.landlord[i].setVisible(b);
		}
	}

	// ��ʱ��ģ��ʱ��
	public void timeWait(int n, int player) {

		if (main.currentList[player].size() > 0)
			Common.hideCards(main.currentList[player]);
		if (player == 1) // ������ң�30�뵽��ֱ����һ�ҳ���
		{
			int i = n;
			while (main.nextPlayer == false && i >= 0) {
				main.time[player].setText("����ʱ:" + i);
				main.time[player].setVisible(true);
				sleepSeconds(1);
				i--;
			}
			if (i == -1 && player == 1) {
				// ����ҳ�ʱ
			}
			main.nextPlayer = false;
		} else {
			for (int i = n; i >= 0; i--) {
				sleepSeconds(1);
				main.time[player].setText("����ʱ:" + i);
				main.time[player].setVisible(true);
			}
		}
		main.time[player].setVisible(false);
	}

	// ʹȫ���Ʊ���Ƿ�ɵ��
	public void makeCanClick(List<Card> list, boolean b) {
		for (Card card : list) {
			card.setCanClick(b);
		}
	}

	// �򿪳��ư�ť
	public void turnOn(boolean flag) {
		main.publishCard[0].setVisible(flag);
		main.publishCard[1].setVisible(flag);
		main.publishCard[2].setVisible(flag);
	}

	// �趨����
	public void setlord(int i) {
		Point point = new Point();
		Point point1 = new Point();
		Point point2 = new Point();
		if (i == 1) // ���ǵ���
		{
			point.x = 80;
			point.y = 430;
			point1.x = 80;
			point1.y = 20;
			point2.x = 700;
			point2.y = 20;
		}
		if (i == 0) {
			point.x = 80;
			point.y = 20;
			point1.x = 80;
			point1.y = 430;
			point2.x = 700;
			point2.y = 20;
		}
		if (i == 2) {
			point.x = 700;
			point.y = 20;
			point1.x = 80;
			point1.y = 20;
			point2.x = 80;
			point2.y = 430;
		}
		main.dizhu.setLocation(point);
		main.farmer1.setLocation(point1);
		main.farmer2.setLocation(point2);
		main.dizhu.setVisible(true);
		main.farmer1.setVisible(true);
		main.farmer2.setVisible(true);
	}

	// �����Ʒ���
	public void openlord(boolean is) {
		for (int i = 0; i < 3; i++) {
			if (is)
				main.lordList.get(i).turnFront(); // �����Ʒ���
			else {
				main.lordList.get(i).turnRear(); // �����Ʊպ�
			}
			main.lordList.get(i).setCanClick(true);// �ɱ����
		}
	}

	public void sleepSeconds(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
