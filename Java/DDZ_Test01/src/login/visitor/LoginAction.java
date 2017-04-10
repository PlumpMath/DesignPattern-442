package login.visitor;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by DrownFish on 2017/4/9.
 */
public class LoginAction implements LoginInterface {
    @Override
    public void visitNormalUser(NormalUser normalUser) throws IOException, InterruptedException {
        JOptionPane.showMessageDialog(null,
                "用户可以登录",
                "登陆成功",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void visitAdminUser(AdminUser adminUser) throws IOException, InterruptedException {
        JOptionPane.showMessageDialog(null,
                "仅允许查看成绩，如进行游戏请使用用户账号登录",
                "登陆成功",JOptionPane.ERROR_MESSAGE);
    }
}
