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
    private PatientList patients = null;

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
     * @throws SQLException when there is a problem with ResultSet statement
     */
    @Override
    public void promptMenu() throws SQLException
    {
        int choice = -999;
        mainMenu: while(true)
        {
            loadStaffData();
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Available options:");
            System.out.println("1 - Patient in-queue lookup");
            System.out.println("2 - Admission update");
            System.out.println("3 - Discharge patient");
            System.out.println("4 - Update data");
            System.out.println("5 - Logout");

            choiceMenu:
            while (true)
            {
                choice = IOUtils.getInteger("Please enter your choice of action: ");
                switch (choice)
                {
                    case 1:
                        if(showPatientsInQueue())
                            admitPatient();
                        continue mainMenu;
                    case 2:

                        continue mainMenu;
                    case 3:

                        continue mainMenu;
                    case 4:
                        continue mainMenu;
                    case 5:
                        break mainMenu;
                    default:
                        System.out.println("Unavailable choice input");
                        continue choiceMenu;
                }
            }
        }
    }


    /**
     * Abstract method preparing data from database as needed for each staff type
     */
    @Override
    public void loadStaffData() throws SQLException
    {
        ResultSet patientRS = DBManager.getPatientList();
        PatientList.initialize(patientRS);
        patientRS.close();
        ResultSet queueRS = DBManager.getPatientListInQueue(getStaffID());
    }

    /**
     * print list of patients assigned to this doctor
     * and choose whether to admit the patient
     * If choose to admit, remove patient from the waiting list
     * First In First Out Strategy ONLY
     */
    public boolean showPatientsInQueue()
    {
        currentPatient = null;
        if(!PatientList.showPatients())
        {
            System.out.println("--There is no patient in queue--");
            return false;
        }
        String patientName = PatientList.getPatient(0).getFirstName() + " " + PatientList.getPatient(0).getLastName();
        System.out.println("**System follows First In First Out policy only");
        String confirmation = IOUtils.getStringSameLine("Proceed to admit " + patientName + "?[YES/NO]: ").trim();
        if(!confirmation.equalsIgnoreCase("YES"))
            return false;
        currentPatient = PatientList.getPatient(0);
        return true;
    }

    /**
     * Select patient from the list and store in currentPatient
     * @param index index of patient in the list
     */
    public void selectPatient(int index)
    {
        currentPatient = patients.getPatient(index);
    }

    /**
     *  Admit current chosen patient to the hospital,
     *  remove the patient name from the waiting list
     */
    public void admitPatient()
    {
        System.out.println("-----------------------------------------------------------------------------------");
        currentPatient.printPatientBasicInfo();

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
        System.out.println("5 : Prescribe medicine");
        System.out.println("----------------------------");
        int optionSelect = IOUtils.getInteger("Please enter your choice of action: ");
        switch (optionSelect)
        {
            case 1:
                recordSymptoms();
                break;
            case 2:
                recordTreatments();
                break;
            case 3:
                recordLabTests();
                break;
            case 4:
                recordDiagnosis();
                break;
            case 5:
                prescribe();
                break;
            default:
                System.out.println("Invalid input");
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
        currentPatient.printPatientBasicInfo();
    }

    /**
     * Record information about symptom of selected patient
     */
    private void recordSymptoms()
    {
        boolean bContinue = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String symptom = IOUtils.getString("Input Symptom (enter 0 to return): ");
            if (symptom.equals("0"))
            {
                break;
            }
            pAdmission.getSymptoms().addSymptom(symptom);
            String cont = IOUtils.getString("Record more symptoms?(y/n): ");
            if (cont.equals("y"))
            {
                bContinue = true;
            }
            else if (cont.equals("n"))
            {
                bContinue = false;
            }
            else
            {
                bContinue = false;
            }
        }
    }

    /**
     * Record information about treatment of selected patient
     */
    private void recordTreatments()
    {
        boolean bContinue = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String treatment = IOUtils.getString("Input treatment (enter 0 to return): ");
            if (treatment.equals("0"))
            {
                break;
            }
            pAdmission.getTreatments().addTreatment(treatment);
            String cont = IOUtils.getString("Record more treatment?(y/n): ");
            if (cont.equals("y"))
            {
                bContinue = true;
            }
            else if (cont.equals("n"))
            {
                bContinue = false;
            }
            else
            {
                bContinue = false;
            }
        }
    }

    /**
     * Record information about lab test of selected patient
     */
    private void recordLabTests()
    {
        boolean bContinue = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String labTest = IOUtils.getString("Input Lab test name (enter 0 to return): ");
            if (labTest.equals("0"))
            {
                break;
            }
            String result = "-";
            boolean bEmpty = true;
            while (bEmpty)
            {
                result = IOUtils.getString("Input Lab test result: ");
                if (!result.isEmpty())
                {
                    bEmpty = false;
                }
                else
                {
                    System.out.println("Result cannot be empty. Input again.");
                }
            }
            pAdmission.getLabTests().addLabTest(labTest, result);
            String cont = IOUtils.getString("Record more lab test?(y/n): ");
            if (cont.equals("y"))
            {
                bContinue = true;
            }
            else if (cont.equals("n"))
            {
                bContinue = false;
            }
            else
            {
                bContinue = false;
            }
        }
    }

    private void recordDiagnosis()
    {
        String diagnosis = IOUtils.getString("Enter patient's diagnosis : ");
        currentPatient.getAdmission().setDiagnosis(diagnosis);
    }

    /**
     * prescribe list of medicine to selected patient
     */
    private void prescribe()
    {
        boolean bContinue = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String medicine = IOUtils.getString("Input medicine (enter 0 to return): ");
            if (medicine.equals("0"))
            {
                break;
            }
            pAdmission.getPrescriptions().addMedicine(medicine);
            String cont = IOUtils.getString("Record more prescription?(y/n): ");
            if (cont.equals("y"))
            {
                bContinue = true;
            }
            else if (cont.equals("n"))
            {
                bContinue = false;

            }
            else
            {
                bContinue = false;
            }
        }
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
