package DesignPattern.visitor;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by DrownFish on 2017/4/9.
 */
public abstract class User {
    private String name;
    private String password;
    public abstract void accept(LoginInterface loginInterface) throws SQLException, IOException, InterruptedException;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
