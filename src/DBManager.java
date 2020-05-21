import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

/**
 * Database Manager class responsible for all communication
 * with the Hospital Information Database
 */
public class DBManager
{
    /**Object responsible for database connection*/
    private static Connection DB = null;
    /**Object needed for communication with database*/
    private static Statement stmt = null;
    /**Object responsible for querying information from database*/
    private static ResultSet RS = null;

    /**
     * Establish database connection
     */
    public static void connectDatabase()
    {
        //DO NOT CHANGE: responsible for database login
        String url = "jdbc:mysql://remotemysql.com:3306/gRDM5lyOKB";
        String user = "gRDM5lyOKB";
        String pwd = "zTKv1VSG7W";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.print("Try establishing database connection......");
            //opening database connection to MySQL server
            DB = DriverManager.getConnection(url,user,pwd);
            //getting Statement object to be ready for query execution
            stmt = DB.createStatement();
            if(DB != null)
                System.out.println("Database Connected");
            else
                System.out.println("Database Connection failed");
        } catch (ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Terminate database communication
     */
    public static void disconnectDatabase()
    {
        try
        {
            if(RS != null)
                RS.close();
            if(stmt != null)
                stmt.close();
            if(DB != null)
            {
                DB.close();
                System.out.println("Database disconnect successfully");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Find the user from the database and collect the user data if exist
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     * @return return User information in ResultSet format
     */
    public static ResultSet userLogin(String username, String password)
    {
        int count = 0; //In case there are duplicate user
        String queryUsername = "username = \'" + username + "\'";
        String queryPassword = " AND password = \'" + password + "\'";
        String query = "SELECT userID,fName,lName,role FROM users WHERE " + queryUsername + queryPassword;
        RS = null;
        //executing the selected query
        try
        {
            RS = stmt.executeQuery(query);
            while(RS.next())
            {
                count++;
                String firstName = RS.getString(2);
                String lastName = RS.getString(3);
                //In case there are duplicate user, extreme case that could happen during user registration
                if(count > 1)
                {
                    System.out.println("Error: found duplicate user, please contact admin for further analysis");
                    System.exit(0);
                }
                System.out.println("Welcome " + firstName + " " + lastName);
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        //If user not found return null, this mean the username or password might be wrong
        if(count == 0)
        {

            return null;
        }
        return RS;
    }

    /**
     * Get all the staff registered in the database
     * @param role To specify the staff role that needed query
     * @return return doctors information in ResultSet format
     * @throws SQLException
     */
    public static ResultSet getStaffList(String role)
    {
        RS = null;
        try
        {
            String queryDoctors = "SELECT userID, fName, lName, role FROM users WHERE role = \'" + role + "\'";
            RS = stmt.executeQuery(queryDoctors);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return RS;
    }

    /**
     * get all the patients registered within the database
     * @return patients information in ResultSet format
     * @throws SQLException
     */
    public static ResultSet getPatientList()
    {
        RS = null;
        String queryPatients = "SELECT * FROM patients";
        try
        {
            RS = stmt.executeQuery(queryPatients);
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return RS;
    }

    //Clerk zone--------------------------------------------------------------------------------------------------------
    /**
     * Add new billing into the database, clerk can then access
     * these data to generate bill for patient
     * @param bill bill object obtained after discharging patient
     */
    public static void addBill(Bill bill)
    {
        String[] name = bill.getPatientName().split(" ");
        ArrayList<String> treatments = bill.getTreatments();
        ArrayList<String> labTest = bill.getLabTest();
        ArrayList<String> prescription = bill.getPrescription();

        String queryBill = "INSERT INTO `bills`(`bill_fName`, `bill_lName`, `patientID`, `treatmentList`, `labTestList`, `prescriptionList`) VALUES (";
        queryBill += "'" + name[0] + "','" + name[1] + "','";
        for(int i = 0; i < treatments.size(); i++)
        {
            queryBill += treatments.get(i);
            if(i+1 != treatments.size())
                queryBill += "|";
        }
        queryBill += "','";
        for(int i = 0; i < labTest.size(); i++)
        {
            queryBill += labTest.get(i);
            if(i+1 != labTest.size())
                queryBill += "|";
        }
        queryBill += "','";
        for(int i = 0; i < prescription.size(); i++)
        {
            queryBill += prescription.get(i);
            if(i+1 != prescription.size())
                queryBill += "|";
        }
        queryBill += "');";
        System.out.println(queryBill); //still testing
    }

    /**
     * Query bills from database, can get either already paid bill or
     * unpaid bill as designated by the input choice
     * @param paid choosing whether to get paid or unpaid bill list.
     * @return
     * @throws SQLException
     */
    public static ResultSet getBills(boolean paid)
    {
        int count = 1;
        RS = null;
        String queryBill = null;
        if(paid)
            queryBill = "SELECT * FROM bills WHERE payDate IS NOT NULL";
        else
            queryBill = "SELECT * FROM bills WHERE payDate IS NULL";
        try
        {
            RS = stmt.executeQuery(queryBill);
        } catch(SQLException e)
        {
            e.printStackTrace();
        }
        return RS;
    }

    /**
     * Method to update the unpaid bill in database, mark the bill as paid
     * by specify the payDate timestamp.
     * @param bill Bill object of the bill that user wants to mark as paid.
     */
    public static void billPaid(Bill bill)
    {
        String query = "UPDATE bills SET payDate = ? WHERE billID = ?";
        try
        {
            PreparedStatement prepareStmt = DB.prepareStatement(query);
            prepareStmt.setTimestamp(1,bill.getPayDate());
            prepareStmt.setInt(2,bill.getBillID());
            prepareStmt.executeUpdate();
            prepareStmt.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    //Nurse zone--------------------------------------------------------------------------------------------------------

    /**
     * Method for assigning doctor for the patient
     * Insert new row into database which act as a queue for the chosen patient.
     * @param patient The patient user wanted to be add to queue
     * @param doctor The assigned doctor for the current queue
     * @return status of SQL statement execution
     */
    public static boolean addQueue(Patient patient, Doctor doctor)
    {
        int patientID = patient.getPatientID();
        int doctorID = doctor.getStaffID();
        String query = "INSERT INTO assignQueue (doctorID, patientID)" + "values(?, ?)";
        try
        {
            PreparedStatement preparedStmt = DB.prepareStatement(query);
            preparedStmt.setInt(1,doctorID);
            preparedStmt.setInt(2,patientID);
            preparedStmt.execute();
            preparedStmt.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Method responsible for adding new patient into the database
     * @param patient
     * @return status of the database operation
     */
    public static boolean addNewPatient(Patient patient)
    {
        String query = "INSERT INTO patients (patientID, fName, lName, sex, address, phone)" + "values(?,?,?,?,?,?)";
        try
        {
            PreparedStatement preparedStatement = DB.prepareStatement(query);
            preparedStatement.setInt(1, patient.getPatientID());
            preparedStatement.setString(2,patient.getFirstName());
            preparedStatement.setString(3,patient.getLastName());
            preparedStatement.setString(4,patient.getSex());
            preparedStatement.setString(5,patient.getAddress());
            preparedStatement.setString(6,patient.getPhone());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Method to update new information of the specific patient to the database
     * @param patient the chosen patient to have information updated
     * @return status of the database update operation
     */
    public static boolean modifyPatient(Patient patient)
    {
        String query = "UPDATE patients SET fName = ?, lName = ?, sex = ?, address = ?, phone = ?  WHERE patientID = ?";
        try
        {
            PreparedStatement prepareStmt = DB.prepareStatement(query);
            prepareStmt.setString(1,patient.getFirstName());
            prepareStmt.setString(2,patient.getLastName());
            prepareStmt.setString(3,patient.getSex());
            prepareStmt.setString(4,patient.getAddress());
            prepareStmt.setString(5,patient.getPhone());
            prepareStmt.setInt(6,patient.getPatientID());
            prepareStmt.executeUpdate();
            prepareStmt.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //Doctor zone-------------------------------------------------------------------------------------------------------

    /**
     * Query all waiting queue for the specific doctor
     * @param doctorID ID of the chosen doctor
     * @return ResultSet containing all waiting queue of that doctor
     */
    public static ResultSet getPatientListInQueue(int doctorID)
    {
        String query = "SELECT * FROM patients JOIN assignQueue ON assignQueue.patientID = patients.patientID WHERE assignQueue.doctorID = ?";
        try
        {
            PreparedStatement prepareStmt = DB.prepareStatement(query);
            prepareStmt.setInt(1,doctorID);
            RS = prepareStmt.executeQuery();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return RS;
    }

    /**
     * Method to remove the first queue of the specific doctor from database
     * @param doctorID ID of the doctor assigned with the queue
     * @param patientID ID of patient of the given queue
     * @return
     */
    public static boolean removeQueue(int doctorID, int patientID)
    {
        String query = "DELETE FROM assignQueue WHERE doctorID = ? AND patientID = ? ORDER BY queueTime ASC LIMIT 1";
        try
        {
            PreparedStatement prepareStmt = DB.prepareStatement(query);
            prepareStmt.setInt(1, doctorID);
            prepareStmt.setInt(2, patientID);
            prepareStmt.execute();
            prepareStmt.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Query all undischarged patient information and their admission
     * information from database
     * @param doctorID
     * @return
     */
    public static ResultSet getAdmissions(int doctorID)
    {
        //String query = "SELECT * FROM admissions WHERE doctorID = ? AND dischargeDate IS NULL";
        String query = "SELECT * FROM patients JOIN admissions ON patients.patientID = admissions.patientID WHERE admissions.doctorID = ? AND admissions.dischargeDate IS NULL";
        try
        {
            PreparedStatement prepareStmt = DB.prepareStatement(query);
            prepareStmt.setInt(1, doctorID);
            RS = prepareStmt.executeQuery();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return RS;
    }

    public static boolean addAdmission(Admission admission)
    {
        String query = "INSERT INTO admissions (diagnosis, symptoms, labTests, labResult, treatments, prescriptions, patientID, doctorID)"
                + "values(?,?,?,?,?,?,?,?)";
        try
        {
            PreparedStatement preparedStatement = DB.prepareStatement(query);
            preparedStatement.setString(1, admission.getDiagnosis());
            preparedStatement.setString(2, admission.getSymptoms());
            preparedStatement.setString(3, admission.getLabTests());
            preparedStatement.setString(4, admission.getLabResults());
            preparedStatement.setString(5, admission.getTreatments());
            preparedStatement.setString(6, admission.getPrescriptions());
            preparedStatement.setInt(7, admission.getPatientID());
            preparedStatement.setInt(8, admission.getAssignedDoctor());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @param admission
     * @return
     */
    public static boolean updateAdmission(Admission admission)
    {
        String query = "UPDATE admissions SET diagnosis = ?, symptoms = ?, labTests = ?, labResult = ?, treatments = ?, prescriptions = ? WHERE patientID = ? AND doctorID = ?";
        try
        {
            PreparedStatement preparedStatement = DB.prepareStatement(query);
            preparedStatement.setString(1, admission.getDiagnosis());
            preparedStatement.setString(2, admission.getSymptoms());
            preparedStatement.setString(3, admission.getLabTests());
            preparedStatement.setString(4, admission.getLabResults());
            preparedStatement.setString(5, admission.getTreatments());
            preparedStatement.setString(6, admission.getPrescriptions());
            preparedStatement.setInt(7, admission.getPatientID());
            preparedStatement.setInt(8, admission.getAssignedDoctor());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}