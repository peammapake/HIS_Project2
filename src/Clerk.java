import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Clerk extends Staff
{
    /**Arraylist containing all instances of unpaid bill*/
    private static ArrayList<Bill> unpaidBills = new ArrayList<Bill>();

    /**Arraylist containing all instances of paid bill*/
    private static ArrayList<Bill> paidBills = new ArrayList<Bill>();

    /**Arraylist of all clerk*/
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
    /**
     * Main menu responsible for showing and accepting chosen operation from the user
     */
    public void promptMenu() throws SQLException
    {
        int choice = -999;
        mainMenu: while(true)
        {
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Available options:");
            System.out.println("1 - View unpaid billing list");
            System.out.println("2 - View paid billing list");
            System.out.println("3 - Update data");
            System.out.println("4 - Logout");

            choiceMenu: while(true)
            {
                loadStaffData();
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
                        continue mainMenu;
                    case 4:
                        break mainMenu;
                    default:
                        System.out.println("Unavailable input choice");
                        continue choiceMenu;
                }
            }
        }
    }

    @Override
    /**
     * Method responsible for loading and setting up all necessary data from database
     * - Clerk only needs to know all the bills both paid and unpaid
     */
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

    /**
     * Display all unpaid bill with basic information with index to choose
     */
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

    /**
     * Display all paid bill with basic information and index to choose
     */
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

    /**
     * Generate full detail bill of the chosen bill
     * if the bill is unpaid, further ask if user want to update the bill to paid
     * @param paid choose whether the bill is already paid or not
     */
    private void generateBill(boolean paid)
    {
        loop: while(true)
        {
            int billIndex = IOUtils.getInteger("Specify Index to show full bill (0 to return): ");
            if(billIndex <= 0)
                break loop;
            billIndex = billIndex-1;
            if(paid)
            {
                if (billIndex > paidBills.size())
                {
                    System.out.println("Unknown index please try again");
                    continue loop;
                }
                paidBills.get(billIndex).printBill();
            }
            else
            {
                if (billIndex > unpaidBills.size())
                {
                    System.out.println("Unknown index please try again");
                    continue loop;
                }
                unpaidBills.get(billIndex).printBill();
                String confirm = IOUtils.getString("Mark this bill as paid?(YES to confirm else to return): ");
                if(confirm.equalsIgnoreCase("YES"))
                {
                    unpaidBills.get(billIndex).billPaid();
                }
            }
            break;
        }
    }
}
