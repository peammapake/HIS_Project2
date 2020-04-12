import java.sql.*;

public class DBManager
{
    private static Connection DB = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;


    public static void connectDatabase()
    {
        String url = "jdbc:mysql://remotemysql.com:3306/gRDM5lyOKB";
        String user = "gRDM5lyOKB";
        String pwd = "zTKv1VSG7W";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.print("Try establishing database connection......");
            //opening database connection to MySQL server
            DB = DriverManager.getConnection(url,user,pwd);
            //getting Statement object to be ready for query execution
            stmt = DB.createStatement();
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

    public static void userLogin(String username, String password) throws SQLException
    {
        String queryUsername = "username = \'" + username + "\'";
        String queryPassword = " AND password = \'" + password + "\';";
        String query = "SELECT userID,fName,lName,role FROM users WHERE " + queryUsername + queryPassword;
        //executing select query
        rs = stmt.executeQuery(query);

        while(rs.next())
        {
            int userID = rs.getInt(1);
            String firstName = rs.getString(2);
            String lastName = rs.getString(3);
            String role = rs.getString(4);
            System.out.println(userID + firstName + lastName + role);
        }
    }
}
