import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientList
{
    private static ArrayList<Patient> patients = new ArrayList<Patient>();

    public static void initialize(ResultSet patientRS) throws SQLException
    {
        while(patientRS.next())
        {
            addPatient(new Patient(patientRS));
        }
    }

    public static void addPatient(Patient patient)
    {
        patients.add(patient);
    }

    public static Patient getPatient(int index)
    {
        Patient patient = patients.get(index);
        return patient;
    }

    public static void showPatients()
    {
        int index = 1;
        for(Patient patient: patients)
        {
            int pID = patient.getPatientID();
            String fName = patient.getFirstName();
            String lName = patient.getLastName();
            String sex = patient.getSex();

            System.out.println(index + "- PatientID:" + pID + " Name: " + fName + " " + lName + " Sex: " + sex);
            index++;
        }
    }

    public static int getSize()
    {
        return patients.size();
    }

    /**
     * Get the ID of the latest patient
     * @return
     */
    public static int getLatestPatientID()
    {
        int latest = patients.size();
        return patients.get(latest).getPatientID();
    }
}
