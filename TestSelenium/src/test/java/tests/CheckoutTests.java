package tests;

import base.BaseTest;
import pages.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTests extends BaseTest {

    @Test
    public void completeEndToEndCheckout() {
        // 1. Login
        HomePage home = new HomePage(driver);
        home.goToLogin();
        LoginPage login = new LoginPage(driver);
        login.login("alice05@example.com", "test");

        // 2. Laptops & Notebooks → Show All
        home.goToLaptopsAndNotebooks();

        // 3. Click HP LP3065 and add to cart (leave default date & qty)
        LaptopsPage laptopsPage = new LaptopsPage(driver);
        laptopsPage.clickHPLaptop();
        LaptopProductPage productPage = new LaptopProductPage(driver);
        productPage.addToCart();

        // 4. Verify success message
        String successMsg = productPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Success: You have added"),
                "Add to cart success message not displayed");
        Assert.assertTrue(successMsg.contains("HP LP3065"),
                "Product name missing");

        // ********** ADDED WAIT **********
        try {
            Thread.sleep(2000);   // wait for mini-cart to fully load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 5. Open mini-cart and check item presence (via ShoppingCartPage)
        driver.findElement(By.id("cart")).click();
        ShoppingCartPage cart = new ShoppingCartPage(driver);
        Assert.assertTrue(cart.isProductInMiniCart("HP LP3065"),
                "HP LP3065 not found in mini cart");

        // 6. Click “View Cart” inside mini cart (go to full cart page)
        driver.findElement(By.xpath("//a[contains(@href,'checkout/cart') and contains(text(),'View Cart')]")).click();

        // Now on full shopping cart page – verify product, price, delivery date
        Assert.assertTrue(cart.isProductInCart("HP LP3065"));
        String unitPrice = cart.getProductUnitPrice("HP LP3065");
        Assert.assertFalse(unitPrice.isEmpty(), "Unit price should not be empty");
        Assert.assertTrue(cart.verifyTotalMatches(), "Cart totals do not match");
        String deliveryDate = cart.getDeliveryDate("HP LP3065");
        Assert.assertEquals(deliveryDate, "2011-04-22", "Delivery date mismatch");

        // 7. Click Checkout
        driver.findElement(By.xpath("//a[contains(@href,'checkout/checkout') and contains(text(),'Checkout')]")).click();

        // 8. Fill billing details as a NEW address
        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.fillNewBillingAddress(
                "Alice", "Test",
                "123 Main St", "New York",
                "10001", "United States", "New York"
        );
        checkout.clickBillingContinue();

        // 9. Shipping address: choose the new address (by partial text "Alice Test")
        checkout.selectShippingAddressContaining("Alice Test");
        checkout.clickShippingAddressContinue();

        // 10-13. Delivery method: add comment and continue
        checkout.enterCommentAndContinue("Leave at the front door");

        // 14. Payment method: agree to Terms & Conditions (retry loop)
        checkout.agreeTermsAndContinue();

        // 15-16. Confirm order section – verify total contains currency symbol
        // (Total should be: product price + flat shipping $8.00)
        String confirmTotal = checkout.getConfirmTotal();
        Assert.assertTrue(confirmTotal.contains("$"), "Confirm total is missing currency symbol");

        // 17. Confirm the order
        checkout.confirmOrder();

        // 18. Check success message and cart is empty
        Assert.assertTrue(checkout.isOrderPlacedMessageDisplayed(),
                "Order placed message not shown");
        String cartText = checkout.getCartTotalText();
        Assert.assertTrue(cartText.contains("0 item(s)"),
                "Cart should be empty after order");

        // 19. Logout
        AccountPage account = new AccountPage(driver);
        account.logout();
    }
}