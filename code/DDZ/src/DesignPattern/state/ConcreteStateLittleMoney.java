package DesignPattern.state;

import javax.swing.*;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class ConcreteStateLittleMoney implements State {
    @Override
    public void handle(int money) {
        JOptionPane.showMessageDialog(null,
                "������"+money+"Ԫ,�뾡���ֵ",
                "��½�ɹ�",JOptionPane.INFORMATION_MESSAGE);
    }
}
