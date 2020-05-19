import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient
{
    /**Patient ID of the current patient*/
    private int patientID;
    /**First name of the patient*/
    private String firstName;
    /**Patient last name*/
    private String lastName;
    /**Patient physical sexuality*/
    private String sex;
    /**Patient home address*/
    private String address;
    /**Phone number of patient, cannot be integer because 0 in front*/
    private String phone;
    /**Patient current admission*/
    private Admission admission;

    /**
     * Constructor for patient include all patient basic information
     * @param patientInfo ResultSet from SQl query
     * @throws SQLException input use ResultSet which require error handling
     */
    public Patient(ResultSet patientInfo) throws SQLException
    {
        patientID = patientInfo.getInt(1);
        firstName = patientInfo.getString(2);
        lastName = patientInfo.getString(3);
        sex = patientInfo.getString(4);
        address = patientInfo.getString(5);
        phone = patientInfo.getString(6);
        admission = new Admission();
    }

    /**
     * Overloading constructor so it is also capable of adding new patient using basic variable input
     * @param patientID Patient ID
     * @param firstName Patient first name
     * @param lastName Patient last name
     * @param sex Patient physical sexuality
     * @param address Patient home address
     * @param phone Patient's phone number
     */
    public Patient(int patientID, String firstName, String lastName, String sex, String address, String phone)
    {
        this.patientID = patientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
        admission = new Admission();
    }

    /**
     * Print basic information of the patient
     */
    public void printPatientBasicInfo()
    {
        System.out.println("---- Patient information ----");
        System.out.println("Patient ID : " + getPatientID());
        System.out.println("Name : " + getFirstName() + " " + getLastName());
        System.out.println("Sex : " + getSex());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone: " + getPhone());
        System.out.println("-----------------------------------------------------------------------------------");
        /*System.out.println("Symptoms : ");
        SymptomList symptoms = admission.getSymptoms();
        for (int i = 0 ; i < symptoms.getSymptomSize(); i++)
        {
            System.out.println("\t" + i + " - " + symptoms.getSymptom(i));
        }
        System.out.println("Treatment : ");
        TreatmentList treatments = admission.getTreatments();
        for (int i = 0 ; i < treatments.getTreatmentsSize(); i++)
        {
            System.out.println("\t" + i + " - " + treatments.getTreatment(i));
        }
        System.out.println("Lab test and result : ");
        LabTestList labTests = admission.getLabTests();
        for (int i = 0 ; i < labTests.getLabTestSize(); i++)
        {
            System.out.println("\t" + i + " - " + labTests.getLabTest(i));
        }
        System.out.println("Prescription : ");
        Prescriptions prescriptions = admission.getPrescriptions();
        for (int i = 0 ; i < prescriptions.getPrescriptionSize(); i++)
        {
            System.out.println("\t" + i + " - " + prescriptions.getMedicine(i));
        }
        System.out.println("Prescription : " + admission.getDiagnosis());
        System.out.println("Assigned Doctor: " + admission.getAssignedDoctor());*/

    }

    //Getter and Setter zone--------------------------------------------------------------------------------------------

    /**
     * @return patient ID
     */
    public int getPatientID()
    {
        return patientID;
    }

    /**
     * @return patient first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @return patient last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @return patient physical sexuality
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * @return patient home address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @return phone number
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * set new first name
     * @param firstName new first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * set new last name
     * @param lastName new last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * set patient sexuality
     * @param sex new physical sexuality
     */
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    /**
     * set patient home address
     * @param address new home address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * set phone number
     * @param phone new phone number
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * ???????
     * @return
     */
    public Admission getAdmission()
    {
        return admission;
    }
}
