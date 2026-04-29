package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    WebDriver driver;
    WebDriverWait wait;

    public SearchPage(WebDriver driver){

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    By productName = By.xpath("//div[@class='product-thumb']//h4/a");
    By searchKeywordInput  = By.id("input-search");
    By categoryDropdown    = By.name("category_id");
    By subCategoryCheckbox = By.name("sub_category");
    By searchButton        = By.id("button-search");
    By noResultsMessage    = By.xpath("//p[contains(text(),'There is no product that matches')]");


    public boolean resultMAtching(String value){
        List<WebElement> products = driver.findElements(productName);

        for(WebElement product: products){
            String name = product.getText().toLowerCase();

            if(!name.contains(value.toLowerCase())){
                return false;
            }

        }
        return true;
    }

    public void enterKeyword(String keyword) {
        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchKeywordInput));
        input.clear();
        input.sendKeys(keyword);
    }

    public void selectCategory(String categoryText) {
        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(categoryDropdown));
        new Select(dropdown).selectByVisibleText(categoryText);
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public boolean isNoResultMessageDisplayed() {
        try {
            return wait.until(
                            ExpectedConditions.visibilityOfElementLocated(noResultsMessage))
                    .isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void checkSearchInSubCategories() {
        WebElement checkbox = wait.until(
                ExpectedConditions.elementToBeClickable(subCategoryCheckbox));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    public boolean isProductDisplayed(String value) {
        List<WebElement> products = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(productName));
        for (WebElement product : products) {
            if (product.getText().toLowerCase().contains(value.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}