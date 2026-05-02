package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SearchPage;
import utils.ExcelDataReader;

public class SearchTests extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() {
        return ExcelDataReader.getTestData("SearchData");   // Sheet name
    }

    @Test(dataProvider = "searchData")
    public void searchByName(String testName, String keyword) {

        System.out.println("Running test: " + testName);

        // 1. Login
        login();

        HomePage home = new HomePage(driver);

        // 2. Search
        home.search(keyword);

        // 3. Validate results
        SearchPage searchPage = new SearchPage(driver);
        Assert.assertTrue(
                searchPage.resultMAtching(keyword),
                "Search results do not match: " + keyword
        );

        // 4. Logout
        logout();
    }
}