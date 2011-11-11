package com.eviltester.seleniumtutorials;

public class MyFirstSeleniumTests {

}

package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;

public class SeleniumJUnitTest extends SeleneseTestCase {
	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "http://www.youtube.com/");
		selenium.start();
	}

	@Test
	public void testSeleniumJUnit() throws Exception {
		selenium.open("/results?search_query=selenium+ide&aq=f");
		selenium.type("id=masthead-search-term", "selenium ide 1.3.0");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=The Grails Framework 1.3.7");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='table-of-content']/div[4]/a/span");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
