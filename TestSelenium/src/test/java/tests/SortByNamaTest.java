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

        home.goToPhonesAndPDAs();


        PhonesAndPDAsPage phonesPage = new PhonesAndPDAsPage(driver);
        phonesPage.sortByNameAscending();
        phonesPage.sortByNameDescending();

        logout();

    }
}
