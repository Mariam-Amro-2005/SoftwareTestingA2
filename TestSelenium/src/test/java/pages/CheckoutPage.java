package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    // Billing Address Locators
    private final By newBillingRadio = By.cssSelector("input[name='payment_address'][value='new']");
    private final By billingFirstname = By.id("input-payment-firstname");
    private final By billingLastname = By.id("input-payment-lastname");
    private final By billingCompany = By.id("input-payment-company");
    private final By billingAddress1 = By.id("input-payment-address-1");
    private final By billingCity = By.id("input-payment-city");
    private final By billingPostcode = By.id("input-payment-postcode");
    private final By billingCountry = By.id("input-payment-country");
    private final By billingZone = By.id("input-payment-zone");
    private final By btnContinueBilling = By.id("button-payment-address");

    // Shipping Address Locators
    private final By newShippingRadio = By.cssSelector("input[name='shipping_address'][value='new']");
    private final By shippingFirstname = By.id("input-shipping-firstname");
    private final By shippingLastname = By.id("input-shipping-lastname");
    private final By shippingCompany = By.id("input-shipping-company");
    private final By shippingAddress1 = By.id("input-shipping-address-1");
    private final By shippingCity = By.id("input-shipping-city");
    private final By shippingPostcode = By.id("input-shipping-postcode");
    private final By shippingCountry = By.id("input-shipping-country");
    private final By shippingZone = By.id("input-shipping-zone");
    private final By btnContinueShipping = By.id("button-shipping-address");

    // Other Locators
    private final By flatShippingRadio = By.cssSelector("input[name='shipping_method'][value='flat.flat']");
    private final By orderComment = By.name("comment");
    private final By btnContinueShippingMethod = By.id("button-shipping-method");
    private final By termsCheckbox = By.name("agree");
    private final By btnContinuePayment = By.id("button-payment-method");
    private final By btnConfirmOrder = By.id("button-confirm");
    private final By successMessage = By.xpath("//h1[contains(text(),'Your order has been placed')]");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
        this.js = (JavascriptExecutor) driver;
    }

    // ====================== COUNTRY & ZONE HELPER ======================
    private void selectCountryAndZone(By countryLocator, By zoneLocator,
                                      String countryValue, String zoneValue) {

        // Select Country
        WebElement country = wait.until(ExpectedConditions.elementToBeClickable(countryLocator));
        new Select(country).selectByValue(countryValue);

        // Wait for zones to load via AJAX
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(zoneLocator)));

        // Additional wait for options to populate
        wait.until(driver -> {
            Select select = new Select(driver.findElement(zoneLocator));
            return select.getOptions().size() > 1;
        });

        // Select Zone
        new Select(driver.findElement(zoneLocator)).selectByValue(zoneValue);
    }

    // ====================== BILLING ADDRESS ======================
    public void selectNewBillingAddress() {
        clickWithJS(newBillingRadio);
        wait.until(ExpectedConditions.visibilityOfElementLocated(billingFirstname));
    }

    public void fillNewBillingAddress(String fn, String ln, String comp, String addr1,
                                      String city, String post, String country, String zone) {
        sendKeysWithClear(billingFirstname, fn);
        sendKeysWithClear(billingLastname, ln);
        sendKeysWithClear(billingCompany, comp);
        sendKeysWithClear(billingAddress1, addr1);
        sendKeysWithClear(billingCity, city);
        sendKeysWithClear(billingPostcode, post);

        selectCountryAndZone(billingCountry, billingZone, country, zone);
    }

    public void continueBillingAddress() {
        clickWithJS(btnContinueBilling);
    }

    // ====================== SHIPPING ADDRESS ======================
    public void selectNewShippingAddress() {
        clickWithJS(newShippingRadio);
        wait.until(ExpectedConditions.visibilityOfElementLocated(shippingFirstname));
    }

    public void fillNewShippingAddress(String fn, String ln, String comp, String addr1,
                                       String city, String post, String country, String zone) {
        sendKeysWithClear(shippingFirstname, fn);
        sendKeysWithClear(shippingLastname, ln);
        sendKeysWithClear(shippingCompany, comp);
        sendKeysWithClear(shippingAddress1, addr1);
        sendKeysWithClear(shippingCity, city);
        sendKeysWithClear(shippingPostcode, post);

        selectCountryAndZone(shippingCountry, shippingZone, country, zone);
    }

    public void continueShippingAddress() {
        clickWithJS(btnContinueShipping);
    }

    // ====================== HELPER METHODS ======================
    private void sendKeysWithClear(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        if (text != null && !text.isEmpty()) {
            element.sendKeys(text);
        }
    }

    private void clickWithJS(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", driver.findElement(locator));
        }
    }

    // ====================== REST OF CHECKOUT ======================
    public void selectFlatShippingMethod() {
        clickWithJS(flatShippingRadio);
    }

    public void enterOrderComment(String comment) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(orderComment));
        el.clear();
        if (comment != null && !comment.isEmpty()) {
            el.sendKeys(comment);
        }
    }

    public void continueShippingMethod() {
        clickWithJS(btnContinueShippingMethod);
    }

    public void checkTermsAndConditions() {
        clickWithJS(termsCheckbox);
    }

    public void continuePaymentMethod() {
        clickWithJS(btnContinuePayment);
    }

    public void confirmOrder() {
        clickWithJS(btnConfirmOrder);
    }

    public boolean isOrderPlacedSuccessfully() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}