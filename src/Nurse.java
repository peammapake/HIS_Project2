import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class represent nurse in hospital information system
 */
public class Nurse extends Staff {
    /**
     * List of every patient currently in system
     */
    private PatientList patients = new PatientList();

    /**
     * Currently selected patient
     */
    private Patient currentPatient;

    /**
     * Static list of nurse in system
     */
    public static ArrayList<Nurse> nurseArrayList = new ArrayList<Nurse>();

    /**
     * Constructor method create instance of nurse
     * use user information from database
     *
     * @param userInfo user information from SQL database
     */
    public Nurse(ResultSet userInfo) throws SQLException {
        super(userInfo);
        nurseArrayList.add(this);
    }

    /**
     * Abstract method show option available in each specific roles
     */
    @Override
    public void promptMenu()
    {

    }

    /**
     * register patient into system
     */
    public void registerPatient() {

    }

    /**
     * Assign doctor to patient
     *
     * @param doctor doctor to assign
     */
    private void assignDoctor(Doctor doctor) {

    }

    /**
     * Print every patient in the list
     */
    public void showsPatients() {

    }

    /**
     * Print selected patient's information
     */
    public void printPatientInfo()
    {

    }
}
