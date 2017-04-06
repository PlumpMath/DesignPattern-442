package thread;

import utils.OneSendCard;
import utils.cardType.CardTypeFactory;
import view.Common;
import view.Home;
import cardDesign.Card;
import java.awt.Color;
import java.awt.Point;
import java.util.List;

/**
 * Created by DrownFish on 2017/3/10.
 */


/**
 * 出牌线程
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
        // 选地主时间段
        for (int i = 10; i >= 0; i--) {
            // 等待十秒钟选地主
            if (main.dizhuFlag == -1) {
                // 没有确定谁是地主
                main.time[1].setText("倒计时:" + i);
                sleepSeconds(1);
            } else {
                // 抢完地主了
                break;
            }
        }
        /**
         * 当自己没有抢地主，并且时间到时，进行电脑抢地主操作
         */
        if(main.dizhuFlag == -1){
            main.dizhuFlag = Common.computeLord(main.playerList[0], main.playerList[2]);
        }

        /**
         * 发地主牌阶段
         */
        if (main.dizhuFlag == 1) {
            /**
             * 自己是地主
             */
            main.playerList[1].addAll(main.lordList);
            openlord(true);
            this.sleepSeconds(2);// 等待2秒
            Common.order(main.playerList[1]);
            Common.rePosition(main, main.playerList[1], 1);
            main.publishCard[1].setEnabled(false);
            setlord(1);
        } else {
            /**
             * 电脑选到地主
             */
            if (main.dizhuFlag == 2) {
                main.time[2].setText("抢地主");
                main.time[2].setVisible(true);
                setlord(2);// 设定地主
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
                main.time[0].setText("抢地主");
                main.time[0].setVisible(true);
                setlord(0);// 设定地主
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
        /**
         * 出牌时间段
         */
        turnOn(false);
        for (int i = 0; i < 3; i++) {
            main.time[i].setText("不要");
            main.time[i].setVisible(false);
        }
        turnOnLord(false);// 让抢地主按钮掩藏
        while (true) {

            if (main.turn == 1) // 自己
            {
                turnOn(true);// 出牌按钮 --我出牌
                // 如果我主动出牌关闭不要按钮
                if (main.preChuPai == 1)
                    main.publishCard[1].setEnabled(false);
                else {
                    main.publishCard[1].setEnabled(true);
                }
                // 如果主动出牌，清理桌面
                clearTable(1);
                if (main.preChuPai == 1) {
                    clearTable();
                }
                /**
                 * 使所有的牌可以点击
                 */
                this.makeCanClick(main.playerList[1], true);
                timeWait(30, 1);// 我自己的定时器
                turnOn(false);// 选完关闭
                main.turn = (main.turn + 1) % 3;
                // main.preChuPai=1;

            }
            if (main.turn == 0) {
                // 启动一个电脑计时线程
                main.hasSend[0] = 0;// 表示没有出牌完成
                computerTimer(0);
                clearTable(0);
                computer0();
                main.turn = (main.turn + 1) % 3;

            }
            if (main.turn == 2) {
                clearTable(2);
                // 启动一个电脑计时线程
                main.hasSend[2] = 0;// 表示没有出牌完成
                computerTimer(2);
                computer2();
                main.turn = (main.turn + 1) % 3;
            }

            if (win()) // 判断输赢
                break;
        }

    }

    /**
     * 判断输赢
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
     * 电脑倒计时
     * @param computer
     */
    public void computerTimer(int computer) {
        int total = 10;
        // 倒计时：
        main.time[computer].setText("倒计时:" + String.valueOf(10));
        main.time[computer].setVisible(true);
        for (int i = total; i >= 10; i--) {
            if (main.hasSend[computer] != 0) {
                // 已经出完牌或者不要就退出
                break;
            }
            main.time[computer].setText("倒计时:" + String.valueOf(i));
            sleepSeconds(1);
        }
    }

    /**
     * 清理某人的桌面
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
     * 清理桌面
     */
    private void clearTable() {
        // TODO Auto-generated method stub
        for (int i = 0; i < 3; i++) {
            clearTable(i);
        }
    }

    /**
     * 电脑2出牌 出牌分为（要牌和自主出牌） 要牌规则1 只要是敌人就要牌，只要友人就不要
     */
    private void computer2() {
        int role = 2;
        if (!isSelfSendCard(role)) {
            // 如果电脑2（自己）不是主动出牌
            if (!isFriend(role, main.preChuPai)) {
                // 如果不是同伙关系
                OneSendCard oneSendCard = CardTypeFactory.getOneSendCardBiggerButleast(main.playerList[2],
                        main.preOneSendCard);

                if (oneSendCard == null) {
                    // 要不了
                    main.hasSend[role] = 2; //不要状态
                    main.time[role].setText("不要");
                } else {
                    showCard(role, oneSendCard);
                    // 设置上一次出的什么牌，谁出的牌，是否已经出牌
                    main.preOneSendCard = oneSendCard;
                    // 将出牌增加到已出列表
                    main.hasSendList.addAll(oneSendCard.getOneSendCardList());
                    main.currentList[role] = oneSendCard.getOneSendCardList();
                    main.preChuPai = role;
                    main.hasSend[role] = 1; //要 状态
                    main.time[role].setText("我要");
                }
            } else {
                // 是同伙关系，暂时不要
                main.time[role].setText("不要");
                main.hasSend[role] = 2; //不要状态
            }
        } else {
            // 电脑2主动出牌
            OneSendCard oneSendCard = CardTypeFactory.getFirstBestOneSendCard(main.playerList[2]);
            showCard(role, oneSendCard);
            main.preOneSendCard = oneSendCard;
            // 将出牌增加到已出列表
            main.hasSendList.addAll(oneSendCard.getOneSendCardList());
            main.currentList[role] = oneSendCard.getOneSendCardList();
            main.preChuPai = role;
            main.hasSend[role] = 1;
        }
    }

    /**
     * 出牌效果
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
        point.y = (400 / 2) - (list.size() + 1) * 15 / 2;// 屏幕中部
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
     * 电脑0出牌 出牌分为（要牌和自主出牌）
     * 规则：
     *      1.如果是友方主动出牌，那么不要
     *      2.如果是地方主动出牌
     *
     */
    private void computer0() {
        // TODO Auto-generated method stub
        int role = 0;
        if (!isSelfSendCard(role)) {
            // 如果电脑0不是主动出牌
            if (!isFriend(role, main.preChuPai)) {
                // 如果不是同伙关系
                OneSendCard oneSendCard = CardTypeFactory.getBiggerOneSendCard(main.playerList[role],
                        main.preOneSendCard);
                if (oneSendCard == null) {
                    // 要不了
                    main.hasSend[role] = 2;
                    main.time[role].setText("不要");
                } else {
                    showCard(role, oneSendCard);
                    // 设置上一次出的什么牌，谁出的牌，是否已经出牌
                    main.preOneSendCard = oneSendCard;
                    // 将出牌增加到已出列表
                    main.hasSendList.addAll(oneSendCard.getOneSendCardList());
                    main.preChuPai = role;
                    main.hasSend[role] = 1;
                    main.time[role].setText("我要");
                }
            } else {
                // 是同伙关系，暂时不要
                main.time[role].setText("不要");
                main.hasSend[role] = 2;
            }
        } else {
            // 如果电脑0是主动出牌
            OneSendCard oneSendCard = CardTypeFactory.getFirstBestOneSendCard(main.playerList[role]);
            showCard(role, oneSendCard);
            // 设置上一次出的什么牌，谁出的牌，是否已经出牌
            main.preOneSendCard = oneSendCard;
            // 将出牌增加到已出列表
            main.hasSendList.addAll(oneSendCard.getOneSendCardList());
            main.preOneSendCard = oneSendCard;
            main.currentList[role] = oneSendCard.getOneSendCardList();
            main.preChuPai = role;
            main.hasSend[role] = 1;
        }
    }

    /**
     * 判断是否是主动出牌
     */
    public boolean isSelfSendCard(int player) {
        if (main.preChuPai == -1 || main.preChuPai == player) {
            // 如果还没有人出牌或者上一次出牌的人是自己，本次就是主动出牌
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断两个人是不是一伙的
     */
    public boolean isFriend(int i1, int i2) {
        boolean b = true;
        // 只要两个人中有一个是地主，他们就不是一伙的
        if (i1 == main.dizhuFlag || i2 == main.dizhuFlag) {
            b = false;
        }
        return b;
    }

    /**
     * 抢地主按钮显示，掩藏处理
     * @param b
     */
    private void turnOnLord(boolean b) {
        // TODO Auto-generated method stub
        for (int i = 0; i < 2; i++) {
            main.landlord[i].setVisible(b);
        }
    }

    /**
     * 延时，模拟时钟
     * @param n
     * @param player
     */
    public void timeWait(int n, int player) {

        if (main.currentList[player].size() > 0)
            Common.hideCards(main.currentList[player]);
        if (player == 1) // 如果是我，30秒到后直接下一家出牌
        {
            int i = n;
            while (main.nextPlayer == false && i >= 0) {
                main.time[player].setText("倒计时:" + i);
                main.time[player].setVisible(true);
                sleepSeconds(1);
                i--;
            }
            if (i == -1 && player == 1) {
                // 如果我超时
            }
            main.nextPlayer = false;
        } else {
            for (int i = n; i >= 0; i--) {
                sleepSeconds(1);
                main.time[player].setText("倒计时:" + i);
                main.time[player].setVisible(true);
            }
        }
        main.time[player].setVisible(false);
    }

    /**
     * 使全部牌变的是否可点击
     * @param list
     * @param b
     */
    public void makeCanClick(List<Card> list, boolean b) {
        for (Card card : list) {
            card.setCanClick(b);
        }
    }

    /**
     * 打开出牌按钮
     * @param flag
     */
    public void turnOn(boolean flag) {
        main.publishCard[0].setVisible(flag);//出牌
        main.publishCard[1].setVisible(flag);//不要
        main.publishCard[2].setVisible(flag);//提示
    }

    /**
     * 设定地主
     * @param i
     */
    public void setlord(int i) {
        Point point = new Point();
        Point point1 = new Point();
        Point point2 = new Point();
        if (i == 1) // 我是地主
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

    /**
     * 地主牌翻看
     * @param is
     */
    public void openlord(boolean is) {
        for (int i = 0; i < 3; i++) {
            if (is)
                main.lordList.get(i).turnFront(); // 地主牌翻看
            else {
                main.lordList.get(i).turnRear(); // 地主牌闭合
            }
            main.lordList.get(i).setCanClick(true);// 可被点击
        }
    }

    /**
     * 线程睡眠second秒
     * @param second
     */
    public void sleepSeconds(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
