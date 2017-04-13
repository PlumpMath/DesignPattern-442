package DesignPattern.visitor;

import DesignPattern.DBManager.DBConnection;
import com.mysql.jdbc.Connection;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by DrownFish on 2017/4/9.
 */
public class CheckAction implements LoginInterface {
    @Override
    public int visitNormalUser(NormalUser normalUser) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConn();
        String name = normalUser.getName();
        Statement statement = connection.createStatement();
        String sql = "SELECT winTime,failTime FROM result WHERE PlayerName='"+name+"'";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet!=null){
            resultSet.first();
            String winTime = resultSet.getString("winTime");
            String failTime = resultSet.getString("failTime");
            JOptionPane.showMessageDialog(null,
                    "获胜次数："+ winTime + " 失败次数: " + failTime,
                    "成绩",JOptionPane.INFORMATION_MESSAGE);
        }
        return 0;
    }

    @Override
    public void visitAdminUser(AdminUser adminUser) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConn();
        Statement statement = connection.createStatement();
        String sql = "SELECT PlayerName,winTime,failTime FROM result ";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet!=null){
            String result = "";
            while(resultSet.next()){
                String playerName = resultSet.getString("PlayerName");
                String winTime = resultSet.getString("winTime");
                String failTime = resultSet.getString("failTime");
                result += playerName + " 获胜次数：" + winTime + " 失败次数: " + failTime + "\n";
            }
            JOptionPane.showMessageDialog(null,
                    result,
                    "成绩",JOptionPane.INFORMATION_MESSAGE);
            System.out.println(result);


        }
    }
}
