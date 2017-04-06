package vo;

import javax.swing.*;

/**
 * Created by DrownFish on 2017/4/6.
 */
public abstract class CardAbstrct extends JLabel {
    public void display(){
        this.setIcon(new ImageIcon("images/rear.png"));
    }
}
