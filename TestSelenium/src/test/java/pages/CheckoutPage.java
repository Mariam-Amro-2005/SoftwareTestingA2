package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {

    WebDriver driver;
    WebDriverWait wait;

    // Billing Details (Step 2) – new address radio and form fields
    By newAddressRadio   = By.cssSelector("input[name='payment_address'][value='new']");
    By firstName         = By.id("input-payment-firstname");
    By lastName          = By.id("input-payment-lastname");
    By address1          = By.id("input-payment-address-1");
    By city              = By.id("input-payment-city");
    By postcode          = By.id("input-payment-postcode");
    By country           = By.id("input-payment-country");
    By region            = By.id("input-payment-zone");
    By billingContinue   = By.id("button-payment-address");

    // Shipping Address (Step 3)
    By shippingAddressDropdown = By.id("input-shipping-address");   // appears after billing
    By shippingAddressContinue = By.id("button-shipping-address");

    // Delivery Method (Step 4)
    By commentTextarea         = By.name("comment");
    By shippingMethodContinue  = By.id("button-shipping-method");

    // Payment Method (Step 5)
    By termsCheckbox    = By.name("agree");
    By paymentContinue  = By.id("button-payment-method");
    By warningAlert     = By.cssSelector("div.alert.alert-danger");

    // Confirm Order (Step 6)
    By confirmOrderBtn   = By.id("button-confirm");
    By successHeading    = By.xpath("//h1[contains(text(),'Your order has been placed!')]");
    By cartTotalItems    = By.id("cart-total");
    By orderTotalRow     = By.xpath("//table//tfoot//tr[last()]/td[2]");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    /**
     * Select “I want to use a new address” and fill the billing form.
     */
    public void fillNewBillingAddress(String fName, String lName,
                                      String addr, String cit,
                                      String post, String countryName, String regionName) {
        // Choose new address radio
        wait.until(ExpectedConditions.elementToBeClickable(newAddressRadio)).click();

        // Wait for the new address fields to become visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));

        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(address1).clear();
        driver.findElement(address1).sendKeys(addr);
        driver.findElement(city).clear();
        driver.findElement(city).sendKeys(cit);
        driver.findElement(postcode).clear();
        driver.findElement(postcode).sendKeys(post);

        // Select country
        Select countrySel = new Select(driver.findElement(country));
        countrySel.selectByVisibleText(countryName);

        // Wait for region dropdown to be populated, then select
        wait.until(d -> {
            Select regSel = new Select(driver.findElement(region));
            return regSel.getOptions().stream().anyMatch(opt -> opt.getText().equals(regionName));
        });
        new Select(driver.findElement(region)).selectByVisibleText(regionName);
    }

    public void clickBillingContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(billingContinue)).click();
    }

    /**
     * After billing, wait for the shipping address dropdown to appear,
     * then select the address that contains the given text.
     */
    public void selectShippingAddressContaining(String partialText) {
        wait.until(ExpectedConditions.presenceOfElementLocated(shippingAddressDropdown));
        Select select = new Select(driver.findElement(shippingAddressDropdown));
        List<WebElement> options = select.getOptions();
        boolean found = false;
        for (WebElement option : options) {
            if (option.getText().contains(partialText)) {
                select.selectByVisibleText(option.getText());
                found = true;
                break;
            }
        }
        if (!found) {
            // Fallback: select the last option (newest address)
            select.selectByIndex(options.size() - 1);
        }
    }

    public void clickShippingAddressContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(shippingAddressContinue)).click();
    }

    /**
     * Fill the delivery method comment and proceed.
     */
    public void enterCommentAndContinue(String comment) {
        // Wait for the shipping method panel to load fully
        wait.until(ExpectedConditions.presenceOfElementLocated(commentTextarea));
        WebElement area = driver.findElement(commentTextarea);
        area.clear();
        area.sendKeys(comment);

        wait.until(ExpectedConditions.elementToBeClickable(shippingMethodContinue)).click();
    }

    /**
     * Agree to Terms & Conditions (retry up to 3 times).
     */
    public void agreeTermsAndContinue() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }

            driver.findElement(paymentContinue).click();

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(warningAlert));
                // Warning appeared – retry
            } catch (Exception e) {
                // No warning → success
                break;
            }
        }
    }

    public String getConfirmTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderTotalRow))
                .getText();
    }

    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderBtn)).click();
    }

    public boolean isOrderPlacedMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successHeading))
                .isDisplayed();
    }

    public String getCartTotalText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotalItems))
                .getText();
    }
}