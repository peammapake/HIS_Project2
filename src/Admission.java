import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class Admission {

    /**ID of the current admission*/
    private int admitID;

    /**Date of admission*/
    private Timestamp admitDate;

    /**Date of discharge, should be null if not discharge from hospital yet*/
    private Timestamp dischargeDate;

    /**Doctor's diagnosis of this patient checkup*/
    private String diagnosis;

    /**List of symptoms in checkup*/
    private ArrayList<String> symptomList = null;

    /**List of lab test*/
    private ArrayList<LabTest> labTestList = new ArrayList<LabTest>();

    /**List of treatments*/
    private ArrayList<String> treatmentList = null;

    /**List of medicine prescribe to this patient*/
    private ArrayList<String> prescriptionList = null;

    /**ID of the assigned doctor*/
    private int doctorID;

    /**Patient ID*/
    private int patientID;

    /**
     * Constructor for admission
     * @param admission ResultSet pointing at the row that needs for constructing new admission
     */
    public Admission(ResultSet admission) throws SQLException
    {
        admitID = admission.getInt(1);
        admitDate = admission.getTimestamp(2);
        diagnosis = admission.getString(4);
        symptomList = new ArrayList(Arrays.asList(admission.getString(5).split("\\|")));
        String[] labTest = admission.getString(6).split("\\|");
        String[] labResult = admission.getString(7).split("\\|");
        treatmentList =  new ArrayList(Arrays.asList(admission.getString(8).split("\\|")));
        prescriptionList = new ArrayList(Arrays.asList(admission.getString(9).split("\\|")));
        doctorID = admission.getInt(10);
        patientID = admission.getInt(11);
        //Check in case some lab test lack result
        if(labTest.length == labResult.length)
        {
            for(int i = 0; i < labTest.length; i++)
            {
                addLabTest(labTest[i],labResult[i]);
            }
        }
        else
            System.out.println("Error: patientID: " + patientID
                    + " Lab test list is incomplete, Please contact administrator for further inspection");

    }


    public void printAdmission()
    {
        System.out.println("-----------------------------------------------------------------------------------");
        System.out.println("Admission ID: " + admitID + " PatientID: " + patientID);
        System.out.println("Admit Datetime: " + admitDate);
        if(dischargeDate != null)
            System.out.println("Discharge Datetime: " + dischargeDate);
        System.out.println("Condition Diagnosis: " + diagnosis);
        System.out.println("Patient Symptoms: ");
        for (String symptom: symptomList)
            System.out.println("\t- " + symptom);
        System.out.println("Lab Test and Result:");
        for(LabTest lab: labTestList)
            System.out.println("\t- " + lab.getLabTestName() + " : " + lab.getResult());
        System.out.println("Treatment: ");
        for(String treatment: treatmentList)
            System.out.println("\t- " + treatment);
        System.out.println("Medicine Prescription");
        for (String prescription: prescriptionList)
            System.out.println("\t- " + prescription);
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
    public Timestamp getAdmitDate()
    {
        return admitDate;
    }

    public Timestamp getDischargeDate()
    {
        return dischargeDate;
    }

    public ArrayList<String> getSymptoms()
    {
        return symptomList;
    }

    public ArrayList<String> getTreatments()
    {
        return treatmentList;
    }

    public ArrayList<LabTest> getLabTests()
    {
        return labTestList;
    }

    public ArrayList<String> getPrescriptions()
    {
        return prescriptionList;
    }

    public String getDiagnosis()
    {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    public int getAssignedDoctor()
    {
        return doctorID;
    }

}
