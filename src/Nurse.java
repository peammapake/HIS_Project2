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
        System.out.println("1 - Patient lookup");
        System.out.println("2 - Register new patient");
        System.out.println("3 - Logout");
    }

    @Override
    public void loadStaffData() throws SQLException
    {

    }

    public void registerPatient()
    {
        System.out.println("Enter new patient information");
        String firstName = IOUtils.getString("First name: ");
        String lastName = IOUtils.getString("Last name: ");
        String sex = IOUtils.getString("Gender: ");
        String address = IOUtils.getString("Address: ");
        int phone = IOUtils.getInteger("Phone Number: ");
        Patient patient = new Patient(firstName, lastName, sex, address, phone);
        System.out.println("Doctor to assign");
        for (int i = 0 ; i < Doctor.getDoctorList().size(); i++)
        {
            System.out.println((i+1) + " - " + Doctor.getDoctorList().get(i).getFullName());
        }
        int doctorIndex = IOUtils.getInteger("Select number of doctor") - 1;
        assignDoctor(Doctor.getDoctorList().get(doctorIndex), patient);
    }

    /**
     * Assign doctor to patient
     *
     * @param doctor doctor to assign
     */
    private void assignDoctor(Doctor doctor, Patient patient)
    {
        doctor.admitPatient(patient);
        patient.getAdmission().setAssignedDoctor(doctor);
    }

    /**
     * Print every patient in the list
     */
    public void showsPatients()
    {

    }

    /**
     * Print selected patient's information
     */
    public void printPatientInfo()
    {

    }
}
