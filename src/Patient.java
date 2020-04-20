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
}
