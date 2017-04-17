package DesignPattern.DBManager;

import java.sql.Connection;

/**
 * Created by DrownFish on 2017/4/14.
 */
public class Test {
    public static void main(String args[]){
        Connection dbConnection1 = DBConnection.getConn();
        Connection dbConnection2 = DBConnection.getConn();

        System.out.println(dbConnection1);
        System.out.println(dbConnection2);
    }
}
