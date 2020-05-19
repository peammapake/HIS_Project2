import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class represent nurse in hospital information system
 */
public class Nurse extends Staff {

    /**
     * Currently selected patient
     */
    private Patient currentPatient = null;

    private ArrayList<Doctor> doctorList = new ArrayList<Doctor>();

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
        int choice = -999;
        mainMenu: while(true)
        {
            System.out.println("1 - Patient lookup");
            System.out.println("2 - Register new patient");
            System.out.println("3 - Logout");

            choiceMenu:
            while (true)
            {
                choice = IOUtils.getInteger("Please enter your choice of action: ");
                switch (choice)
                {
                    case 1:
                        patientLookUp();
                        continue mainMenu;
                    case 2:
                        registerPatient();
                        continue mainMenu;
                    case 3:
                        break mainMenu;
                    default:
                        System.out.println("Unavailable input choice");
                        continue choiceMenu;
                }
            }
        }
    }

    @Override
    public void loadStaffData() throws SQLException
    {
        ResultSet doctorRS = DBManager.getStaffList("DOCTOR");
        while(doctorRS.next())
        {
            doctorList.add(new Doctor(doctorRS));
        }
        //doctorRS.close();
    }

    public void registerPatient()
    {
        System.out.println("Enter new patient information");
        String firstName = IOUtils.getStringSameLine("First name: ");
        String lastName = IOUtils.getStringSameLine("Last name: ");
        String sex = IOUtils.getStringSameLine("Sex: ");
        String address = IOUtils.getStringSameLine("Address: ");
        int phone = IOUtils.getInteger("Phone Number: ");
        int thisPatientID = PatientList.getLatestPatientID() + 1; //New patient will have an ID after the last patient
        currentPatient = new Patient(thisPatientID,firstName, lastName, sex, address, phone);
        if(DBManager.addNewPatient(currentPatient))
            System.out.println("Successfully add new patient");
        else
            System.out.println("Error: Add new patient unsuccessful");
        System.out.println("-----------------------------------------------------------------------------------");
        printPatientInfo();
        assignDoctor();
    }

    /**
     * Assign a doctor to the currently selected patient
     * Responsible for notifying database to add new patient queue
     * for the specific doctor
     */
    private void assignDoctor()
    {
        int doctorIndex = 0;
        System.out.println("Available doctors");
        for (int i = 0 ; i < doctorList.size(); i++)
        {
            System.out.println((i+1) + " - " + doctorList.get(i).getFullName());
        }
        loop: while(true)
        {
            doctorIndex = IOUtils.getInteger("Select dorktor by index: ");
            if ((doctorIndex <= 0)||(doctorIndex > doctorList.size()))
            {
                System.out.println("Unavailable doctor, please try again");
                continue loop;
            }
            break;
        }
        doctorIndex--; //Change index to computer standard(NEEDED)
        Doctor currentDoctor = doctorList.get(doctorIndex);
        if (DBManager.addQueue(currentPatient, currentDoctor))
            System.out.println("Successfully assign Dr." + currentDoctor.firstName + " for " + currentPatient.getFirstName());
        else
            System.out.println("Error: Assign queue unsuccessful");
    }

    /**
     * Select patient from the list and store in currentPatient
     * @param index     index of patient in the list
     */
    public void selectPatient(int index)
    {
        currentPatient = PatientList.getPatient(index);
    }
    /**
     * Print selected patient's information
     */
    private void printPatientInfo()
    {
        currentPatient.printPatientBasicInfo();
        System.out.println("-----------------------------------------------------------------------------------");
    }

    /**
     * Method to show all of the registered patient
     * User can choose to further assign the doctor for patient
     * or go back
     */
    private void patientLookUp()
    {
        PatientList.showPatients();
        loop: while(true)
        {
            int index = IOUtils.getInteger("Specify Index to show patient's full info. (0 to return): ");
            if (index <= 0)
                break loop;
            index = index - 1;
            currentPatient = PatientList.getPatient(index);
            printPatientInfo();
            assignDoctor();
            break;
        }
    }

}
