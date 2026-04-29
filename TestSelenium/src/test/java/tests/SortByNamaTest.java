package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import pages.PhonesAndPDAsPage;

public class SortByNamaTest extends BaseTest {
    @Test
    public void sortByName(){

        HomePage home = new HomePage(driver);
        login();
        driver.get("http://tutorialsninja.com/demo/index.php?route=common/home");

        home.goToPhonesAndPDAs();


        PhonesAndPDAsPage phonesPage = new PhonesAndPDAsPage(driver);
        phonesPage.sortByNameAscending();
        phonesPage.sortByNameDescending();

        logout();

    }
}
