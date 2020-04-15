import java.util.ArrayList;

public class Bill
{
    Patient patient = null;
    private ArrayList<String> treatment = new ArrayList<String>();
    private ArrayList<String> labTest = new ArrayList<String>();
    private ArrayList<String> prescription = new ArrayList<String>();

    public String getPatientName()
    {
        return null;//template
    }

    public ArrayList<String> getTreatments()
    {
        return treatment;
    }

    public ArrayList<String> getLabTest()
    {
        return labTest;
    }

    public ArrayList<String> getPrescription()
    {
        return prescription;
    }

    public void printBill()
    {

    }
}
