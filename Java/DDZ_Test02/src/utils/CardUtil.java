package utils;

import vo.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by DrownFish on 2017/3/10.
 */

public class CardUtil {
    public CardUtil() {
    }

    public static Map<String, Integer> asValueStaticCount(List<Card> list) {
        HashMap map = new HashMap();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            Card c = (Card)var3.next();
            String key = String.valueOf(c.getValue());
            if(map.containsKey(String.valueOf(c.getValue()))) {
                map.put(key, Integer.valueOf(((Integer)map.get(key)).intValue() + 1));
            } else {
                map.put(key, Integer.valueOf(1));
            }
        }

        return map;
    }

    public static Map<String, Integer> asValueStaticCountByValue(List<Card> list, int count) {
        HashMap map = new HashMap();
        Iterator entry = list.iterator();

        while(entry.hasNext()) {
            Card newMap = (Card)entry.next();
            String key = String.valueOf(newMap.getValue());
            if(map.containsKey(String.valueOf(newMap.getValue()))) {
                map.put(key, Integer.valueOf(((Integer)map.get(key)).intValue() + 1));
            } else {
                map.put(key, Integer.valueOf(1));
            }
        }

        HashMap newMap1 = new HashMap();
        Iterator key1 = map.entrySet().iterator();

        while(key1.hasNext()) {
            Map.Entry entry1 = (Map.Entry)key1.next();
            if(((Integer)entry1.getValue()).intValue() == count) {
                newMap1.put((String)entry1.getKey(), (Integer)entry1.getValue());
            }
        }

        return newMap1;
    }

    public static int getBiggerButLeastFromList(List<Integer> list, int value) {
        Object[] objs = list.toArray();
        Arrays.sort(objs);
        Object[] var6 = objs;
        int var5 = objs.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            Object o = var6[var4];
            int i = Integer.parseInt(o.toString());
            if(i > value) {
                return i;
            }
        }

        return 0;
    }

    public static List<Card> getCardListByValueAndCount(List<Card> playerList, int value, int count) {
        ArrayList retList = new ArrayList();
        int has = 0;
        Iterator var6 = playerList.iterator();

        while(var6.hasNext()) {
            Card bc = (Card)var6.next();
            if(bc.getValue() == value) {
                retList.add(bc);
                ++has;
                if(has >= count) {
                    break;
                }
            }
        }

        return retList;
    }

    public static int getLeastFromList(List<Integer> oneList) {
        Object[] objs = oneList.toArray();
        Arrays.sort(objs);
        int ret = Integer.parseInt(objs[0].toString());
        return ret;
    }

    public static List<Card> getRestListByRemove(List<Card> playerList, List<Card> needList) {
        ArrayList retList = new ArrayList();
        Iterator var4 = playerList.iterator();

        while(var4.hasNext()) {
            Card bc = (Card)var4.next();
            boolean has = false;
            Iterator var7 = needList.iterator();

            while(var7.hasNext()) {
                Card bc2 = (Card)var7.next();
                if(bc.isEqual(bc2)) {
                    has = true;
                    break;
                }
            }

            if(!has) {
                retList.add(bc);
            }
        }

        return retList;
    }

    public static Card getLeastCardFromCardList(List<Card> restList) {
        int initValue = ((Card)restList.get(0)).getValue();
        Card bc = null;
        Iterator var4 = restList.iterator();

        while(var4.hasNext()) {
            Card b = (Card)var4.next();
            if(b.getValue() < initValue) {
                initValue = b.getValue();
                bc = b;
            }
        }

        return bc;
    }

    public static List<Integer> getLeastFromList(List oneList, int count) {
        Object[] objs = oneList.toArray();
        Arrays.sort(objs);
        ArrayList iList = new ArrayList();

        for(int i = 0; i < count; ++i) {
            iList.add(Integer.valueOf(Integer.parseInt(objs[i].toString())));
        }

        return iList;
    }

    public static List<Integer> getLeastFromCardList(List<Card> cardList, int count) {
        ArrayList oneList = new ArrayList();
        Iterator var4 = cardList.iterator();

        while(var4.hasNext()) {
            Card bc = (Card)var4.next();
            oneList.add(Integer.valueOf(bc.getValue()));
        }

        return getLeastFromList(oneList, count);
    }

    public static Integer[] sortCardList(List<Card> preList) {
        ArrayList list = new ArrayList();
        Iterator arr = preList.iterator();

        while(arr.hasNext()) {
            Card objs = (Card)arr.next();
            list.add(Integer.valueOf(objs.getValue()));
        }

        Object[] var5 = list.toArray();
        Arrays.sort(var5);
        Integer[] var6 = new Integer[var5.length];

        for(int i = 0; i < var5.length; ++i) {
            var6[i] = Integer.valueOf(Integer.parseInt(var5[i].toString()));
        }

        return var6;
    }

    public static List<Card> removeByCount(List<Card> list, int count) {
        ArrayList retList = new ArrayList();
        Map map = asValueStaticCount(list);
        ArrayList removeList = new ArrayList();
        Iterator var6 = map.entrySet().iterator();

        while(var6.hasNext()) {
            Map.Entry b = (Map.Entry)var6.next();
            if(((Integer)b.getValue()).intValue() == count) {
                removeList.add((String)b.getKey());
            }
        }

        var6 = list.iterator();

        while(var6.hasNext()) {
            Card b1 = (Card)var6.next();
            String str = String.valueOf(b1.getValue());
            if(!removeList.contains(str)) {
                retList.add(b1);
            }
        }

        return retList;
    }

    public static List<Card> getSingleCardListBy(List<Card> restList, int count) {
        Map map = asValueStaticCount(restList);
        ArrayList singleList = new ArrayList();
        new ArrayList();
        ArrayList retList = new ArrayList();
        Iterator needCount = map.entrySet().iterator();

        while(needCount.hasNext()) {
            Map.Entry objs = (Map.Entry)needCount.next();
            if(((Integer)objs.getValue()).intValue() == 1 && !((String)objs.getKey()).equals("16") && !((String)objs.getKey()).equals("17")) {
                singleList.add(Integer.valueOf(Integer.parseInt((String)objs.getKey())));
            }
        }

        int newRestList;
        List sortCardList;
        Object[] var14;
        int var15;
        if(singleList.size() > count) {
            var14 = singleList.toArray();
            Arrays.sort(var14);

            for(var15 = 0; var15 < count; ++var15) {
                newRestList = Integer.parseInt(var14[var15].toString());
                sortCardList = getCardListByValueAndCount(restList, Integer.parseInt(var14[var15].toString()), 1);
                retList.addAll(sortCardList);
            }
        } else {
            var14 = singleList.toArray();
            Arrays.sort(var14);

            for(var15 = 0; var15 < singleList.size(); ++var15) {
                newRestList = Integer.parseInt(var14[var15].toString());
                sortCardList = getCardListByValueAndCount(restList, ((Integer)singleList.get(var15)).intValue(), 1);
                retList.addAll(sortCardList);
            }

            var15 = count - singleList.size();
            List var16 = getRestListByRemove(restList, retList);
            Integer[] var17 = sortCardList(var16);
            Integer[] var13 = var17;
            int var12 = var17.length;

            for(int var11 = 0; var11 < var12; ++var11) {
                int temp = var13[var11].intValue();
                if(var15 == 0) {
                    break;
                }

                retList.addAll(getCardListByValueAndCount(var16, temp, 1));
                --var15;
            }
        }

        return retList;
    }

    public static List<Card> getDoubleCardListBy(List<Card> restList, int count) {
        Map map = asValueStaticCount(restList);
        ArrayList doubleList = new ArrayList();
        ArrayList threeList = new ArrayList();
        new ArrayList();
        ArrayList retList = new ArrayList();
        Iterator needCount = map.entrySet().iterator();

        while(needCount.hasNext()) {
            Map.Entry objs = (Map.Entry)needCount.next();
            if(((Integer)objs.getValue()).intValue() == 2) {
                doubleList.add(Integer.valueOf(Integer.parseInt((String)objs.getKey())));
            }

            if(((Integer)objs.getValue()).intValue() == 3) {
                threeList.add(Integer.valueOf(Integer.parseInt((String)objs.getKey())));
            }
        }

        int threeObjs;
        List i;
        Object[] var13;
        int var14;
        if(doubleList.size() > count) {
            var13 = doubleList.toArray();
            Arrays.sort(var13);

            for(var14 = 0; var14 < count; ++var14) {
                threeObjs = Integer.parseInt(var13[var14].toString());
                i = getCardListByValueAndCount(restList, Integer.parseInt(var13[var14].toString()), 2);
                retList.addAll(i);
            }
        } else {
            var13 = doubleList.toArray();
            Arrays.sort(var13);

            for(var14 = 0; var14 < doubleList.size(); ++var14) {
                threeObjs = Integer.parseInt(var13[var14].toString());
                i = getCardListByValueAndCount(restList, ((Integer)doubleList.get(var14)).intValue(), 2);
                retList.addAll(i);
            }

            var14 = count - doubleList.size();
            if(threeList.size() < var14) {
                return null;
            }

            Object[] var15 = threeList.toArray();
            Arrays.sort(var15);

            for(int var16 = 0; var16 < var14; ++var16) {
                Integer key = Integer.valueOf(Integer.parseInt(var15[var16].toString()));
                List tempList = getCardListByValueAndCount(restList, key.intValue(), 2);
                retList.addAll(tempList);
            }
        }

        return retList;
    }

    public static Map<Integer, OneSendCard> asValueStaticOneSendCard(List<OneSendCard> singleOrDouble) {
        HashMap map = new HashMap();
        Iterator var3 = singleOrDouble.iterator();

        while(var3.hasNext()) {
            OneSendCard osc = (OneSendCard)var3.next();
            Card bc = (Card)osc.getOneSendCardList().get(0);
            map.put(Integer.valueOf(bc.getValue()), osc);
        }

        return map;
    }

    public static List<Integer> sortIntegerList(List<Integer> singleList) {
        ArrayList retList = new ArrayList();
        Object[] objs = singleList.toArray();
        Arrays.sort(objs);
        Object[] var6 = objs;
        int var5 = objs.length;

        for(int var4 = 0; var4 < var5; ++var4) {
            Object o = var6[var4];
            retList.add(Integer.valueOf(Integer.parseInt(o.toString())));
        }

        return retList;
    }
}

