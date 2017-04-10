package login.state;

import javax.swing.*;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class ConcreteStateLittleMoney implements State {
    @Override
    public void handle(int money) {
        JOptionPane.showMessageDialog(null,
                "您还有"+money+"元,请尽快充值",
                "登陆成功",JOptionPane.INFORMATION_MESSAGE);
    }
}
