package seleniumtest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
public class AuditGoogle {
	static ExtentTest test;
	static ExtentReports report;
	@BeforeClass
		public static void startTest()
		{
			report = new ExtentReports("target/"+"ExtentReport.html");
			test = report.startTest("ExtentDemo");
		}
	@Test
		public void extentReportsDemo() throws IOException, InterruptedException	{
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\venu.gopal.nistala\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
		    chromeDebug(); // Initiate chrome-debug on port 9222
			String newWindow = null;
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222"); // Initiate chrome driver on port 9222
			ChromeDriver driver = new ChromeDriver(options);
			String URL = "https://www.google.co.in";
			driver.get(URL);
//			driver.findElement(By.id("user-name")).sendKeys("standard_user");
//			driver.findElement(By.id("password")).sendKeys("secret_sauce");
//			driver.findElement(By.id("login-button")).click();
			if(driver.getTitle().equals("Google")) {
				test.log(LogStatus.PASS, "Navigated to the specified URL");
			}
			else{
				test.log(LogStatus.FAIL, "Test Failed");
			}
			URL = driver.getCurrentUrl();
			newWindow = windowOperations(driver); // Close the window to avoid conflict with Lighthouse window and in newWindow store the control of new empty window opened 
			lighthouseAudit(URL, "LightHouseAudit"); // Trigger Lighthouse audits on the page showing all Items for sale and generate a report by the name Items.html

			driver.quit();

		}
			private static String windowOperations(ChromeDriver driver) throws InterruptedException {
				String originalWindow = driver.getWindowHandle();
				driver.switchTo().newWindow(WindowType.TAB);
				String newWindow = driver.getWindowHandle();
				driver.switchTo().window(originalWindow);
				driver.close();
				return newWindow;
			}		
			private static void chromeDebug() throws IOException  {
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "chrome-debug --port=9222");
				builder.redirectErrorStream(true);
				Process p = builder.start();
			}
			private static void lighthouseAudit(String URL, String ReportName) throws IOException {
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "lighthouse", URL,"--only-categories=accessibility,performance,pwa", "--port=9222",
						"--preset=desktop", "--output=html", "--output-path=target/" + ReportName + ".html", "--view");
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while (true) {
					line = r.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				}
			}
	@AfterClass
		public static void endTest()	{
			report.endTest(test);
			report.flush();
		}

}