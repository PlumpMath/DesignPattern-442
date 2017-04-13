package DesignPattern.observer;

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

    public void setjLabel(JLabel jLabel) {
        this.jLabel = jLabel;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void getNotify() {
        jLabel.setText("小样");
        jLabel.setVisible(true);
    }

    @Override
    public void notifyCenter(ControlCenter controlCenter) {
        jLabel.setText("我快出完了");
        controlCenter.notifyObserver(name);
    }

}
