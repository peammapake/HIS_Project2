import java.util.ArrayList;

public class PatientList
{
    private ArrayList<Patient> patients = new ArrayList<Patient>();

    public PatientList()
    {

    }

    public Patient getPatient(int index)
    {
        Patient patient = patients.get(index);
        return patient;
    }
    public int getSize()
    {
        return patients.size();
    }
}
