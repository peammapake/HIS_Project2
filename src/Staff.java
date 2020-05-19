import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *  Abstract class represent staff in hospital system
 *  Create by YAKKIN_TONKATSU group
 */
public abstract class Staff
{

    /** ID of staff */
    protected int staffID;

    /** first name of staff */
    protected String firstName;

    /** last name of staff */
    protected String lastName;

    /** The role of the staff*/
    protected String role;

    /** static List of every staff */
    static protected ArrayList<Staff> staffArrayList = new ArrayList<Staff>();

    /**
     * Constructor method create instance of staff
     * use user information from database
     * @param userInfo      user information from SQL database
     */
    public Staff(ResultSet userInfo) throws SQLException
    {
        staffID = userInfo.getInt(1);
        firstName = userInfo.getString(2);
        lastName = userInfo.getString(3);
        role = userInfo.getString(4);
    }

    /**
     * Abstract method show option available in each specific roles
     */
    public abstract void promptMenu() throws SQLException;

    /**
     * Abstract method preparing data from database as needed for each staff type
     */
    public abstract void loadStaffData() throws SQLException;

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getFullName()
    {
        return firstName + " " + lastName;
    }


    public int getStaffID()
    {
        return staffID;
    }
}
