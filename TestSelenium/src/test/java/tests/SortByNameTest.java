package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PhonesAndPDAsPage;

public class SortByNameTest extends BaseTest {
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
