package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LaptopsPage {
    WebDriver driver;
    WebDriverWait wait;

    public LaptopsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By hpLaptopLink = By.xpath("//h4/a[contains(text(), 'HP LP3065')]");

    public void clickHPLaptop() {
        wait.until(ExpectedConditions.elementToBeClickable(hpLaptopLink)).click();
    }
}