package view;

import javax.swing.*;

/**
 * Created by DrownFish on 2017/4/7.
 */
public interface Observer {
    public String getName();
    /**
     * 当收到其他人要出完牌的通知时
     */
    public void getNotify();

    /**
     * 当要出完时通知控制中心
     */
    public void notifyCenter();
}
