package tests;

import base.BaseTest;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DesktopsPage;
import pages.HomePage;
import utils.CSVDataReader;

import java.io.IOException;

public class CurrencyTest extends BaseTest {

    private static final String CURRENCY_CSV = "src/test/resources/currency_data.csv";

    @DataProvider(name = "test-data")
    public Object[][] getCurrencyData() throws IOException, CsvException {
        return CSVDataReader.getTestData(CURRENCY_CSV);
    }

    @Test(dataProvider = "test-data")
    public void testCurrencyChangeOnDesktopsPage(String[] data) {
        String testName = data[0];
        String initialCurrency = data[1];
        String changedCurrency = data[2];

        System.out.println("Running test: " + testName);

        login();

        HomePage homePage = new HomePage(driver);
        homePage.goToAllDesktops();

        DesktopsPage desktopsPage = new DesktopsPage(driver);

        Assert.assertTrue(
                desktopsPage.arePricesDisplayedIn(initialCurrency),
                "Prices should be in default currency: " + initialCurrency
        );

        homePage.changeCurrencyToEuro();

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