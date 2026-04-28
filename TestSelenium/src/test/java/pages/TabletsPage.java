package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TabletsPage {
    WebDriver driver;
    WebDriverWait wait;

    public TabletsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By samsungTabAddToCart = By.xpath("//h4/a[contains(text(), 'Samsung Galaxy Tab 10.1')]/ancestor::div[@class='caption']/following-sibling::div//button[contains(@onclick, 'cart.add')]");
    By successMessage = By.cssSelector(".alert-success");

    public void addSamsungTabToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(samsungTabAddToCart)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }
}