package thread;

import utils.OneSendCard;
import utils.cardType.CardTypeFactory;
import view.Common;
import view.Home;
import vo.Card;
import java.awt.Color;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

/**
 * Created by DrownFish on 2017/3/10.
 */

public class TimeThread extends Thread implements Runnable {
    private Home main;

    public TimeThread(Home main) {
        this.main = main;
    }

    public void run() {
        int i;
        for(i = 10; i >= 0 && this.main.dizhuFlag == -1; --i) {
            this.main.time[1].setText("倒计时:" + i);
            this.sleepSeconds(1);
        }

        if(this.main.dizhuFlag == 1) {
            this.main.playerList[1].addAll(this.main.lordList);
            this.openlord(true);
            this.sleepSeconds(2);
            Common.order(this.main.playerList[1]);
            Common.rePosition(this.main, this.main.playerList[1], 1);
            this.main.publishCard[1].setEnabled(false);
            this.setlord(1);
        } else if(this.main.dizhuFlag == 2) {
            this.main.time[2].setText("抢地主");
            this.main.time[2].setVisible(true);
            this.setlord(2);
            this.openlord(true);
            this.sleepSeconds(3);
            this.main.playerList[2].addAll(this.main.lordList);
            Common.order(this.main.playerList[2]);
            Common.rePosition(this.main, this.main.playerList[2], 2);
            this.openlord(false);
            if(Home.debug) {
                this.openlord(true);
            }
        } else {
            this.main.time[0].setText("抢地主");
            this.main.time[0].setVisible(true);
            this.setlord(0);
            this.openlord(true);
            this.sleepSeconds(3);
            this.main.playerList[0].addAll(this.main.lordList);
            Common.order(this.main.playerList[0]);
            Common.rePosition(this.main, this.main.playerList[0], 0);
            this.openlord(false);
            if(Home.debug) {
                this.openlord(true);
            }
        }

        this.turnOn(false);

        for(i = 0; i < 3; ++i) {
            this.main.time[i].setText("不要");
            this.main.time[i].setVisible(false);
        }

        do {
            if(this.main.turn == 1) {
                this.turnOn(true);
                if(this.main.preChuPai == 1) {
                    this.main.publishCard[1].setEnabled(false);
                } else {
                    this.main.publishCard[1].setEnabled(true);
                }

                this.clearTable(1);
                if(this.main.preChuPai == 1) {
                    this.clearTable();
                }

                this.turnOnLord(false);
                this.makeCanClick(this.main.playerList[1], true);
                this.timeWait(30, 1);
                this.turnOn(false);
                this.main.turn = (this.main.turn + 1) % 3;
            }

            if(this.main.turn == 0) {
                this.main.hasSend[0] = 0;
                this.computerTimer(0);
                this.clearTable(0);
                this.computer0();
                this.main.turn = (this.main.turn + 1) % 3;
            }

            if(this.main.turn == 2) {
                this.clearTable(2);
                this.main.hasSend[2] = 0;
                this.computerTimer(2);
                this.computer2();
                this.main.turn = (this.main.turn + 1) % 3;
            }
        } while(!this.win());

    }

    private boolean win() {
        for(int i = 0; i < 3; ++i) {
            if(this.main.playerList[i].size() == 0) {
                String s;
                if(i == 1) {
                    s = "You win!";
                    this.main.winJLabel[0].setVisible(true);
                    this.main.winJLabel[1].setVisible(true);
                    this.main.winOrLose.setForeground(Color.RED);
                } else if(this.isFriend(i, 1)) {
                    s = "You win!";
                    this.main.winJLabel[0].setVisible(true);
                    this.main.winJLabel[1].setVisible(true);
                    this.main.winOrLose.setForeground(Color.RED);
                } else {
                    s = "You lose!";
                    this.main.loseJLabel[0].setVisible(true);
                    this.main.loseJLabel[1].setVisible(true);
                    this.main.winOrLose.setForeground(Color.black);
                }

                int j;
                for(j = 0; j < this.main.playerList[(i + 1) % 3].size(); ++j) {
                    ((Card)this.main.playerList[(i + 1) % 3].get(j)).turnFront();
                }

                for(j = 0; j < this.main.playerList[(i + 2) % 3].size(); ++j) {
                    ((Card)this.main.playerList[(i + 2) % 3].get(j)).turnFront();
                }

                this.main.winOrLose.setText(s);
                this.main.winOrLose.setVisible(true);
                return true;
            }
        }

        return false;
    }

    public void computerTimer(int computer) {
        byte total = 10;
        this.main.time[computer].setText("倒计时:" + String.valueOf(10));
        this.main.time[computer].setVisible(true);

        for(int i = total; i >= 10 && this.main.hasSend[computer] == 0; --i) {
            this.main.time[computer].setText("倒计时:" + String.valueOf(i));

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }
        }

    }

    private void clearTable(int i) {
        Iterator var3 = this.main.currentList[i].iterator();

        while(var3.hasNext()) {
            Card bc = (Card)var3.next();
            bc.setVisible(false);
        }

        this.main.currentList[i].clear();
    }

    private void clearTable() {
        for(int i = 0; i < 3; ++i) {
            this.clearTable(i);
        }

    }

    private void computer2() {
        byte role = 2;
        OneSendCard oneSendCard;
        if(!this.isSelfSendCard(role)) {
            if(!this.isFriend(role, this.main.preChuPai)) {
                oneSendCard = CardTypeFactory.getOneSendCardBiggerButleast(this.main.playerList[2], this.main.preOneSendCard);
                if(oneSendCard == null) {
                    this.main.hasSend[role] = 2;
                    this.main.time[role].setText("不要");
                } else {
                    this.showCard(role, oneSendCard);
                    this.main.preOneSendCard = oneSendCard;
                    this.main.hasSendList.addAll(oneSendCard.getOneSendCardList());
                    this.main.currentList[role] = oneSendCard.getOneSendCardList();
                    this.main.preChuPai = role;
                    this.main.hasSend[role] = 1;
                    this.main.time[role].setText("我要");
                }
            } else {
                this.main.time[role].setText("不要");
                this.main.hasSend[role] = 2;
            }
        } else {
            oneSendCard = CardTypeFactory.getFirstBestOneSendCard(this.main.playerList[2]);
            this.showCard(role, oneSendCard);
            this.main.preOneSendCard = oneSendCard;
            this.main.hasSendList.addAll(oneSendCard.getOneSendCardList());
            this.main.currentList[role] = oneSendCard.getOneSendCardList();
            this.main.preChuPai = role;
            this.main.hasSend[role] = 1;
        }

    }

    private void showCard(int role, OneSendCard oneSendCard) {
        List list = oneSendCard.getOneSendCardList();
        this.main.currentList[role].clear();
        Point point = new Point();
        if(role == 0) {
            point.x = 200;
        }

        if(role == 2) {
            point.x = 550;
        }

        if(role == 1) {
            point.x = 385 - (this.main.currentList[1].size() + 1) * 15 / 2;
            point.y = 300;
        }

        point.y = 200 - (list.size() + 1) * 15 / 2;
        Iterator var6 = list.iterator();

        while(var6.hasNext()) {
            Card card = (Card)var6.next();
            Common.move(card, card.getLocation(), point);
            point.y += 15;
            this.main.container.setComponentZOrder(card, 0);
            card.turnFront();
            this.main.currentList[role].add(card);
            this.main.playerList[role].remove(card);
        }

        Common.rePosition(this.main, this.main.playerList[role], role);
    }

    private void computer0() {
        byte role = 0;
        OneSendCard oneSendCard;
        if(!this.isSelfSendCard(role)) {
            if(!this.isFriend(role, this.main.preChuPai)) {
                oneSendCard = CardTypeFactory.getBiggerOneSendCard(this.main.playerList[role], this.main.preOneSendCard);
                if(oneSendCard == null) {
                    this.main.hasSend[role] = 2;
                    this.main.time[role].setText("不要");
                } else {
                    this.showCard(role, oneSendCard);
                    this.main.preOneSendCard = oneSendCard;
                    this.main.hasSendList.addAll(oneSendCard.getOneSendCardList());
                    this.main.preChuPai = role;
                    this.main.hasSend[role] = 1;
                    this.main.time[role].setText("我要");
                }
            } else {
                this.main.time[role].setText("不要");
                this.main.hasSend[role] = 2;
            }
        } else {
            oneSendCard = CardTypeFactory.getFirstBestOneSendCard(this.main.playerList[role]);
            this.showCard(role, oneSendCard);
            this.main.preOneSendCard = oneSendCard;
            this.main.hasSendList.addAll(oneSendCard.getOneSendCardList());
            this.main.preOneSendCard = oneSendCard;
            this.main.currentList[role] = oneSendCard.getOneSendCardList();
            this.main.preChuPai = role;
            this.main.hasSend[role] = 1;
        }

    }

    public boolean isSelfSendCard(int player) {
        return this.main.preChuPai == -1 || this.main.preChuPai == player;
    }

    public boolean isFriend(int i1, int i2) {
        boolean b = true;
        if(i1 == this.main.dizhuFlag || i2 == this.main.dizhuFlag) {
            b = false;
        }

        return b;
    }

    private void turnOnLord(boolean b) {
        for(int i = 0; i < 2; ++i) {
            this.main.landlord[i].setVisible(b);
        }

    }

    public void timeWait(int n, int player) {
        if(this.main.currentList[player].size() > 0) {
            Common.hideCards(this.main.currentList[player]);
        }

        int i;
        if(player == 1) {
            for(i = n; !this.main.nextPlayer && i >= 0; --i) {
                this.main.time[player].setText("倒计时:" + i);
                this.main.time[player].setVisible(true);
                this.sleepSeconds(1);
            }

            if(i == -1) {
                ;
            }

            this.main.nextPlayer = false;
        } else {
            for(i = n; i >= 0; --i) {
                this.sleepSeconds(1);
                this.main.time[player].setText("倒计时:" + i);
                this.main.time[player].setVisible(true);
            }
        }

        this.main.time[player].setVisible(false);
    }

    public void makeCanClick(List<Card> list, boolean b) {
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            Card card = (Card)var4.next();
            card.setCanClick(b);
        }

    }

    public void turnOn(boolean flag) {
        this.main.publishCard[0].setVisible(flag);
        this.main.publishCard[1].setVisible(flag);
        this.main.publishCard[2].setVisible(flag);
    }

    public void setlord(int i) {
        Point point = new Point();
        Point point1 = new Point();
        Point point2 = new Point();
        if(i == 1) {
            point.x = 80;
            point.y = 430;
            point1.x = 80;
            point1.y = 20;
            point2.x = 700;
            point2.y = 20;
        }

        if(i == 0) {
            point.x = 80;
            point.y = 20;
            point1.x = 80;
            point1.y = 430;
            point2.x = 700;
            point2.y = 20;
        }

        if(i == 2) {
            point.x = 700;
            point.y = 20;
            point1.x = 80;
            point1.y = 20;
            point2.x = 80;
            point2.y = 430;
        }

        this.main.dizhu.setLocation(point);
        this.main.farmer1.setLocation(point1);
        this.main.farmer2.setLocation(point2);
        this.main.dizhu.setVisible(true);
        this.main.farmer1.setVisible(true);
        this.main.farmer2.setVisible(true);
    }

    public void openlord(boolean is) {
        for(int i = 0; i < 3; ++i) {
            if(is) {
                ((Card)this.main.lordList.get(i)).turnFront();
            } else {
                ((Card)this.main.lordList.get(i)).turnRear();
            }

            ((Card)this.main.lordList.get(i)).setCanClick(true);
        }

    }

    public void sleepSeconds(int second) {
        try {
            Thread.sleep((long)(second * 1000));
        } catch (InterruptedException var3) {
            var3.printStackTrace();
        }

    }
}
