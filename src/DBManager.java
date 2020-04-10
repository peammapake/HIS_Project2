import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager
{
    public static void connectDatabase()
    {
        String url = "jdbc:mysql://sql208.epizy.com:3306/epiz_25496321_HISdatabase";
        String user = "epiz_25496321";
        String pwd = "AajKAzYGBB";
        Connection connect = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/his?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            if(connect != null)
                System.out.println("Database Connected");
            else
                System.out.println("Database Connection failed");
        } catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }

        try
        {
            if(connect != null)
                connect.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static userLogin(String username, String password)
    {

    }
}
