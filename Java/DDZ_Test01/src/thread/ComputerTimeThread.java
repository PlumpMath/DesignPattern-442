package thread;

import view.Home;

/**
 * Created by DrownFish on 2017/3/10.
 */

public class ComputerTimeThread extends Thread implements Runnable {
    private Home main;
    private int computer;

    public ComputerTimeThread(Home main, int computer) {
        this.main = main;
        this.computer = computer;
    }

    public void run() {
        byte total = 10;
        this.main.time[this.computer].setText("倒计时:" + String.valueOf(10));
        this.main.time[this.computer].setVisible(true);

        for(int i = total; i >= 0 && this.main.hasSend[this.computer] == 0; --i) {
            this.main.time[this.computer].setText("倒计时:" + String.valueOf(i));

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
            }
        }

        if(this.main.hasSend[this.computer] == 1) {
            this.main.time[this.computer].setText("我要");
        } else {
            this.main.time[this.computer].setText("不要");
        }

    }
}
