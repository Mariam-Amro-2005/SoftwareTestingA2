package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LaptopProductPage {
    WebDriver driver;
    WebDriverWait wait;

    public LaptopProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By deliveryDateInput = By.id("input-option225");
    By addToCartButton = By.id("button-cart");
    By successMessage = By.cssSelector(".alert-success");

    public void setDeliveryDate(String date) {
        WebElement dateField = wait.until(ExpectedConditions.visibilityOfElementLocated(deliveryDateInput));
        dateField.clear();
        dateField.sendKeys(date);
        dateField.sendKeys(Keys.ENTER);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

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