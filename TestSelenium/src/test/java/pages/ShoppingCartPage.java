package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ShoppingCartPage {
    WebDriver driver;
    WebDriverWait wait;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean isProductInCart(String productName) {
        try {
            String xpath = "//table[@class='table table-bordered']//tbody//tr//td[2]" +
                    "[contains(., '" + productName + "')]";

            WebElement productCell = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))
            );

            String cellText = productCell.getText()
                    .replace("***", "")   // remove stock warning
                    .replace("Reward Points:", "")
                    .trim();

            boolean found = cellText.contains(productName);

            System.out.println("isProductInCart('" + productName + "') -> " + found);
            System.out.println("Cell text found: '" + cellText + "'");

            return found;

        } catch (Exception e) {
            System.out.println("Product '" + productName + "' NOT found in cart. Error: " + e.getMessage());
            return false;
        }
    }

    public String getProductUnitPrice(String productName) {
        try {
            String xpath = "//table[@class='table table-bordered']/tbody/tr[td//*[contains(text(), '" + productName + "')]]/td[5]";
            WebElement priceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            String price = priceElement.getText();
            System.out.println("Found price for " + productName + ": " + price);
            return price;
        } catch (Exception e) {
            System.out.println("Error getting price: " + e.getMessage());
            return "";
        }
    }


    public double getTotalAmount() {
        WebElement totalElement = driver.findElement(By.xpath("//strong[text()='Total:']/ancestor::td/following-sibling::td"));
        String totalText = totalElement.getText();
        System.out.println("Total from page: " + totalText);
        return Double.parseDouble(totalText.replace("$", "").replace(",", ""));
    }


    public double calculateExpectedTotal() {
        List<WebElement> totalPriceElements = driver.findElements(By.xpath("//table[@class='table table-bordered']/tbody/tr[td[5]]/td[6]"));
        double total = 0;
        for (WebElement price : totalPriceElements) {
            String priceText = price.getText().replace("$", "").replace(",", "");
            if (!priceText.isEmpty()) {
                total += Double.parseDouble(priceText);
                System.out.println("Adding product total: " + priceText);
            }
        }
        System.out.println("Calculated total: " + total);
        return total;
    }
    public boolean verifyTotalMatches() {
        double actualTotal = getTotalAmount();
        double calculatedTotal = calculateExpectedTotal();
        System.out.println("Actual Total: $" + actualTotal);
        System.out.println("Calculated Total: $" + calculatedTotal);
        return Math.abs(actualTotal - calculatedTotal) < 0.01;
    }



    public String getDeliveryDate(String productName) {
        try {
            String xpath = "//table[@class='table table-bordered']//tr[td[2][contains(., '" + productName + "')]]" + "//small[contains(text(),'Delivery Date:')]";

            WebElement deliveryElem = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            String deliveryText = deliveryElem.getText();
            return deliveryText.replace("Delivery Date:", "").trim();
        } catch (Exception e) {
            System.out.println("No delivery date found for: " + productName);
            return "";
        }
    }


    public boolean isProductInMiniCart(String productName) {
        try {
            String xpath = "//div[@id='cart']//table[contains(@class,'table-striped')]//td[2]"
                    + "[contains(., '" + productName + "')]";
            WebElement cell = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))
            );
            return cell.getText().contains(productName);
        } catch (Exception e) {
            return false;
        }
    }





    private final By checkoutButton = By.linkText("Checkout");
    public void clickCheckout() {
        try {
            WebElement checkoutBtn = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
            checkoutBtn.click();
            System.out.println("Clicked on Checkout button successfully.");
        } catch (Exception e) {
            System.out.println("Failed to click Checkout button: " + e.getMessage());
            try {
                WebElement fallbackBtn = driver.findElement(By.xpath("//a[contains(text(),'Checkout')]"));
                fallbackBtn.click();
                System.out.println("Clicked Checkout using fallback locator.");
            } catch (Exception ex) {
                throw new RuntimeException("Could not click Checkout button", ex);
            }
        }
    }

}