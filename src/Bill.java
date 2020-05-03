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

    /**
     * Bill constructor containing information for each individual bill coming
     * from database. Can contain either paid or unpaid bills
     * @param billRS ResultSet of the specific bill
     * @param billPaid designated the bill status whether it is paid or not
     * @throws SQLException
     */
    public Bill(ResultSet billRS, boolean billPaid) throws SQLException
    {
        billID = billRS.getInt(1);
        regDate = billRS.getTimestamp(2);

        if(billPaid)
            payDate = billRS.getTimestamp(3);

        String firstName = billRS.getString(4);
        String lastName = billRS.getString(5);
        int patientID = billRS.getInt(6);

        String treatmentList = billRS.getString(7);
        String labTestList = billRS.getString(8);
        String prescriptionList = billRS.getString(9);
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

