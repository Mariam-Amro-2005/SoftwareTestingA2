package tests;

import base.BaseTest;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.CSVDataReader;

import java.io.IOException;

public class SearchTest extends BaseTest {

    private static final String SEARCH_SUBCAT_CSV = "src/test/resources/search_subcategories.csv";

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws IOException, CsvException {
        return CSVDataReader.getTestData(SEARCH_SUBCAT_CSV);
    }

    @Test(dataProvider = "searchData")
    public void testSearchWithSubCategories(String[] data) {
        String testName = data[0];
        String keyword = data[1];
        String category = data[2];
        String expectNoResultInitially = data[3];
        String expectedProduct = data[4];
        String enableSubCategorySearch = data[5];

        HomePage home = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);

        System.out.println("Running test: " + testName);

        login();

        home.clickSearchIcon();
        searchPage.enterKeyword(keyword);
        searchPage.selectCategory(category);
        searchPage.clickSearchButton();

        if (Boolean.parseBoolean(expectNoResultInitially)) {
            Assert.assertTrue(searchPage.isNoResultMessageDisplayed(),
                    "Expected 'no products found' message");
        }

        if (Boolean.parseBoolean(enableSubCategorySearch)) {
            searchPage.checkSearchInSubCategories();
            searchPage.clickSearchButton();
        }

        Assert.assertTrue(searchPage.isProductDisplayed(expectedProduct),
                "Expected product '" + expectedProduct + "' to be displayed");

        logout();
    }
}