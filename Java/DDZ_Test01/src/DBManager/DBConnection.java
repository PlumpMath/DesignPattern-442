package DBManager;
import com.mysql.jdbc.Connection;

import java.sql.DriverManager;

/**
 * Created by DrownFish on 2017/4/7.
 */
public class DBConnection {
    private Connection conn = null;

    String userName;
    String userPass;
    public DBConnection() {
        this.userName = "root";
        this.userPass = "111518";
    }

    public Connection getConn(){

        try{
            //在数据库中验证用户
            String className="com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/DDZ?useUnicode=true&characterEncoding=UTF-8";
            //加载驱动
            Class.forName(className);
            //得到连接
            conn = (Connection) DriverManager.getConnection(url,this.userName,this.userPass);
        }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
}
