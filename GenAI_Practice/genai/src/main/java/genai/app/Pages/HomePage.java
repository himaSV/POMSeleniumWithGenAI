package genai.app.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import genai.app.Base.TestBase;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends TestBase{
	private WebDriverWait wait;

    @FindBy(xpath = "//div/ul[@class='top-menu']/li")
    private List<WebElement> itemList;
	
    @FindBy(xpath = "//ul[@class='top-menu']/li/a")
    private List<WebElement> itemListText;
	
    @FindBy(xpath = "//div[@class='product-grid']/div")
    private List<WebElement> productItems;
    
    @FindBy(xpath = "//h2/a[@href='/build-your-own-expensive-computer-2']")
    private WebElement buildexpensiveComputer;  
    

//    protected WebDriver driver;

    public HomePage() throws IOException {
        super();
        PageFactory.initElements(driver, this);
        // initialize explicit wait for element interactions
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
    }
    
    /**
     * Returns the visible text of each top-menu item (e.g. Books, Computers, Electronics).
     */
    public List<String> getMenuItemNames() {
        List<String> names = new ArrayList<>();
        if (itemListText == null) {
            return names;
        }
        for (WebElement e : itemListText) {
            try {
                String txt = e.getText();
                if (txt != null) {
                    txt = txt.trim();
                    if (!txt.isEmpty()) {
                        names.add(txt);
                    }
                }
            } catch (Exception ex) {
                // ignore individual element errors and continue
            }
        }
        return names;
    }

    /**
     * Returns the number of top-menu items.
     */
    public int getMenuItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    /**
     * Returns the number of products displayed in the product grid.
     */
    public int getProductCount() {
        return productItems == null ? 0 : productItems.size();
    }

    /**
     * Convenience method to print menu item names and the total count to stdout.
     */
    public void printMenuItemsAndCount() {
        List<String> names = getMenuItemNames();
        System.out.println("Menu items (" + names.size() + "):");
        for (String n : names) {
            System.out.println(" - " + n);
        }
    }
    
    /**
     * Clicks the "Build your own expensive computer" product link.
     * Waits until clickable and uses JS click as a fallback for overlays/stale issues.
     * @throws IOException 
     */
    public ProductPage clickBuildYourOwnExpensiveComputer() throws IOException {
        if (buildexpensiveComputer == null) {
            throw new IllegalStateException("Build your own expensive computer link not present on the page");
        }
        try {
            wait.until(ExpectedConditions.elementToBeClickable(buildexpensiveComputer));
            buildexpensiveComputer.click();
        } catch (Exception e) {
            // fallback to JS click
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", buildexpensiveComputer);
            } catch (Exception jsEx) {
                throw new RuntimeException("Unable to click 'Build your own expensive computer' link", jsEx);
            }
        }
        return new ProductPage();
    }
}