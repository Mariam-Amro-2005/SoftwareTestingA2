package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.ExcelDataReader;
import utils.Log;

@Feature("Checkout")
public class CheckoutTests extends BaseTest {
/*
    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        return ExcelDataReader.getTestData("CheckoutProcess");
    }

    @Test(dataProvider = "checkoutData")
    @Description("Complete end‑to‑end checkout process with product HP LP3065")
    public void completeEndToEndCheckout(String email, String password,
                                         String productName,
                                         String firstName, String lastName,
                                         String address, String city,
                                         String postcode, String country, String region,
                                         String comment) {

        // 1. Login
        HomePage home = new HomePage(driver);
        home.goToLogin();
        LoginPage login = new LoginPage(driver);
        login.login(email, password);

        // 2. Laptops & Notebooks → Show All
        home.goToLaptopsAndNotebooks();

        // 3. Click product and add to cart
        LaptopsPage laptops = new LaptopsPage(driver);
        laptops.clickHPLaptop();
        LaptopProductPage productPage = new LaptopProductPage(driver);
        productPage.addToCart();

        // 4. Verify success message
        String successMsg = productPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Success: You have added"));
        Assert.assertTrue(successMsg.contains(productName));

        // 5. Open mini‑cart and check item
        driver.findElement(By.id("cart")).click();
        ShoppingCartPage cart = new ShoppingCartPage(driver);
        Assert.assertTrue(cart.isProductInMiniCart(productName));

        // 6. Click “View Cart”
        driver.findElement(By.partialLinkText("View Cart")).click();

        // Verify full cart page
        Assert.assertTrue(cart.isProductInCart(productName));
        String unitPrice = cart.getProductUnitPrice(productName);
        Assert.assertFalse(unitPrice.isEmpty());
        Assert.assertTrue(cart.verifyTotalMatches());
        String deliveryDate = cart.getDeliveryDate(productName);
        Assert.assertFalse(deliveryDate.isEmpty());

        // 7. Click Checkout on the full cart page
        driver.findElement(By.cssSelector("a.btn.btn-primary[href*='checkout/checkout']")).click();

        // 8. Fill billing details
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillNewBillingAddress(firstName, lastName, address, city,
                postcode, country, region);
        checkout.clickBillingContinue();

        // 9. Select shipping address (partial text = firstName + lastName)
        String expectedPartial = firstName + lastName;
        checkout.selectShippingAddress(expectedPartial);
        checkout.clickShippingAddressContinue();

        // 10-13. Delivery method: add comment and continue
        checkout.enterCommentAndContinue(comment);

        // 14. Payment method: agree to Terms & Conditions
        checkout.agreeTermsAndContinue();

        // 15-16. Verify confirm total
        String confirmTotal = checkout.getConfirmTotal();
        Assert.assertTrue(confirmTotal.contains("$"));

        // 17. Confirm the order
        checkout.confirmOrder();

        // 18. Success message and empty cart
        Assert.assertTrue(checkout.isOrderPlacedMessageDisplayed());
        String cartText = checkout.getCartTotalText();
        Assert.assertTrue(cartText.contains("0 item(s)"));

        // 19. Logout
        AccountPage account = new AccountPage(driver);
        account.logout();
        Log.info("Checkout test completed successfully");
    }*/
}