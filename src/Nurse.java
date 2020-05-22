import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class represent nurse in hospital information system
 * Group YakkinTonkatsu
 * Create by   Nonthakorn Sukprom 60070503435
 *             Bhimapaka Thapanangkun 60070503447
 */
public class Nurse extends Staff {

    /**Currently selected patient*/
    private Patient currentPatient = null;

    /**List of all available doctor*/
    private ArrayList<Doctor> doctorList = null;


    /**
     * Constructor method create instance of nurse
     * use user information from database
     *
     * @param userInfo user information from SQL database
     */
    public Nurse(ResultSet userInfo) throws SQLException {
        super(userInfo);
    }

    @Override
    /**
     * Abstract method show option available in each specific roles
     */
    public void promptMenu() throws SQLException
    {
        int choice = -999;
        mainMenu: while(true)
        {
            loadStaffData();
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println("Available options:");
            System.out.println("1 - Patient lookup");
            System.out.println("2 - Register new patient");
            System.out.println("3 - Modify patient information");
            System.out.println("4 - Update data");
            System.out.println("5 - Logout");

            choiceMenu: while (true)
            {
                choice = IOUtils.getInteger("Please enter your choice of action: ");
                switch (choice)
                {
                    case 1:
                        if(patientLookUp())
                            assignDoctor();
                        continue mainMenu;
                    case 2:
                        registerPatient();
                        assignDoctor();
                        continue mainMenu;
                    case 3:
                        patientLookUp();
                        modifyPatientInfo();
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

    @Override
    /**
     * Method responsible for loading and setting up all necessary data from database
     * - Nurse needs to know all doctor and patient
     */
    public void loadStaffData() throws SQLException
    {
        doctorList = new ArrayList<Doctor>();
        ResultSet doctorRS = DBManager.getStaffList("DOCTOR");
        while(doctorRS.next())
        {
            doctorList.add(new Doctor(doctorRS));
        }
        doctorRS.close();
        ResultSet patientRS = DBManager.getPatientList();
        PatientList.initialize(patientRS);
        patientRS.close();

    }

    /**
     * Method to insert and add new patient into the system and database
     */
    private void registerPatient()
    {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Enter new patient information");
        String firstName = IOUtils.getStringSameLine("First name: ");
        String lastName = IOUtils.getStringSameLine("Last name: ");
        String sex = null;
        while(true)
        {
            sex = IOUtils.getStringSameLine("Sex[M/F]: ");
            if((!sex.equalsIgnoreCase("M"))&&(!sex.equalsIgnoreCase("F")))
            {
                System.out.println("Sex must be M or F (Male/Female)");
                continue;
            }
            break;
        }
        String address = IOUtils.getStringSameLine("Address: ");
        String phone = IOUtils.getStringSameLine("Phone Number: ");
        int thisPatientID = PatientList.getLatestPatientID() + 1; //New patient will have an ID after the last patient
        currentPatient = new Patient(thisPatientID,firstName, lastName, sex, address, phone);
        if(DBManager.addNewPatient(currentPatient))
            System.out.println("Successfully add new patient");
        else
            System.out.println("Error: Add new patient unsuccessful");
        printPatientInfo();
    }

    /**
     * Method to modify the information of the current chosen patient and
     * update it on database
     *
     */
    private void modifyPatientInfo()
    {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Enter patient" + currentPatient.getFirstName() + " " + currentPatient.getLastName() + "new information");
        String firstName = IOUtils.getStringSameLine("First name: ");
        String lastName = IOUtils.getStringSameLine("Last name: ");
        String sex = null;
        while(true)
        {
            sex = IOUtils.getStringSameLine("Sex[M/F]: ").trim();
            if((!sex.equalsIgnoreCase("M"))&&(!sex.equalsIgnoreCase("F")))
            {
                System.out.println("Sex must be M or F (Male/Female)");
                continue;
            }
            break;
        }
        String address = IOUtils.getStringSameLine("Address: ");
        String phone = IOUtils.getStringSameLine("Phone Number: ");

        currentPatient.setFirstName(firstName);
        currentPatient.setLastName(lastName);
        currentPatient.setSex(sex);
        currentPatient.setAddress(address);
        currentPatient.setPhone(phone);
        printPatientInfo();

        if(!DBManager.modifyPatient(currentPatient))
            System.out.println("Error: Update patient information unsuccessful");
    }

    /**
     * Assign a doctor to the currently selected patient
     * Responsible for notifying database to add new patient queue
     * for the specific doctor
     */
    private boolean assignDoctor()
    {
        int doctorIndex = 0;
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Available doctors");
        for (int i = 0 ; i < doctorList.size(); i++)
        {
            System.out.println((i+1) + " - " + doctorList.get(i).getFullName());
        }
        loop: while(true)
        {
            doctorIndex = IOUtils.getInteger("Select dorktor by index (0 to return): ");
            if(doctorIndex == 0)
                return false;
            if ((doctorIndex < 0)||(doctorIndex > doctorList.size()))
            {
                System.out.println("Unavailable doctor, please try again");
                continue loop;
            }
            break;
        }
        doctorIndex--; //Change index to computer standard(NEEDED)
        Doctor currentDoctor = doctorList.get(doctorIndex);
        if (DBManager.addQueue(currentPatient, currentDoctor))
        {
            System.out.println("Successfully assign Dr." + currentDoctor.firstName + " for " + currentPatient.getFirstName());
            return true;
        }
        else
        {
            System.out.println("Error: Assign queue unsuccessful");
        }
        return false;
    }

    /**
     * Select patient from the list and store in currentPatient
     * The index must be computer standard(START AT 0)
     * @param index     index of patient in the list
     */
    private void selectPatient(int index)
    {
        currentPatient = PatientList.getPatient(index);
    }

    /**
     * Print selected patient's information
     */
    private void printPatientInfo()
    {
        currentPatient.printPatientBasicInfo();
    }

    /**
     * Method to show all of the registered patient
     * User can choose to further assign the doctor for patient
     * or go back
     */
    private boolean patientLookUp()
    {
        PatientList.showPatients();
        loop: while(true)
        {
            int index = IOUtils.getInteger("Specify Index to show patient's full info. (0 to return): ");
            if (index == 0)
                return false;
            if ((index < 0) || (index > PatientList.getSize()))
            {
                System.out.println("Chosen doctor unavailable");
                continue loop;
            }
            index = index - 1;
            selectPatient(index);
            printPatientInfo();
            break;
        }
        return true;
    }



}
