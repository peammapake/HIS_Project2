import javax.xml.transform.Result;
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
                System.out.println("Database disconnect successfully");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean userLogin(String username, String password) throws SQLException
    {
        int count = 0;
        String queryUsername = "username = \'" + username + "\'";
        String queryPassword = " AND password = \'" + password + "\';";
        String query = "SELECT userID,fName,lName,role FROM users WHERE " + queryUsername + queryPassword;
        //executing select query
        ResultSet userRS = stmt.executeQuery(query);

        while(userRS.next())
        {
            count++;
            int userID = userRS.getInt(1);
            String firstName = userRS.getString(2);
            String lastName = userRS.getString(3);
            String role = userRS.getString(4);
            //In case there are duplicate user, extreme case that could happen during user registration
            if(count > 1)
            {
                System.out.println("Error: found duplicate user, please contact admin for further analysis");
                System.exit(0);
            }
            System.out.println("Welcome " + firstName + " " + lastName);
        }
        if(count == 0)
        {
            System.out.println("Sorry: User not found, please try again");
            return false;
        }
        return true;
    }

    public static ResultSet getDoctorList() throws SQLException
    {
        String queryDoctors = "SELECT userID, fName, lName FROM users WHERE role = 'DOCTOR';";
        ResultSet doctorRS = stmt.executeQuery(queryDoctors);
        return doctorRS;
    }
}