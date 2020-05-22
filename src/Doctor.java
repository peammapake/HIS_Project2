import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *  Class represent Doctor in hospital information system
 *  Create by   Nonthakorn Sukprom 60070503435
 *              Bhimapaka Thapanangkun 60070503447
 */
public class Doctor extends Staff
{
    /** current selected patient*/
    private Patient currentPatient;

    private ArrayList<Patient> admitPatients = new ArrayList<Patient>();

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

            choiceMenu: while (true)
            {
                choice = IOUtils.getInteger("Please enter your choice of action: ");
                switch (choice)
                {
                    case 1:
                        if(showPatientsInQueue())
                        {
                            admitPatient();
                            recordInformation();
                            recordNewAdmission();
                        }
                        continue mainMenu;
                    case 2:
                        showAdmissions();
                        if(chooseAdmittedPatient())
                        {
                            currentPatient.printCurrentAdmission();
                            recordInformation();
                            recordModifyAdmission();
                        }
                        continue mainMenu;
                    case 3:
                        showAdmissions();
                        dischargePatient();
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
        ResultSet patientRS = DBManager.getPatientListInQueue(getStaffID());
        PatientList.initialize(patientRS);
        patientRS.close();

        ResultSet admissionRS = DBManager.getAdmissions(getStaffID());
        admitPatients = new ArrayList<Patient>();
        while(admissionRS.next())
        {
            admitPatients.add(new Patient(admissionRS,true));
        }
        admissionRS.close();
    }

    /**
     * print list of patients assigned to this doctor
     * and choose whether to admit the patient
     * First In First Out Strategy ONLY
     * @return return true if user chose to admit patient
     */
    private boolean showPatientsInQueue()
    {
        currentPatient = null;
        if(!PatientList.showPatients())
        {
            System.out.println("---- There is no patient in queue ----");
            return false;
        }
        String patientName = PatientList.getPatient(0).getFirstName() + " " + PatientList.getPatient(0).getLastName();
        System.out.println("**System follows First In First Out policy only");
        String confirmation = IOUtils.getStringSameLine("Proceed to admit " + patientName + "?[YES/NO]: ").trim();
        if(!confirmation.equalsIgnoreCase("YES"))
            return false;
        return true;
    }

    /**
     * Print all admissions basic info of the current doctor
     */
    private void showAdmissions()
    {
        int index = 1;
        System.out.println("-----------------------------------------------------------------------------------");
        for(Patient admit: admitPatients)
        {
            String fName = admit.getFirstName();
            String lName = admit.getLastName();
            int pID = admit.getPatientID();
            Timestamp date = admit.getAdmission().getAdmitDate();
            System.out.println(index + " - " + fName + " " + lName + " Patient ID: " + pID + " Date: " + date);
            index++;
        }
    }

    private boolean chooseAdmittedPatient()
    {
        loop: while(true)
        {
            int index = -999;
            index = IOUtils.getInteger("Choose patient by index (0 to return): ");
            if(index == -999)
                continue loop;
            if(index == 0)
                return false;
            index--;
            currentPatient = admitPatients.get(index);
            break;
        }
        return true;
    }

    /**
     *  Admit first patient in queue to the hospital,
     *  remove the patient from the waiting list
     */
    private void admitPatient()
    {
        currentPatient = PatientList.getPatient(0);
        DBManager.removeQueue(getStaffID(), currentPatient.getPatientID());
        currentPatient.addAdmission(getStaffID());
        PatientList.removePatient(0);
        System.out.println("-----------------------------------------------------------------------------------");
        currentPatient.printPatientBasicInfo();
    }

    /**
     * Contact database to add new admission and print error if fail
     */
    private void recordNewAdmission()
    {
        if(DBManager.addAdmission(currentPatient.getAdmission()))
            System.out.println("Successfully add "+ currentPatient.getFirstName() + " " + currentPatient.getLastName() + " new admission");
        else
            System.out.println("Error: Add new admission to database unsuccessful");
    }

    /**
     * Contact database to modify current patient admission and print error if fail
     */
    private void recordModifyAdmission()
    {
        if(DBManager.updateAdmission(currentPatient.getAdmission()))
            System.out.println("Successfully update admission of " + currentPatient.getFirstName() + " " + currentPatient.getLastName());
        else
            System.out.println("Error: Modify admission to database unsuccessful");
    }

    /**
     *  Sub menu for record information to selected patient record
     */
    private void recordInformation()
    {
        System.out.println("---- Record Information ----");
        System.out.println("1 : Record Symptoms");
        System.out.println("2 : Record Treatment");
        System.out.println("3 : Record Lab test and result");
        System.out.println("4 : Record Diagnosis");
        System.out.println("5 : Prescribe medicine");
        System.out.println("6 : Save information and return to main menu");
        loop: while(true)
        {
            int optionSelect = IOUtils.getInteger("Please enter your choice of action: ");
            switch (optionSelect)
            {
                case 1:
                    recordSymptoms();
                    continue loop;
                case 2:
                    recordTreatments();
                    continue loop;
                case 3:
                    recordLabTests();
                    continue loop;
                case 4:
                    recordDiagnosis();
                    continue loop;
                case 5:
                    prescribe();
                    continue loop;
                case 6:
                    break loop;
                default:
                    System.out.println("Invalid input");
                    continue loop;
            }
        }
    }

    /**
     * Discharge currently selected patient from the system
     */
    public void dischargePatient()
    {
        System.out.println("-----------------------------------------------------------------------------------");
        loop1: while(true)
        {
            int index = IOUtils.getInteger("Input patient index to discharge (0 to return): ");
            if(index == 0)
                break loop1;
            if(index == -999)
                continue loop1;
            String confirm = IOUtils.getStringSameLine("Confirm discharge patient [YES/NO]: ");
            if(confirm.equalsIgnoreCase("YES"))
            {
                int admissionID = currentPatient.getAdmission().getAdmitID();
                Timestamp time = new Timestamp(System.currentTimeMillis());
                String fullName = currentPatient.getFirstName() + " " + currentPatient.getLastName();
                if(DBManager.dischargePatient(admissionID,time))
                    System.out.println("Successfully discharge " + fullName + " at: " + time);
                else
                    System.out.println("Error: Update discharge time on database unsuccessful");
                if(DBManager.addBill(currentPatient))
                    System.out.println("Successfully generate bill for " + fullName);
                else
                    System.out.println("Error: Insert new bill unsuccessful");
            }
        }
    }


    /**
     * Record information about symptom of selected patient
     */
    private void recordSymptoms()
    {
        boolean bContinue = true;
        boolean bEmpty = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String symptom = "";
            bEmpty = true;
            while (bEmpty)
            {
                symptom = IOUtils.getString("Input Symptom (enter 0 to return): ");
                if (symptom.trim().isEmpty())
                {
                    System.out.println("Input cannot be blank. Try again.");
                }
                else
                {
                    bEmpty = false;
                }
            }
            if (symptom.equals("0"))
            {
                break;
            }
            pAdmission.addSymptom(symptom);
            String cont = IOUtils.getString("Record more symptoms?(y/n): ");
            if (cont.equalsIgnoreCase("y"))
            {
                bContinue = true;
            }
            else if (cont.equalsIgnoreCase("n"))
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
        boolean bEmpty = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String treatment = "";
            bEmpty = true;
            while (bEmpty)
            {
                treatment = IOUtils.getString("Input treatment (enter 0 to return): ");
                if (treatment.trim().isEmpty())
                {
                    System.out.println("Input cannot be blank. Try again.");
                }
                else
                {
                    bEmpty = false;
                }
            }
            if (treatment.equals("0"))
            {
                break;
            }
            pAdmission.addTreatment(treatment);
            String cont = IOUtils.getString("Record more treatment?(y/n): ");
            if (cont.equalsIgnoreCase("y"))
            {
                bContinue = true;
            }
            else if (cont.equalsIgnoreCase("n"))
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
        boolean bEmpty = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String result = "";
            String labTest = "";
            bEmpty = true;
            while (bEmpty)
            {
                labTest = IOUtils.getString("Input Lab test name (enter 0 to return): ");
                if (labTest.trim().isEmpty())
                {
                    System.out.println("Input cannot be blank. Try again.");
                }
                else
                {
                    bEmpty = false;
                }
            }
            if (labTest.equalsIgnoreCase("0"))
            {
                break;
            }
            bEmpty = true;
            while (bEmpty)
            {
                result = IOUtils.getString("Input Lab test result: ");
                if (result.trim().isEmpty())
                {
                    System.out.println("Input cannot be blank. Try again.");
                }
                else
                {
                    bEmpty = false;
                }
            }
            pAdmission.addLabTest(labTest, result);
            String cont = IOUtils.getString("Record more lab test?(y/n): ");
            if (cont.equalsIgnoreCase("y"))
            {
                bContinue = true;
            }
            else if (cont.equalsIgnoreCase("n"))
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
        boolean bEmpty = true;
        String diagnosis = "";
        while (bEmpty)
        {
            diagnosis = IOUtils.getString("Enter patient's diagnosis : ");
            if (diagnosis.trim().isEmpty())
            {
                System.out.println("Input cannot be blank. Try again.");
            }
            else
            {
                bEmpty = false;
            }
        }
        currentPatient.getAdmission().setDiagnosis(diagnosis);
    }

    /**
     * prescribe list of medicine to selected patient
     */
    private void prescribe()
    {
        boolean bContinue = true;
        boolean bEmpty = true;
        Admission pAdmission = currentPatient.getAdmission();
        while (bContinue)
        {
            String medicine = "";
            bEmpty = true;
            while (bEmpty)
            {
                medicine = IOUtils.getString("Enter prescription: ");
                if (medicine.trim().isEmpty())
                {
                    System.out.println("Input cannot be blank. Try again.");
                }
                else
                {
                    bEmpty = false;
                }
            }
            if (medicine.equals("0"))
            {
                break;
            }
            pAdmission.addPrescription(medicine);
            String cont = IOUtils.getString("Record more prescription?(y/n): ");
            if (cont.equalsIgnoreCase("y"))
            {
                bContinue = true;
            }
            else if (cont.equalsIgnoreCase("n"))
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
