package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;
import utils.ExcelDataReader;

public class SearchTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return ExcelDataReader.getTestData("SearchData");
    }

    @Test(dataProvider = "searchData")
    public void testSearchWithSubCategories(String testName,
                                            String keyword,
                                            String category,
                                            String expectNoResultInitially,
                                            String expectedProduct,
                                            String enableSubCategorySearch) {

        HomePage home = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        System.out.println("Running test: " + testName);

        // 1. Login
        login();

        // 2. Click on Search icon
        home.clickSearchIcon();

        // 3. Enter keyword
        searchPage.enterKeyword(keyword);

        // 4. Select category
        searchPage.selectCategory(category);
        searchPage.clickSearchButton();

        // 5. Verify no result message (initially without sub-categories)
        if (Boolean.parseBoolean(expectNoResultInitially)) {
            Assert.assertTrue(searchPage.isNoResultMessageDisplayed(),
                    "Expected 'no products found' message when searching '" + keyword +
                            "' in '" + category + "' without subcategories");
        }

        // 6. Enable "Search in subcategories" if required
        if (Boolean.parseBoolean(enableSubCategorySearch)) {
            searchPage.checkSearchInSubCategories();
            searchPage.clickSearchButton();
        }

        // 7. Verify product is displayed
        Assert.assertTrue(searchPage.isProductDisplayed(expectedProduct),
                "Expected product '" + expectedProduct + "' to be displayed");

        // 8. Logout
        logout();
    }
}