package genai.app.Pages;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import genai.app.Base.TestBase;
import genai.app.Pages.HomePage;
import genai.app.Utils.*;

/**
 * Page object for the login page of the demo web shop (https://demowebshop.tricentis.com/login)
 */
public class LoginPage extends TestBase {

    private WebDriverWait wait;

    @FindBy(id = "Email")
    private WebElement emailInput;

    @FindBy(id = "Password")
    private WebElement passwordInput;

    @FindBy(css = "input.login-button")
    private WebElement loginButton;

    @FindBy(css = "a.ico-logout")
    private WebElement logoutLink;

    @FindBy(css = "a.account")
    private WebElement accountLink;
    
    waitUtils wt=new waitUtils();
    public LoginPage() throws IOException {
        super();
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Enter email into email field.
     */
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    /**
     * Enter password into password field.
     */
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Clicks the login button.
     */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    /**
     * Convenience method to perform full login.
     * Returns true when login appears successful (logout link visible).
     */
    public boolean login(String email, String password) {
        try {
            enterEmail(email);
            enterPassword(password);
            clickLogin();
            // wait for logout or account link to appear
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.ico-logout")),
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a.account"))));
            return isLoggedIn();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Login using properties object containing "username"/"email" and "password" keys.
     */
    public boolean loginFromProperties(Properties prop) {
        if (prop == null) return false;
        String email = prop.getProperty("username", prop.getProperty("email", ""));
        String password = prop.getProperty("password", "");
        return login(email, password);
    }

    /**
     * Returns true if the user is currently logged in (logout link visible).
     */
    public boolean isLoggedIn() {
        try {
            return logoutLink.isDisplayed() || accountLink.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns the visible account name after login (if present), otherwise null.
     */
    public String getLoggedInUserName() {
        try {
            if (accountLink.isDisplayed()) return accountLink.getText().trim();
        } catch (Exception e) {
            // ignore
        }
        return null;
    }
    public HomePage loginWithEmail(String usr , String pswd) throws IOException {
    	
    	emailInput.sendKeys(usr);
    	passwordInput.sendKeys(pswd);
    	loginButton.click();
    	wt.waitForPeriod();
    	return new HomePage();
    	
    }

    
    
    
}