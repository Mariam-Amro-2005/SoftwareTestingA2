package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;

public class SearchTest extends BaseTest {

    @Test
    public void testSearchAppleInComponentsWithSubCategories() {
        HomePage home = new HomePage(driver);        LoginPage loginPage;

        SearchPage searchPage = new SearchPage(driver);
        //1- Login by any valid user
        login();

        //2- Click on "Search" icon


        home.clickSearchIcon();

        //3- Enter "Apple" in Search Keyword
        searchPage.enterKeyword("Apple");

        //4- Choose "components"
        searchPage.selectCategory("Components");

        searchPage.clickSearchButton();

        //5- No products found

        Assert.assertTrue(
                searchPage.isNoResultMessageDisplayed(),
                "Expected 'no products found' message when searching Apple in Components (no subcategories)"
        );

        //6- Check on "Search in subCatergories"
        searchPage.checkSearchInSubCategories();

        searchPage.clickSearchButton();

        //7- "Apple Cinema 30" displayed

        Assert.assertTrue(
                searchPage.isProductDisplayed("Apple Cinema 30"),
                "Expected 'Apple Cinema 30' to appear after enabling Search in subcategories"
        );

        //8- Log out
        logout();
    }


}