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
     * @return
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
            preparedStatement.setInt(6,patient.getPhone());
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}