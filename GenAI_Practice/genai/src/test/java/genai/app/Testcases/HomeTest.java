package genai.app.Testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import genai.app.Base.TestBase;
import genai.app.Pages.HomePage;
import genai.app.Pages.LoginPage;
import genai.app.Pages.ProductPage;

public class HomeTest extends TestBase{
	LoginPage loginpage;
	HomePage homepage ;
	ProductPage productpage;
	
public HomeTest() throws IOException {
		super();
		
	}
	
@BeforeMethod
public void setup() throws InterruptedException, IOException {
	initialization();
	loginpage=new LoginPage();
	homepage = new HomePage();
}
    @Test
    public void testReadMenu() throws IOException {
       
      loginpage.login("genai@gmail.com", "genai2025");       
      homepage.getMenuItemNames();
      homepage.printMenuItemsAndCount();
      homepage.clickBuildYourOwnExpensiveComputer();
     
    
    }
    @AfterMethod
    public void tearDown() {
//    	driver.quit();
    }
}


