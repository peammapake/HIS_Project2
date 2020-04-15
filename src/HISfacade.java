import java.sql.ResultSet;
import java.sql.SQLException;

public class HISfacade
{
    private Staff user = null;
    public static void main(String[] args) throws SQLException
    {
        String username;
        String password;
        ResultSet userInfo = null;
        System.out.println("Welcome to Hospital Information System");
        DBManager.connectDatabase();
        while(true)
        {
            username = IOUtils.getString("Please enter username: ");
            password = IOUtils.getString("Please enter password: ");
            userInfo = DBManager.userLogin(username, password);
            if(userInfo == null)
                continue;
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

    private static void initialize(ResultSet userInfo) throws SQLException
    {
        //user = new Staff(userInfo);
        DBManager.getDoctorList();
        DBManager.getPatientList();
    }
}
