package login.visitor;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by DrownFish on 2017/4/9.
 */
public class AdminUser extends User {
    private String name ;
    private String password;

    @Override
    public void accept(LoginInterface loginInterface) throws IOException, InterruptedException, SQLException {
        loginInterface.visitAdminUser(this);
    }

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
