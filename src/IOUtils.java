import java.io.*;
import java.util.Date;
import java.text.SimpleDateFormat;

/** 
 * Class that provides static functions for doing terminal input
 *
 *  Created by Sally Goldin, 30 April 2014, for CPE113
 *  Updated comments, 30 Dec 2019
 *  Use to simplify Java lab exercises.
 *  Fixed bug in handline bare newline in getBareString - 18 Jan 2019
 */
public class IOUtils
{
    /**
     * Check for both Windows and Linux terminators. Remove both
     * \r and \n
     * @param fullString    String from the keyboard
     * @return string without terminators
     */
    private static String stripTerminators(String fullString)
    {
       int pos = fullString.indexOf("\r");
       if (pos < 0) // no carriage return
           pos = fullString.indexOf("\n"); // check linefeed
       if (pos >= 0)  // if either terminator
	   fullString = fullString.substring(0,pos);
           // chop off the terminator(s)
       return fullString;
    }
    /**
     * Asks for a string and returns it as the value of the function
     * @param   prompt    String to print, asking a question
     * @return  The string the user entered (maximum 100 chars long) 
     */
    public static String getString(String prompt)
    {
       String inputString;
       int readBytes = 0;
       byte buffer[] = new byte[200]; 
       System.out.println(prompt);
       try
       {
           readBytes = System.in.read(buffer,0,200);
       }
       catch (IOException ioe)
       {
	   System.out.println("Input/output exception - Exiting");
	   System.exit(1);
       }
       inputString = new String(buffer);
       inputString = stripTerminators(inputString);
       return inputString;
    }


    /**
     * Asks for an integer and returns it as the value of the function
     * @param   prompt    String to print, asking a question
     * @return value entered. If not an integer, prints an error message
     * and returns -999  
     */
    public static int getInteger(String prompt)
    {
       int value = -999;	   
       String inputString;
       int readBytes = 0;
       byte buffer[] = new byte[200]; 
       System.out.println(prompt);
       try
       {
           readBytes = System.in.read(buffer,0,200);
       }
       catch (IOException ioe)
       {
	   System.out.println("Input/output exception - Exiting");
	   System.exit(1);
       }
       inputString = new String(buffer);
       inputString = stripTerminators(inputString);
       try 
       {
           value = Integer.parseInt(inputString);
       }
       catch (NumberFormatException nfe) 
       {
	   System.out.println("Bad number entered");
       }
       return value;
    }

    /**
     * Reads a string and returns it as the value of the function,
     * without any prompt. Remove the newline before returning.
     * @return  The string the user entered (maximum 100 chars long) 
     */
    public static String getBareString()
    {
       String inputString;
       int readBytes = 0;
       byte buffer[] = new byte[200]; 
       try
       {
           readBytes = System.in.read(buffer,0,200);
       }
       catch (IOException ioe)
       {
	   System.out.println("Input/output exception - Exiting");
	   System.exit(1);
       }
       inputString = new String(buffer);
       inputString = stripTerminators(inputString);
       return inputString;
    }


    /** 
     *  Creates and returns a string with the current date
     *  and time, to use as a time stamp.
     * @return date/time string in the form "yyyy-mm-dd hh:mm:ss"
     */
    public static String getDateTime()
    {
	Date now = new Date();
	SimpleDateFormat formatter = 
              new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	return formatter.format(now);
    }
}
