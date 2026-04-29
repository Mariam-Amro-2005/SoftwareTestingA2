package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DesktopsPage;
import pages.HomePage;

public class CurrencyTest extends BaseTest {

    @Test
    public void testCurrencyChangeOnDesktopsPage() {

        //Login
        login();

        HomePage homePage = new HomePage(driver);

        //2- Click on "Desktops" ,"Show all Desktops"

        homePage.goToAllDesktops();

        DesktopsPage desktopsPage = new DesktopsPage(driver);

        //3- By default the Prices will displayed in "$ dollar"

        Assert.assertTrue(
                desktopsPage.arePricesDisplayedIn("$"),
                "All prices should be in $ by default"
        );

        //4- Change the Currency from the upper Right handSide to "€ Euro"

        homePage.changeCurrencyToEuro();
        //5- The Prices changed accordingly
        Assert.assertTrue(
                desktopsPage.arePricesDisplayedIn("€"),
                "All prices should change to € after selecting Euro currency"
        );

        Assert.assertEquals(
                homePage.getActiveCurrencySymbol(), "€",
                "Currency button should show € after switching to Euro"
        );

        //Logout
        logout();
    }
}