package tests;

import base.BaseTest;
import pages.*;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.CSVDataReader;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public class SearchTeasts extends BaseTest {

    private static final String TEST_DATA_PATH =
            "src/test/resources/search_data.csv";

    @DataProvider(name = "searchData")
    public Object[][] getSearchData() throws IOException, CsvException {
        return CSVDataReader.getTestData(TEST_DATA_PATH);
    }

    @Test(dataProvider = "searchData")
    public void searchByName(String[] data) {

        String testName = data[0];
        String keyword = data[1];

        System.out.println("Running test: " + testName);

        // 1. Login
        login();

        HomePage home = new HomePage(driver);

        // 2. Search (NO hardcoded value)
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