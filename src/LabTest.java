/**
 * This class hold information of one lab test
 * lab test name and its result
 * Group YakkinTonkatsu
 * Create by   Nonthakorn Sukprom 60070503435
 *             Bhimapaka Thapanangkun 60070503447
 */
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
     * @return lab test name
     */
    public String getLabTestName()
    {
        return labTestName;
    }

    /**
     * @return result of the lab test
     */
    public String getResult()
    {
        return result;
    }
}
