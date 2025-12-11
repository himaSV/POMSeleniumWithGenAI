package genai.app.Pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import genai.app.Base.TestBase;

public class CheckoutPage extends TestBase {
	private WebDriverWait wait;

    @FindBy(css = "a.ico-cart")
    private WebElement goToCartLink;
    
    @FindBy(xpath = "//input[@onclick='Billing.save()']")
    private WebElement billSaveBtn;  
       
    
    @FindBy(id = "termsofservice")
    private WebElement termServices;
    
    @FindBy(id = "checkout")
    private WebElement btn;
    
    @FindBy(id = "BillingNewAddress_CountryId")
    private WebElement  sel;
    
    @FindBy(id = "billing-address-select")
    private WebElement  addr;
    
    @FindBy(xpath ="//input[@onclick='Shipping.save()']")
    private WebElement  shippingSaveBTn;
    
    @FindBy(xpath ="//input[@onclick='ShippingMethod.save()']")
    private WebElement  shipMethodBtn;
   
    
    
   
    public CheckoutPage() throws IOException {
    	PageFactory.initElements(driver, this);
    
    }

    public void goToCart() {
    	goToCartLink.click();
    }

    public void agreeTerms() {
        
        if (!termServices.isSelected()) termServices.click();
    }

    public void clickCheckout() {
        
        btn.click();
    }

    // Select billing country from standard nopCommerce country select (best-effort)
    public void selectBillingCountry(String country) {
        try {
            // common id used by nopCommerce for billing country
            
            Select s = new Select(sel);
            s.selectByVisibleText(country);
        } catch (Exception e) {
            // fallback: try to find any select with name or id containing 'Country'
            try {
                WebElement sel = driver.findElement(By.cssSelector("select[id*='Country'], select[name*='Country']"));
                Select s = new Select(sel);
                s.selectByVisibleText(country);
            } catch (Exception ignore) {
                // ignore - selection may already be set or handled in test data
            }
        }
    }

    // Select an existing billing address from the address dropdown (if present)
    public void selectBillingAddress(String visibleText) {
        try {
           
            Select s = new Select(addr);
            s.selectByVisibleText(visibleText);
        } catch (Exception e) {
            // fallback: look for radio buttons or address list
            try {
                List<WebElement> radios = driver.findElements(By.name("billing_address_id"));
                for (WebElement r : radios) {
                    if (r.getAttribute("value") != null && r.getAttribute("value").equals(visibleText)) {
                        r.click();
                        return;
                    }
                }
            } catch (Exception ignore) {}
        }
    }

    public void continueBilling() {
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("IMPLICITWAIT_TIMEOUT"))));
        billSaveBtn.click();
         billSaveBtn.click();
         driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
         shippingSaveBTn.click();
//         shippingSaveBTn.click();
     }

     public void continueShippingMethod() {
    	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
    	 shipMethodBtn.click();
    	 shipMethodBtn.click();
     }

     // Choose a shipping method by visible label (best-effort)
     public void selectShippingMethod(String methodLabel) {
        try {
            List<WebElement> options = driver.findElements(By.name("shippingoption"));
            for (WebElement o : options) {
                String text = "";
                try { text = o.findElement(By.xpath("following-sibling::label[1]")).getText(); } catch (Exception ignore) {}
                if ((text != null && text.contains(methodLabel)) || (o.getAttribute("value") != null && o.getAttribute("value").contains(methodLabel))) {
                    if (!o.isSelected()) o.click();
                    return;
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

     public void continuePaymentMethod() {
         WebElement btn = driver.findElement(By.cssSelector("input[onclick*='PaymentMethod.save']"));
         btn.click();
     }

     // Choose a payment method; default to "Cash on Delivery" when methodLabel is null
     public void selectPaymentMethod(String methodLabel) {
        if (methodLabel == null || methodLabel.trim().isEmpty()) methodLabel = "Cash on Delivery";
        try {
            List<WebElement> options = driver.findElements(By.name("paymentmethod"));
            for (WebElement o : options) {
                String text = "";
                try { text = o.findElement(By.xpath("following-sibling::label[1]")).getText(); } catch (Exception ignore) {}
                if ((text != null && text.contains(methodLabel)) || (o.getAttribute("value") != null && o.getAttribute("value").toLowerCase().contains("cash"))) {
                    if (!o.isSelected()) o.click();
                    return;
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }
     public void continuePaymentInfo() {
         WebElement btn = driver.findElement(By.cssSelector("input[onclick*='PaymentInfo.save']"));
         btn.click();
     }

     public void continueConfirmOrder() {
         WebElement btn = driver.findElement(By.cssSelector("input[onclick*='ConfirmOrder.save']"));
         btn.click();
     }

     // Final confirm/place order button (some themes have a separate "Confirm" button)
     public void clickConfirmButton() {
         try {
             // try an explicit place order button
             WebElement btn = driver.findElement(By.cssSelector("input[onclick*='ConfirmOrder.placeOrder'], input.button-1.confirm-order-next-step-button"));
             btn.click();
         } catch (Exception e) {
             // fallback to existing continueConfirmOrder
             try { continueConfirmOrder(); } catch (Exception ignore) {}
         }
     }

     public String getOrderConfirmationMessage() {
         try {
             return driver.findElement(By.cssSelector("div.section .title")).getText();
         } catch (Exception e) {
             return null;
         }
     }

     public String getOrderNumber() {
         try {
             WebElement el = driver.findElement(By.cssSelector("div.order-number"));
             return el.getText();
         } catch (Exception e) {
             return null;
         }
     }

     // Convenience: check success message contains expected text
     public boolean isOrderSuccessDisplayed() {
         String msg = getOrderConfirmationMessage();
         if (msg == null) return false;
         return msg.toLowerCase().contains("your order has been successfully processed") || msg.toLowerCase().contains("thank you");
     }

     // Convenience: try to extract the order id (digits) from order-number text
     public String extractOrderId() {
         String txt = getOrderNumber();
         if (txt == null) return null;
         // common format: "Order number: 12345"
         String digits = txt.replaceAll(".*?(\\d+).*", "$1");
         if (digits.equals(txt)) return null;
         return digits;
     }
 }