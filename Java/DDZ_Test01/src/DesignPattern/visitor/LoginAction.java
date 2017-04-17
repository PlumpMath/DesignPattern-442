package DesignPattern.visitor;

import DesignPattern.DBManager.DBConnection;

import DesignPattern.state.ConcreteStateEnoughMoney;
import DesignPattern.state.ConcreteStateLittleMoney;
import DesignPattern.state.ConcreteStateNoMoney;
import DesignPattern.state.State;

import javax.swing.*;
import java.io.IOException;
import java.sql.*;

/**
 * Created by DrownFish on 2017/4/9.
 */
public class LoginAction implements LoginInterface {
    @Override
    public int visitNormalUser(NormalUser normalUser) throws IOException, InterruptedException, SQLException {
        /**
         * 连接数据库，查询成绩，判断应该调用的状态
         */
        Connection connection = DBConnection.getConn();
        String sql = "SELECT name,Money FROM user WHERE name=?";
        System.out.println(sql);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,normalUser.getName());
        System.out.println("normalUser name" + normalUser.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        State state;
        if(resultSet!=null){
            resultSet.first();
            int money = resultSet.getInt("Money");
            if(money > 100){
                state = new ConcreteStateEnoughMoney();
                state.handle(money);
                return 1;
            }else if(money <= 100 && money > 0){
                state = new ConcreteStateLittleMoney();
                state.handle(money);
                return 1;
            }else if(money <= 0){
                state = new ConcreteStateNoMoney();
                state.handle(money);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public void visitAdminUser(AdminUser adminUser) throws IOException, InterruptedException {
        JOptionPane.showMessageDialog(null,
                "仅允许查看成绩，如进行游戏请使用用户账号登录",
                "登陆成功",JOptionPane.ERROR_MESSAGE);
    }
}
