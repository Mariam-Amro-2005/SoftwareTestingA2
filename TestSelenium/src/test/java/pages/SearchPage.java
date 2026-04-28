package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.openqa.selenium.WebElement;

public class SearchPage {

    WebDriver driver;
    public SearchPage(WebDriver driver){
        this.driver = driver;
    }

    By productName = By.xpath("//div[@class='product-thumb']//h4/a");

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
}