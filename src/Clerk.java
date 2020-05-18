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
        int choice = -999;
        boolean state = true;
        mainMenu: while(true)
        {
            System.out.println("1 - View unpaid billing list");
            System.out.println("2 - View paid billing list");
            System.out.println("3 - Logout");

            choiceMenu: while(true)
            {
                choice = IOUtils.getInteger("Please enter your choice of action: ");
                if (choice <= 0)
                    continue choiceMenu;
                switch (choice)
                {
                    case 1:
                        showUnpaidBills();
                        generateBill(false);
                        continue mainMenu;
                    case 2:
                        showPaidBills();
                        generateBill(true);
                        continue mainMenu;
                    case 3:
                        break mainMenu;
                    default:
                        continue choiceMenu;
                }
            }
        }
    }

    @Override
    public void loadStaffData() throws SQLException
    {
        ResultSet unpaidRS = null;
        ResultSet paidRS = null;
        unpaidRS = DBManager.getBills(false);
        while(unpaidRS.next())
        {
            int billID = unpaidRS.getInt(1);
            Timestamp regDate = unpaidRS.getTimestamp(2);
            String firstName = unpaidRS.getString(4);
            String lastName = unpaidRS.getString(5);
            int patientID = unpaidRS.getInt(6);
            String treatmentList = unpaidRS.getString(7);
            String labTestList = unpaidRS.getString(8);
            String prescriptionList = unpaidRS.getString(9);
            unpaidBills.add(new Bill(billID,regDate,firstName,lastName,patientID,treatmentList,labTestList,prescriptionList));
        }
        paidRS = DBManager.getBills(true);
        while(paidRS.next())
        {
            int billID = paidRS.getInt(1);
            Timestamp regDate = paidRS.getTimestamp(2);
            Timestamp payDate = paidRS.getTimestamp(3);
            String firstName = paidRS.getString(4);
            String lastName = paidRS.getString(5);
            int patientID = paidRS.getInt(6);
            String treatmentList = paidRS.getString(7);
            String labTestList = paidRS.getString(8);
            String prescriptionList = paidRS.getString(9);
            paidBills.add(new Bill(billID,regDate,payDate,firstName,lastName,patientID,treatmentList,labTestList,prescriptionList));
        }

    }

    public void showUnpaidBills()
    {
        int index = 1;
        for(Bill bill: unpaidBills)
        {
            Timestamp regDate = bill.getRegisterDate();
            String patientName = bill.getPatientName();
            System.out.println("Index:" + index + "- Name:" + patientName + " Date:" + regDate);
            index++;
        }
    }

    public void showPaidBills()
    {
        int index = 1;
        for(Bill bill: paidBills)
        {
            Timestamp regDate = bill.getRegisterDate();
            String patientName = bill.getPatientName();
            System.out.println("Index: " + index + "- Name:" + patientName + " Date:" + regDate);
            index++;
        }
    }

    private void generateBill(boolean paid)
    {
        loop: while(true)
        {
            int billID = IOUtils.getInteger("Specify Index to show full bill (0 to return): ");
            if(billID <= 0)
                break loop;
            billID = billID-1;
            if(paid)
            {
                if (billID > paidBills.size())
                {
                    System.out.println("Unknown index please try again");
                    continue loop;
                }
                paidBills.get(billID).printBill();
            }
            else
            {
                if (billID > unpaidBills.size())
                {
                    System.out.println("Unknown index please try again");
                    continue loop;
                }
                unpaidBills.get(billID).printBill();
                String confirm = IOUtils.getString("Mark this bill as paid?(YES to confirm else to return): ");
                if(confirm.equalsIgnoreCase("YES"))
                {
                    unpaidBills.get(billID).billPaid();
                }
            }

            break;
        }

    }


}
