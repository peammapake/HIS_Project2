import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient
{
    private int patientID;
    private String firstName;
    private String lastName;
    private String sex;
    private String address;
    private int phone;
    private Admission admission;

    public Patient(ResultSet patientInfo) throws SQLException
    {
        patientID = patientInfo.getInt(1);
        firstName = patientInfo.getString(2);
        lastName = patientInfo.getString(3);
        sex = patientInfo.getString(4);
        phone = patientInfo.getInt(5);
        admission = new Admission();
    }

    public Patient(String firstName, String lastName, String sex, String address, int phone)
    {
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

    public int getPhone()
    {
        return phone;
    }

    public Admission getAdmission()
    {
        return admission;
    }

    public void printPatientInfo()
    {
        System.out.println("---- Patient information ----");
        System.out.println("Patient ID : " + getPatientID());
        System.out.println("Name : " + getFirstName() + " " + getLastName());
        System.out.println("Gender : " + getSex());
        System.out.println("Symptoms : ");
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
        System.out.println("Assigned Doctor: " + admission.getAssignedDoctor());

    }
}
