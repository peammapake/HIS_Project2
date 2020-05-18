import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Hospital Information System aka HIS
 * a program aimed to implement an application that closely resemble real world HIS
 * HISfacade act as a facade class for the program and also the main face in interacting with user
 *
 * Create by YAKKIN_TONKATSU
 */
public class HISfacade
{
    /** User instance created in prepare for user login*/
    public static Staff user = null;

    /**
     * main class of the HIS application
     * @param args
     * @throws SQLException Throws SQL error message
     */
    public static void main(String[] args) throws SQLException
    {
        String username;
        String password;
        ResultSet userInfo = null; //Collect user information from MySQL database
        System.out.println("Welcome to Hospital Information System");
        DBManager.connectDatabase();
        //Will loop until the user successfully login
        while(true)
        {
            username = IOUtils.getString("Please enter username: ");
            password = IOUtils.getString("Please enter password: ");
            userInfo = DBManager.userLogin(username, password);
            if(userInfo == null)
            {
                System.out.println("Sorry: User not found, please try again");
                continue;
            }
            break;
        }
        System.out.print("Load data, please wait...........");
        try
        {
            initialize(userInfo);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("Success!");

        DBManager.disconnectDatabase();

    }

    /**
     * Prepare all the necessary data
     * from database into objects
     * @param userInfo get the current user information to identify the user type
     * @throws SQLException in case error happen with ResultSet
     */
    private static void initialize(ResultSet userInfo) throws SQLException
    {
        //Iterate to the first row in result set *Should have only 1 row*
        userInfo.first();
        String userRole = userInfo.getString(4);
        switch(userRole)
        {
            case "DOCTOR":
                user = new Doctor(userInfo);
                user.loadStaffData();
                break;
            case "NURSE":
                user = new Nurse(userInfo);
                user.loadStaffData();
                break;
            case "CLERK":
                user = new Clerk(userInfo);
                user.loadStaffData();
                break;
        }
        userInfo.close();
    }

}
