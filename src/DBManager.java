import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager
{
    private static Connection DB = null;


    public static void connectDatabase()
    {
        String url = "jdbc:mysql://remotemysql.com:3306/gRDM5lyOKB";
        String user = "gRDM5lyOKB";
        String pwd = "zTKv1VSG7W";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.print("Try establishing database connection......");
            //connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/his?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            DB = DriverManager.getConnection(url,user,pwd);
            if(DB != null)
                System.out.println("Database Connected");
            else
                System.out.println("Database Connection failed");
        } catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
    }
    public static void disconnectDatabase()
    {
        try
        {
            if(DB != null)
            {
                DB.close();
                System.out.println("Database disconnected successfully");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void userLogin(String username, String password)
    {
    }
}
