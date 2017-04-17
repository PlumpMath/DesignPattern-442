package DesignPattern.DBManager;
import com.mysql.jdbc.Connection;

import java.sql.DriverManager;

/**
 * Created by DrownFish on 2017/4/7.
 */
public class DBConnection {
    private static DBConnection dbConnection;
    private Connection conn;
    private DBConnection() {
    }
    public static Connection getConn(){

        if (dbConnection == null){
            dbConnection = new DBConnection();
            if(dbConnection.conn == null) {
                try {
                    //在数据库中验证用户
                    String className = "com.mysql.jdbc.Driver";
                    String url = "jdbc:mysql://localhost:3306/DDZ?useUnicode=true&characterEncoding=UTF-8";
                    //加载驱动
                    Class.forName(className);
                    //得到连接
                    dbConnection.conn = (Connection) DriverManager.getConnection(url, "root", "111518");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return dbConnection.conn;
    }
}
