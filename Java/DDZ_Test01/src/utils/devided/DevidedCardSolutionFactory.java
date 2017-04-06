package utils.devided;

import utils.CardUtil;
import utils.OneSendCard;
import cardDesign.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DevidedCardSolutionFactory {
    private static DevidedCardSolutionFactory dcsf = null;

    private DevidedCardSolutionFactory() {
    }

    public static DevidedCardSolutionFactory getInstance() {
        if(dcsf == null) {
            dcsf = new DevidedCardSolutionFactory();
        }

        return dcsf;
    }

    public void getDevidedCardSolution(List<Card> cards, List<DevidedCardSolution> solutions, DevidedCardSolution currSolution) {
        List oneSendCardList = this.getAllPossibleOneSendCard(cards);
        if(oneSendCardList != null && oneSendCardList.size() > 0) {
            Iterator var6 = oneSendCardList.iterator();

            while(var6.hasNext()) {
                OneSendCard singleOrDouble1 = (OneSendCard)var6.next();
                DevidedCardSolution nextSolution = new DevidedCardSolution();
                ArrayList oneSendCards = new ArrayList();
                new ArrayList();
                if(currSolution != null && currSolution.getOneSendCards() != null && currSolution.getOneSendCards().size() > 0) {
                    oneSendCards.addAll(currSolution.getOneSendCards());
                }

                oneSendCards.add(singleOrDouble1);
                nextSolution.setOneSendCards(oneSendCards);
                List restCards = CardUtil.getRestListByRemove(cards, singleOrDouble1.getOneSendCardList());
                this.getDevidedCardSolution(restCards, solutions, nextSolution);
            }
        } else {
            List singleOrDouble = this.getOneSendCardOfSingleOrDouble(cards);
            if(currSolution == null) {
                currSolution = new DevidedCardSolution();
            }

            currSolution.setSingleOrDouble(singleOrDouble);
            solutions.add(currSolution);
        }

    }

    public DevidedCardSolution getBestDevidedCardSolution(List<DevidedCardSolution> solutions) {
        DevidedCardSolution best = null;
        List allBestSolutions = this.getAllBestDevidedCardSolution(solutions);
        double shouShu = 100.0D;
        Iterator var7 = allBestSolutions.iterator();

        while(var7.hasNext()) {
            DevidedCardSolution temp = (DevidedCardSolution)var7.next();
            if(shouShu > temp.getSendCount()) {
                best = temp;
            }
        }

        return best;
    }

    public List<DevidedCardSolution> getAllBestDevidedCardSolution(List<DevidedCardSolution> solutions) {
        double initValue = 100.0D;
        Object best = null;
        ArrayList tempList = new ArrayList();
        Iterator var7 = solutions.iterator();

        while(var7.hasNext()) {
            DevidedCardSolution solution = (DevidedCardSolution)var7.next();
            if(solution.getSingleCount() < initValue) {
                initValue = solution.getSingleCount();
                tempList.clear();
                tempList.add(solution);
            } else if(solution.getSingleCount() == initValue) {
                tempList.add(solution);
            }
        }

        return tempList;
    }

    public OneSendCard getFirstOneSendCard(DevidedCardSolution dcs) {
        List oldSingleOrDouble = dcs.getSingleOrDouble();
        ArrayList saveSingleOrDouble = new ArrayList();
        Iterator isSingleFirst = oldSingleOrDouble.iterator();

        OneSendCard osc;
        while(isSingleFirst.hasNext()) {
            OneSendCard hasNessary = (OneSendCard)isSingleFirst.next();
            osc = new OneSendCard(hasNessary.getOneSendCardList(), hasNessary.getCardType());
            saveSingleOrDouble.add(osc);
        }

        boolean hasNessary1 = false;
        boolean isSingleFirst1 = false;
        if(dcs.getSingleCount() > 0.0D) {
            hasNessary1 = true;
        } else {
            hasNessary1 = false;
        }

        osc = null;
        double minRate;
        OneSendCard temp;
        Iterator var10;
        if(!hasNessary1) {
            this.dispatchSingleOrDouble(dcs);
            minRate = 1.0D;
            if(dcs.getOneSendCards() != null) {
                var10 = dcs.getOneSendCards().iterator();

                while(var10.hasNext()) {
                    temp = (OneSendCard)var10.next();
                    if(temp.getBiggerRate() < minRate) {
                        osc = temp;
                        minRate = temp.getBiggerRate();
                    }
                }
            }
        } else {
            this.dispatchSingleOrDouble(dcs);
            minRate = 2.0D;
            if(dcs.getOneSendCards() != null) {
                var10 = dcs.getOneSendCards().iterator();

                while(var10.hasNext()) {
                    temp = (OneSendCard)var10.next();
                    if(temp.getBiggerRate() < minRate) {
                        osc = temp;
                        minRate = temp.getBiggerRate();
                    }
                }
            }

            if(dcs.getSingleOrDouble() != null) {
                var10 = dcs.getSingleOrDouble().iterator();

                while(var10.hasNext()) {
                    temp = (OneSendCard)var10.next();
                    if(temp.getBiggerRate() < minRate) {
                        osc = temp;
                        minRate = temp.getBiggerRate();
                    }
                }
            }
        }

        if(osc == null || osc.getCardType().equals("c1") || osc.getCardType().equals("c2")) {
            osc = this.getMinSingleOrDouble(saveSingleOrDouble);
        }

        return osc;
    }

    private OneSendCard getMinSingleOrDouble(List<OneSendCard> saveSingleOrDouble) {
        Map map = CardUtil.asValueStaticOneSendCard(saveSingleOrDouble);
        ArrayList intList = new ArrayList();
        Iterator var5 = map.keySet().iterator();

        while(var5.hasNext()) {
            Integer tempOsc = (Integer)var5.next();
            intList.add(tempOsc);
        }

        List intList1 = CardUtil.sortIntegerList(intList);
        if(intList1 != null && intList1.size() != 0) {
            OneSendCard tempOsc1 = (OneSendCard)map.get(intList1.get(0));
            return tempOsc1;
        } else {
            return null;
        }
    }

    public void dispatchSingleOrDouble(DevidedCardSolution dcs) {
        Map map = CardUtil.asValueStaticOneSendCard(dcs.getSingleOrDouble());
        int singleCount = 0;
        int doubleCount = 0;
        new ArrayList();
        new ArrayList();
        Iterator temp = dcs.getSingleOrDouble().iterator();

        while(temp.hasNext()) {
            OneSendCard needCount = (OneSendCard)temp.next();
            if(needCount.getCardType().equals("c1")) {
                ++singleCount;
            }

            if(needCount.getCardType().equals("c2")) {
                ++doubleCount;
            }
        }

        boolean var21 = false;
        if(dcs.getOneSendCards() != null) {
            Iterator var9 = dcs.getOneSendCards().iterator();

            while(true) {
                while(true) {
                    OneSendCard var23;
                    ArrayList var24;
                    do {
                        if(!var9.hasNext()) {
                            var9 = dcs.getOneSendCards().iterator();

                            while(true) {
                                do {
                                    do {
                                        if(!var9.hasNext()) {
                                            return;
                                        }

                                        var23 = (OneSendCard)var9.next();
                                    } while(!var23.getCardType().equals("c3"));
                                } while(dcs.getSingleOrDouble().size() <= 0);

                                map = CardUtil.asValueStaticOneSendCard(dcs.getSingleOrDouble());
                                var24 = new ArrayList();
                                Iterator var28 = map.keySet().iterator();

                                while(var28.hasNext()) {
                                    Integer var30 = (Integer)var28.next();
                                    var24.add(var30);
                                }

                                List var25 = CardUtil.sortIntegerList(var24);
                                OneSendCard var31 = (OneSendCard)map.get(var25.get(0));
                                List var29 = var31.getOneSendCardList();
                                dcs.getSingleOrDouble().remove(var31);
                                var23.getOneSendCardList().addAll(var29);
                                if(var31.getCardType().equals("c1")) {
                                    var23.setCardType("c31");
                                } else {
                                    var23.setCardType("c32");
                                }
                            }
                        }

                        var23 = (OneSendCard)var9.next();
                    } while(!var23.getCardType().equals("c111222"));

                    map = CardUtil.asValueStaticOneSendCard(dcs.getSingleOrDouble());
                    singleCount = 0;
                    doubleCount = 0;
                    ArrayList singleList = new ArrayList();
                    ArrayList doubleList = new ArrayList();
                    Iterator tempOsc = map.entrySet().iterator();

                    while(tempOsc.hasNext()) {
                        Entry intList = (Entry)tempOsc.next();
                        if(((OneSendCard)intList.getValue()).getCardType().equals("c1")) {
                            singleList.add((Integer)intList.getKey());
                            ++singleCount;
                        }

                        if(((OneSendCard)intList.getValue()).getCardType().equals("c2")) {
                            doubleList.add((Integer)intList.getKey());
                            ++doubleCount;
                        }
                    }

                    List var19 = CardUtil.sortIntegerList(singleList);
                    List var20 = CardUtil.sortIntegerList(doubleList);
                    int var22 = var23.getOneSendCardList().size() / 3;
                    var24 = new ArrayList();
                    int addCardList;
                    int var27;
                    OneSendCard var32;
                    if(singleCount >= var22) {
                        for(var27 = 0; var27 < var22; ++var27) {
                            addCardList = ((Integer)var19.get(var27)).intValue();
                            var32 = (OneSendCard)map.get(Integer.valueOf(addCardList));
                            dcs.getSingleOrDouble().remove(var32);
                            var24.addAll(var32.getOneSendCardList());
                        }

                        var23.getOneSendCardList().addAll(var24);
                        var23.setCardType("c11122234");
                    } else if(doubleCount >= var22) {
                        for(var27 = 0; var27 < var22; ++var27) {
                            addCardList = ((Integer)var20.get(var27)).intValue();
                            var32 = (OneSendCard)map.get(Integer.valueOf(addCardList));
                            dcs.getSingleOrDouble().remove(var32);
                            var24.addAll(var32.getOneSendCardList());
                        }

                        var23.getOneSendCardList().addAll(var24);
                        var23.setCardType("c1112223344");
                    } else {
                        boolean var26 = false;
                        var27 = singleCount + doubleCount * 2;
                        if(var27 >= var22) {
                            addCardList = var22 - singleCount;

                            int shotDoubleCount;
                            int shotSingleCount;
                            for(shotDoubleCount = 0; shotDoubleCount < var22; ++shotDoubleCount) {
                                shotSingleCount = ((Integer)var19.get(shotDoubleCount)).intValue();
                                OneSendCard i = (OneSendCard)map.get(Integer.valueOf(shotSingleCount));
                                dcs.getSingleOrDouble().remove(i);
                                var24.addAll(i.getOneSendCardList());
                            }

                            var23.getOneSendCardList().addAll(var24);
                            shotDoubleCount = addCardList / 2;

                            int var33;
                            for(shotSingleCount = 0; shotSingleCount < shotDoubleCount; ++shotSingleCount) {
                                var33 = ((Integer)var20.get(shotSingleCount)).intValue();
                                OneSendCard tempInt = (OneSendCard)map.get(Integer.valueOf(var33));
                                dcs.getSingleOrDouble().remove(tempInt);
                                var24.addAll(tempInt.getOneSendCardList());
                            }

                            var23.getOneSendCardList().addAll(var24);
                            shotSingleCount = addCardList % 2;

                            for(var33 = 0; var33 < shotSingleCount; ++var33) {
                                int var34 = ((Integer)var20.get(var33)).intValue();
                                OneSendCard tempOsc1 = (OneSendCard)map.get(Integer.valueOf(var34));
                                Card bc = (Card)tempOsc1.getOneSendCardList().remove(0);
                                tempOsc1.setCardType("c1");
                                var24.add(bc);
                            }

                            var23.getOneSendCardList().addAll(var24);
                            var23.setCardType("c11122234");
                        }
                    }
                }
            }
        }
    }

    private List<OneSendCard> getOneSendCardOfSingleOrDouble(List<Card> cards) {
        ArrayList retList = new ArrayList();
        Map map = CardUtil.asValueStaticCount(cards);
        Iterator var5 = map.entrySet().iterator();

        while(var5.hasNext()) {
            Entry entry = (Entry)var5.next();
            List list1;
            OneSendCard o1;
            if(((Integer)entry.getValue()).intValue() == 1) {
                list1 = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt((String)entry.getKey()), 1);
                o1 = new OneSendCard(list1, "c1");
                retList.add(o1);
            } else if(((Integer)entry.getValue()).intValue() == 2) {
                list1 = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt((String)entry.getKey()), 2);
                o1 = new OneSendCard(list1, "c2");
                retList.add(o1);
            }
        }

        return retList;
    }

    private List<OneSendCard> getAllPossibleOneSendCard(List<Card> cards) {
        ArrayList retList = new ArrayList();
        List bombList = this.getAllBombByCardList(cards);
        List threeList = this.getAllThreeByCardList(cards);
        List planeList = this.getAllPlaneByCardList(cards);
        List lianDuiList = this.getAlllianDuiByCardList(cards);
        List lianZiList = this.getAlllianZiByCardList(cards);
        List doubleList = this.getAlllianZiByCardList(cards);
        retList.addAll(bombList);
        retList.addAll(threeList);
        retList.addAll(planeList);
        retList.addAll(lianDuiList);
        retList.addAll(lianZiList);
        retList.addAll(doubleList);
        return retList;
    }

    private List<OneSendCard> getAlllianZiByCardList(List<Card> cards) {
        byte minCount = 5;
        byte maxCount = 12;
        Map map = CardUtil.asValueStaticCount(cards);
        OneSendCard osc = null;
        ArrayList lianziList = new ArrayList();

        for(int i = minCount; i <= maxCount; ++i) {
            for(int j = 3; j <= 14 - i + 1; ++j) {
                boolean bHas = true;

                for(int totalCards = j; totalCards < j + i; ++totalCards) {
                    if(map.get(String.valueOf(totalCards)) == null || ((Integer)map.get(String.valueOf(totalCards))).intValue() == 0) {
                        bHas = false;
                        break;
                    }
                }

                if(bHas) {
                    ArrayList var13 = new ArrayList();

                    for(int h = j; h < j + i; ++h) {
                        List tempCards = null;
                        tempCards = CardUtil.getCardListByValueAndCount(cards, h, 1);
                        var13.addAll(tempCards);
                    }

                    osc = new OneSendCard(var13, "c123");
                    lianziList.add(osc);
                }
            }
        }

        return lianziList;
    }

    private List<OneSendCard> getAlllianDuiByCardList(List<Card> cards) {
        byte minCount = 3;
        byte maxCount = 10;
        Map map = CardUtil.asValueStaticCount(cards);
        OneSendCard osc = null;
        ArrayList lianziList = new ArrayList();

        for(int i = minCount; i <= maxCount; ++i) {
            for(int j = 3; j <= 14 - i + 1; ++j) {
                boolean bHas = true;

                for(int totalCards = j; totalCards < j + i; ++totalCards) {
                    if(map.get(String.valueOf(totalCards)) == null || ((Integer)map.get(String.valueOf(totalCards))).intValue() < 2) {
                        bHas = false;
                        break;
                    }
                }

                if(bHas) {
                    ArrayList var13 = new ArrayList();

                    for(int h = j; h < j + i; ++h) {
                        List tempCards = null;
                        tempCards = CardUtil.getCardListByValueAndCount(cards, h, 2);
                        var13.addAll(tempCards);
                    }

                    osc = new OneSendCard(var13, "c112233");
                    lianziList.add(osc);
                }
            }
        }

        return lianziList;
    }

    public List<OneSendCard> getAllPlaneByCardList(List<Card> cards) {
        ArrayList planeList = new ArrayList();
        Map map = CardUtil.asValueStaticCount(cards);
        ArrayList intListList = new ArrayList();
        ArrayList threeList = new ArrayList();
        Iterator preNumber = map.entrySet().iterator();

        while(preNumber.hasNext()) {
            Entry objs = (Entry)preNumber.next();
            if(((Integer)objs.getValue()).intValue() >= 3) {
                threeList.add(Integer.valueOf(Integer.parseInt((String)objs.getKey())));
            }
        }

        Object[] var16 = threeList.toArray();
        Arrays.sort(var16);
        int var17 = 0;
        ArrayList tempList = new ArrayList();

        for(int temList = 0; temList < var16.length; ++temList) {
            int curNumber = Integer.parseInt(var16[temList].toString());
            if(var17 == 0) {
                tempList.add(Integer.valueOf(curNumber));
            } else if(var17 == curNumber - 1) {
                tempList.add(Integer.valueOf(curNumber));
            } else {
                if(tempList.size() >= 2) {
                    intListList.add(tempList);
                }

                tempList = new ArrayList();
            }

            var17 = curNumber;
        }

        if(tempList.size() >= 2) {
            intListList.add(tempList);
        }

        Iterator var19 = intListList.iterator();

        while(var19.hasNext()) {
            ArrayList var18 = (ArrayList)var19.next();
            OneSendCard osc = null;
            ArrayList totalCards = new ArrayList();
            List tempCards = null;
            Iterator var15 = var18.iterator();

            while(var15.hasNext()) {
                Integer temInt = (Integer)var15.next();
                tempCards = CardUtil.getCardListByValueAndCount(cards, temInt.intValue(), 3);
                totalCards.addAll(tempCards);
            }

            osc = new OneSendCard(totalCards, "c111222");
            planeList.add(osc);
        }

        return planeList;
    }

    private List<OneSendCard> getAllThreeByCardList(List<Card> cards) {
        Map map = CardUtil.asValueStaticCount(cards);
        ArrayList bombList = new ArrayList();
        Iterator var5 = map.entrySet().iterator();

        while(var5.hasNext()) {
            Entry entry = (Entry)var5.next();
            if(((Integer)entry.getValue()).intValue() >= 3) {
                List bombcards = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt((String)entry.getKey()), 3);
                OneSendCard osc = new OneSendCard(bombcards, "c3");
                bombList.add(osc);
            }
        }

        return bombList;
    }

    private List<OneSendCard> getAllDoubleByCardList(List<Card> cards) {
        Map map = CardUtil.asValueStaticCount(cards);
        ArrayList bombList = new ArrayList();
        Iterator var5 = map.entrySet().iterator();

        while(var5.hasNext()) {
            Entry entry = (Entry)var5.next();
            if(((Integer)entry.getValue()).intValue() >= 2) {
                List bombcards = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt((String)entry.getKey()), 2);
                OneSendCard osc = new OneSendCard(bombcards, "c2");
                bombList.add(osc);
            }
        }

        return bombList;
    }

    private List<OneSendCard> getAllBombByCardList(List<Card> cards) {
        Map map = CardUtil.asValueStaticCount(cards);
        ArrayList bombList = new ArrayList();
        List bombcards;
        OneSendCard osc;
        if(map.get("16") != null && map.get("17") != null) {
            ArrayList entry = new ArrayList();
            List bombcards1 = CardUtil.getCardListByValueAndCount(cards, 16, 1);
            bombcards = CardUtil.getCardListByValueAndCount(cards, 17, 1);
            entry.addAll(bombcards1);
            entry.addAll(bombcards);
            osc = new OneSendCard(entry, "c4");
            bombList.add(osc);
        }

        Iterator bombcards11 = map.entrySet().iterator();

        while(bombcards11.hasNext()) {
            Entry entry1 = (Entry)bombcards11.next();
            if(((Integer)entry1.getValue()).intValue() == 4) {
                bombcards = CardUtil.getCardListByValueAndCount(cards, Integer.parseInt((String)entry1.getKey()), 4);
                osc = new OneSendCard(bombcards, "c4");
                bombList.add(osc);
            }
        }

        return bombList;
    }
}
