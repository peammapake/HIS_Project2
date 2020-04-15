import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Clerk extends Staff
{
    private static ArrayList<Bill> bills= new ArrayList<Bill>();

    public static ArrayList<Clerk> clerkArrayList = new ArrayList<Clerk>();

    /**
     * Constructor method create instance of staff
     * use user information from database
     * In case the user is a clerk
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

    public void showBills()
    {

    }

    public void generateBill()
    {

    }

    public void paidBill()
    {

    }

    public void removeBill()
    {

    }
}
