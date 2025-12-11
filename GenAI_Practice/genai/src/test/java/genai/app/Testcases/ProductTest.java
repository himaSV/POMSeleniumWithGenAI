package genai.app.Testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import genai.app.Base.TestBase;
import genai.app.Pages.HomePage;
import genai.app.Pages.LoginPage;
import genai.app.Pages.ProductPage;

public class ProductTest extends TestBase {
	LoginPage loginpage;
	HomePage homepage ;
	ProductPage productpage;
	
public ProductTest() throws IOException {
		super();
		
	}
	
@BeforeMethod
public void setup() throws InterruptedException, IOException {
	initialization();
	loginpage=new LoginPage();
	homepage = new HomePage();
	productpage=new ProductPage();
}
    @Test
    public void testAddToCart() throws IOException {
       
      loginpage.login("genai@gmail.com", "genai2025");     
      homepage.getMenuItemNames();
      homepage.printMenuItemsAndCount();
      homepage.clickBuildYourOwnExpensiveComputer();
      productpage.selectProcessor("Slow");
      productpage.selectRAM("2 GB");
      productpage.selectHDD("320 GB");
      productpage.selectSoftwareByValue("Office Suite [+100.00]");
      productpage.setQty(2);
      productpage.clickAddToCart();
      productpage.goToCart();
      
    }
    @AfterMethod
    public void tearDown() {
//    	driver.quit();
    }
}


