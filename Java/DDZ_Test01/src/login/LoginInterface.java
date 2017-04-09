package login;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by DrownFish on 2017/4/9.
 */
public interface LoginInterface {
    public void visitNormalUser(NormalUser normalUser) throws SQLException, IOException, InterruptedException;
    public void visitAdminUser(AdminUser adminUser) throws IOException, InterruptedException, SQLException;
}