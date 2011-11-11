import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.thoughtworks.selenium.SeleniumException;


public class hf1 {
	// Declared global variables can be accessed from any method in the class.
	public static int xTCRows, xTCCols;
	public static int xTDRows, xTDCols; 
	public static String[][] xTCdata;
	public static String[][] xTDdata;
	public static int xTSRows, xTSCols; 
	public static String[][] xTSdata;
	public static String vBrowser, vURL, vText, vGetText, vAttribute, vEmail, vPswd;
	public static String vTechnology, vCity, vZIP;
	public static String vKeyword, vIP1, vIP2, vIP3;
	public static long vSleep;
	public static WebDriver myD = new HtmlUnitDriver();

	public static void main(String[] args) throws Exception {
		// My variables or Test Datas
		String eXPath;
		String xlPath;
		
		// Read the Test Steps XL sheet.
		xlPath = "C:/SeleniumData/Automation Plan 1.1.xls";
		xlTSRead(xlPath);
		xlTCRead(xlPath);
		xlTDRead(xlPath);

		// Intializing my data and objects
		vAttribute = "value";
		vSleep = 4000L;

		
		// Initializing my Browser that we wish to work with

		for (int k=1;k<xTDRows; k++){ // Go to each Test data row
			if (xTDdata[k][1].equals("Y")) { // check if the execute flag is Y 
			// Then load the variables
				vBrowser = xTDdata[k][3];
				vURL = xTDdata[k][2];
				vEmail = xTDdata[k][7];
				vPswd = xTDdata[k][6];
				vTechnology = xTDdata[k][10];
				vCity = xTDdata[k][8];
				vZIP = xTDdata[k][9];
				
				for (int j = 1; j<xTCRows; j++) { //Go to each row in the TC Sheet and see if the execute flag is Y
					if (xTCdata[j][4].equals("Y")) { //		If y then go to each row in TS Sheet 
						for (int i = 1; i <xTSRows; i++){
							if (xTCdata[j][1].equals(xTSdata[i][0])){	// and see if TCID's match
								System.out.print(xTSdata[i][0]);
								vKeyword = xTSdata[i][4];
								vIP1 = xTSdata[i][5];
								vIP2 = xTSdata[i][6];
								vIP3 = xTSdata[i][7];
								System.out.println("---" + vKeyword + "````" + vIP1 + "````" + vIP2  + "````" + vIP3);
								// Identify the variable names and assign corresponding values
								getIP();
								// Identify the keyword and execute the corresponding function
								try {
									keyword_executor();
								} 
								catch (NoSuchElementException e) {
									System.out.println("Send Keys failed");
									System.out.println("Error is " + e);
								}
							}
						}
					}
				}
			}
		}
			
	}

	public static void navigate_to(WebDriver mD, String fURL) {
		mD.navigate().to(fURL);
	}
	
	public static void send_keys(WebDriver mD, String fxPath, String fText) {
			mD.findElement(By.xpath(fxPath)).sendKeys(fText);
	}
	
	public static void click_element(WebDriver mD, String fxPath) {

		mD.findElement(By.xpath(fxPath)).click();


	}
	
	public static void click_link(WebDriver mD, String fText) {

		mD.findElement(By.linkText(fText)).click();

	}
	
	public static String get_text(WebDriver mD, String fxPath) {

		return mD.findElement(By.xpath(fxPath)).getText();
	}
	
	public static String verify_text(WebDriver mD, String fxPath, String fText) {

		String fTextOut = mD.findElement(By.xpath(fxPath)).getText();
		if (fTextOut.equals(fText)){
			return "Pass";
		} else {
			return "Fail";
		}


	}
	public static String element_present(WebDriver mD, String fxPath){
			if (mD.findElement(By.xpath(fxPath)).isDisplayed()){
				return "Pass";
			} else {
				return "Fail";
			}
	}
	
	public static String link_present(WebDriver mD, String fText){
			if (mD.findElement(By.linkText(fText)).isDisplayed()){
				return "Pass";
			} else {
				return "Fail";
			}


	}

	public static String get_attribute(WebDriver mD, String fxPath, String fAttribute) {
		return mD.findElement(By.xpath(fxPath)).getAttribute(fAttribute);
	}
	
	public static String verify_attribute(WebDriver mD, String fxPath, String fAttribute, String fText) {
		String fTextOut = mD.findElement(By.xpath(fxPath)).getAttribute(fAttribute);
		if (fTextOut.equals(fText)){
			return "Pass";
		} else {
			return "Fail";
		}
	}
	
	public static void close_browser(WebDriver mD){
		mD.close();
	}
	
	public static void wait_time(Long fWait) throws Exception{
		Thread.sleep(fWait);
	}
	
	public static void xlTSRead(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(2);	// Referring to 1st sheet
		xTSRows = mySheet.getLastRowNum()+1;
		xTSCols = mySheet.getRow(0).getLastCellNum();
		System.out.println("Rows are " + xTSRows);
		System.out.println("Cols are " + xTSCols);
		xTSdata = new String[xTSRows][xTSCols];
        for (int i = 0; i < xTSRows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xTSCols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xTSdata[i][j] = value;
	               }
	        }	
	}
	
	public static void xlTCRead(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(1);	// Referring to 1st sheet
		xTCRows = mySheet.getLastRowNum()+1;
		xTCCols = mySheet.getRow(0).getLastCellNum();
		System.out.println("Rows are " + xTCRows);
		System.out.println("Cols are " + xTCCols);
		xTCdata = new String[xTCRows][xTCCols];
        for (int i = 0; i < xTCRows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xTCCols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xTCdata[i][j] = value;
	               }
	        }	
	}
	
	public static void xlTDRead(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		HSSFSheet mySheet = myWB.getSheetAt(3);	// Referring to 4th sheet
		xTDRows = mySheet.getLastRowNum()+1;
		xTDCols = mySheet.getRow(0).getLastCellNum();
		System.out.println("Rows are " + xTDRows);
		System.out.println("Cols are " + xTDCols);
		xTDdata = new String[xTDRows][xTDCols];
        for (int i = 0; i < xTDRows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xTDCols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xTDdata[i][j] = value;
	               }
	        }	
	}
	
	public static String cellToString(HSSFCell cell) {
		// This function will convert an object of type excel cell to a string value
	        int type = cell.getCellType();
	        Object result;
	        switch (type) {
	            case HSSFCell.CELL_TYPE_NUMERIC: //0
	                result = cell.getNumericCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_STRING: //1
	                result = cell.getStringCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_FORMULA: //2
	                throw new RuntimeException("We can't evaluate formulas in Java");
	            case HSSFCell.CELL_TYPE_BLANK: //3
	                result = "-";
	                break;
	            case HSSFCell.CELL_TYPE_BOOLEAN: //4
	                result = cell.getBooleanCellValue();
	                break;
	            case HSSFCell.CELL_TYPE_ERROR: //5
	                throw new RuntimeException ("This cell has an error");
	            default:
	                throw new RuntimeException("We don't support this cell type: " + type);
	        }
	        return result.toString();
	    }
	public static void getIP(){
		if (vIP1.equals("vURL")){
			vIP1 = vURL;
		}
		if (vIP1.equals("vTechnology")){
			vIP1 = vTechnology;
		}
		if (vIP1.equals("vZIP")){
			vIP1 = vZIP;
		}
		if (vIP1.equals("vCity")){
			vIP1 = vCity;
		}
		if (vIP1.equals("vEmail")){
			vIP1 = vEmail;
		}
		if (vIP1.equals("vPswd")){
			vIP1 = vPswd;
		}
		if (vIP2.equals("vURL")){
			vIP2 = vURL;
		}
		if (vIP2.equals("vTechnology")){
			vIP2 = vTechnology;
		}
		if (vIP2.equals("vZIP")){
			vIP2 = vZIP;
		}
		if (vIP2.equals("vCity")){
			vIP2 = vCity;
		}
		if (vIP2.equals("vEmail")){
			vIP2 = vEmail;
		}
		if (vIP2.equals("vPswd")){
			vIP2 = vPswd;
		}
		if (vIP3.equals("vURL")){
			vIP3 = vURL;
		}
		if (vIP3.equals("vTechnology")){
			vIP3 = vTechnology;
		}
		if (vIP3.equals("vZIP")){
			vIP3 = vZIP;
		}
		if (vIP3.equals("vCity")){
			vIP3 = vCity;
		}
		if (vIP3.equals("vEmail")){
			vIP3 = vEmail;
		}
		if (vIP3.equals("vPswd")){
			vIP3 = vPswd;
		}
	}
	public static void keyword_executor() throws Exception {

		if (vKeyword.equals("navigate_to")){
			// Initializing my Browser that we wish to work with
			if (vBrowser.equals("IE")) {
				myD = new InternetExplorerDriver();
			}
			if (vBrowser.equals("FF")) {
				myD = new FirefoxDriver();
			}
			if (vBrowser.equals("HTML")) {
				myD = new HtmlUnitDriver();
			}
			navigate_to(myD, vIP1);
		}
		if (vKeyword.equals("send_keys")){
			send_keys(myD, vIP1, vIP2);
		}
		if (vKeyword.equals("click_element")){
			click_element(myD, vIP1);
		}
		if (vKeyword.equals("click_link")){
			click_link(myD, vIP1);
		}
		if (vKeyword.equals("get_text")){
			get_text(myD, vIP1);
		}
		if (vKeyword.equals("verify_text")){
			System.out.println("Text present " + vIP2 + " is a " + verify_text(myD, vIP1, vIP2));
		}
		if (vKeyword.equals("get_attribute")){
			get_attribute(myD, vIP1, vIP2);
		}
		if (vKeyword.equals("verify_attribute")){
			verify_attribute(myD, vIP1, vIP2, vIP3);
		}
		if (vKeyword.equals("close_browser")){
			close_browser(myD);
		}
		if (vKeyword.equals("wait_time")){
			wait_time(vSleep);
		}
		if (vKeyword.equals("element_present")){
			System.out.println("Element " + vIP1 + " is " + element_present(myD, vIP1));
		}
		if (vKeyword.equals("link_present")){
			System.out.println("Link " + vIP1 + " is " + link_present(myD, vIP1));
		}

	}
}
