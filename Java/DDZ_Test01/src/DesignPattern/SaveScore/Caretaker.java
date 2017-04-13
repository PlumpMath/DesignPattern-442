package DesignPattern.SaveScore;

/**
 * Created by DrownFish on 2017/4/9.
 */
public class Caretaker {
    public int[] score = new int[2];

    public void saveScore(int[] score) {
        this.score[0] = score[0];
        this.score[1] = score[1];
    }

    public int[] reCoverScore(){
        return this.score;
    }

}
