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
     * @throws SQLException In case there's an error in MySQL query
     */
    public static ResultSet userLogin(String username, String password) throws SQLException
    {
        int count = 0; //In case there are duplicate user
        String queryUsername = "username = \'" + username + "\'";
        String queryPassword = " AND password = \'" + password + "\';";
        String query = "SELECT userID,fName,lName,role FROM users WHERE " + queryUsername + queryPassword;
        //executing the selected query
        ResultSet userRS = stmt.executeQuery(query);

        while(userRS.next())
        {
            count++;
            String firstName = userRS.getString(2);
            String lastName = userRS.getString(3);
            //In case there are duplicate user, extreme case that could happen during user registration
            if(count > 1)
            {
                System.out.println("Error: found duplicate user, please contact admin for further analysis");
                System.exit(0);
            }
            System.out.println("Welcome " + firstName + " " + lastName);
        }
        //If user not found return null, this mean the username or password might be wrong
        if(count == 0)
        {
            System.out.println("Sorry: User not found, please try again");
            return null;
        }
        return userRS;
    }

    /**
     * Get all the staff registered in the database
     * @param role To specify the staff role that needed query
     * @return return doctors information in ResultSet format
     * @throws SQLException
     */
    public static ResultSet getStaffList(String role) throws SQLException
    {
        String queryDoctors = "SELECT userID, fName, lName FROM users WHERE role = \'" + role + "\';";
        ResultSet staffRS = stmt.executeQuery(queryDoctors);
        return staffRS;
    }

    /**
     * get all the patients registered within the database
     * @return patients information in ResultSet format
     * @throws SQLException
     */
    public static ResultSet getPatientList() throws SQLException
    {
        String queryPatients = "SELECT * FROM patients;";
        ResultSet patientRS = stmt.executeQuery(queryPatients);
        return patientRS;
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
    public static ResultSet getBills(boolean paid) throws SQLException
    {
        int count = 1;
        String queryBill = null;
        if(paid)
            queryBill = "SELECT * FROM bills WHERE payDate IS NOT NULL";
        else
            queryBill = "SELECT * FROM bills WHERE payDate IS NULL";
        ResultSet billRS = stmt.executeQuery(queryBill);
        return billRS;
    }
}