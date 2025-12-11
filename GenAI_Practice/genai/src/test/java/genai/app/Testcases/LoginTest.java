package genai.app.Testcases;

import genai.app.Base.TestBase;
import genai.app.Pages.*;
import genai.app.Pages.LoginPage;
import genai.app.Utils.waitUtils;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {
	LoginPage loginpage;
	HomePage homepage ;
	waitUtils wait ;
	
public LoginTest() throws IOException {
		super();
		
	}
	
@BeforeMethod
public void setup() throws InterruptedException, IOException {
	initialization();
	loginpage=new LoginPage();
}
    @Test
    public void testLogin() throws IOException {
    	
       
      loginpage.login("genai@gmail.com", "genai2025");

      homepage = new HomePage();
    
    }
    
    @AfterMethod
    public void tearDown() {
//    	driver.quit();
    }
}
