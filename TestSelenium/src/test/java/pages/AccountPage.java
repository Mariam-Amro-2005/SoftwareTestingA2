package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {

    WebDriver driver;
    WebDriverWait wait;
    public AccountPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By successHeader = By.xpath("//h1[text()='Your Account Has Been Created!']");
    By logoutLink = By.linkText("Logout");

    By myAccountDropdown = By.xpath("//a[@title='My Account']");
    By logoutDirect = By.xpath("//a[contains(@href, 'route=account/logout')]");
    By logoutFromDropdown = By.xpath("//ul[@class='dropdown-menu dropdown-menu-right']//a[contains(@href, 'route=account/logout')]");



    public boolean isRegistrationSuccessful(){

        return driver.findElement(successHeader).isDisplayed();
    }

    public boolean isLogoutDisplayed(){

        return driver.findElement(logoutLink).isDisplayed();
    }

    public void logout(){
        try {
            // Try direct Logout link first (simpler)
            WebElement logoutLink = wait.until(ExpectedConditions.elementToBeClickable(logoutDirect));
            logoutLink.click();
        } catch (Exception e) {
            // If direct link not clickable, use dropdown method
            try {
                // Click on My Account to open dropdown
                wait.until(ExpectedConditions.elementToBeClickable(myAccountDropdown)).click();

                // Click Logout from dropdown
                wait.until(ExpectedConditions.elementToBeClickable(logoutFromDropdown)).click();
            } catch (Exception e2) {
                throw new RuntimeException("Failed to click Logout: " + e2.getMessage());
            }
        }
    }
}