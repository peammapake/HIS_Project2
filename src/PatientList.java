import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientList
{
    /**Array list containing all instances of patients loaded*/
    private static ArrayList<Patient> patients = new ArrayList<Patient>();

    /**
     * Initialize query all patient from the database
     * @param patientRS ResultSet of all query patients
     */
    public static void initialize(ResultSet patientRS) throws SQLException
    {
        patients = new ArrayList<Patient>();
        while(patientRS.next())
        {
            addPatient(new Patient(patientRS));
        }
    }

    /**
     * Add new patient to the arraylist
      * @param patient the patient to be add
     */
    public static void addPatient(Patient patient)
    {
        patients.add(patient);
    }

    /**
     * Display all patient within the patient list
     * with index to allow user to choose
     */
    public static void showPatients()
    {
        int index = 1;
        for(Patient patient: patients)
        {
            int pID = patient.getPatientID();
            String fName = patient.getFirstName();
            String lName = patient.getLastName();
            String sex = patient.getSex();

            System.out.println(index + " - PatientID:" + pID + " Name: " + fName + " " + lName + " Sex: " + sex);
            index++;
        }
    }

    /**
     * Get specific patient, indicate through index in ArrayList
     * @param index Index of the patient within arraylist
     * @return Patient of the chosen index
     */
    public static Patient getPatient(int index)
    {
        Patient patient = patients.get(index);
        return patient;
    }

    /**
     * Get the number of all patient in the list
     * @return Total number of patients
     */
    public static int getSize()
    {
        return patients.size();
    }

    /**
     * Get the ID of the latest patient to be registered
     * @return ID of the latest patient
     */
    public static int getLatestPatientID()
    {
        int latest = patients.size() - 1;
        return patients.get(latest).getPatientID();
    }
}
