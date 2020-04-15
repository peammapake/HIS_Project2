import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *  Class represent Doctor in hospital information system
 *  Create by YAKKIN_TONKATSU group
 */
public class Doctor extends Staff
{
    /** List of patients assigned to doctor by nurse*/
    private PatientList patients = new PatientList();

    /** current selected patient*/
    private Patient currentPatient;

    /** Static list of doctors in the system*/
    public static ArrayList<Doctor> doctorList = new ArrayList<>();

    /**
     * Constructor method create instance of doctor
     * use staff information from database
     *
     * @param userInfo user information from SQL database
     */
    public Doctor(ResultSet userInfo)
    {
        super(userInfo);
    }

    /**
     * Abstract method show option available in each specific role.s
     */
    @Override
    public void promptMenu()
    {

    }

    /**
     * print list of patients assigned to this  doctor
     */
    public void showPatients()
    {

    }

    /**
     * Select patient from the list and store in currentPatient
     * @param index
     */
    public void selectPatient(int index)
    {

    }

    /**
     *  Admit selected patient to bed
     */
    public void admitPatient()
    {

    }

    /**
     *  Sub menu for record information to selected patient record
     */
    public void recordInformation()
    {

    }

    /**
     * Discharge patient from the system
     */
    public void dischargePatient()
    {

    }

    /**
     * Print all information of selected patient
     */
    public void printPatientInfo()
    {

    }

    /**
     * Record information about symptom of selected patient
     */
    private void recordSymptoms()
    {

    }

    /**
     * Record information about treatment of selected patient
     */
    private void recordTreatments()
    {

    }

    /**
     * Record information about lab test of selected patient
     */
    private void recordLabTests()
    {

    }

    private void recordDiagnosis()
    {

    }

    /**
     * prescribe list of medicine to selected patient
     */
    private void prescribe()
    {

    }

    /**
     * Method return list of Doctor in the system
     * @return  doctorList  ArrayList of Doctor
     */
    public static ArrayList<Doctor> getDoctorList()
    {
        return doctorList;
    }

    /**
     * Method return current selected patient of doctor
     * @return  currentPatient      Patient currently selected by this doctors
     */
    public Patient getCurrentPatient()
    {
        return currentPatient;
    }
}
