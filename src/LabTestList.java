import java.util.ArrayList;

public class LabTestList
{

    private ArrayList<LabTest> labTests = new ArrayList<>();

    public LabTestList()
    {

    }

    public String getLabTest(int index)
    {
        LabTest lt = labTests.get(index);
        return lt.getLabTestName() + " : " + lt.getResult();
    }

    public ArrayList<LabTest> getLabTests()
    {
        return labTests;
    }

    public int getLabTestSize()
    {
        return labTests.size();
    }
}
