package DesignPattern.SaveScore;

import DesignPattern.DBManager.DBConnection;

import java.sql.*;

/**
 * Created by DrownFish on 2017/4/10.
 */
public class Originator {
    public int[] getScore(String player) throws SQLException {
        Connection connection = DBConnection.getConn();
        Statement statement = connection.createStatement();
        String sql = "SELECT PlayerName,winTime,failTime FROM result WHERE PlayerName = '" + player+"'";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet!=null){
            resultSet.first();
            int score[] = new int[2];
            score[0] = resultSet.getInt("winTime");
            score[1] = resultSet.getInt("failTime");
            return score;
        }
        return null;
    }
    public void saveScore(int score[],String playerName) throws SQLException {
        Connection connection = DBConnection.getConn();
        String sql = "UPDATE result SET winTime=?,failTime=? WHERE PlayerName=?  ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        System.out.println(sql);
        preparedStatement.setInt(1,score[0]);
        preparedStatement.setInt(2,score[1]);
        preparedStatement.setString(3,playerName);

        int row = preparedStatement.executeUpdate();
        System.out.println(row);
    }
}
