import java.util.ArrayList;

public class TreatmentList {

    private ArrayList<String> treatments = new ArrayList<>();

    public TreatmentList()
    {

    }

    public void addTreatment(String treatment)
    {
        treatments.add(treatment);
    }

    public String getTreatment(int index)
    {
        return treatments.get(index);
    }

    public ArrayList<String> getTreatments()
    {
        return treatments;
    }

    public int getTreatmentsSize()
    {
        return treatments.size();
    }
}
