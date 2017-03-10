package com.must.game.thread;
import com.must.game.view.Home;

/**
 * ���Լ�ʱ�߳�
 * @author Administrator
 *
 */
public class ComputerTimeThread extends Thread implements Runnable {
	private Home main;
	private int computer;

	public ComputerTimeThread(Home main, int computer) {
		this.main = main;
		this.computer = computer;
	}

	public void run() {
		int total = 10;
		// ����ʱ��
		main.time[this.computer].setText("����ʱ:" + String.valueOf(10));
		main.time[this.computer].setVisible(true);
		for (int i = total; i >= 0; i--) {
			if (main.hasSend[computer] != 0) {
				// �Ѿ������ƻ��߲�Ҫ���˳�
				break;
			}
			main.time[this.computer].setText("����ʱ:" + String.valueOf(i));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (main.hasSend[computer] == 1) {
			main.time[computer].setText("��Ҫ");
		} else {
			main.time[computer].setText("��Ҫ");
		}
	}
}
