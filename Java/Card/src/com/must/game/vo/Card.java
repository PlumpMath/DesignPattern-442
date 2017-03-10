package com.must.game.vo;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.must.game.view.Common;
import com.must.game.view.Home;

/**
 * �����Ƴ�����
 * 
 * @author Administrator
 *
 */
public class Card extends JLabel implements MouseListener, Comparable {
	public Card(Home main, int value, int color, boolean isOn) {
		this.main = main;
		this.value = value;
		this.color = color;
		this.isOn = isOn;
		this.canClick = false;
		this.clicked = false;
		initImagePath();
		if (this.isOn)
			this.turnFront();
		else {
			this.turnRear();
		}
		this.setSize(71, 96);
		this.setVisible(true);
		this.addMouseListener(this);
	}

	Home main;
	
	// ֽ�ƴ�С(3,4...14��A��15��2��16��С����17�Ǵ���)
	private int value;
	// ֽ�ƻ�ɫ��1��2��3��4��5���ֻ�ɫ��
	private int color;
	// ͼƬ��ַ
	private String imagePath;
	// �Ƿ�����
	private boolean isOn;
	// �Ƿ���Ա����
	private boolean canClick;
	// �Ƿ񱻵����
	public boolean clicked = false;// �Ƿ�����
	
	
	// ��ʼ��ͼƬ·��

	private void initImagePath() {
		int realValue = 0;
		if (this.value == 16) {
			this.imagePath = "5-1";
		} else if (this.value == 17) {
			this.imagePath = "5-2";
		} else if (this.value >= 14 && this.value <= 15) {
			realValue = this.value - 13;
			this.imagePath = this.color + "-" + realValue;
		} else {
			this.imagePath = this.color + "-" + this.value;
		}
	}

	// ����
	public void turnFront() {
		this.setIcon(new ImageIcon("images/" + this.imagePath + ".gif"));
		this.isOn = true;
	}

	// ����
	public void turnRear() {
		this.setIcon(new ImageIcon("images/rear.png"));
		this.isOn = false;
	}

	/**
	 * �ж��������Ƿ���һ����
	 * 
	 * @param bc
	 * @return
	 */
	public boolean isEqual(Card bc) {
		if ((this.getValue() == bc.getValue()) && (this.getColor() == bc.getColor())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (canClick) {
			move();
		}
	}

	/**
	 * �������ϻ������ƶ�
	 */
	public void move() {
		Point from = this.getLocation();
		int step; // �ƶ��ľ���
		if (clicked)
			step = -20;
		else {
			step = 20;
		}
		clicked = !clicked; // ����
		// ����ѡ�е�ʱ����ǰ�ƶ�һ��/����һ��
		Common.move(this, from, new Point(from.x, from.y - step));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public boolean isCanClick() {
		return canClick;
	}

	public void setCanClick(boolean canClick) {
		this.canClick = canClick;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Card c2 = (Card) arg0;
		if (this.getValue() > c2.getValue()) {
			return 1;
		} else if (this.getValue() < c2.getValue()) {
			return -1;
		} else {
			return 0;
		}
	}
}
