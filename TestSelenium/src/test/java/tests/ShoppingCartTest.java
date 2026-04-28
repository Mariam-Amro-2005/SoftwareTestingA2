package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class ShoppingCartTest extends BaseTest {

    @Test
    public void testAddMultipleItemsToCart() {
        HomePage home = new HomePage(driver);

        //1- Login by any valid user
         home.goToLogin();
        LoginPage login = new LoginPage(driver);
        login.login("user22222@gmail.com", "12345");
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }


        //2- Click on "Tablets"
        home.goToTablets();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        //3- Add "Samsung Galaxy Tab 10.1" to the cart
        TabletsPage tabletsPage = new TabletsPage(driver);
        tabletsPage.addSamsungTabToCart();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        //4- Info message "Success: You have added Samsung Galaxy Tab 10.1 to your shopping cart!"

        String successMsg = tabletsPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("Success: You have added Samsung Galaxy Tab 10.1"));

        //5- Open shopping cart and check on the item added & it's price
        home.openShoppingCart();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        Assert.assertTrue(cartPage.isProductInCart("Samsung Galaxy Tab 10.1"), "Samsung Tablet not found in cart!");

        String unitPrice = cartPage.getProductUnitPrice("Samsung Galaxy Tab 10.1");
        System.out.println("Retrieved unit price: '" + unitPrice + "'");
        Assert.assertEquals(unitPrice, "$241.99", "Unit price doesn't match!");

        // 6- Go to "Laptops" & Add  "HP LP3065" laptop

        home.goToLaptopsAndNotebooks();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        LaptopsPage laptopsPage = new LaptopsPage(driver);
        laptopsPage.clickHPLaptop();
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        //7- Change the delivery date  & add it to the shopping cart
        LaptopProductPage laptopProductPage = new LaptopProductPage(driver);
        String deliveryDate = "2026-05-15";
        laptopProductPage.setDeliveryDate(deliveryDate);
        laptopProductPage.addToCart();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        String successmsg = laptopProductPage.getSuccessMessage();
        Assert.assertTrue(successmsg.contains("Success: You have added HP LP3065"));

        //8- Open the shopping cart to check on the item and it's details (delivery date)

        home.openShoppingCart();
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }

        Assert.assertTrue(cartPage.isProductInCart("HP LP3065"), "HP Laptop not found in cart!");
        String actualDelivery = cartPage.getDeliveryDate("HP LP3065");
        Assert.assertTrue(actualDelivery.contains(deliveryDate) || actualDelivery.contains("15/05/2026"),
                "Delivery date mismatch! Expected: " + deliveryDate + " but found: " + actualDelivery);
        //9- check on the "Total" to  be equal the total price of the items
        boolean totalMatches = cartPage.verifyTotalMatches();
        Assert.assertTrue(totalMatches, "Total doesn't match sum of items!");

        //10- Log out
        AccountPage account = new AccountPage(driver);
        account.logout();
    }
}



