import java.sql.ResultSet;
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

    /** static List of every staff */
    static protected ArrayList<Staff> staffArrayList = new ArrayList<Staff>();

    /**
     * Constructor method create instance of staff
     * use user information from database
     * @param userInfo      user information from SQL database
     */
    public Staff(ResultSet userInfo)
    {

    }

    /**
     * Abstract method show option available in each specific role.s
     */
    public abstract void promptMenu();

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }
}
