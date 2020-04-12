import java.sql.SQLException;

public class HISfacade
{

    public static void main(String[] args) throws SQLException
    {

        String username;
        String password;
        System.out.println("Welcome to Hospital Information System");
        DBManager.connectDatabase();
        username = IOUtils.getString("Please enter username: ");
        password = IOUtils.getString("Please enter password: ");
        DBManager.userLogin(username,password);
        DBManager.disconnectDatabase();

    }
}
