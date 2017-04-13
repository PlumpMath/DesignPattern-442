package DesignPattern.DBManager;

import java.sql.*;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class UpdateMoney {
    public int getMoney(String playerName) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConn();
        Statement statement = connection.createStatement();
        String sql = "SELECT name,Money FROM user WHERE PlayerName = '" + playerName+"'";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet!=null){
            resultSet.first();
            int money = resultSet.getInt("Money");
            return money;
        }
        return 0;
    }
    public int saveMoney(int money,String playerName) throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConn();
        String sql = "UPDATE user SET Money=? WHERE PlayerName=?  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println(sql);
        preparedStatement.setInt(1,money);
        preparedStatement.setString(2,playerName);
        int row = preparedStatement.executeUpdate();
        System.out.println(row);
        return 0;
    }
}
