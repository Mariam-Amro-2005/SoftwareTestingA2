package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DesktopsPage;
import pages.HomePage;
import utils.ExcelDataReader;

public class CurrencyTest extends BaseTest {

    @DataProvider(name = "test-data")
    public Object[][] getCurrencyData() {
        return ExcelDataReader.getTestData("CurrencyData");
    }

    @Test(dataProvider = "test-data")
    public void testCurrencyChangeOnDesktopsPage(String testName,
                                                 String initialCurrency,
                                                 String changedCurrency) {

        System.out.println("Running test: " + testName);

        login();

        HomePage homePage = new HomePage(driver);
        homePage.goToAllDesktops();

        DesktopsPage desktopsPage = new DesktopsPage(driver);

        // Default currency check
        Assert.assertTrue(
                desktopsPage.arePricesDisplayedIn(initialCurrency),
                "Prices should be in default currency: " + initialCurrency
        );

        // Change currency
        homePage.changeCurrencyToEuro();

        // Validate updated currency
        Assert.assertTrue(
                desktopsPage.arePricesDisplayedIn(changedCurrency),
                "Prices should change to: " + changedCurrency
        );

        Assert.assertEquals(
                homePage.getActiveCurrencySymbol(),
                changedCurrency,
                "Currency button mismatch"
        );

        logout();
    }
}