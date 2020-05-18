import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Bill
{

    private int billID;
    private Timestamp regDate = null;
    private Timestamp payDate = null;
    private String firstName = null;
    private String lastName = null;
    private int patientID;
    private ArrayList<String> treatmentList = new ArrayList<String>();
    private ArrayList<String> labTestList = new ArrayList<String>();
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

    public int getBillID()
    {
        return billID;
    }
    public Timestamp getRegisterDate()
    {
        return regDate;
    }
    public Timestamp getPayDate()
    {
        return payDate;
    }
    public String getPatientName()
    {
        String name = firstName + " " + lastName;
        return name;
    }
    public int getPatientID()
    {
        return patientID;
    }

    public ArrayList<String> getTreatments()
    {
        return treatmentList;
    }

    public ArrayList<String> getLabTest()
    {
        return labTestList;
    }

    public ArrayList<String> getPrescription()
    {
        return prescriptionList;
    }


}

