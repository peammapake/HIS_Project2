import java.sql.ResultSet;
import java.sql.SQLException;
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
    public static ArrayList<Doctor> doctorArrayList = new ArrayList<>();

    /**
     * Constructor method create instance of doctor
     * use staff information from database
     *
     * @param userInfo user information from SQL database
     */
    public Doctor(ResultSet userInfo) throws SQLException
    {
        super(userInfo);
        doctorArrayList.add(this);

    }

    /**
     * Abstract method show option available in each specific role.s
     */
    @Override
    public void promptMenu()
    {
        System.out.println("1 - Patient lookup");
        System.out.println("2 - Discharge patient");
        System.out.println("3 - Logout");
    }

    @Override
    public void loadStaffData() throws SQLException
    {

    }

    /**
     * print list of patients assigned to this  doctor
     */
    public void showPatients()
    {
        for (int i = 0; i < patients.getSize(); i++)
        {
            Patient patient = patients.getPatient(i);
            String patientName = patient.getFirstName() + " " + patient.getLastName();
            System.out.println((i + 1) + patientName);
        }
    }

    /**
     * Select patient from the list and store in currentPatient
     * @param index     index of patient in the list
     */
    public void selectPatient(int index)
    {
        currentPatient = patients.getPatient(index);
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
        System.out.println("---- Record Information ----");
        System.out.println("1 : Record Symptoms");
        System.out.println("2 : Record Treatment");
        System.out.println("3 : Record Lab test and result");
        System.out.println("4 : Record Diagnosis");
        System.out.println("5 : Prescribe");
        System.out.println("----------------------------");
        int optionSelect = IOUtils.getInteger("Enter number of option : ");
        switch (optionSelect)
        {
            // TODO
        }
    }

    /**
     * Discharge currently selected patient from the system
     */
    public void dischargePatient()
    {
        // Should Create BillManager class to call and create Bill in that class
    }

    /**
     * Print all information of selected patient
     */
    public void printPatientInfo()
    {
        Admission patientAdmission = currentPatient.getAdmission();
        System.out.println("---- Patient information ----");
        System.out.println("Patient ID : " + currentPatient.getPatientID());
        System.out.println("Name : " + currentPatient.getFirstName() + " " + currentPatient.getLastName());
        System.out.println("Gender : " + currentPatient.getSex());
        System.out.println("Symptoms : ");
        for (int i = 0 ; i < patientAdmission.getSymptomsSize(); i++)
        {
            System.out.println("\t" + i + " - " + patientAdmission.getSymptom(i));
        }
        System.out.println("Treatment : ");
        for (int i = 0 ; i < patientAdmission.getTreatmentsSize(); i++)
        {
            System.out.println("\t" + i + " - " + patientAdmission.getTreatment(i));
        }
        System.out.println("Lab test and result : ");
        for (int i = 0 ; i < patientAdmission.getLabTestSize(); i++)
        {
            System.out.println("\t" + i + " - " + patientAdmission.getLabTest(i));
        }


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
        return doctorArrayList;
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
