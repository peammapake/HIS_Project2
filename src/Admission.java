import java.util.Date;

public class Admission {
    /**Date of admission*/
    private Date admitDate;
    /**List of symptoms in checkup*/
    private SymptomList symptoms = new SymptomList();
    /**List of treatment*/
    private TreatmentList treatments = new TreatmentList();
    /**List of lab test*/
    private LabTestList labTests = new LabTestList();
    /**List of medicine prescribe to this patient*/
    private Prescriptions prescriptions = new Prescriptions();
    /**Doctor's diagnosis of this patient checkup*/
    private String diagnosis;
    /**Name of assigned doctor*/
    private String assignedDoctor;

    /**
     * Constructor
     */
    public Admission()
    {

    }


    public Date getAdmitDate() {
        return admitDate;
    }

    public SymptomList getSymptoms()
    {
        return symptoms;
    }

    public String getSymptom(int index)
    {
        return symptoms.getSymptom(index);
    }

    public TreatmentList getTreatments()
    {
        return treatments;
    }

    public LabTestList getLabTests()
    {
        return labTests;
    }

    public Prescriptions getPrescriptions()
    {
        return prescriptions;
    }

    public String getDiagnosis()
    {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis)
    {
        this.diagnosis = diagnosis;
    }

    public String getAssignedDoctor()
    {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor doctor)
    {
        assignedDoctor = doctor.getFullName();
    }

}
