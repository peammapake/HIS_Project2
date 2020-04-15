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
    //Admission admission....waiting for the class

    public Patient(ResultSet patientInfo) throws SQLException
    {
        patientID = patientInfo.getInt(1);
        firstName = patientInfo.getString(2);
        lastName = patientInfo.getString(3);
        sex = patientInfo.getString(4);
        phone = patientInfo.getInt(5);
    }


}
