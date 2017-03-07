/**
 * 
 */
package data.service;

import java.util.Calendar;

import com.Eureka.www.AppTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * @author Administrator
 *
 */
public class IDataInterfaceTest extends TestCase {
	private IDataInterface data;
	/**
	 * @param name
	 */
	public IDataInterfaceTest(String name) {
		super(name);
		data=new DataInterfaceImpl();
	}
	
	/**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( IDataInterfaceTest.class );
    }
    public void testsignUp(){
    	assertEquals(false, data.signUpCheck("   lyx", "lyxzzz"));
    	assertEquals(false, data.signUpCheck("!lyx", "lyxzzz"));
    	assertEquals(true, data.signUpCheck("lyx", "lyxzzz"));
    	assertEquals(true, data.signUpCheck("lyx123", "lyxzzz"));
    	assertEquals(false, data.signUpCheck("lyx", "lyxzzz"));	
    	assertEquals(true, data.signUpCheck("Lyx", "lyx123456"));
    }
    public void testsignIn(){
    	assertEquals(false, data.signInCheck("lYx", "lyxzzz"));
    	assertEquals(false, data.signInCheck("ly!x", "ly"));
    	assertEquals(false, data.signInCheck("lyx", "ly"));
    	assertEquals(true, data.signInCheck("Lyx", "lyx123456"));
    	assertEquals(true, data.signInCheck("lyx", "lyxzzz"));
    	assertEquals(false, data.signInCheck("lyx", "lyxz"));
    	assertEquals(false, data.signInCheck("lyx", "  lyxz"));
    	assertEquals(false, data.signInCheck("lyx", "!lyxz"));
    	assertEquals(false, data.signInCheck("lyx", "lyxzZz"));
    }
    public void testgetSingleStockInfo(){
    	Calendar day1=Calendar.getInstance();
    	Calendar day2=Calendar.getInstance();
    	Calendar day3=Calendar.getInstance();
    	Calendar day4=Calendar.getInstance();
    	Calendar day5=Calendar.getInstance();
    	day1.set(2005, 1, 1);
    	day2.set(2006, 3, 2);
    	day3.set(2007, 5, 26);
    	day4.set(2010, 7, 3);
    	day5.set(2014, 4, 29);
//    	data.getSingleStockInfo(stockcode, begin, end)
    }
}
