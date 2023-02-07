package seleniumtest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
public class MainClass {
	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\venu.gopal.nistala\\Downloads\\chromedriver_win32 (2)\\chromedriver.exe");
//		options = webdriver.ChromeOptions();
//		options.add_argument("start-maximized");
//		driver = webdriver.Chrome(executable_path='/home/myname/projects/myproject/chromedriver', options=options)
		WebDriver driver = new ChromeDriver();
		String baseUrl = "https://www.lcbo.com";
        String expectedTitle = "Welcome";
        String actualTitle = "";
        driver.get(baseUrl);
        actualTitle = driver.getTitle();
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
            System.out.println(actualTitle);
        }
        driver.close();
        System.exit(0);	}
}
