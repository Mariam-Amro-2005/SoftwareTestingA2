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


    By searchInput = By.name("search");
    By searchBtn = By.cssSelector("#search button");


    By tablets = By.xpath("//a[contains(text(), 'Tablets')]");
    By laptopsDropdown = By.xpath("//a[contains(text(), 'Laptops & Notebooks')]");
    By showAllLaptops = By.xpath("//a[contains(text(), 'Show AllLaptops & Notebooks')]");
    By shoppingCart = By.xpath("//a[@title='Shopping Cart']");


    By phonesAndPDAs = By.xpath("//a[contains(text(), 'Phones & PDAs')]");


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



}