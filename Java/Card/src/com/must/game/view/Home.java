package com.must.game.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import com.must.game.thread.TimeThread;
import com.must.game.utils.OneSendCard;
import com.must.game.utils.cardType.CardTypeFactory;
import com.must.game.utils.cardType.cardTypeImpl.CardTypeString;
import com.must.game.vo.Card;

public class Home extends JFrame implements ActionListener, Runnable {
	public static Home main = null;
	public Container container = null;// ��������
	public JButton landlord[] = new JButton[2];// ��������ť
	public JButton publishCard[] = new JButton[3];// ���ư�ť
	public int dizhuFlag = -1;// ������־
	public int turn;
	public JLabel dizhu, farmer1, farmer2; // ����ͼ��
	public List<Card> currentList[] = new ArrayList[3]; // ��ǰ�ĳ���
	public List<Card> playerList[] = new ArrayList[3]; // ����3����ұ�
	public List<Card> lordList;// ������
	public List<Card> hasSendList = new ArrayList<Card>();// �Ѿ�������
	public Card card[] = new Card[54]; // ����54����
	public JLabel time[] = new JLabel[3]; // ��ʱ��
	TimeThread t;// ��ʱ�߳�
	public boolean nextPlayer = false; // ת����ɫ
	public static boolean debug = true;
	// ��һ�����Ƶ�����0�ǵ���һ��1���Լ���2�ǵ���2
	public int preChuPai = -1;
	// ��һ�γ�����
	public OneSendCard preOneSendCard;
	// ���Գ����Ƿ���� 0û����ϣ�1 ������� 2 ��Ҫ
	public int[] hasSend = new int[] { 0, 0, 0 };
	public ImageIcon logImage, startImage, exitImage,robImage,
					noImage, tipImage, disImage, doImage, changeImage;
	public JLabel logJLabel, winOrLose;
	public JButton startBtn, exitBtn, changeBtn;
	public ImageIcon winImage, loseImage;
	public JLabel[] winJLabel, loseJLabel;
	int color = 0;
 
	public Home() {
		Init();// ��ʼ��
		container.repaint();
	}

	// ��ʼ������
	public void Init() {
		this.setTitle("������");
		this.setSize(830, 620);
		setResizable(false);
		setLocationRelativeTo(getOwner()); // ��Ļ����
		container = this.getContentPane();
		container.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		// ���ÿ�ʼ����İ�ť
		logImage = new ImageIcon("images/log.png");
		logJLabel = new JLabel(logImage);
		logJLabel.setSize(logImage.getIconWidth(), logImage.getIconHeight());
		logJLabel.setLocation((container.getWidth() - logImage.getIconWidth()) / 2,
				(container.getHeight() - logImage.getIconHeight()) / 4);
		container.add(logJLabel);
		startImage = new ImageIcon("images//startbtn.png");
		startBtn = new JButton(startImage);
		startBtn.setSize(startImage.getIconWidth(), startImage.getIconHeight());
		startBtn.setLocation((container.getWidth() - startImage.getIconWidth()) / 2, 350);
		startBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				startGame();
			}
		});
		container.add(startBtn);
		exitImage = new ImageIcon("images//exitGame.png");
		exitBtn = new JButton(exitImage);
		exitBtn.setSize(exitImage.getIconWidth(), exitImage.getIconHeight());
		exitBtn.setLocation((container.getWidth() - exitImage.getIconWidth()) / 2, 400);
		exitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		container.add(exitBtn);
	}

	void startGame() {
		container.removeAll();
		SetButton();// �����˵� ��ť(������������,��ʱ��)
		CardInit();// ����
		getLord(); // �����ƿ�ʼ������
		time[1].setVisible(true);
		t = new TimeThread(this);
		t.start();
	}

	// ������
	public void getLord() {
		for (int i = 0; i < 2; i++)
			landlord[i].setVisible(true);
	}

	// ��ʼ����
	// ����ϴ��
	public void CardInit() {
		int count = 0;
		// ��ʼ����ǰ52
		for (int i = 1; i <= 4; i++) {
			for (int j = 3; j <= 15; j++) {
				card[count] = new Card(this, j, i, false);
				card[count].setLocation(350, 50);
				container.add(card[count]);
				count++;
			}
		}
		card[52] = new Card(this, 16, 5, false);
		card[52].setLocation(350, 50);
		container.add(card[52]);
		card[53] = new Card(this, 17, 5, false);
		card[53].setLocation(350, 50);
		container.add(card[53]);
		// ����˳��
		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int a = random.nextInt(54);
			int b = random.nextInt(54);
			Card k = card[a];
			card[a] = card[b];
			card[b] = k;
		}

		// ��ʼ����
		for (int i = 0; i < 3; i++)
			playerList[i] = new ArrayList<Card>(); // �����
		lordList = new ArrayList<Card>();// ����������
		int t = 0;
		for (int i = 0; i <= 53; i++) {
			if (i >= 51) // ������
			{
				Common.move(card[i], card[i].getLocation(), new Point(380 + (i - 52) * 80, 10));
				lordList.add(card[i]);
				continue;
			}
			switch ((t++) % 3) {
			case 0:
				// ������
				Common.move(card[i], card[i].getLocation(), new Point(50, 60 + (i + 1) * 5));
				playerList[0].add(card[i]);
				if (this.debug == false) {
					card[i].turnFront(); // ��ʾ����
				}
				break;
			case 1:
				// ��
				Common.move(card[i], card[i].getLocation(), new Point(180 + (i + 1) * 7, 450));
				playerList[1].add(card[i]);
				card[i].turnFront(); // ��ʾ����
				break;
			case 2:
				// �ұ����
				if (this.debug == false) {
					card[i].turnFront(); // ��ʾ����
				}
				Common.move(card[i], card[i].getLocation(), new Point(700, 60 + (i + 1) * 5));
				playerList[2].add(card[i]);
				break;
			}
			container.setComponentZOrder(card[i], 0);
		}
		// ���������򣬴Ӵ�С
		for (int i = 0; i < 3; i++) {
			Common.order(playerList[i]);
			Common.rePosition(this, playerList[i], i);// ���¶�λ
		}
		dizhu = new JLabel(new ImageIcon("images/dizhu.gif"));
		farmer1 = new JLabel(new ImageIcon("images/farmer1.png"));
		farmer2 = new JLabel(new ImageIcon("images/farmer2.jpg"));
		dizhu.setVisible(false);
		farmer1.setVisible(false);
		farmer2.setVisible(false);
		dizhu.setSize(40, 40);
		farmer1.setSize(40, 40);
		farmer2.setSize(40, 40);
		container.add(dizhu);
		container.add(farmer1);
		container.add(farmer2);
	}

	// �������ܰ�ť
	public void SetButton() {
		robImage = new ImageIcon("images/rob.png");
		noImage = new ImageIcon("images/no.png");
		tipImage = new ImageIcon("images/tip.png");
		disImage = new ImageIcon("images/dis.png");
		doImage = new ImageIcon("images/do.png");

		landlord[0] = new JButton(robImage);
		landlord[1] = new JButton(noImage);
		publishCard[0] = new JButton(doImage);
		publishCard[1] = new JButton(disImage);
		publishCard[2] = new JButton(tipImage);
		landlord[0].setSize(robImage.getIconWidth(), robImage.getIconHeight());
		landlord[1].setSize(noImage.getIconWidth(), noImage.getIconHeight());
		publishCard[0].setSize(doImage.getIconWidth(), doImage.getIconHeight());
		publishCard[1].setSize(disImage.getIconWidth(), disImage.getIconHeight());
		publishCard[2].setSize(tipImage.getIconWidth(), tipImage.getIconHeight());
		for (int i = 0; i < 2; i++) {
			publishCard[i].setLocation(270 + i * 100, 400);
			landlord[i].setLocation(320 + i * 100, 400);
			container.add(landlord[i]);
			landlord[i].addActionListener(this);
			landlord[i].setVisible(false);
			container.add(publishCard[i]);
			publishCard[i].setVisible(false);
			publishCard[i].addActionListener(this);
		}
		// ��ʾ��ť
		publishCard[2].setLocation(270 + 2 * 100, 400);
		container.add(publishCard[2]);
		publishCard[2].setVisible(false);
		publishCard[2].addActionListener(this);
		for (int i = 0; i < 3; i++) {
			time[i] = new JLabel("����ʱ:");
			time[i].setVisible(false);
			container.add(time[i]);
		}
		time[0].setBounds(140, 230, 60, 20);
		time[1].setBounds(374, 360, 60, 20);
		time[2].setBounds(620, 230, 60, 20);

		for (int i = 0; i < 3; i++) {
			currentList[i] = new ArrayList<Card>();
		}

		// ��Ϸ������ʾ
		winOrLose = new JLabel("You win!");
		winOrLose.setSize(600, 100);
		winOrLose.setHorizontalAlignment(JLabel.CENTER);
		winOrLose.setFont(new Font("����", Font.BOLD, 50));
		winOrLose.setLocation((container.getWidth() - winOrLose.getWidth()) / 2, 200);
		winOrLose.setVisible(false);
		container.add(winOrLose);

		// ʤ����ʾ�̻�
		winImage = new ImageIcon("images/win.gif");
		winJLabel = new JLabel[2];
		for (int i = 0; i < winJLabel.length; i++) {
			winJLabel[i] = new JLabel(winImage);
			winJLabel[i].setSize(winImage.getIconWidth(), winImage.getIconHeight());
			winJLabel[i].setLocation((container.getWidth() - winOrLose.getWidth()) / 2 + 350 * i, 200);
			winJLabel[i].setVisible(false);
			container.add(winJLabel[i]);
		}

		// ʧ����ʾ��ˮ
		loseImage = new ImageIcon("images/lose.gif");
		loseJLabel = new JLabel[2];
		for (int i = 0; i < loseJLabel.length; i++) {
			loseJLabel[i] = new JLabel(loseImage);
			loseJLabel[i].setSize(300, 150);
			loseJLabel[i].setLocation((container.getWidth() - winOrLose.getWidth()) / 2 + 300 * i, 175);
			loseJLabel[i].setVisible(false);
			container.add(loseJLabel[i]);
		}

		// ��ɫ��ť
		changeImage = new ImageIcon("images/change.png");
		changeBtn = new JButton(changeImage);
		changeBtn.setSize(changeImage.getIconWidth(), changeImage.getIconHeight());
		changeBtn.setOpaque(true);
		changeBtn.setLocation(690, 520);
		changeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (color % 6) {
				case 0:
					container.setBackground(new Color(221, 221, 221));
					break;
				case 1:
					container.setBackground(new Color(176, 255, 176));
					break;
				case 2:
					container.setBackground(new Color(167, 223, 237));
					break;
				case 3:
					container.setBackground(new Color(255, 206, 231));
					break;
				case 4:
					container.setBackground(new Color(200, 191, 231));
					break;
				case 5:
					container.setBackground(new Color(238, 238, 238));
					break;
				}
				container.repaint();
				color++;
			}
		});
		container.add(changeBtn);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == landlord[0]) {
			time[1].setText("������");
			this.dizhuFlag = 1;
			this.turn = 1;
		}
		if (e.getSource() == landlord[1]) {
			time[1].setText("����");
			this.dizhuFlag = Common.computeLord(this.playerList[0], this.playerList[2]);
			this.turn = this.dizhuFlag;
		}
		// ����ǲ�Ҫ
		if (e.getSource() == publishCard[1]) {
			this.nextPlayer = true;
			currentList[1].clear();
			time[1].setText("��Ҫ");
		}
		// �������ʾ��ť
		if (e.getSource() == publishCard[2]) {
			// �ж���ѹ�ƻ����������ƣ�����CardTypeFactory��ȡѹ����ʾ������������ʾ
			OneSendCard osc = null;
			if (this.preChuPai == 1 || this.preChuPai == -1) {
				// ����������
				osc = CardTypeFactory.getAutoPrompt(this.playerList[1]);

			} else {
				osc = CardTypeFactory.getBiggerPrompt(this.playerList[1], this.preOneSendCard);
			}
			if (osc == null) {
				// ˵��Ҫ����,��Ҫ���㲻Ҫ��ͬ�Ķ���
				this.nextPlayer = true;
				currentList[1].clear();
				time[1].setText("��Ҫ");
			} else {
				// Ҫ���ˣ�����Card�����ƶ�
				for (Card bc : osc.getOneSendCardList()) {
					bc.move();
				}
			}
		}
		// ����ǳ��ư�ť
		if (e.getSource() == publishCard[0]) {
			List<Card> c = new ArrayList<Card>();
			// ��ѡ����
			for (int i = 0; i < playerList[1].size(); i++) {
				Card card = playerList[1].get(i);
				if (card.clicked) {
					c.add(card);
				}
			}
			// �Ƿ���Գ��Ʊ��1�ɳ���0���ɳ���
			int flag = 0;
			// �������������
			if (this.preChuPai == -1 || this.preChuPai == 1) {
				String cardType = CardTypeFactory.getCardType(c);
				if (!cardType.equals(CardTypeString.NONE_CARDTYPE)) {
					// ����������һ������
					flag = 1;
					preOneSendCard = new OneSendCard(c, cardType);
					this.preChuPai = 1;
				}
			} // ����Ҹ���
			else {
				String cardType = CardTypeFactory.getCardType(c);
				if (!cardType.equals(CardTypeString.NONE_CARDTYPE)) {
					// ����������һ������
					OneSendCard oneSendCard = new OneSendCard(c, cardType);
					int compareValue = CardTypeFactory.compareOneSendCard(oneSendCard, this.preOneSendCard);
					if (compareValue > 0) {
						flag = 1;
						preOneSendCard = oneSendCard;
						this.preChuPai = 1;
					} else {
						flag = 0;
					}
				} else {
					flag = 0;
				}

			}
			// �ж��Ƿ���ϳ���
			if (flag == 1) {
				currentList[1] = c;
				// ���Լ����������ӵ�hasSendList
				Home.this.hasSendList.addAll(c);
				playerList[1].removeAll(currentList[1]);// �Ƴ��ߵ���
				// ��λ����
				Point point = new Point();
				point.x = (770 / 2) - (currentList[1].size() + 1) * 15 / 2;
				point.y = 300;
				for (int i = 0, len = currentList[1].size(); i < len; i++) {
					Card card = currentList[1].get(i);
					Common.move(card, card.getLocation(), point);
					point.x += 15;
				}
				// �����ƺ�����������
				Common.rePosition(this, playerList[1], 1);
				time[1].setVisible(false);
				this.nextPlayer = true;
			}
		}
	}

	public static void main(String args[]) {
		Home me = new Home();
		Home.main = me;
		new Thread(me).start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}

	/**
	 * �ж��������ǲ���һ���
	 */
	public boolean isFriend(int i1, int i2) {
		boolean b = true;
		// ֻҪ����������һ���ǵ��������ǾͲ���һ���
		if (i1 == this.dizhuFlag || i2 == this.dizhuFlag) {
			b = false;
		}
		return b;
	}
}
