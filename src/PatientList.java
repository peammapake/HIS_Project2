import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Collection class of patient hold all instance of Patient in system
 * Group YakkinTonkatsu
 * Create by   Nonthakorn Sukprom 60070503435
 *             Bhimapaka Thapanangkun 60070503447
 */
public class PatientList
{
    /**Array list containing all instances of patients loaded*/
    private static ArrayList<Patient> patients = null;

    /**
     * Initialize query all patient from the database
     * @param patientRS ResultSet of all query patients
     */
    public static void initialize(ResultSet patientRS) throws SQLException
    {
        patients = new ArrayList<Patient>();
        while(patientRS.next())
        {
            addPatient(new Patient(patientRS,false));
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
     * @return Return status whether the list is empty or not
     */
    public static boolean showPatients()
    {
        int index = 1;
        if(patients.isEmpty())
            return false;
        for(Patient patient: patients)
        {
            int pID = patient.getPatientID();
            String fName = patient.getFirstName();
            String lName = patient.getLastName();
            String sex = patient.getSex();

            System.out.println(index + " - PatientID:" + pID + " Name: " + fName + " " + lName + " Sex: " + sex);
            index++;
        }
        return true;
    }

    /**
     * Remove patient from the list
     * Indicate through index number
     * @param index Indicate the patient to be remove
     * @return status whether the system can remove the patient from list
     */
    public static boolean removePatient(int index)
    {
        if(patients.isEmpty())
            return false;
        patients.remove(index);
        return true;
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
