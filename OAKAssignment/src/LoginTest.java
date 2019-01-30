import java.io.FileInputStream;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTest {

	WebDriver driver;

	@BeforeClass
	@Parameters("Browser")
	public void configBC(String browser) throws Exception {
		// Check if parameter passed from TestNG is 'firefox'
		if (browser.equalsIgnoreCase("firefox")) {
			// create firefox instance
			System.setProperty("webdriver.firefox.marionette", ".\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		// Check if parameter passed as 'chrome'
		else if (browser.equalsIgnoreCase("chrome")) {
			// set path to chromedriver.exe
			System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
			// create chrome instance
			driver = new ChromeDriver();
		} else {
			// If no browser passed throw exception
			throw new Exception("Browser is not correct");
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}


	@AfterMethod()
	public void configAM() throws Exception {
		driver.findElement(By.xpath("//span[@class='gb_cb gbii']")).click(); 
		driver.findElement(By.id("gb_71")).click();

	}
	@AfterTest
	public void configAT() throws Exception {

		driver.close();

	}  



	@Test

	public void LoginTest() throws Throwable {


		driver.get("https://gmail.com");
		driver.findElement(By.id("identifierId")).click();
		FileInputStream fis = new FileInputStream(".\\TestData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet("Sheet1");
		Row row = null;
		String cellValueMaybeNull = null;

		Iterator<Row> rowIterator = sh.iterator(); // Traversing over each row
		// of XLSX file
		while (rowIterator.hasNext()) {
			Row row1 = rowIterator.next(); // For each row, iterate through each
			// columns
			Iterator<Cell> cellIterator = row1.cellIterator();
			String username = null;
			String password = null;
			if (row1.getRowNum() != 0) {
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getColumnIndex() == 1) {
						username = cell.getStringCellValue();
					} else if (cell.getColumnIndex() == 2) {
						password = cell.getStringCellValue();
					}
				}
				if (username == null) {
					break;
				}
				driver.findElement(By.id("identifierId")).sendKeys(username);
				driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
				Thread.sleep(3000);
				driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf']")).sendKeys(password);
				driver.findElement(By.xpath("//span[@class='RveJvd snByac']")).click();
			}
		}
	}





}




