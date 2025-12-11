package genai.app.Pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import genai.app.Base.TestBase;

public class ProductPage extends TestBase {

    private WebDriverWait wait;

    @FindBy(xpath = "//dl/dd[1]/ul/li/label")
    private List<WebElement> processorItems;

    @FindBy(xpath = "//dl/dd[2]/ul/li/label")
    private List<WebElement> RAMItems;
    
    @FindBy(xpath = "//dl/dd[3]/ul/li/label")
    private List<WebElement> HDDItems;
    
    @FindBy(xpath = "//dl/dd[4]/ul/li/label")
    private List<WebElement> softwareItems;
    @FindBy(id = "add-to-cart-button-74")
    private WebElement addToCartbtn;
    @FindBy(css = "a.ico-cart")
    private WebElement cart;
    


    public ProductPage() throws IOException {
        super();
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void selectProcessor(String visibleText) {
        // processor may be a select or radio - try select first
        try {
          
           
            for (WebElement o : processorItems) {
            	System.out.println("   ...   "+o.getText());
                if (o.getText().trim().equalsIgnoreCase("Slow")) {
                    o.click();
                    return;
                }
            }
        } catch (Exception e) {
            // try radio buttons
            List<WebElement> rads = driver.findElements(By.name("product_attribute_72"));
            for (WebElement r : rads) {
                if (r.getAttribute("value") != null && r.getAttribute("value").equals(visibleText)) {
                    r.click();
                    return;
                }
                if (r.getText() != null && r.getText().contains(visibleText)) {
                    r.click();
                    return;
                }
            }
        }
    }

    public void selectRAM(String visibleText) {
        try {
            
            for (WebElement o : RAMItems) {
                if (o.getText().trim().equalsIgnoreCase(visibleText.trim())) {
                    o.click();
                    return;
                }
            }
        } catch (Exception e) {
            List<WebElement> rads = driver.findElements(By.name("product_attribute_74"));
            for (WebElement r : rads) {
                if (r.getText() != null && r.getText().contains(visibleText)) {
                    r.click();
                    return;
                }
            }
        }
    }

    public void selectHDD(String visibleText) {
try {
            
            for (WebElement o : HDDItems) {
                if (o.getText().trim().equalsIgnoreCase(visibleText.trim())) {
                    o.click();
                    return;
                }
               
            }
           
			}catch (Exception e) {
            // ignore
        }
    }

    public void selectSoftwareByValue(String value) {
        try {
            
            for (WebElement b : softwareItems) {
                if (b.getText().trim().equalsIgnoreCase(value.trim())) {
                    if (!b.isSelected()) b.click();
                    return;
                }
                if (b.getText() != null && b.getText().contains(value)) {
                    if (!b.isSelected()) b.click();
                    return;
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

    public void setQty(int qty) {
        try {
            WebElement q = driver.findElement(By.id("addtocart_74_EnteredQuantity"));
            q.clear();
            q.sendKeys(String.valueOf(qty));
        } catch (Exception e) {
            try {
                WebElement q = driver.findElement(By.className("qty-input"));
                q.clear();
                q.sendKeys(String.valueOf(qty));
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    public void clickAddToCart() {
        try {
            
            addToCartbtn.click();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("IMPLICITWAIT_TIMEOUT"))));
        } catch (Exception e) {
            try {
                WebElement btn = driver.findElement(By.cssSelector("input.button-1.add-to-cart-button"));
                btn.click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("IMPLICITWAIT_TIMEOUT"))));
            } catch (Exception ex) {
                // fallback: try add to cart link
                try { driver.findElement(By.cssSelector("a[href*='addproducttocart']")).click(); } catch (Exception ignore) {}
            }
        }
    }

    // New: navigate to shopping cart page and wait until cart page is loaded
    public void goToCart() {
        try {
            // common cart icon used across pages
            
            cart.click();
            // wait for shopping cart header or cart table to be visible
            try {
                wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.page.shopping-cart-page")),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.cart")),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1"))
                ));
            } catch (Exception ignored) {
                // ignore wait issues; navigation still attempted
            }
        } catch (Exception e) {
            // fallback: direct cart URL if available in properties
            String cartUrl = null;
            try { cartUrl = prop.getProperty("CART_URL"); } catch (Exception ignore) {}
            if (cartUrl != null && !cartUrl.isEmpty()) {
                driver.navigate().to(cartUrl);
            }
        }
    }

    // New: try to read cart item count from the cart link (if site shows count)
    public int getCartItemCount() {
        try {
            WebElement cart = driver.findElement(By.cssSelector("a.ico-cart"));
            String txt = cart.getText();
            if (txt == null || txt.trim().isEmpty()) return 0;
            txt = txt.replaceAll("[^0-9]", "");
            if (txt.isEmpty()) return 0;
            return Integer.parseInt(txt);
        } catch (Exception e) {
            return 0;
        }
    }

}