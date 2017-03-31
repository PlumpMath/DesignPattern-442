package utils.cardType;

import java.util.ArrayList;
import java.util.List;
import utils.OneSendCard;
import utils.cardType.cardTypeImpl.CardTypeString;
import utils.cardType.cardTypeImpl.DoubleCardType;
import utils.cardType.cardTypeImpl.FourCardType;
import utils.cardType.cardTypeImpl.FourOneOneCardType;
import utils.cardType.cardTypeImpl.FourTwoTwoCardType;
import utils.cardType.cardTypeImpl.OneOneTwoTwoCardType;
import utils.cardType.cardTypeImpl.OneTwoThreeCardType;
import utils.cardType.cardTypeImpl.PlaneCardType;
import utils.cardType.cardTypeImpl.PlaneOneCardType;
import utils.cardType.cardTypeImpl.PlaneTwoCardType;
import utils.cardType.cardTypeImpl.SingleCardType;
import utils.cardType.cardTypeImpl.ThreeAndOneCardType;
import utils.cardType.cardTypeImpl.ThreeAndTwoCardType;
import utils.cardType.cardTypeImpl.ThreeCardType;
import utils.devided.DevidedCardSolution;
import utils.devided.DevidedCardSolutionFactory;
import vo.Card;

/**
 * Created by DrownFish on 2017/3/10.
 */

/**
 * 牌型工厂方法
 *
 * @author Administrator
 *
 */
public class CardTypeFactory {
    public static String[] cardTypeStrings = { CardTypeString.SINGLE_CARDTYPE };
    /**
     * 准备所有类型
     */
    public static ICardType[] cardTypes = { new SingleCardType(), new DoubleCardType(), new ThreeCardType(),
            new FourCardType(), new ThreeAndOneCardType(), new ThreeAndTwoCardType(), new FourOneOneCardType(),
            new FourTwoTwoCardType(), new OneTwoThreeCardType(), new OneOneTwoTwoCardType(), new PlaneCardType(),
            new PlaneOneCardType(), new PlaneTwoCardType() };

    /**
     * 根据传入的字符串返回牌型
     *
     * @param cardTypeString
     *            牌型的字符串
     * @return 牌型的实例
     */
    public static ICardType getCardTypeByCardTypeString(String cardTypeString) {

        if (cardTypeString.equals(CardTypeString.SINGLE_CARDTYPE)) {
            return new SingleCardType();
        } else if (cardTypeString.equals(CardTypeString.DOUBLE_CARDTYPE)) {
            return new DoubleCardType();
        } else if (cardTypeString.equals(CardTypeString.THREE_CARDTYPE)) {
            return new ThreeCardType();
        } else if (cardTypeString.equals(CardTypeString.FOUR_CARDTYPE)) {
            return new FourCardType();
        } else if (cardTypeString.equals(CardTypeString.THREEANDONE_CARDTYPE)) {
            return new ThreeAndOneCardType();
        } else if (cardTypeString.equals(CardTypeString.THREEANTWO_CARDTYPE)) {
            return new ThreeAndTwoCardType();
        } else if (cardTypeString.equals(CardTypeString.FOURONEONE_CARDTYPE)) {
            return new FourOneOneCardType();
        } else if (cardTypeString.equals(CardTypeString.FOURTWOTWO_CARDTYPE)) {
            return new FourTwoTwoCardType();
        } else if (cardTypeString.equals(CardTypeString.ONETWOTHREE_CARDTYPE)) {
            return new OneTwoThreeCardType();
        } else if (cardTypeString.equals(CardTypeString.ONEONETWOTWO_CARDTYPE)) {
            return new OneOneTwoTwoCardType();
        } else if (cardTypeString.equals(CardTypeString.PLANE_CARDTYPE)) {
            return new PlaneCardType();
        } else if (cardTypeString.equals(CardTypeString.PLANEONE_CARDTYPE)) {
            return new PlaneOneCardType();
        } else if (cardTypeString.equals(CardTypeString.PLANEONE_CARDTYPE)) {
            return new PlaneOneCardType();
        } else if (cardTypeString.equals(CardTypeString.PLANETWO_CARDTYPE)) {
            return new PlaneTwoCardType();
        }
        return null;
    }

    /**
     * 通过遍历类型，返回该类型字符串
     *
     * @param list
     * @return
     */
    public static String getCardType(List<Card> list) {
        for (ICardType cardType : cardTypes) {
            if (cardType.matches(list)) {
                return cardType.getName();
            }
        }
        return CardTypeString.NONE_CARDTYPE;
    }

    /**
     * 如果是朋友出牌，如果能顺牌，则顺牌，比如单牌，要顺牌
     *
     * @param playerList
     *            所有的牌
     * @param preOneSendCard
     *            上一次出的牌
     * @return
     */
    public static OneSendCard getFriendBiggerOneSendCard(List<Card> playerList, OneSendCard preOneSendCard) {
        return null;
    }

    /**
     * 找出压牌的出牌，首先从最优方案中能够压牌的 OneSendCard，如果不存在，则再调用
     * getOneSendCardBiggerButleast找到一个可以压牌的OneSendCard
     *
     * @param playerList
     *            所有的牌
     * @param preOneSendCard
     *            上一次出的牌
     * @return
     */
    public static OneSendCard getBiggerOneSendCard(List<Card> playerList, OneSendCard preOneSendCard) {
        DevidedCardSolutionFactory dcsf = DevidedCardSolutionFactory.getInstance();
        List<DevidedCardSolution> solutions = new ArrayList<DevidedCardSolution>();
        DevidedCardSolution currSolution = null;
        dcsf.getDevidedCardSolution(playerList, solutions, currSolution);
        DevidedCardSolution bestSolution = dcsf.getBestDevidedCardSolution(solutions);
        List<DevidedCardSolution> allBestSolution = dcsf.getAllBestDevidedCardSolution(solutions);
        List<OneSendCard> biggerList = new ArrayList<OneSendCard>();

        // 在没有匹配单牌时找
        if (preOneSendCard.getCardType().equals(CardTypeString.SINGLE_CARDTYPE)
                || preOneSendCard.getCardType().equals(CardTypeString.DOUBLE_CARDTYPE)) {
            // 在单牌和双牌中找
            for (DevidedCardSolution tempSolution : allBestSolution) {
                if (tempSolution.getSingleOrDouble() != null) {
                    for (OneSendCard osc : tempSolution.getSingleOrDouble()) {
                        if (osc.getCardType().equals(preOneSendCard.getCardType())) {
                            // 如果类型相同
                            ICardType cardType = CardTypeFactory
                                    .getCardTypeByCardTypeString(preOneSendCard.getCardType());
                            if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
                                // 如果大于，则加入到biggerList中
                                biggerList.add(osc);
                            }
                        }
                    }
                }
            }

        } else if (preOneSendCard.getCardType().equals(CardTypeString.THREEANDONE_CARDTYPE)
                || preOneSendCard.getCardType().equals(CardTypeString.THREEANTWO_CARDTYPE)
                || preOneSendCard.getCardType().equals(CardTypeString.PLANEONE_CARDTYPE)
                || preOneSendCard.getCardType().equals(CardTypeString.PLANETWO_CARDTYPE)) {
            // 如果是这几种类型，需要在匹配了单牌后找
            dcsf.dispatchSingleOrDouble(bestSolution);
            // 在非单牌和非双牌中找
            for (DevidedCardSolution tempSolution : allBestSolution) {
                if (tempSolution.getOneSendCards() != null) {
                    for (OneSendCard osc : tempSolution.getOneSendCards()) {
                        if (osc.getCardType().equals(preOneSendCard.getCardType())) {
                            // 如果类型相同
                            ICardType cardType = CardTypeFactory
                                    .getCardTypeByCardTypeString(preOneSendCard.getCardType());
                            if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
                                // 如果大于，则加入到biggerList中
                                biggerList.add(osc);
                            }
                        }
                    }
                }
            }
        } else {
            // 在非单牌和非双牌中找
            for (DevidedCardSolution tempSolution : allBestSolution) {
                if (tempSolution.getOneSendCards() != null) {
                    for (OneSendCard osc : tempSolution.getOneSendCards()) {
                        if (osc.getCardType().equals(preOneSendCard.getCardType())) {
                            // 如果类型相同
                            ICardType cardType = CardTypeFactory
                                    .getCardTypeByCardTypeString(preOneSendCard.getCardType());
                            if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
                                // 如果大于，则加入到biggerList中
                                biggerList.add(osc);
                            }
                        }
                    }
                }
            }
        }
        // 如果最优方案中普通类型找不到，尝试找最优方案中的炸弹类型
        if (bestSolution.getOneSendCards() != null) {
            for (OneSendCard osc : bestSolution.getOneSendCards()) {
                if (osc.getCardType().equals(CardTypeString.FOUR_CARDTYPE)) {
                    // 如果是炸弹
                    ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(preOneSendCard.getCardType());
                    if (!preOneSendCard.getCardType().equals(CardTypeString.FOUR_CARDTYPE)) {
                        // 如果之前不是炸弹，则加入到biggerList中
                        biggerList.add(osc);
                    } else {
                        // 如果之前也是炸弹
                        if (cardType.compare(osc.getOneSendCardList(), preOneSendCard.getOneSendCardList()) > 0) {
                            biggerList.add(osc);
                        }
                    }
                }
            }
        }
        if (biggerList.size() == 0) {
            // 最优方案中找不到大于pre OneSendCard的牌型，则继续调用getOneSendCardBiggerButleast
            return getOneSendCardBiggerButleast(playerList, preOneSendCard);
        } else {

            // 找到了大于
            if (biggerList.size() == 1) {
                return biggerList.get(0);
            } else {
                // 如果找到的数量大于1
                // 通过比较大小的方式，找到所有牌中最小的那个
                ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(preOneSendCard.getCardType());
                OneSendCard osc = biggerList.get(0);
                for (OneSendCard temp : biggerList) {
                    if (cardType.compare(osc.getOneSendCardList(), temp.getOneSendCardList()) > 0) {
                        osc = temp;
                    }
                }
                return osc;
            }
        }
    }

    /**
     * 从所有的牌中找到比上一次出牌大的中最小的那个一次出牌
     *
     * @param playerList
     *            所有的牌
     * @param preOneSendCard
     *            上一次出的牌
     * @return
     */
    public static OneSendCard getOneSendCardBiggerButleast(List<Card> playerList, OneSendCard preOneSendCard) {
        // TODO Auto-generated method stub
        ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(preOneSendCard.getCardType());
        OneSendCard ret = null;
        if (playerList.size() < cardType.getMinLength()) {
            ret = null;
        } else {
            ret = cardType.getOneSendCardBiggerButleast(playerList, preOneSendCard);
        }
        // 如果牌型不是炸弹，且按牌型找不到可以压牌的牌，尝试炸弹
        if ((!cardType.getName().equals(CardTypeString.FOUR_CARDTYPE)) && ret == null) {
            ICardType bomb = getCardTypeByCardTypeString(CardTypeString.FOUR_CARDTYPE);
            // 创建一个不存在的炸弹，2222，让炸弹类型计算比它大的牌型
            List<Card> cardList = new ArrayList<Card>();
            for (int i = 0; i < 4; i++) {
                Card bc = new Card(null, 2, 1, false);
                cardList.add(bc);
            }
            OneSendCard virtual = new OneSendCard(cardList, CardTypeString.FOUR_CARDTYPE);
            ret = bomb.getOneSendCardBiggerButleast(playerList, virtual);
        }
        return ret;
    }

    /**
     * 比较两次出牌的大小
     *
     * @param oneSendCard
     * @param preOneSendCard
     * @return 1：大于 0：小于或等于 -1：类型不匹配
     */
    public static int compareOneSendCard(OneSendCard oneSendCard, OneSendCard preOneSendCard) {
        // TODO Auto-generated method stub
        String cardType1 = oneSendCard.getCardType();
        String cardType2 = preOneSendCard.getCardType();
        if (!cardType1.equals(cardType2) && !cardType1.equals(CardTypeString.FOUR_CARDTYPE)) {
            // 如果两种类型不一样，且cardType1不是炸弹
            return -1;
        } else if (!cardType1.equals(cardType2) && cardType1.equals(CardTypeString.FOUR_CARDTYPE)) {
            // 如果两种类型不一样，且cardType1是炸弹
            return 1;
        } else {
            ICardType cardType = CardTypeFactory.getCardTypeByCardTypeString(cardType1);
            return cardType.compare(oneSendCard.getOneSendCardList(), preOneSendCard.getOneSendCardList());
        }
    }

    /**
     * 电脑自主出牌
     *
     * @param playerList
     * @return
     */
    public static OneSendCard getFirstBestOneSendCard(List<Card> playerList) {
        DevidedCardSolutionFactory dcsf = DevidedCardSolutionFactory.getInstance();
        List<DevidedCardSolution> solutions = new ArrayList<DevidedCardSolution>();
        DevidedCardSolution currSolution = null;
        dcsf.getDevidedCardSolution(playerList, solutions, currSolution);
        DevidedCardSolution bestSolution = dcsf.getBestDevidedCardSolution(solutions);
        OneSendCard osc = dcsf.getFirstOneSendCard(bestSolution);
        return osc;
    }

    /**
     * 获取自主出牌提示的出牌
     *
     * @param list
     * @return
     */
    public static OneSendCard getAutoPrompt(List<Card> list) {
        DevidedCardSolutionFactory dcsf = DevidedCardSolutionFactory.getInstance();
        List<DevidedCardSolution> solutions = new ArrayList<DevidedCardSolution>();
        DevidedCardSolution currSolution = null;
        dcsf.getDevidedCardSolution(list, solutions, currSolution);
        DevidedCardSolution bestSolution = dcsf.getBestDevidedCardSolution(solutions);
        OneSendCard osc = dcsf.getFirstOneSendCard(bestSolution);
        return osc;
    }

    /**
     * 获取压牌的提示牌
     *
     * @param list
     * @return
     */
    public static OneSendCard getBiggerPrompt(List<Card> list, OneSendCard preOneSendCard) {
        // TODO Auto-generated method stub
        return getBiggerOneSendCard(list, preOneSendCard);
    }
}
