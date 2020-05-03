import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Clerk extends Staff
{
    private static ArrayList<Bill> unpaidBills = new ArrayList<Bill>();

    private static ArrayList<Bill> paidBills = new ArrayList<Bill>();

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
        clerkArrayList.add(this);
    }

    @Override
    public void promptMenu()
    {
        System.out.println("1 - View unpaid billing list");
        System.out.println("2 - View paid billing list");
        System.out.println("3 - Logout");
    }

    @Override
    public void loadStaffData() throws SQLException
    {
        ResultSet unpaidRS = null;
        ResultSet paidRS = null;
        unpaidRS = DBManager.getBills(true);
        paidRS = DBManager.getBills(false);

        while(unpaidRS.next())
        {
            unpaidBills.add(new Bill(unpaidRS,false));
        }
        while(paidRS.next())
        {
            int billID = unpaidRS.getInt(1);
            Timestamp regDate = unpaidRS.getTimestamp(2);
            Timestamp payDate = unpaidRS.getTimestamp(3);
            String firstName = unpaidRS.getString(4);
            String lastName = unpaidRS.getString(5);
            int patientID = unpaidRS.getInt(6);
            String treatmentList = unpaidRS.getString(7);
            String labTestList = unpaidRS.getString(8);
            String prescriptionList = unpaidRS.getString(9);
        }

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
