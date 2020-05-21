import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class represent Patient in hospital information system
 * Create by   Nonthakorn Sukprom 60070503435
               Bhimapaka Thapanangkun 60070503447
 */
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
    private Admission admission = null;

    /**
     * Constructor for patient include all patient basic information
     * and current admission if this user is admitting to the hospital
     * @param patientInfo ResultSet from SQl query
     * @param admit Status marking whether this patient is admitting to hospital or not
     * @throws SQLException input use ResultSet which require error handling
     */
    public Patient(ResultSet patientInfo, boolean admit) throws SQLException
    {
        patientID = patientInfo.getInt(1);
        firstName = patientInfo.getString(2);
        lastName = patientInfo.getString(3);
        sex = patientInfo.getString(4);
        address = patientInfo.getString(5);
        phone = patientInfo.getString(6);
        if(admit)
        {
            admission = new Admission(patientInfo);
        }
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
    }

    /**
     * Create new admission for the patient
     * @param doctorID  ID of doctor who is assigned with this patient
     */
    public void addAdmission(int doctorID)
    {
        admission = new Admission(doctorID, getPatientID());
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
        if(address != null)
            System.out.println("Address: " + getAddress());
        if(phone != null)
            System.out.println("Phone: " + getPhone());
        System.out.println("-----------------------------------------------------------------------------------");
    }

    /**
     * Print admission of the patient if this patient is
     * currently in admission
     */
    public void printCurrentAdmission()
    {
        if(this.admission == null)
            System.out.println("This patient is not in admission currently");
        else
            this.admission.printAdmission();
    }

    //public void newAdmission

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
     * @return  patient's admission record
     */
    public Admission getAdmission()
    {
        return admission;
    }
}
