import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class represent bill in hospital information system
 * Bill is generated after the patient are discharged from admission
 * bill contains all services and product need to be paid by patient
 * in one certain admission
 * Create by   Nonthakorn Sukprom 60070503435
 *             Bhimapaka Thapanangkun 60070503447
 */
public class Bill
{
    /** ID of this bill */
    private int billID;
    /** register time of this bill */
    private Timestamp regDate = null;
    /** paid time of this bill */
    private Timestamp payDate = null;
    /** first name of patient responsible for this bill */
    private String firstName = null;
    /** last name of patient responsible for this bill */
    private String lastName = null;
    /** ID of patient responsible for this bill */
    private int patientID;
    /** List of treatment recorded in this bill */
    private ArrayList<String> treatmentList = new ArrayList<String>();
    /** List of lab test recorded in this bill */
    private ArrayList<String> labTestList = new ArrayList<String>();
    /** List of medicine recorded in this bill */
    private ArrayList<String> prescriptionList = new ArrayList<String>();

    /** Constructor for bill, an Overloading method for constructing unpaid bill
     * @param id Bill id from database
     * @param reg Register date of the bill
     * @param fName Patient first name
     * @param lName Patient last name
     * @param pID Patient ID
     * @param treatment List of treatment in string
     * @param labTest List of lab test in string
     * @param prescription List of medicine prescription in string
     */
    public Bill(int id, Timestamp reg, String fName, String lName, int pID, String treatment, String labTest, String prescription)
    {
        billID = id;
        regDate = reg;
        firstName = fName;
        lastName = lName;
        patientID = pID;
        treatmentList = new ArrayList(Arrays.asList(treatment.split("\\|")));
        labTestList = new ArrayList(Arrays.asList(labTest.split("\\|")));
        prescriptionList = new ArrayList(Arrays.asList(prescription.split("\\|")));
    }

    /** Constructor for bill, an Overloading method for constructing paid bill
     * @param id Bill id from database
     * @param reg Register date of the bill
     * @param pay Pay date of the bill
     * @param fName Patient first name
     * @param lName Patient last name
     * @param pID Patient ID
     * @param treatment List of treatment in string
     * @param labTest List of lab test in string
     * @param prescription List of medicine prescription in string
     */
    public Bill(int id, Timestamp reg, Timestamp pay,String fName, String lName, int pID, String treatment, String labTest, String prescription)
    {
        billID = id;
        regDate = reg;
        payDate = pay;
        firstName = fName;
        lastName = lName;
        patientID = pID;
        treatmentList = new ArrayList(Arrays.asList(treatment.split("\\|")));
        labTestList = new ArrayList(Arrays.asList(labTest.split("\\|")));
        prescriptionList = new ArrayList(Arrays.asList(prescription.split("\\|")));
    }

    /**
     * Print out all information record in this bill
     */
    public void printBill()
    {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Bill ID: " + billID);
        System.out.println("Bill Register Date: " + regDate);
        if(payDate == null)
            System.out.println("Status: UNPAID");
        else
            System.out.println("Status: PAID at " + payDate);
        System.out.println("Patient INFO:");
        System.out.println("\tID: " + patientID);
        System.out.println("\tName: " + firstName + " " + lastName);
        System.out.println("Treatment List:");
        for(String str: treatmentList)
        {
            System.out.println("\t- " + str);
        }
        System.out.println("Lab Test List:");
        for(String str: labTestList)
        {
            System.out.println("\t- " + str);
        }
        System.out.println("Medicine Prescription List:");
        for(String str: prescriptionList)
        {
            System.out.println("\t- " + str);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    /**
     * Change state of the bill from UNPAID to PAID
     * represent that patient has already paid for the medical service they receive
     * in certain admission
     */
    public void billPaid()
    {
        if(payDate != null)
        {
            System.out.println("Error: This bill has been paid already");
        }
        else
        {
            payDate = new Timestamp(System.currentTimeMillis());
            System.out.println("This bill is now paid (Timestamp:" + payDate + ")");
            DBManager.billPaid(this);
        }
    }

    /**
     * @return biiID
     */
    public int getBillID()
    {
        return billID;
    }

    /**
     * @return regDate
     */
    public Timestamp getRegisterDate()
    {
        return regDate;
    }

    /**
     * @return payDate
     */
    public Timestamp getPayDate()
    {
        return payDate;
    }

    /**
     * @return name   Patient's full name
     */
    public String getPatientName()
    {
        String name = firstName + " " + lastName;
        return name;
    }

    /**
     * @return patientID
     */
    public int getPatientID()
    {
        return patientID;
    }

    /**
     * @return treatmentList
     */
    public ArrayList<String> getTreatments()
    {
        return treatmentList;
    }

    /**
     * @return labTestList
     */
    public ArrayList<String> getLabTest()
    {
        return labTestList;
    }

    /**
     * @return prescriptionList
     */
    public ArrayList<String> getPrescription()
    {
        return prescriptionList;
    }


}

