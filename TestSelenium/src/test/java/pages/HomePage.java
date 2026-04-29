package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By myAccount = By.xpath("//span[text()='My Account']");
    By register = By.linkText("Register");
    By login = By.linkText("Login");


    By searchBtn = By.cssSelector("#search button");


    By tablets = By.xpath("//a[contains(text(), 'Tablets')]");
    By laptopsDropdown = By.xpath("//a[contains(text(), 'Laptops & Notebooks')]");
    By showAllLaptops = By.xpath("//a[contains(text(), 'Show AllLaptops & Notebooks')]");
    By shoppingCart = By.xpath("//a[@title='Shopping Cart']");


    By phonesAndPDAs = By.xpath("//a[contains(text(), 'Phones & PDAs')]");

    By desktopsDropdown = By.xpath("//a[contains(text(),'Desktops')][@class='dropdown-toggle']");
    By showAllDesktops  = By.xpath("//a[@class='see-all' and contains(text(),'Show AllDesktops')]");
    By currencyDropdown = By.cssSelector("button.btn.btn-link.dropdown-toggle");
    By euroCurrency     = By.xpath("//button[@name='EUR']");


    public void goToRegister(){

        driver.findElement(myAccount).click();
        driver.findElement(register).click();
    }

    public void goToLogin(){

        driver.findElement(myAccount).click();
        driver.findElement(login).click();
    }


    public void search(String text) {

        By searchBox = By.name("search");

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox)
        );

        input.clear();
        input.sendKeys(text);
    }


    public void goToPhonesAndPDAs() {
        WebElement phonesLink = wait.until(
                ExpectedConditions.elementToBeClickable(phonesAndPDAs)
        );
        phonesLink.click();
    }


    public void goToTablets() {
        wait.until(ExpectedConditions.elementToBeClickable(tablets)).click();
    }

    public void goToLaptopsAndNotebooks() {
        wait.until(ExpectedConditions.elementToBeClickable(laptopsDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(showAllLaptops)).click();
    }

    public void openShoppingCart() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCart)).click();
    }

    public void clickSearchIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBtn)).click();
    }

    public void goToAllDesktops() {
        wait.until(ExpectedConditions.elementToBeClickable(desktopsDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(showAllDesktops)).click();
    }

    public void changeCurrencyToEuro() {
        wait.until(ExpectedConditions.elementToBeClickable(currencyDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(euroCurrency)).click();
    }

    public String getActiveCurrencySymbol() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(currencyDropdown))
                .findElement(By.tagName("strong"))
                .getText().trim();
    }


}