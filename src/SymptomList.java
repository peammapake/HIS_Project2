import java.util.ArrayList;

public class SymptomList {

    private ArrayList<String> symptoms = new ArrayList<>();

    public SymptomList()
    {

    }
    public void addSymptom(String symptom)
    {
        symptoms.add(symptom);
    }

    public String getSymptom(int index)
    {
        return symptoms.get(index);
    }

    public ArrayList<String> getSymptoms()
    {
        return symptoms;
    }

    public int getSymptomSize()
    {
        return symptoms.size();
    }
}
