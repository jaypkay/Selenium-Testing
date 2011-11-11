
import com.thoughtworks.selenium.*;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Pattern;

public class mc6 extends SeleneseTestCase {
	public int xRows, xCols;
	public String xData[][];  // creates an empty 2-d array named xData
	@Before
	public void setUp() throws Exception {
		//System.out.println("this is the 1st line in @Before");
		//myPrint();
		//myPrint2("myprint2 test");
		//myPrint2("@Before");
		// creating an object of type selenium server and storing it in a new object named selenium
		selenium = new DefaultSelenium("localhost", 1234, "*chrome", "http://www.mortgagecalculator.org/");
		selenium.start(); // launching and starting our session for testing
		String xPath = "C:/SeleniumData/mc-data.xls";
		xlRead(xPath); // 
	}
	@Test
	public void testMc1() throws Exception {
		String vLoan, vCredit, vTerm, vRate, vTax, vPMI, vExecute;
		int i;
				
		for (i=1; i<xRows; i=i+1){
			vLoan = xData[i][0];
			vCredit = "label=Good";
			vTerm = xData[i][1];
			vRate = xData[i][2];
			vTax = xData[i][5];
			vPMI = xData[i][6];
			vExecute = xData[i][10];
			if (vExecute.equals("Y")) {
			System.out.println("Loan is " + vLoan);
			System.out.println("Term is " + vTerm);
			System.out.println("Rate is " + vRate);
			System.out.println("Tax is " + vTax);
			System.out.println("PMI is " + vPMI);
			//int vRatei = Integer.parseInt(vRate) - 1;
			//vRate = Integer.toString((Integer.parseInt(vRate) - 1));
			selenium.open("http://www.mortgagecalculator.org/");
			selenium.type("param[principal]", vLoan);
			selenium.type("param[interest_rate]", vRate);
			selenium.type("param[term]", vTerm);
			selenium.type("param[property_tax]", vTax);
			selenium.type("param[pmi]", vPMI);
			selenium.click("//input[@type='submit']");
			selenium.waitForPageToLoad("10000");
			String mc1 = selenium.getText("css=td > h3");
			System.out.println("App1 o/p is " + mc1);
			
			selenium.open("http://www.mortgagecalculatorplus.com/");
			selenium.type("param[principal]", vLoan);
			selenium.type("param[interest_rate]", vRate);
			selenium.type("param[term]", vTerm);
			selenium.type("param[property_tax]", vTax);
			selenium.type("param[pmi]", vPMI);
			selenium.click("//input[@type='submit']");
			selenium.waitForPageToLoad("30000");
			String mc2 = selenium.getText("css=h3");
			System.out.println("App2 o/p is " + mc2);
			
			String vResult = myCompare(mc1,mc2); 
			System.out.println("Row # " + i + " Result is " + vResult);
			}
			else {
				System.out.println("Row # " + i + " not executed.");
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
	
	public void myPrint(){
		System.out.println("My print function does this");
	}
	
	public void myPrint2(String fMsg){
		System.out.println("This is the 1st line in " + fMsg);
	}
	
	public String myCompare(String txt1, String txt2){
		//if(txt1==txt2){
		if(txt1.equals(txt2)){
			//System.out.println("Test is a pass");
			return "Pass";
	
		} else {
			//System.out.println("Test is a fail");
			return "Fail";
		}
	}
	
	public void xlRead(String sPath) throws Exception{
		File myxl = new File(sPath);
		FileInputStream myStream = new FileInputStream(myxl);
		
		HSSFWorkbook myWB = new HSSFWorkbook(myStream);
		//HSSFSheet mySheet = new HSSFSheet(myWB);
		//HSSFSheet mySheet = myWB.getSheetAt(0);	// Referring to 1st sheet
		HSSFSheet mySheet = myWB.getSheetAt(2);	// Referring to 3rd sheet
		//int xRows = mySheet.getLastRowNum()+1;
		//int xCols = mySheet.getRow(0).getLastCellNum();
		xRows = mySheet.getLastRowNum()+1;
		xCols = mySheet.getRow(0).getLastCellNum();
		System.out.println("Rows are " + xRows);
		System.out.println("Cols are " + xCols);
		//String[][] xData = new String[xRows][xCols];
		xData = new String[xRows][xCols];
        for (int i = 0; i < xRows; i++) {
	           HSSFRow row = mySheet.getRow(i);
	            for (int j = 0; j < xCols; j++) {
	               HSSFCell cell = row.getCell(j); // To read value from each col in each row
	               String value = cellToString(cell);
	               xData[i][j] = value;
	               //System.out.print(value);
	               //System.out.print("@@");
	               }
	            //System.out.println("");
	            
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
	
}