package DesignPattern.state;

import javax.swing.*;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class ConcreteStateNoMoney implements State {

    @Override
    public void handle(int money) {
        JOptionPane.showMessageDialog(null,
                "您还有"+money+"元，不能开始游戏",
                "登陆成功",JOptionPane.INFORMATION_MESSAGE);
    }
}
