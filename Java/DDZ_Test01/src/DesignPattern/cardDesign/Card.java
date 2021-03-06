package DesignPattern.cardDesign;

import view.Common;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * Created by DrownFish on 2017/3/10.
 */

public class Card extends JLabel implements MouseListener, Comparable,CardInterface{
    private int value;
    private int color;
    private String imagePath;
    private boolean isOn;
    private boolean canClick;
    public boolean clicked = false;

    /**
     * 构造函数，根据值和花色值进行选择图片的位置
     * @param value
     * @param color
     * @param isOn
     */
    public Card( int value, int color, boolean isOn) {
        this.value = value;
        this.color = color;
        this.isOn = isOn;
        this.canClick = false;
        this.clicked = false;
        this.initImagePath();
        if(this.isOn) {
            this.turnFront();
        } else {
            this.turnRear();
        }

        this.setSize(71, 96);
        this.setVisible(true);
        this.addMouseListener(this);
    }

    /**
     * 根据传入值选择花色和值
     */
    private void initImagePath() {
        boolean realValue = false;
        if(this.value == 16) {
            this.imagePath = "5-1";
        } else if(this.value == 17) {
            this.imagePath = "5-2";
        } else if(this.value >= 14 && this.value <= 15) {
            int realValue1 = this.value - 13;
            this.imagePath = this.color + "-" + realValue1;
        } else {
            this.imagePath = this.color + "-" + this.value;
        }

    }

    /**
     * 正面
     */
    @Override
    public void turnFront() {
        this.setIcon(new ImageIcon("images/" + this.imagePath + ".gif"));
        this.isOn = true;
    }

    /**
     * 背面
     */
    @Override
    public void turnRear() {
        this.setIcon(new ImageIcon(this.rearPNG));
        this.isOn = false;
    }

    public boolean isEqual(Card bc) {
        return this.getValue() == bc.getValue() && this.getColor() == bc.getColor();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        if(this.canClick) {
            this.move();
        }

    }

    /**
     * 牌被点击时，向上或者向下移动20像素
     */
    @Override
    public void move() {
        Point from = this.getLocation();
        byte step;
        if(this.clicked) {
            step = -20;
        } else {
            step = 20;
        }

        //点击状态翻转
        this.clicked = !this.clicked;
        Common.move(this, from, new Point(from.x, from.y - step));
    }

    public void mouseReleased(MouseEvent e) {
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isOn() {
        return this.isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isCanClick() {
        return this.canClick;
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public int compareTo(Object arg0) {
        Card c2 = (Card)arg0;
        return this.getValue() > c2.getValue()?1:(this.getValue() < c2.getValue()?-1:0);
    }

}

