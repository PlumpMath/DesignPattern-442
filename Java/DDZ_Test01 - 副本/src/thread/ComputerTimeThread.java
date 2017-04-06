package thread;

import view.Home;

/**
 * 电脑计时线程
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
        // 倒计时：
        main.time[this.computer].setText("倒计时:" + String.valueOf(10));
        main.time[this.computer].setVisible(true);
        for (int i = total; i >= 0; i--) {
            if (main.hasSend[computer] != 0) {
                // 已经出完牌或者不要就退出
                break;
            }
            main.time[this.computer].setText("倒计时:" + String.valueOf(i));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (main.hasSend[computer] == 1) {
            main.time[computer].setText("我要");
        } else {
            main.time[computer].setText("不要");
        }
    }
}
