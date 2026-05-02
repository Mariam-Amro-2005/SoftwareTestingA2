package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.CSVDataReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.time.Duration;

public class CheckoutTest extends BaseTest {

    private static final String TEST_DATA_PATH = "src/test/resources/checkout_test_data.csv";

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() throws IOException, CsvException {
        return CSVDataReader.getTestData(TEST_DATA_PATH);
    }

    @Test(dataProvider = "checkoutData")
    public void testFullCheckoutProcess(String[] data) {

        // Test Data
        String testName = data[0];
        String email = data[1];
        String password = data[2];
        String laptopName = data[3];
        String deliveryDate = data[4];

        // Billing & Shipping Address Details
        String firstname = data[5];
        String lastname = data[6];
        String company = data[7];
        String address1 = data[8];
        String address2 = data[9];        // can be empty
        String city = data[10];
        String postcode = data[11];
        String countryId = data[12];      // e.g. "63" for Egypt
        String zoneId = data[13];         // zone id after country selection
        String shippingPostcode = data[14];
        String comment = data[15];

        System.out.println("Running Checkout Test: " + testName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        // Step 1: Login with valid user
        home.goToLogin();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email")));
        login.login(email, password);

        // Step 2-3: Go to Laptops → Add HP LP3065
        home.goToLaptopsAndNotebooks();

        LaptopsPage laptopsPage = new LaptopsPage(driver);
        laptopsPage.clickHPLaptop();

        LaptopProductPage laptopProductPage = new LaptopProductPage(driver);
        laptopProductPage.setDeliveryDate(deliveryDate);
        laptopProductPage.addToCart();

        Assert.assertTrue(laptopProductPage.getSuccessMessage().contains("Success"),
                "Failed to add HP LP3065 to cart");

        // Step 5-6: Open Shopping Cart and verify item
        home.openShoppingCart();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));

        Assert.assertTrue(cartPage.isProductInCart(laptopName),
                "HP LP3065 was not found in the shopping cart");

        // Step 7: Click Checkout
        cartPage.clickCheckout();


        CheckoutPage checkout = new CheckoutPage(driver);

        // Step 8-10: Billing Address - Use NEW Address
        checkout.selectNewBillingAddress();
        checkout.fillNewBillingAddress(firstname, lastname, company, address1,
                city, postcode, countryId, zoneId);
        checkout.continueBillingAddress();

        // Step 11: Shipping Address - Use NEW Address
        checkout.selectNewShippingAddress();
        checkout.fillNewShippingAddress(firstname, lastname, company, address1,
                city, shippingPostcode, countryId, zoneId);
        checkout.continueShippingAddress();

        // Step 12-13: Delivery Method
        checkout.selectFlatShippingMethod();
        checkout.enterOrderComment(comment);
        checkout.continueShippingMethod();

        // Step 14: Payment Method + Agree to Terms
        checkout.checkTermsAndConditions();
        checkout.continuePaymentMethod();

        // Step 15-17: Confirm Order
        checkout.confirmOrder();

        // Step 18: Verify Order Success
        Assert.assertTrue(checkout.isOrderPlacedSuccessfully(),
                "Order was not placed successfully! Success message not displayed.");

        // Step 19: Logout
        AccountPage accountPage = new AccountPage(driver);
        accountPage.logout();

        System.out.println("✅ Checkout Test '" + testName + "' PASSED successfully!");
    }
}