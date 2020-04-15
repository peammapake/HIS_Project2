import javax.xml.transform.Result;
import java.sql.*;

/**
 * Database Manager class responsible for all communication
 * with the Hospital Information Database
 */
public class DBManager
{
    /**Object responsible for database connection*/
    private static Connection DB = null;
    /**Object needed for communication with database*/
    private static Statement stmt = null;

    /**
     * Establish database connection
     */
    public static void connectDatabase()
    {
        //DO NOT CHANGE: responsible for database login
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

    /**
     * Terminate database communication
     */
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

    /**
     * Find the user from the database and collect the user data if exist
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     * @return return User information in ResultSet format
     * @throws SQLException In case there's an error in MySQL query
     */
    public static ResultSet userLogin(String username, String password) throws SQLException
    {
        int count = 0; //In case there are duplicate user
        String queryUsername = "username = \'" + username + "\'";
        String queryPassword = " AND password = \'" + password + "\';";
        String query = "SELECT userID,fName,lName,role FROM users WHERE " + queryUsername + queryPassword;
        //executing the selected query
        ResultSet userRS = stmt.executeQuery(query);

        while(userRS.next())
        {
            count++;
            String firstName = userRS.getString(2);
            String lastName = userRS.getString(3);
            //In case there are duplicate user, extreme case that could happen during user registration
            if(count > 1)
            {
                System.out.println("Error: found duplicate user, please contact admin for further analysis");
                System.exit(0);
            }
            System.out.println("Welcome " + firstName + " " + lastName);
        }
        //If user not found return null, this mean the username or password might be wrong
        if(count == 0)
        {
            System.out.println("Sorry: User not found, please try again");
            return null;
        }
        return userRS;
    }

    /**
     * Get all the doctors registered in the database
     * @return return doctors information in ResultSet format
     * @throws SQLException
     */
    public static ResultSet getDoctorList() throws SQLException
    {
        String queryDoctors = "SELECT userID, fName, lName FROM users WHERE role = 'DOCTOR';";
        ResultSet doctorRS = stmt.executeQuery(queryDoctors);
        return doctorRS;
    }

    /**
     * get all the patients registered within the database
     * @return patients information in ResultSet format
     * @throws SQLException
     */
    public static ResultSet getPatientList() throws SQLException
    {
        String queryPatients = "SELECT * FROM patients;";
        ResultSet patientRS = stmt.executeQuery(queryPatients);
        return patientRS;
    }
}