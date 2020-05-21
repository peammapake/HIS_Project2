import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Admission record of patient
 * hold information of one certain admission of patient
 * Create by   Nonthakorn Sukprom 60070503435
 *             Bhimapaka Thapanangkun 60070503447
 */
public class Admission {

    /**ID of the current admission*/
    private int admitID;

    /**Date of admission*/
    private Timestamp admitDate;

    /**Date of discharge, should be null if not discharge from hospital yet*/
    private Timestamp dischargeDate;

    /**Doctor's diagnosis of this patient checkup*/
    private String diagnosis = "";

    /**List of symptoms in checkup*/
    private ArrayList<String> symptomList = new ArrayList<String>();

    /**List of lab test*/
    private ArrayList<LabTest> labTestList = new ArrayList<LabTest>();

    /**List of treatments*/
    private ArrayList<String> treatmentList = new ArrayList<String>();

    /**List of medicine prescribe to this patient*/
    private ArrayList<String> prescriptionList = new ArrayList<String>();

    /**Patient ID*/
    private int patientID;

    /**ID of the assigned doctor*/
    private int doctorID;

    /**
     * Constructor for admission using resultset given from database query
     * @param admission ResultSet pointing at the row that needs for constructing new admission
     */
    public Admission(ResultSet admission) throws SQLException
    {
        admitID = admission.getInt(7);
        admitDate = admission.getTimestamp(8);
        diagnosis = admission.getString(10);
        if(admission.getString(11) != null)
            symptomList = new ArrayList(Arrays.asList(admission.getString(11).split("\\|")));
        if(admission.getString(14) != null)
            treatmentList =  new ArrayList(Arrays.asList(admission.getString(14).split("\\|")));
        if(admission.getString(15) != null)
            prescriptionList = new ArrayList(Arrays.asList(admission.getString(15).split("\\|")));
        patientID = admission.getInt(16);
        doctorID = admission.getInt(17);

        String testString = admission.getString(12);
        String resultString = admission.getString(13);

        if((testString!=null)&&(resultString!=null))
        {
            String[] labTest = testString.split("\\|");
            String[] labResult = resultString.split("\\|");
            //Check in case some lab test or result is incomplete
            if (labTest.length == labResult.length)
            {
                for (int i = 0; i < labTest.length; i++)
                {
                    addLabTest(labTest[i], labResult[i]);
                }
            }
            else
                System.out.println("Error: patientID: " + patientID + " Lab test list is incomplete, Please contact administrator for further inspection");
        }
    }

    /**
     * Overloading Admission constructor to be able to accept creating of new
     * admission of a patient
     * @param doctorID ID of the doctor responsible
     * @param patientID ID of the patient to take admission
     */
    public Admission(int doctorID, int patientID)
    {
        symptomList = new ArrayList<String>();
        treatmentList = new ArrayList<String>();
        prescriptionList = new ArrayList<String>();
        labTestList = new ArrayList<LabTest>();
        this.patientID = patientID;
        this.doctorID = doctorID;
    }

    /**
     * Print out all information hold by this admission record
     */
    public void printAdmission()
    {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Admission ID: " + admitID + " PatientID: " + patientID);
        System.out.println("Admit Datetime: " + admitDate);
        if(dischargeDate != null)
            System.out.println("Discharge Datetime: " + dischargeDate);
        if(diagnosis != null)
            System.out.println("Condition Diagnosis: " + diagnosis);
        if(symptomList != null)
        {
            System.out.println("Patient Symptoms: ");
            for (String symptom : symptomList)
                System.out.println("\t- " + symptom);
        }
        if(labTestList != null)
        {
            System.out.println("Lab Test and Result:");
            for (LabTest lab : labTestList)
                System.out.println("\t- " + lab.getLabTestName() + " : " + lab.getResult());
            System.out.println("Treatment: ");
            for (String treatment : treatmentList)
                System.out.println("\t- " + treatment);
            System.out.println("Medicine Prescription");
        }
        if(prescriptionList != null)
        {
            for (String prescription : prescriptionList)
                System.out.println("\t- " + prescription);
        }
        System.out.println("-----------------------------------------------------------------------------------");
    }

    /**
     * Add new symptom to the symptomList string list
     * @param symptom string of the new symptom to add
     */
    public void addSymptom(String symptom)
    {
        symptomList.add(symptom);
    }

    /**
     * Method to add new lab test and result to the labTestList of the current instance
     * @param test Lab test method name
     * @param result Result of the lab test
     */
    public void addLabTest(String test, String result)
    {
        labTestList.add(new LabTest(test,result));
    }

    /**
     * Add new treatment to treatmentList
     * @param treatment treatment string to add to the list
     */
    public void addTreatment(String treatment)
    {
        treatmentList.add(treatment);
    }

    /**
     * Add new prescription to prescriptionList
     * @param prescription new medical prescription string to add
     */
    public void addPrescription(String prescription)
    {
        prescriptionList.add(prescription);
    }

    //Getter/Setter zone------------------------------------------------------------------------------------------------

    /**
     * @return admission ID
     */
    public int getAdmitID()
    {
        return admitID;
    }

    /**
     * @return admit date
     */
    public Timestamp getAdmitDate()
    {
        return admitDate;
    }

    /**
     * @return discharge date
     */
    public Timestamp getDischargeDate()
    {
        return dischargeDate;
    }

    /**
     * Return list of symptom record in this admission as plain string
     * separated by "|"
     * @return  list of symptoms, null if empty
     */
    public String getSymptoms()
    {
        String symptoms = "";
        if(symptomList == null)
            return null;
        for(int i = 0; i < symptomList.size(); i++)
        {
            symptoms += symptomList.get(i);
            if(i < symptomList.size() - 1)
                symptoms += "|";
        }
        return symptoms;
    }

    /**
     * Return list of treatment record in this admission as plain string
     * separated by "|"
     * @return  list of treatment, null if empty
     */
    public String getTreatments()
    {
        String treatments = "";
        if(treatmentList == null)
            return null;
        for(int i = 0; i < treatmentList.size(); i++)
        {
            treatments += treatmentList.get(i);
            if(i < treatmentList.size() - 1)
                treatments += "|";
        }
        return  treatments;
    }

    /**
     * Return list of lab test record in this admission as plain string
     * separated by "|"
     * @return  list of lab test, null if empty
     */
    public String getLabTests()
    {
        String labTests = "";
        if(labTestList == null)
            return null;
        for(int i = 0; i < labTestList.size(); i++)
        {
            labTests += labTestList.get(i).getLabTestName();
            if(i < labTestList.size() - 1)
                labTests += "|";
        }
        return  labTests;
    }

    /**
     * Return list of lab test result record in this admission as plain string
     * separated by "|"
     * @return  list of lab test result, null if empty
     */
    public String getLabResults()
    {
        String labResults = "";
        if(labTestList == null)
            return null;
        for(int i = 0; i < labTestList.size(); i++)
        {
            labResults += labTestList.get(i).getResult();
            if(i < labTestList.size() - 1)
                labResults += "|";
        }
        return labResults;
    }

    /**
     * Return list of prescription record in this admission as plain string
     * separated by "|"
     * @return  list medicine in prescription, null if empty
     */
    public String getPrescriptions()
    {
        String prescriptions = "";
        if(prescriptionList == null)
            return null;
        for(int i = 0; i < prescriptionList.size(); i++)
        {
            prescriptions += prescriptionList.get(i);
            if(i < prescriptionList.size() - 1)
                prescriptions += "|";
        }
        return prescriptions;
    }

    /**
     * @return diagnosis
     */
    public String getDiagnosis()
    {
        return diagnosis;
    }

    /**
     * @return doctorID
     */
    public int getAssignedDoctor()
    {
        return doctorID;
    }

    /**
     * @return patientID
     */
    public int getPatientID()
    {
        return patientID;
    }

    /**
     * set diagnosis
     * @param diagnosis new diagnosis
     */
    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }
}
