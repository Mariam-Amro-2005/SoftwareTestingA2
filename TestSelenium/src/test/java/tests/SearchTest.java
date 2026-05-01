package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SearchPage;
import utils.CSVDataReader;
import com.opencsv.exceptions.CsvException;
import java.io.IOException;

public class SearchTest extends BaseTest {

    private static final String TEST_DATA_PATH = "src/test/resources/search_test_data.csv";

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws IOException, CsvException {
        return CSVDataReader.getTestData(TEST_DATA_PATH);
    }

    @Test(dataProvider = "searchData")
    public void testSearchWithSubCategories(String[] data) {

        String testName = data[0];
        String keyword = data[1];
        String category = data[2];
        boolean expectNoResultInitially = Boolean.parseBoolean(data[3]);
        String expectedProduct = data[4];
        boolean enableSubCategorySearch = Boolean.parseBoolean(data[5]);

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
        if (expectNoResultInitially) {
            Assert.assertTrue(searchPage.isNoResultMessageDisplayed(),
                    "Expected 'no products found' message when searching '" + keyword +
                            "' in '" + category + "' without subcategories");
        }

        // 6. Enable "Search in subcategories" if required
        if (enableSubCategorySearch) {
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