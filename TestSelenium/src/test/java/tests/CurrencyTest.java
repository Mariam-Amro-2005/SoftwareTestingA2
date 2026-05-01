package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DesktopsPage;
import pages.HomePage;
import utils.CSVDataReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public class CurrencyTest extends BaseTest {

    private static final String TEST_DATA_PATH =
            "src/test/resources/currency_test_data.csv";

    @DataProvider(name = "currencyData")
    public Object[][] getCurrencyData() throws IOException, CsvException {
        return CSVDataReader.getTestData(TEST_DATA_PATH);
    }

    @Test(dataProvider = "currencyData")
    public void testCurrencyChangeOnDesktopsPage(String[] data) {

        String testName = data[0];
        String initialCurrency = data[1];
        String changedCurrency = data[2];

        System.out.println("Running test: " + testName);

        // Login
        login();

        HomePage homePage = new HomePage(driver);

        // Navigate
        homePage.goToAllDesktops();

        DesktopsPage desktopsPage = new DesktopsPage(driver);

        // Default currency check (data-driven)
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

        // Logout
        logout();
    }
}