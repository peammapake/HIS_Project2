import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient
{
    private int patientID;
    private String firstName;
    private String lastName;
    private String sex;
    private String address;
    private String phone;
    private Admission admission;

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

    public int getPatientID()
    {
        return patientID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getSex()
    {
        return sex;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Admission getAdmission()
    {
        return admission;
    }

    public void printPatientBasicInfo()
    {
        System.out.println("---- Patient information ----");
        System.out.println("Patient ID : " + getPatientID());
        System.out.println("Name : " + getFirstName() + " " + getLastName());
        System.out.println("Sex : " + getSex());
        System.out.println("Address: " + getAddress());
        System.out.println("Phone: " + getPhone());
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
}
