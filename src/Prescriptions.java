import java.util.ArrayList;

public class Prescriptions
{
    private ArrayList<String> medicines = new ArrayList<>();

    public Prescriptions()
    {

    }

    public String getMedicine(int index)
    {
        return medicines.get(index);
    }

    public ArrayList<String> getMedicines()
    {
        return medicines;
    }

    public int getPrescriptionSize()
    {
        return medicines.size();
    }
}
