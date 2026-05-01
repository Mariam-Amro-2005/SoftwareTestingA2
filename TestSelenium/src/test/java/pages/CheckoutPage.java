package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Log;

import java.time.Duration;
import java.util.List;

public class CheckoutPage {

    WebDriver driver;
    WebDriverWait wait;

    // Billing Details – Step 2
    private final By newAddressRadio       = By.cssSelector("input[name='payment_address'][value='new']");
    private final By firstNameInput        = By.id("input-payment-firstname");
    private final By lastNameInput         = By.id("input-payment-lastname");
    private final By address1Input         = By.id("input-payment-address-1");
    private final By cityInput             = By.id("input-payment-city");
    private final By postcodeInput         = By.id("input-payment-postcode");
    private final By countrySelect         = By.id("input-payment-country");
    private final By regionSelect          = By.id("input-payment-zone");
    private final By billingContinueBtn    = By.id("button-payment-address");

    // Shipping Address – Step 3
    private final By shippingAddressPanelLink = By.cssSelector("a[href='#collapse-shipping-address']");
    private final By shippingExistingRadio   = By.cssSelector("input[name='shipping_address'][value='existing']");
    private final By shippingAddressDropdown = By.id("input-shipping-address");
    private final By shippingAddressContinue = By.id("button-shipping-address");

    // Delivery Method – Step 4
    private final By shippingMethodPanelLink = By.cssSelector("a[href='#collapse-shipping-method']");
    private final By commentTextarea         = By.name("comment");
    private final By shippingMethodContinue  = By.id("button-shipping-method");
    private final By shippingMethodRadio     = By.cssSelector("#collapse-shipping-method .radio input");

    // Payment Method – Step 5
    private final By paymentMethodPanelLink = By.cssSelector("a[href='#collapse-payment-method']");
    private final By termsCheckbox          = By.name("agree");
    private final By paymentContinue        = By.id("button-payment-method");
    private final By warningAlert           = By.cssSelector("div.alert.alert-danger");
    private final By paymentMethodRadio     = By.cssSelector("#collapse-payment-method .radio input");

    // Confirm Order – Step 6
    private final By confirmPanelLink = By.cssSelector("a[href='#collapse-checkout-confirm']");
    private final By confirmOrderBtn  = By.id("button-confirm");
    private final By successHeading   = By.tagName("h1");
    private final By cartTotalItems   = By.id("cart-total");
    private final By orderTotalRow    = By.cssSelector("#collapse-checkout-confirm tfoot tr:last-child td:last-child");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void sleepOneSec() {
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    /** Generic helper to expand a panel by its link. */
    private void openPanel(By panelLink) {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(panelLink));
        String href = link.getAttribute("href");
        String targetId = href.substring(href.indexOf("#"));
        By panelBody = By.cssSelector(targetId);
        WebElement panel = driver.findElement(panelBody);
        if (panel.getAttribute("class") == null || !panel.getAttribute("class").contains("in")) {
            link.click();
            wait.until(ExpectedConditions.attributeContains(panelBody, "class", "in"));
        }
    }

    // -------------------- Billing --------------------

    @Step("Fill new billing address: {fName} {lName}, {addr}, {cit}, {post}, {countryName} / {regionName}")
    public void fillNewBillingAddress(String fName, String lName,
                                      String addr, String cit,
                                      String post, String countryName, String regionName) {
        Log.info("Filling new billing address for " + fName + " " + lName);
        wait.until(ExpectedConditions.elementToBeClickable(newAddressRadio)).click();

        // Wait for the new address fields to become visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));

        driver.findElement(firstNameInput).clear();
        driver.findElement(firstNameInput).sendKeys(fName);
        driver.findElement(lastNameInput).clear();
        driver.findElement(lastNameInput).sendKeys(lName);
        driver.findElement(address1Input).clear();
        driver.findElement(address1Input).sendKeys(addr);
        driver.findElement(cityInput).clear();
        driver.findElement(cityInput).sendKeys(cit);
        driver.findElement(postcodeInput).clear();
        driver.findElement(postcodeInput).sendKeys(post);

        // Select country (triggers AJAX region reload)
        new Select(driver.findElement(countrySelect)).selectByVisibleText(countryName);

        // Wait for the region dropdown to contain the required option,
        // ignoring StaleElementReferenceException while the DOM refreshes.
        WebDriverWait staleSafeWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        staleSafeWait.ignoring(StaleElementReferenceException.class)
                .until(d -> {
                    Select reg = new Select(d.findElement(regionSelect));
                    for (WebElement opt : reg.getOptions()) {
                        if (opt.getText().equals(regionName)) {
                            return true;
                        }
                    }
                    return false;
                });

        new Select(driver.findElement(regionSelect)).selectByVisibleText(regionName);
        Log.info("Billing address filled successfully");
        sleepOneSec();
    }

    @Step("Click Billing Continue")
    public void clickBillingContinue() {
        Log.info("Clicking Billing Continue");
        wait.until(ExpectedConditions.elementToBeClickable(billingContinueBtn)).click();
    }

    // -------------------- Shipping Address --------------------

    @Step("Select shipping address containing: {partialText}")
    public void selectShippingAddress(String partialText) {
        Log.info("Selecting shipping address containing '" + partialText + "'");
        // Ensure the shipping panel is open
        openPanel(shippingAddressPanelLink);

        // Ensure the "I want to use an existing address" radio is selected
        WebElement existingRadio = wait.until(ExpectedConditions.elementToBeClickable(shippingExistingRadio));
        if (!existingRadio.isSelected()) {
            existingRadio.click();
        }

        sleepOneSec(); // wait for dropdown to be ready
        wait.until(ExpectedConditions.presenceOfElementLocated(shippingAddressDropdown));
        Select select = new Select(driver.findElement(shippingAddressDropdown));
        List<WebElement> options = select.getOptions();
        for (WebElement option : options) {
            if (option.getText().contains(partialText)) {
                select.selectByVisibleText(option.getText());
                return;
            }
        }
        // Fallback: select the last option (newest address)
        if (options.size() > 0) {
            select.selectByIndex(options.size() - 1);
        }
    }

    @Step("Click Shipping Address Continue")
    public void clickShippingAddressContinue() {
        Log.info("Clicking Shipping Address Continue");
        wait.until(ExpectedConditions.elementToBeClickable(shippingAddressContinue)).click();
    }

    // -------------------- Delivery Method --------------------

    @Step("Enter delivery comment and continue: {comment}")
    public void enterCommentAndContinue(String comment) {
        Log.info("Waiting for shipping method to load...");
        sleepOneSec(); // allow AJAX to start loading
        // Wait until the radio button in the shipping method panel appears
        wait.until(ExpectedConditions.presenceOfElementLocated(shippingMethodRadio));
        // Ensure panel is open (it should be, but we force)
        openPanel(shippingMethodPanelLink);
        // Fill comment
        WebElement area = wait.until(ExpectedConditions.elementToBeClickable(commentTextarea));
        area.clear();
        area.sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(shippingMethodContinue)).click();
    }

    // -------------------- Payment Method --------------------

    @Step("Agree to Terms & Conditions and continue (retry up to 3 times)")
    public void agreeTermsAndContinue() {
        Log.info("Waiting for payment method to load...");
        sleepOneSec(); // allow AJAX to load
        wait.until(ExpectedConditions.presenceOfElementLocated(paymentMethodRadio));
        openPanel(paymentMethodPanelLink);

        for (int attempt = 1; attempt <= 3; attempt++) {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(termsCheckbox));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
            driver.findElement(paymentContinue).click();

            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(warningAlert));
                Log.warn("Terms warning appeared, retrying... attempt " + attempt);
            } catch (Exception e) {
                Log.info("Terms accepted successfully");
                break;
            }
        }
    }

    // -------------------- Confirm Order --------------------

    @Step("Get confirm order total")
    public String getConfirmTotal() {
        sleepOneSec(); // let panel load
        openPanel(confirmPanelLink);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderTotalRow)).getText();
    }

    @Step("Click Confirm Order")
    public void confirmOrder() {
        Log.info("Confirming order");
        openPanel(confirmPanelLink);
        wait.until(ExpectedConditions.elementToBeClickable(confirmOrderBtn)).click();
    }

    @Step("Check if order placed message is displayed")
    public boolean isOrderPlacedMessageDisplayed() {
        WebElement h1 = wait.until(ExpectedConditions.visibilityOfElementLocated(successHeading));
        return h1.getText().contains("Your order has been placed!");
    }

    @Step("Get mini‑cart items count text")
    public String getCartTotalText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotalItems)).getText();
    }
}