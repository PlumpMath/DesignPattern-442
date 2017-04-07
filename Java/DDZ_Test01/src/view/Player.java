package view;

import javax.swing.*;

/**
 * Created by DrownFish on 2017/4/7.
 */
public class Player implements Observer {
    private JLabel jLabel;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void getNotify() {
        jLabel.setText("小样");
    }

    @Override
    public void notifyCenter() {

    }

}
