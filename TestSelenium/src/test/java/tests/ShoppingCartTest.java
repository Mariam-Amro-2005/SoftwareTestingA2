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

public class ShoppingCartTest extends BaseTest {

    private static final String TEST_DATA_PATH =
            "src/test/resources/cart_test_data.csv";

    @DataProvider(name = "cartData")
    public Object[][] getCartData() throws IOException, CsvException {
        return CSVDataReader.getTestData(TEST_DATA_PATH);
    }

    @Test(dataProvider = "cartData")
    public void testAddMultipleItemsToCart(String[] data) {

        String testName = data[0];
        String email = data[1];
        String password = data[2];
        String tabletName = data[3];
        String laptopName = data[4];
        String deliveryDate = data[5];
        String expectedTabletPrice = data[6];

        System.out.println("Running test: " + testName);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        HomePage home = new HomePage(driver);
        LoginPage login = new LoginPage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);


        home.goToLogin();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-email")));

        login.login(email, password);


        home.goToTablets();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-layout")
        ));

        TabletsPage tabletsPage = new TabletsPage(driver);
        tabletsPage.addSamsungTabToCart();

        Assert.assertTrue(
                tabletsPage.getSuccessMessage().contains("Success"),
                "Tablet not added successfully"
        );


        home.openShoppingCart();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));

        Assert.assertTrue(
                cartPage.isProductInCart(tabletName),
                "Tablet not found in cart"
        );

        Assert.assertEquals(
                cartPage.getProductUnitPrice(tabletName),
                expectedTabletPrice,
                "Tablet price mismatch"
        );


        home.goToLaptopsAndNotebooks();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(laptopName)
        ));

        LaptopsPage laptopsPage = new LaptopsPage(driver);
        laptopsPage.clickHPLaptop();

        LaptopProductPage laptopProductPage = new LaptopProductPage(driver);
        laptopProductPage.setDeliveryDate(deliveryDate);
        laptopProductPage.addToCart();

        Assert.assertTrue(
                laptopProductPage.getSuccessMessage().contains("Success"),
                "Laptop not added successfully"
        );


        home.openShoppingCart();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("content")));

        Assert.assertTrue(
                cartPage.isProductInCart(laptopName),
                "Laptop not found in cart"
        );

        String actualDelivery = cartPage.getDeliveryDate(laptopName);

        Assert.assertTrue(
                actualDelivery.contains(deliveryDate),
                "Delivery date mismatch"
        );


        Assert.assertTrue(
                cartPage.verifyTotalMatches(),
                "Cart total mismatch"
        );


        AccountPage account = new AccountPage(driver);
        account.logout();
    }
}