import java.sql.ResultSet;
import java.sql.SQLException;

public class Clerk extends Staff
{


    /**
     * Constructor method create instance of staff
     * use user information from database
     *
     * @param userInfo user information from SQL database
     */
    public Clerk(ResultSet userInfo) throws SQLException
    {
        super(userInfo);
    }

    @Override
    public void promptMenu()
    {

    }
}
