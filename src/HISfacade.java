import java.sql.SQLException;

public class HISfacade
{

    public static void main(String[] args) throws SQLException
    {
        String username;
        String password;
        System.out.println("Welcome to Hospital Information System");
        DBManager.connectDatabase();
        while(true)
        {
            username = IOUtils.getString("Please enter username: ");
            password = IOUtils.getString("Please enter password: ");
            if(DBManager.userLogin(username, password))
                break;
        }
        DBManager.disconnectDatabase();

    }
}
