package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DesktopsPage {

    WebDriver driver;
    WebDriverWait wait;

    public DesktopsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Locators ---

    // All price elements in the product grid
    By productPrices = By.cssSelector("div.product-thumb p.price");

    // --- Methods ---

    /**
     * Step 3 & 5: Returns true if ALL visible product prices contain the given currency symbol.
     * Pass "$" to verify dollar, "€" to verify euro.
     */
    public boolean arePricesDisplayedIn(String currencySymbol) {
        List<WebElement> prices = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(productPrices));
        for (WebElement price : prices) {
            if (!price.getText().contains(currencySymbol)) {
                return false;
            }
        }
        return true;
    }
}