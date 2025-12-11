package genai.app.Utils;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import genai.app.Base.TestBase;

import org.openqa.selenium.WebElement;
import java.util.Properties;

public class waitUtils extends TestBase {

    private WebDriver driver;
    private WebDriverWait wait;

    public waitUtils( ) throws IOException {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitForPeriod() {
    	
    	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(prop.getProperty("IMPLICITWAIT_TIMEOUT"))));
    }
}
