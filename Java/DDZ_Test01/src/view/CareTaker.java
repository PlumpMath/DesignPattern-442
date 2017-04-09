package view;

import cardDesign.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DrownFish on 2017/4/9.
 */
public class CareTaker {
    public java.util.List<Card>[] playerList = new ArrayList[3];
    public java.util.List<Card> hasSendList = new ArrayList();

    public java.util.List<Card>[] getArrayList(){
        java.util.List<Card>[] arrayLists = new ArrayList[4];
        for(int i=0;i<3;i++){
            arrayLists[i] =  playerList[i];
        }
        arrayLists[3] =  hasSendList;
        return  arrayLists;
    }

    public void saveArrayList(List<Card>[] playerList, List<Card> hasSendList){
        for(int i = 0; i<playerList.length; i++){
            this.playerList[i] =  playerList[i];
        }
        this.hasSendList =  hasSendList;
    }

    public List<Card>[] getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Card>[] playerList) {
        this.playerList = playerList;
    }

    public List<Card> getHasSendList() {
        return hasSendList;
    }

    public void setHasSendList(List<Card> hasSendList) {
        this.hasSendList = hasSendList;
    }
}
