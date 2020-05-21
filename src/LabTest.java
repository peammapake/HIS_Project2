public class LabTest
{
    /**The method name of the lab test*/
    private String labTestName;

    /**The result of lab test*/
    private String result;

    /**
     * Constructor for LabTest
     * the object include lab test name and its result
     * @param test
     * @param result
     */
    public LabTest(String test, String result)
    {
        this.labTestName = test;
        this.result = result;
    }

    /**
     * getter method for labTestName attribute
     * @return  labTestName
     */
    public String getLabTestName()
    {
        return labTestName;
    }

    /**
     * getter method for result attribute
     * @return  result
     */
    public String getResult()
    {
        return result;
    }
}
