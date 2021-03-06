package view;

import vo.Card;
import java.awt.Point;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DrownFish on 2017/3/10.
 */

public class Common {
    public Common() {
    }

    public static void move(Card card, Point from, Point to) {
        if(to.x != from.x) {
            double k = 1.0D * (double)(to.y - from.y) / (double)(to.x - from.x);
            double b = (double)to.y - (double)to.x * k;
            boolean flag = false;
            byte flag1;
            if(from.x < to.x) {
                flag1 = 20;
            } else {
                flag1 = -20;
            }

            for(int i = from.x; Math.abs(i - to.x) > 20; i += flag1) {
                double y = k * (double)i + b;
                card.setLocation(i, (int)y);

                try {
                    Thread.sleep(5L);
                } catch (InterruptedException var12) {
                    var12.printStackTrace();
                }
            }
        }

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

    public static void rePosition(Home m, java.util.List<Card> list, int flag) {
        Point p = new Point();
        if(flag == 0) {
            p.x = 50;
            p.y = 225 - (list.size() + 1) * 15 / 2;
        }

        if(flag == 1) {
            p.x = 400 - (list.size() + 1) * 21 / 2;
            p.y = 450;
        }

        if(flag == 2) {
            p.x = 700;
            p.y = 225 - (list.size() + 1) * 15 / 2;
        }

        int len = list.size();

        for(int i = 0; i < len; ++i) {
            Card card = (Card)list.get(i);
            move(card, card.getLocation(), p);
            m.container.setComponentZOrder(card, 0);
            if(flag == 1) {
                p.x += 21;
            } else {
                p.y += 15;
            }
        }

    }

    public static int computeLord(java.util.List<Card> playerList, java.util.List<Card> playerList2) {
        int score0 = getScore(playerList);
        int score2 = getScore(playerList2);
        return score0 > score2?0:2;
    }

    public static int getScore(java.util.List<Card> list) {
        int count = 0;
        int i = 0;

        for(int len = list.size(); i < len; ++i) {
            Card card = (Card)list.get(i);
            if(card.getValue() == 16) {
                count += 4;
            }

            if(card.getValue() == 17) {
                count += 5;
            }

            if(card.getValue() == 15) {
                count += 2;
            }
        }

        return count;
    }

    public static void hideCards(java.util.List<Card> list) {
        int i = 0;

        for(int len = list.size(); i < len; ++i) {
            ((Card)list.get(i)).setVisible(false);
        }

    }
}

