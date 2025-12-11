package genai.app.Base;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

public class TestBase {
	
	public static WebDriver driver;
	public static Properties prop;
	
	
	public TestBase() throws IOException {
		FileInputStream fis=new FileInputStream("C:/Users/HIMA/eclipse-workspace/GenAI_Practice/genai/src/main/java/genai/app/Config/Config.properties");
		prop=new Properties();
		prop.load(fis);	
		
	}
	public static void initialization() {		
			String browserName=prop.getProperty("browser");
			
			if(browserName.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver","C:/Users/HIMA/Downloads/chromedriver-win64/chromedriver-win64/chromedriver.exe");
				driver=new ChromeDriver();			
				
				
			}
			else if (browserName.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver","//User/hima/Downloads/geckodriver");
				driver=new FirefoxDriver();
				
			}
			else
				System.out.println("No such browser name :"+browserName);
			
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();		
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(prop.getProperty("PAGE_LOAD_TIMEOUT"))));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("IMPLICITWAIT_TIMEOUT"))));
			driver.get(prop.getProperty("url"));
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Integer.parseInt(prop.getProperty("PAGE_LOAD_TIMEOUT"))));
		
		
	}
	
	public static WebDriver getDriver(){
		return driver;
	}
	
	
}
