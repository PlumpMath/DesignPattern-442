package view;

import thread.TimeThread;
import utils.OneSendCard;
import utils.cardType.CardTypeFactory;
import vo.Card;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Created by DrownFish on 2017/3/10.
 */

public class Home extends JFrame implements ActionListener, Runnable {
    public static Home main = null;
    public Container container = null;
    public JButton[] landlord = new JButton[2];
    public JButton[] publishCard = new JButton[3];
    public int dizhuFlag = -1;
    public int turn;
    public JLabel dizhu;
    public JLabel farmer1;
    public JLabel farmer2;
    public java.util.List<Card>[] currentList = new ArrayList[3];
    public java.util.List<Card>[] playerList = new ArrayList[3];
    public java.util.List<Card> lordList;
    public java.util.List<Card> hasSendList = new ArrayList();
    public Card[] card = new Card[54];
    public JLabel[] time = new JLabel[3];
    TimeThread t;
    public boolean nextPlayer = false;
    public static boolean debug = true;
    public int preChuPai = -1;
    public OneSendCard preOneSendCard;
    public int[] hasSend = new int[3];
    public ImageIcon logImage;
    public ImageIcon startImage;
    public ImageIcon exitImage;
    public ImageIcon robImage;
    public ImageIcon noImage;
    public ImageIcon tipImage;
    public ImageIcon disImage;
    public ImageIcon doImage;
    public ImageIcon changeImage;
    public JLabel logJLabel;
    public JLabel winOrLose;
    public JButton startBtn;
    public JButton exitBtn;
    public JButton changeBtn;
    public ImageIcon winImage;
    public ImageIcon loseImage;
    public JLabel[] winJLabel;
    public JLabel[] loseJLabel;
    int color = 0;

    public Home() {
        this.Init();
        this.container.repaint();
    }

    public void Init() {
        this.setTitle("斗地主");
        this.setSize(830, 620);
        this.setResizable(false);
        this.setLocationRelativeTo(this.getOwner());
        this.container = this.getContentPane();
        this.container.setLayout((LayoutManager)null);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
        this.logImage = new ImageIcon("images/log.png");
        this.logJLabel = new JLabel(this.logImage);
        this.logJLabel.setSize(this.logImage.getIconWidth(), this.logImage.getIconHeight());
        this.logJLabel.setLocation((this.container.getWidth() - this.logImage.getIconWidth()) / 2, (this.container.getHeight() - this.logImage.getIconHeight()) / 4);
        this.container.add(this.logJLabel);
        this.startImage = new ImageIcon("images//startbtn.png");
        this.startBtn = new JButton(this.startImage);
        this.startBtn.setSize(this.startImage.getIconWidth(), this.startImage.getIconHeight());
        this.startBtn.setLocation((this.container.getWidth() - this.startImage.getIconWidth()) / 2, 350);
        this.startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Home.this.startGame();
            }
        });
        this.container.add(this.startBtn);
        this.exitImage = new ImageIcon("images//exitGame.png");
        this.exitBtn = new JButton(this.exitImage);
        this.exitBtn.setSize(this.exitImage.getIconWidth(), this.exitImage.getIconHeight());
        this.exitBtn.setLocation((this.container.getWidth() - this.exitImage.getIconWidth()) / 2, 400);
        this.exitBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Home.this.dispose();
            }
        });
        this.container.add(this.exitBtn);
    }

    void startGame() {
        this.container.removeAll();
        this.SetButton();
        this.CardInit();
        this.getLord();
        this.time[1].setVisible(true);
        this.t = new TimeThread(this);
        this.t.start();
    }

    public void getLord() {
        for(int i = 0; i < 2; ++i) {
            this.landlord[i].setVisible(true);
        }

    }

    public void CardInit() {
        int count = 0;

        int t;
        int i;
        for(t = 1; t <= 4; ++t) {
            for(i = 3; i <= 15; ++i) {
                this.card[count] = new Card(this, i, t, false);
                this.card[count].setLocation(350, 50);
                this.container.add(this.card[count]);
                ++count;
            }
        }

        this.card[52] = new Card(this, 16, 5, false);
        this.card[52].setLocation(350, 50);
        this.container.add(this.card[52]);
        this.card[53] = new Card(this, 17, 5, false);
        this.card[53].setLocation(350, 50);
        this.container.add(this.card[53]);

        for(t = 0; t < 100; ++t) {
            Random var7 = new Random();
            int a = var7.nextInt(54);
            int b = var7.nextInt(54);
            Card k = this.card[a];
            this.card[a] = this.card[b];
            this.card[b] = k;
        }

        for(t = 0; t < 3; ++t) {
            this.playerList[t] = new ArrayList();
        }

        this.lordList = new ArrayList();
        t = 0;

        for(i = 0; i <= 53; ++i) {
            if(i >= 51) {
                Common.move(this.card[i], this.card[i].getLocation(), new Point(380 + (i - 52) * 80, 10));
                this.lordList.add(this.card[i]);
            } else {
                switch(t++ % 3) {
                    case 0:
                        Common.move(this.card[i], this.card[i].getLocation(), new Point(50, 60 + (i + 1) * 5));
                        this.playerList[0].add(this.card[i]);
                        if(!debug) {
                            this.card[i].turnFront();
                        }
                        break;
                    case 1:
                        Common.move(this.card[i], this.card[i].getLocation(), new Point(180 + (i + 1) * 7, 450));
                        this.playerList[1].add(this.card[i]);
                        this.card[i].turnFront();
                        break;
                    case 2:
                        if(!debug) {
                            this.card[i].turnFront();
                        }

                        Common.move(this.card[i], this.card[i].getLocation(), new Point(700, 60 + (i + 1) * 5));
                        this.playerList[2].add(this.card[i]);
                }

                this.container.setComponentZOrder(this.card[i], 0);
            }
        }

        for(i = 0; i < 3; ++i) {
            Common.order(this.playerList[i]);
            Common.rePosition(this, this.playerList[i], i);
        }

        this.dizhu = new JLabel(new ImageIcon("images/dizhu.gif"));
        this.farmer1 = new JLabel(new ImageIcon("images/farmer1.png"));
        this.farmer2 = new JLabel(new ImageIcon("images/farmer2.jpg"));
        this.dizhu.setVisible(false);
        this.farmer1.setVisible(false);
        this.farmer2.setVisible(false);
        this.dizhu.setSize(40, 40);
        this.farmer1.setSize(40, 40);
        this.farmer2.setSize(40, 40);
        this.container.add(this.dizhu);
        this.container.add(this.farmer1);
        this.container.add(this.farmer2);
    }

    public void SetButton() {
        this.robImage = new ImageIcon("images/rob.png");
        this.noImage = new ImageIcon("images/no.png");
        this.tipImage = new ImageIcon("images/tip.png");
        this.disImage = new ImageIcon("images/dis.png");
        this.doImage = new ImageIcon("images/do.png");
        this.landlord[0] = new JButton(this.robImage);
        this.landlord[1] = new JButton(this.noImage);
        this.publishCard[0] = new JButton(this.doImage);
        this.publishCard[1] = new JButton(this.disImage);
        this.publishCard[2] = new JButton(this.tipImage);
        this.landlord[0].setSize(this.robImage.getIconWidth(), this.robImage.getIconHeight());
        this.landlord[1].setSize(this.noImage.getIconWidth(), this.noImage.getIconHeight());
        this.publishCard[0].setSize(this.doImage.getIconWidth(), this.doImage.getIconHeight());
        this.publishCard[1].setSize(this.disImage.getIconWidth(), this.disImage.getIconHeight());
        this.publishCard[2].setSize(this.tipImage.getIconWidth(), this.tipImage.getIconHeight());

        int i;
        for(i = 0; i < 2; ++i) {
            this.publishCard[i].setLocation(270 + i * 100, 400);
            this.landlord[i].setLocation(320 + i * 100, 400);
            this.container.add(this.landlord[i]);
            this.landlord[i].addActionListener(this);
            this.landlord[i].setVisible(false);
            this.container.add(this.publishCard[i]);
            this.publishCard[i].setVisible(false);
            this.publishCard[i].addActionListener(this);
        }

        this.publishCard[2].setLocation(470, 400);
        this.container.add(this.publishCard[2]);
        this.publishCard[2].setVisible(false);
        this.publishCard[2].addActionListener(this);

        for(i = 0; i < 3; ++i) {
            this.time[i] = new JLabel("倒计时:");
            this.time[i].setVisible(false);
            this.container.add(this.time[i]);
        }

        this.time[0].setBounds(140, 230, 60, 20);
        this.time[1].setBounds(374, 360, 60, 20);
        this.time[2].setBounds(620, 230, 60, 20);

        for(i = 0; i < 3; ++i) {
            this.currentList[i] = new ArrayList();
        }

        this.winOrLose = new JLabel("You win!");
        this.winOrLose.setSize(600, 100);
        this.winOrLose.setHorizontalAlignment(0);
        this.winOrLose.setFont(new Font("黑体", 1, 50));
        this.winOrLose.setLocation((this.container.getWidth() - this.winOrLose.getWidth()) / 2, 200);
        this.winOrLose.setVisible(false);
        this.container.add(this.winOrLose);
        this.winImage = new ImageIcon("images/win.gif");
        this.winJLabel = new JLabel[2];

        for(i = 0; i < this.winJLabel.length; ++i) {
            this.winJLabel[i] = new JLabel(this.winImage);
            this.winJLabel[i].setSize(this.winImage.getIconWidth(), this.winImage.getIconHeight());
            this.winJLabel[i].setLocation((this.container.getWidth() - this.winOrLose.getWidth()) / 2 + 350 * i, 200);
            this.winJLabel[i].setVisible(false);
            this.container.add(this.winJLabel[i]);
        }

        this.loseImage = new ImageIcon("images/lose.gif");
        this.loseJLabel = new JLabel[2];

        for(i = 0; i < this.loseJLabel.length; ++i) {
            this.loseJLabel[i] = new JLabel(this.loseImage);
            this.loseJLabel[i].setSize(300, 150);
            this.loseJLabel[i].setLocation((this.container.getWidth() - this.winOrLose.getWidth()) / 2 + 300 * i, 175);
            this.loseJLabel[i].setVisible(false);
            this.container.add(this.loseJLabel[i]);
        }

        this.changeImage = new ImageIcon("images/change.png");
        this.changeBtn = new JButton(this.changeImage);
        this.changeBtn.setSize(this.changeImage.getIconWidth(), this.changeImage.getIconHeight());
        this.changeBtn.setOpaque(true);
        this.changeBtn.setLocation(690, 520);
        this.changeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch(Home.this.color % 6) {
                    case 0:
                        Home.this.container.setBackground(new Color(221, 221, 221));
                        break;
                    case 1:
                        Home.this.container.setBackground(new Color(176, 255, 176));
                        break;
                    case 2:
                        Home.this.container.setBackground(new Color(167, 223, 237));
                        break;
                    case 3:
                        Home.this.container.setBackground(new Color(255, 206, 231));
                        break;
                    case 4:
                        Home.this.container.setBackground(new Color(200, 191, 231));
                        break;
                    case 5:
                        Home.this.container.setBackground(new Color(238, 238, 238));
                }

                Home.this.container.repaint();
                ++Home.this.color;
            }
        });
        this.container.add(this.changeBtn);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.landlord[0]) {
            this.time[1].setText("抢地主");
            this.dizhuFlag = 1;
            this.turn = 1;
        }

        if(e.getSource() == this.landlord[1]) {
            this.time[1].setText("不抢");
            this.dizhuFlag = Common.computeLord(this.playerList[0], this.playerList[2]);
            this.turn = this.dizhuFlag;
        }

        if(e.getSource() == this.publishCard[1]) {
            this.nextPlayer = true;
            this.currentList[1].clear();
            this.time[1].setText("不要");
        }

        if(e.getSource() == this.publishCard[2]) {
            OneSendCard c = null;
            if(this.preChuPai != 1 && this.preChuPai != -1) {
                c = CardTypeFactory.getBiggerPrompt(this.playerList[1], this.preOneSendCard);
            } else {
                c = CardTypeFactory.getAutoPrompt(this.playerList[1]);
            }

            if(c == null) {
                this.nextPlayer = true;
                this.currentList[1].clear();
                this.time[1].setText("不要");
            } else {
                Iterator point = c.getOneSendCardList().iterator();

                while(point.hasNext()) {
                    Card flag = (Card)point.next();
                    flag.move();
                }
            }
        }

        if(e.getSource() == this.publishCard[0]) {
            ArrayList var8 = new ArrayList();

            for(int var9 = 0; var9 < this.playerList[1].size(); ++var9) {
                Card var11 = (Card)this.playerList[1].get(var9);
                if(var11.clicked) {
                    var8.add(var11);
                }
            }

            boolean var10 = false;
            int len;
            String var12;
            if(this.preChuPai != -1 && this.preChuPai != 1) {
                var12 = CardTypeFactory.getCardType(var8);
                if(!var12.equals("c0")) {
                    OneSendCard i = new OneSendCard(var8, var12);
                    len = CardTypeFactory.compareOneSendCard(i, this.preOneSendCard);
                    if(len > 0) {
                        var10 = true;
                        this.preOneSendCard = i;
                        this.preChuPai = 1;
                    } else {
                        var10 = false;
                    }
                } else {
                    var10 = false;
                }
            } else {
                var12 = CardTypeFactory.getCardType(var8);
                if(!var12.equals("c0")) {
                    var10 = true;
                    this.preOneSendCard = new OneSendCard(var8, var12);
                    this.preChuPai = 1;
                }
            }

            if(var10) {
                this.currentList[1] = var8;
                this.hasSendList.addAll(var8);
                this.playerList[1].removeAll(this.currentList[1]);
                Point var13 = new Point();
                var13.x = 385 - (this.currentList[1].size() + 1) * 15 / 2;
                var13.y = 300;
                int var14 = 0;

                for(len = this.currentList[1].size(); var14 < len; ++var14) {
                    Card card = (Card)this.currentList[1].get(var14);
                    Common.move(card, card.getLocation(), var13);
                    var13.x += 15;
                }

                Common.rePosition(this, this.playerList[1], 1);
                this.time[1].setVisible(false);
                this.nextPlayer = true;
            }
        }

    }

    public static void main(String[] args) {
        Home me = new Home();
        main = me;
        (new Thread(me)).start();
    }

    public void run() {
    }

    public boolean isFriend(int i1, int i2) {
        boolean b = true;
        if(i1 == this.dizhuFlag || i2 == this.dizhuFlag) {
            b = false;
        }

        return b;
    }
}

