package view;

import thread.TimeThread;
import utils.OneSendCard;
import utils.cardType.CardTypeFactory;
import utils.iniEdit.IniEditorAdapter;
import utils.iniEdit.IniEditorInterface;
import cardDesign.Card;
import cardDesign.CardShare;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    //public JLabel[] tips = new JLabel[3];
    public Player[] players = new Player[3];
    public ControlCenter controlCenter = new ConcreteControlCenter();
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
    public Color backgroundColor = null;
    IniEditorInterface editorTarget = new IniEditorAdapter();

    /**
     * 初始化并将图像展示出
     * @throws InterruptedException
     */
    public Home() throws InterruptedException, IOException {
        this.Init();
        this.container.repaint();
    }

    /**
     * 初始化界面,并添加需要的监听器
     */
    public void Init() throws InterruptedException, IOException {
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
                try {
                    Home.this.startGame();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        //startGame();
    }

    /**
     * 开始游戏，首先清除初始化界面的内容，之后添加Button、Card、
     */
    void startGame() throws InterruptedException, IOException {
        this.container.removeAll();
        //sleep(3000);
        this.SetButton();
        this.CardInit();
        this.getLord();
        this.time[1].setVisible(true);
        this.t = new TimeThread(this);
        this.t.start();
    }

    /**
     * 抢地主按钮图标
     */
    public void getLord() {
        for(int i = 0; i < 2; ++i) {
            this.landlord[i].setVisible(true);
        }
    }

    public void CardInit() {
        int count = 0;


        //使用享元模式获取创建的牌
        CardShare cardShare = new CardShare().getCardShare();
        //初始化黑红梅花片四种牌的每种13张牌
        for(int t = 1; t <= 4; ++t) {
            for(int i = 3; i <= 15; ++i) {
                if(i >= 14 && i <= 15){
                    this.card[count]= (Card) cardShare.getCard(t + " " + (i-13));
                }else{
                    this.card[count]= (Card) cardShare.getCard(t + " " + i);
                }
                this.card[count].setLocation(350, 50);
                this.container.add(this.card[count]);
                ++count;
            }
        }

        //初始化大王和小王
        this.card[52] = (Card) cardShare.getCard("smallKing");
        this.card[52].setLocation(350, 50);
        this.container.add(this.card[52]);

        this.card[53] = (Card) cardShare.getCard("bigKing");
        this.card[53].setLocation(350, 50);
        this.container.add(this.card[53]);

//        打乱牌顺序
        for(int t = 0; t < 100; ++t) {
            Random var7 = new Random();
            int a = var7.nextInt(54);
            int b = var7.nextInt(54);
            Card k = this.card[a];
            this.card[a] = this.card[b];
            this.card[b] = k;
        }

        /**
         * 进行发牌操作
         * 按照循环顺序进行发放，剩下的3张牌为地主牌
         */
        for(int t = 0; t < 3; ++t) {
            this.playerList[t] = new ArrayList();
        }

        this.lordList = new ArrayList();
        int t = 0;

        for(int i = 0; i <= 53; ++i) {
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

        for(int i = 0; i < 3; ++i) {
            Common.order(this.playerList[i]);
            Common.rePosition(this, this.playerList[i], i);
        }

        /**
         * 设置地主与农民的头像
         * 并设置3个的大小与位置，但是并不显示
         */
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

    public void SetButton() throws IOException {
        /**
         * 设置其中会用到的每个Button,加入图标，但是并不显示
         */
        this.robImage = new ImageIcon("images/rob.png");//叫地主
        this.noImage = new ImageIcon("images/no.png");//不叫
        this.tipImage = new ImageIcon("images/tip.png");//提示
        this.disImage = new ImageIcon("images/dis.png");//不要
        this.doImage = new ImageIcon("images/do.png");//出牌

        this.landlord[0] = new JButton(this.robImage);
        this.landlord[1] = new JButton(this.noImage);

        this.publishCard[0] = new JButton(this.doImage);
        this.publishCard[1] = new JButton(this.disImage);
        this.publishCard[2] = new JButton(this.tipImage);

        //根据图片大小设置Button
        this.landlord[0].setSize(this.robImage.getIconWidth(), this.robImage.getIconHeight());
        this.landlord[1].setSize(this.noImage.getIconWidth(), this.noImage.getIconHeight());
        this.publishCard[0].setSize(this.doImage.getIconWidth(), this.doImage.getIconHeight());
        this.publishCard[1].setSize(this.disImage.getIconWidth(), this.disImage.getIconHeight());
        this.publishCard[2].setSize(this.tipImage.getIconWidth(), this.tipImage.getIconHeight());


        /**
         * 将5种按钮添加到界面中，但是并不显示
         */
        for(int i = 0; i < 2; ++i) {
            this.landlord[i].setLocation(320 + i * 100, 400);
            this.landlord[i].addActionListener(this);
            this.landlord[i].setVisible(false);
            this.container.add(this.landlord[i]);

            this.publishCard[i].setLocation(270 + i * 100, 400);
            this.publishCard[i].setVisible(false);
            this.publishCard[i].addActionListener(this);
            this.container.add(this.publishCard[i]);
        }

        this.publishCard[2].setLocation(470, 400);
        this.container.add(this.publishCard[2]);
        this.publishCard[2].setVisible(false);
        this.publishCard[2].addActionListener(this);

        /**
         * 添加倒计时Lable
         */
        for(int i = 0; i < 3; ++i) {
            this.time[i] = new JLabel("倒计时:");
            this.time[i].setVisible(false);
            this.container.add(this.time[i]);
        }

        /**
         * 添加牌少提示
         */
        for(int i=0;i<3;i++){
//            this.tips[i] = new JLabel("tips");
//            this.tips[i].setVisible(true);
//            this.container.add(this.tips[i]);
            players[i] = new Player();
            players[i].setName("player" + i);
            players[i].setjLabel(time[i]);
            controlCenter.join(players[i]);
        }

//        this.time[0].setBounds(140, 40, 100, 20);
//        this.time[1].setBounds(50, 500, 100, 20);
//        this.time[2].setBounds(580, 40, 100, 20);

        this.time[0].setBounds(140, 230, 60, 20);
        this.time[1].setBounds(374, 360, 60, 20);
        this.time[2].setBounds(620, 230, 60, 20);

        /**
         * 当前牌桌上的牌
         */
        for(int i = 0; i < 3; ++i) {
            this.currentList[i] = new ArrayList();
        }

        /**
         * 设置最后输赢提示Label
         */
        this.winOrLose = new JLabel("You win!");
        this.winOrLose.setSize(600, 100);
        this.winOrLose.setHorizontalAlignment(0);
        this.winOrLose.setFont(new Font("黑体", 1, 50));
        this.winOrLose.setLocation((this.container.getWidth() - this.winOrLose.getWidth()) / 2, 200);
        this.winOrLose.setVisible(false);
        this.container.add(this.winOrLose);


        /**
         * 胜利动画
         */
        this.winImage = new ImageIcon("images/win.gif");
        this.winJLabel = new JLabel[2];
        for(int i = 0; i < this.winJLabel.length; ++i) {
            this.winJLabel[i] = new JLabel(this.winImage);
            this.winJLabel[i].setSize(this.winImage.getIconWidth(), this.winImage.getIconHeight());
            this.winJLabel[i].setLocation((this.container.getWidth() - this.winOrLose.getWidth()) / 2 + 350 * i, 200);
            this.winJLabel[i].setVisible(false);
            this.container.add(this.winJLabel[i]);
        }


        /**
         * 失败动画
         */
        this.loseImage = new ImageIcon("images/lose.gif");
        this.loseJLabel = new JLabel[2];
        for(int i = 0; i < this.loseJLabel.length; ++i) {
            this.loseJLabel[i] = new JLabel(this.loseImage);
            this.loseJLabel[i].setSize(300, 150);
            this.loseJLabel[i].setLocation((this.container.getWidth() - this.winOrLose.getWidth()) / 2 + 300 * i, 175);
            this.loseJLabel[i].setVisible(false);
            this.container.add(this.loseJLabel[i]);
        }

        /**
         * 一键改变背景颜色
         */
        this.changeImage = new ImageIcon("images/change.png");
        this.changeBtn = new JButton(this.changeImage);
        this.changeBtn.setSize(this.changeImage.getIconWidth(), this.changeImage.getIconHeight());
        this.changeBtn.setOpaque(true);
        this.changeBtn.setLocation(690, 520);


        int colorR = Integer.parseInt(editorTarget.getValue("myInterface","myColorR"));
        int colorG = Integer.parseInt(editorTarget.getValue("myInterface","myColorG"));
        int colorB = Integer.parseInt(editorTarget.getValue("myInterface","myColorB"));

        Home.this.container.setBackground(new Color(colorR, colorG, colorB));


        this.changeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                switch(Home.this.color % 6) {
                    case 0:
                        backgroundColor = new Color(221, 221, 221);
                        Home.this.container.setBackground(backgroundColor);

                        break;
                    case 1:
                        backgroundColor = new Color(176, 255, 176);
                        Home.this.container.setBackground(backgroundColor);
                        break;
                    case 2:
                        backgroundColor = new Color(167,223,237);
                        Home.this.container.setBackground(backgroundColor);
                        break;
                    case 3:
                        backgroundColor = new Color(255,206,231);
                        Home.this.container.setBackground(backgroundColor);
                        break;
                    case 4:
                        backgroundColor = new Color(200, 191, 231);
                        Home.this.container.setBackground(backgroundColor);
                        break;
                    case 5:
                        backgroundColor = new Color(238, 238, 238);
                        Home.this.container.setBackground(backgroundColor);
                }

                Home.this.container.repaint();
                try {
                    editorTarget.setValue("myInterface","myColorR",backgroundColor.getRed()+"");
                    editorTarget.setValue("myInterface","myColorG",backgroundColor.getGreen()+"");
                    editorTarget.setValue("myInterface","myColorB",backgroundColor.getBlue()+"");

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                ++Home.this.color;
            }
        });
        this.container.add(this.changeBtn);
    }

    public void actionPerformed(ActionEvent e) {
        /**
         * 抢地主
         */
        if(e.getSource() == this.landlord[0]) {
            this.time[1].setText("抢地主");
            this.dizhuFlag = 1;
            this.turn = 1;
        }

        /**
         * 不抢
         */
        if(e.getSource() == this.landlord[1]) {
            this.time[1].setText("不抢");
            this.dizhuFlag = Common.computeLord(this.playerList[0], this.playerList[2]);
            this.turn = this.dizhuFlag;
        }

        /**
         * 出牌
         */
        if(e.getSource() == this.publishCard[0]) {
            ArrayList arrayListChoose = new ArrayList();

            for(int i = 0; i < this.playerList[1].size(); ++i) {
                Card card = (Card)this.playerList[1].get(i);
                if(card.clicked) {
                    arrayListChoose.add(card);
                }
            }

            boolean var10 = false;
            int len;
            String cardType;
            /**
             * 当没有人
             */
            if(this.preChuPai != -1 && this.preChuPai != 1) {
                /**
                 * 判断牌的类型
                 */
                cardType = CardTypeFactory.getCardType(arrayListChoose);

                if(!cardType.equals("c0")) {
                    OneSendCard i = new OneSendCard(arrayListChoose, cardType);
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
                cardType = CardTypeFactory.getCardType(arrayListChoose);
                if(!cardType.equals("c0")) {
                    var10 = true;
                    this.preOneSendCard = new OneSendCard(arrayListChoose, cardType);
                    this.preChuPai = 1;
                }
            }

            if(var10) {
                this.currentList[1] = arrayListChoose;
                this.hasSendList.addAll(arrayListChoose);
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

        /**
         * 不要
         */

        if(e.getSource() == this.publishCard[1]) {
            this.nextPlayer = true;
            this.currentList[1].clear();
            this.time[1].setText("不要");
        }

        /**
         * 提示
         */
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




    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Home me = new Home();
        main = me;
        //(new Thread(me)).start();
    }

    public void run() {
    }

    /**
     * 判断是友方还是敌方
     * @param i1
     * @param i2
     * @return
     */
    public boolean isFriend(int i1, int i2) {
        boolean b = true;
        if(i1 == this.dizhuFlag || i2 == this.dizhuFlag) {
            b = false;
        }

        return b;
    }
}

