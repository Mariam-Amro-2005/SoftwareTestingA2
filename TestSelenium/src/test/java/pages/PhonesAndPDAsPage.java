package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PhonesAndPDAsPage {
    WebDriver driver;
    WebDriverWait wait;


    By sortDropdown = By.id("input-sort");
    public PhonesAndPDAsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    //sort A to Z
    public void sortByNameAscending() {
        Select sortSelect = new Select(driver.findElement(sortDropdown));
        sortSelect.selectByVisibleText("Name (A - Z)");
    }
    //sort Z to A
    public void sortByNameDescending() {
        Select sortSelect = new Select(driver.findElement(sortDropdown));
        sortSelect.selectByVisibleText("Name (Z - A)");
    }

}