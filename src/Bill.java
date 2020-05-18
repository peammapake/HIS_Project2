import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Bill
{
    Patient patient = null;
    private int billID;
    private Timestamp regDate = null;
    private Timestamp payDate = null;
    private ArrayList<String> treatment = new ArrayList<String>();
    private ArrayList<String> labTest = new ArrayList<String>();
    private ArrayList<String> prescription = new ArrayList<String>();

    /** Constructor for bill, an Overloading method for constructing unpaid bill
     * @param id Bill id from database
     * @param reg Register date of the bill
     * @param fName Patient first name
     * @param lName Patient last name
     * @param pId Patient ID
     * @param treatment List of treatment in string
     * @param labTest List of lab test in string
     * @param prescription List of medicine prescription in string
     */
    public Bill(int id, Timestamp reg, String fName, String lName, int pId, String treatment, String labTest, String prescription)
    {

    }

    /** Constructor for bill, an Overloading method for constructing paid bill
     * @param id Bill id from database
     * @param reg Register date of the bill
     * @param pay Pay date of the bill
     * @param fName Patient first name
     * @param lName Patient last name
     * @param pId Patient ID
     * @param treatment List of treatment in string
     * @param labTest List of lab test in string
     * @param prescription List of medicine prescription in string
     */
    public Bill(int id, Timestamp reg, Timestamp pay,String fName, String lName, int pId, String treatment, String labTest, String prescription)
    {

    }

    public String getPatientName()
    {
        return null;//template
    }

    public ArrayList<String> getTreatments()
    {
        return treatment;
    }

    public ArrayList<String> getLabTest()
    {
        return labTest;
    }

    public ArrayList<String> getPrescription()
    {
        return prescription;
    }

    public void printBill()
    {

    }
}

