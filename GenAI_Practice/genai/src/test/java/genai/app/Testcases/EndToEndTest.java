package genai.app.Testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import genai.app.Base.TestBase;
import genai.app.Pages.CheckoutPage;
import genai.app.Pages.HomePage;
import genai.app.Pages.LoginPage;
import genai.app.Pages.ProductPage;

public class EndToEndTest extends TestBase {
    LoginPage loginpage;
    HomePage homepage ;
    ProductPage productpage;
    CheckoutPage checkoutpage;

    public EndToEndTest() throws IOException {
        super();
    }

    @BeforeMethod
    public void setup() throws InterruptedException, IOException {
        initialization();
        loginpage = new LoginPage();
        homepage = new HomePage();
        productpage = new ProductPage();
        checkoutpage = new CheckoutPage();
    }

    @Test
    public void testCheckoutFlow() throws IOException, InterruptedException {
        // Login
        boolean logged = loginpage.login("genai@gmail.com", "genai2025");
        Assert.assertTrue(logged, "Login failed");

        // Navigate to a product and add to cart (reuse existing helper flows)
        homepage.getMenuItemNames();
        homepage.printMenuItemsAndCount();
        homepage.clickBuildYourOwnExpensiveComputer();
        productpage.selectProcessor("Slow");
        productpage.selectRAM("2 GB");
        productpage.selectHDD("320 GB");
        productpage.selectSoftwareByValue("Office Suite [+100.00]");
        productpage.setQty(1);
        productpage.clickAddToCart();

        // Go to cart and start checkout
        productpage.goToCart();

        // 11. Select Country (if needed) and agree to terms
        checkoutpage.selectBillingCountry("United States");
        checkoutpage.agreeTerms();

        // 12. Click on "Check out"
        checkoutpage.clickCheckout();

        // 13. Select Billing address --> click continue
        // If a dropdown of existing addresses exists, choose "New Address" or default option
//        checkoutpage.selectBillingAddress("New Address");
        checkoutpage.selectBillingAddress("genai2025 Cap, KONDAPUR, HYDERABAD 500084, India");
        checkoutpage.continueBilling();

        // 14. Select Shipping Method --> click continue
//        checkoutpage.selectShippingMethod("Ground");
        checkoutpage.continueShippingMethod();

        // 15. Select Payment Method --> Cash on Delivery
        checkoutpage.selectPaymentMethod("Cash on Delivery");
        checkoutpage.continuePaymentMethod();

        // 16. Payment information --> Click on continue
        checkoutpage.continuePaymentInfo();

        // 17. Confirm Order --> Continue
        checkoutpage.continueConfirmOrder();

        // 18. Click on confirm button (some themes have separate confirm)
        checkoutpage.clickConfirmButton();

        // 19. Verify if the message "Your order has been successfully processed"
        boolean success = checkoutpage.isOrderSuccessDisplayed();
        Assert.assertTrue(success, "Order success message not displayed or did not match expected text");

        // 20. Verify if order id is displayed
//        String orderId = checkoutpage.extractOrderId();
//        Assert.assertNotNull(orderId, "Order id was not found");
//        Assert.assertFalse(orderId.trim().isEmpty(), "Order id is empty");
//        System.out.println("Order placed successfully. Order ID: " + orderId);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}