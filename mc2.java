
import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class mc2 extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		//System.out.println("this is the 1st line in @Before");
		//myPrint();
		//myPrint2("myprint2 test");
		myPrint2("@Before");
	}

	@Test
	public void testMc1() throws Exception {
		//System.out.println("this is the 1st line in @Test");
		//myPrint();
		myPrint2("@Test");
	
	}

	@After
	public void tearDown() throws Exception {
		//System.out.println("this is the 1st line in @After");
		//myPrint();
		myPrint2("@After");
	}
	
	public void myPrint(){
		System.out.println("My print function does this");
	}
	
	public void myPrint2(String fMsg){
		//System.out.println(fMsg);
		System.out.println("This is the 1st line in " + fMsg);
	}
}