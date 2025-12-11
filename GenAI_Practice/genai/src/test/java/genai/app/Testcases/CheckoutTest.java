package genai.app.Testcases;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import genai.app.Base.TestBase;
import genai.app.Pages.CheckoutPage;
import genai.app.Pages.HomePage;
import genai.app.Pages.LoginPage;
import genai.app.Pages.ProductPage;

public class CheckoutTest extends TestBase {
	
	LoginPage loginpage;
	HomePage homepage ;
	ProductPage productpage;
	CheckoutPage checkoutpage;


public CheckoutTest() throws IOException {
	super();
	
}

@BeforeMethod
public void setup() throws InterruptedException, IOException {
		initialization();
		loginpage=new LoginPage();
		homepage = new HomePage();
		productpage=new ProductPage();
		checkoutpage=new CheckoutPage();
}

   @Test
public void checkout() throws IOException {
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
	   checkoutpage.selectBillingCountry("India");
	   checkoutpage.agreeTerms();
	   checkoutpage.clickCheckout();
	   checkoutpage.selectShippingMethod("India");
	   checkoutpage.continueBilling();
	   
	   checkoutpage.continueShippingMethod();
	   checkoutpage.continuePaymentMethod();
	   checkoutpage.continuePaymentInfo();	   
	   checkoutpage.continueConfirmOrder();
	   checkoutpage.clickConfirmButton();
	   checkoutpage.getOrderConfirmationMessage();
//	   checkoutpage.getOrderNumber();

}
   @AfterMethod
   public void tearDown() {
//   	driver.quit();
   }
}