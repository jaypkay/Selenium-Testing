
import com.thoughtworks.selenium.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class mc3 extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		//System.out.println("this is the 1st line in @Before");
		//myPrint();
		//myPrint2("myprint2 test");
		myPrint2("@Before");
		// creating an object of type selenium server and storing it in a new object named selenium
		selenium = new DefaultSelenium("localhost", 1235, "*chrome", "http://www.mortgagecalculator.org/");
		selenium.start(); // launching and starting our session for testing
	}

	@Test
	public void testMc1() throws Exception {
		//System.out.println("this is the 1st line in @Test");
		//myPrint();
		String vLoan = "575000";
		String vCredit = "label=Good";
		String vTerm = "24";
		String vRate = "5";
		System.out.println("loan value is " + vLoan);
		myPrint2("@Test");
		// Visit the 1st App
		selenium.open("http://www.mortgagecalculator.org/");
		//selenium.type("param[homevalue]", "");
		selenium.select("param[credit]", vCredit);
		selenium.type("param[principal]", vLoan);
		selenium.select("param[rp]", "New Purchase");
		selenium.type("param[term]", vTerm);
		selenium.click("//input[@type='submit']");
		selenium.waitForPageToLoad("10000");
		String mc1 = selenium.getText("css=td > h3");
		System.out.println(mc1);
		selenium.open("http://www.mortgagecalculatorplus.com/");
		selenium.select("param[credit]", vCredit);
		selenium.type("param[principal]", vLoan);
		selenium.type("param[interest_rate]", vRate);
		selenium.type("param[term]", "24");
		selenium.click("//input[@type='submit']");
		selenium.waitForPageToLoad("30000");
		String mc2 = selenium.getText("css=h3");
		System.out.println(mc2);
	
	}

	@After
	public void tearDown() throws Exception {
		//System.out.println("this is the 1st line in @After");
		//myPrint();
		myPrint2("@After");
		selenium.stop();
	}
	
	public void myPrint(){
		System.out.println("My print function does this");
	}
	
	public void myPrint2(String fMsg){
		//System.out.println(fMsg);
		System.out.println("This is the 1st line in " + fMsg);
	}
}